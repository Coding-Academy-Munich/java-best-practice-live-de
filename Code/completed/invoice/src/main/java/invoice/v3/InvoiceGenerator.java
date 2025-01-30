// Copyright (c) 2024 Dr. Matthias Hölzl. All rights reserved.

package invoice.v3;

import invoice.data.Item;
import invoice.data.Order;
import invoice.data.Product;

import java.util.Map;

class DepartmentStatistics {
    public double produceTotal = 0.0;
    public int produceItems = 0;
    public double dairyTotal = 0.0;
    public int dairyItems = 0;
}

public class InvoiceGenerator {
    public static String generateInvoice(Order order, Map<String, Product> products) {
        DepartmentStatistics departmentStatistics = new DepartmentStatistics();

        StringBuilder outputString = new StringBuilder();
        outputString.append("Order for ").append(order.getCustomerName()).append(":\n");

        for (Item lineItem : order.getItems()) {
            addLineForSingleItem(lineItem, products, departmentStatistics, outputString);
        }
        addVolumeDiscount(departmentStatistics, outputString);

        return outputString.toString();
    }

    private static void addLineForSingleItem(
            Item lineItem, Map<String, Product> products, DepartmentStatistics departmentStatistics,
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
            departmentStatistics.produceItems += quantity;
            discount = quantity >= 4 ? 0.1 : 0.0;
            discountedPrice = itemPrice * (1 - discount);
            price = discountedPrice * quantity;
            departmentStatistics.produceTotal += price;
            outputString.append(
                    String.format("  - %-12s %4d à %.2f = $%.2f", name + ":",
                            quantity, itemPrice, price));
            if (discount > 0) {
                outputString.append(String.format(" (discount: $%.2f)",
                        itemPrice * quantity - price));
            }
            outputString.append("\n");
        } else if (department.equals("Dairy")) {
            departmentStatistics.dairyItems += quantity;
            discount = quantity >= 5 ? 0.05 : 0.0;
            discountedPrice = itemPrice * (1 - discount);
            price = discountedPrice * quantity;
            departmentStatistics.dairyTotal += price;
            outputString.append(
                    String.format("  - %-12s %4d à %.2f = $%.2f", name + ":",
                            quantity, itemPrice, price));
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
            DepartmentStatistics departmentStatistics,
            StringBuilder outputString) {
        double volumeDiscount = 0.0;
        if (departmentStatistics.produceItems >= 10) {
            volumeDiscount += departmentStatistics.produceTotal * 0.05;
        }
        if (departmentStatistics.dairyItems >= 10) {
            volumeDiscount += departmentStatistics.dairyTotal * 0.1;
        }
        double total =
                departmentStatistics.produceTotal + departmentStatistics.dairyTotal;

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