// Copyright (c) 2024 Dr. Matthias Hölzl. All rights reserved.

package invoice.v4a;

import invoice.data.Item;
import invoice.data.Order;
import invoice.data.Product;

import java.util.HashMap;
import java.util.Map;

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
        String department = product.getDepartment();
        double discount;
        double discountedPrice;
        double price;

        if (department.equals("Produce")) {
            DepartmentStatistics departmentStatistics = statistics.get(department);
            departmentStatistics.setNumItems(
                    departmentStatistics.getNumItems() + quantity);
            discount = quantity >= 4 ? 0.1 : 0.0;
            discountedPrice = itemPrice * (1 - discount);
            price = discountedPrice * quantity;
            departmentStatistics.setTotal(departmentStatistics.getTotal() + price);
            outputString.append(
                    String.format("  - %-12s %4d à %.2f = $%.2f", name + ":", quantity,
                            itemPrice, price));
            if (discount > 0) {
                outputString.append(String.format(" (discount: $%.2f)",
                        itemPrice * quantity - price));
            }
            outputString.append("\n");
        } else if (department.equals("Dairy")) {
            DepartmentStatistics departmentStatistics = statistics.get(department);
            departmentStatistics.setNumItems(
                    departmentStatistics.getNumItems() + quantity);
            discount = quantity >= 5 ? 0.05 : 0.0;
            discountedPrice = itemPrice * (1 - discount);
            price = discountedPrice * quantity;
            departmentStatistics.setTotal(departmentStatistics.getTotal() + price);
            outputString.append(
                    String.format("  - %-12s %4d à %.2f = $%.2f", name + ":", quantity,
                            itemPrice, price));
            if (discount > 0) {
                outputString.append(String.format(" (discount: $%.2f)",
                        itemPrice * quantity - price));
            }
            outputString.append("\n");
        } else {
            throw new RuntimeException("Unknown department: " + department);
        }
    }

    private static void addVolumeDiscount(
            Map<String, DepartmentStatistics> departmentStatistics,
            StringBuilder outputString) {
        double volumeDiscount = 0.0;
        DepartmentStatistics produceStatistics = departmentStatistics.get("Produce");
        DepartmentStatistics dairyStatistics = departmentStatistics.get("Dairy");
        if (produceStatistics.getNumItems() >= 10) {
            volumeDiscount += produceStatistics.getTotal() * 0.05;
        }
        if (dairyStatistics.getNumItems() >= 10) {
            volumeDiscount += dairyStatistics.getTotal() * 0.1;
        }
        double total = produceStatistics.getTotal() + dairyStatistics.getTotal();

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
}