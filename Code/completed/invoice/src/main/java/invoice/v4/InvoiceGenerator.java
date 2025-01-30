// Copyright (c) 2024 Dr. Matthias Hölzl. All rights reserved.

package invoice.v4;

import invoice.data.Item;
import invoice.data.Order;
import invoice.data.Product;

import java.util.HashMap;
import java.util.Map;

class DepartmentStatistics {
    public double total = 0.0;
    public int numItems = 0;
}

public class InvoiceGenerator {
    public static String generateInvoice(Order order, Map<String, Product> products) {
        Map<String, DepartmentStatistics> statistics = new HashMap<>();
        statistics.put("Produce", new DepartmentStatistics());
        statistics.put("Dairy", new DepartmentStatistics());

        StringBuilder outputString = new StringBuilder();
        outputString.append("Order for ").append(order.getCustomerName()).append(":\n");

        for (Item lineItem : order.getItems()) {
            addLineForSingleItem(
                    lineItem, products, statistics, outputString);
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
            departmentStatistics.numItems += quantity;
            discount = quantity >= 4 ? 0.1 : 0.0;
            discountedPrice = itemPrice * (1 - discount);
            price = discountedPrice * quantity;
            departmentStatistics.total += price;
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
            departmentStatistics.numItems += quantity;
            discount = quantity >= 5 ? 0.05 : 0.0;
            discountedPrice = itemPrice * (1 - discount);
            price = discountedPrice * quantity;
            departmentStatistics.total += price;
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
            Map<String, DepartmentStatistics> departmentStatistics, StringBuilder outputString) {
        double volumeDiscount = 0.0;
        DepartmentStatistics produceStatistics = departmentStatistics.get("Produce");
        DepartmentStatistics dairyStatistics = departmentStatistics.get("Dairy");
        if (produceStatistics.numItems >= 10) {
            volumeDiscount += produceStatistics.total * 0.05;
        }
        if (dairyStatistics.numItems >= 10) {
            volumeDiscount += dairyStatistics.total * 0.1;
        }
        double total =
                produceStatistics.total + dairyStatistics.total;

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