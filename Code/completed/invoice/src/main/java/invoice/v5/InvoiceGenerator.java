// Copyright (c) 2024 Dr. Matthias Hölzl. All rights reserved.

package invoice.v5;

import invoice.data.Item;
import invoice.data.Order;
import invoice.data.Product;

import java.util.HashMap;
import java.util.Map;

class Department {
    String name;
    double discount;
    int discountThreshold;
    double VolumeDiscount;

    public Department(
            String name,
            double discount,
            int discountThreshold,
            double volumeDiscount) {
        this.name = name;
        this.discount = discount;
        this.discountThreshold = discountThreshold;
        VolumeDiscount = volumeDiscount;
    }

    public String getName() {
        return name;
    }

    public double getDiscount() {
        return discount;
    }

    public int getDiscountThreshold() {
        return discountThreshold;
    }

    public double getVolumeDiscount() {
        return VolumeDiscount;
    }
}

class DepartmentStatistics {
    private double total = 0.0;
    private int numItems = 0;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }
}

public class InvoiceGenerator {
    public static String generateInvoice(Order order, Map<String, Product> products) {
        Map<String, DepartmentStatistics> statistics = new HashMap<>();
        statistics.put("Produce", new DepartmentStatistics());
        statistics.put("Dairy", new DepartmentStatistics());

        StringBuilder outputString = new StringBuilder();
        outputString.append("Order for ").append(order.getCustomerName()).append(":\n");

        for (Item lineItem : order.getItems()) {
            addLineForSingleItem(lineItem, products, statistics, outputString);
        }
        addVolumeDiscount(statistics, outputString);

        return outputString.toString();
    }

    private static void addLineForSingleItem(
            Item lineItem,
            Map<String, Product> products,
            Map<String, DepartmentStatistics> statistics,
            StringBuilder outputString) {
        String name = lineItem.getName();
        int quantity = lineItem.getQuantity();

        Product product = products.get(name);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        double itemPrice = product.getPrice();
        String departmentName = product.getDepartment();
        double discount;
        double discountedPrice;
        double price;

        DepartmentStatistics departmentStatistics = statistics.get(departmentName);
        Department department = departments.get(departmentName);
        departmentStatistics.setNumItems(departmentStatistics.getNumItems() + quantity);
        discount = quantity >= department.getDiscountThreshold() ?
                department.getDiscount() : 0.0;
        discountedPrice = itemPrice * (1 - discount);
        price = discountedPrice * quantity;
        departmentStatistics.setTotal(departmentStatistics.getTotal() + price);
        outputString.append(
                String.format("  - %-12s %4d à %.2f = $%.2f", name + ":", quantity,
                        itemPrice, price));
        if (discount > 0) {
            outputString.append(
                    String.format(" (discount: $%.2f)", itemPrice * quantity - price));
        }
        outputString.append("\n");
    }

    private static void addVolumeDiscount(
            Map<String, DepartmentStatistics> departmentStatistics,
            StringBuilder outputString) {
        double volumeDiscount = 0.0;
        for (String name : departmentStatistics.keySet()) {
            Department dep = departments.get(name);
            DepartmentStatistics stats = departmentStatistics.get(name);
            if (stats.getNumItems() >= 10) {
                volumeDiscount += stats.getTotal() * dep.getVolumeDiscount();
            }
        }
        double total = departmentStatistics.values().stream().mapToDouble(
                DepartmentStatistics::getTotal).sum();

        if (volumeDiscount > 0) {
            outputString.append(String.format("Subtotal:        $%.2f\n", total));
            outputString.append(
                    String.format("Volume discount: $%.2f\n", volumeDiscount));
            outputString.append(
                    String.format("Total:           $%.2f", total - volumeDiscount));
        } else {
            outputString.append(String.format("Total: $%.2f", total));
        }
    }

    private static final Map<String, Department> departments = new HashMap<>();

    static {
        Department produce = new Department("Produce", 0.1, 4, 0.05);
        Department dairy = new Department("Dairy", 0.05, 5, 0.1);
        departments.put(produce.getName(), produce);
        departments.put(dairy.getName(), dairy);
    }

}