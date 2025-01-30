// Copyright (c) 2024 Dr. Matthias Hölzl. All rights reserved.

package invoice.v0;

import invoice.data.Item;
import invoice.data.Order;
import invoice.data.Product;

import java.util.Map;

public class InvoiceGenerator {
    public static String generateInvoice(Order order, Map<String, Product> products) {
        double produceTotal = 0.0;
        int produceItems = 0;
        double dairyTotal = 0.0;
        int dairyItems = 0;

        StringBuilder result = new StringBuilder();
        result.append("Order for ").append(order.getCustomerName()).append(":\n");

        for (Item entry : order.getItems()) {
            String name = entry.getName();
            int quantity = entry.getQuantity();

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
                produceItems += quantity;
                discount = quantity >= 4 ? 0.1 : 0.0;
                discountedPrice = itemPrice * (1 - discount);
                price = discountedPrice * quantity;
                produceTotal += price;
                result.append(String.format("  - %-12s %4d à %.2f = $%.2f",
                    name + ":", quantity, itemPrice, price));
                if (discount > 0) {
                    result.append(String.format(" (discount: $%.2f)",
                        itemPrice * quantity - price));
                }
                result.append("\n");
            } else if (department.equals("Dairy")) {
                dairyItems += quantity;
                discount = quantity >= 5 ? 0.05 : 0.0;
                discountedPrice = itemPrice * (1 - discount);
                price = discountedPrice * quantity;
                dairyTotal += price;
                result.append(String.format("  - %-12s %4d à %.2f = $%.2f",
                    name + ":", quantity, itemPrice, price));
                if (discount > 0) {
                    result.append(String.format(" (discount: $%.2f)",
                        itemPrice * quantity - price));
                }
                result.append("\n");
            } else {
                throw new RuntimeException("Unknown department: " + department);
            }
        }

        double volumeDiscount = 0.0;
        if (produceItems >= 10) {
            volumeDiscount += produceTotal * 0.05;
        }
        if (dairyItems >= 10) {
            volumeDiscount += dairyTotal * 0.1;
        }
        double total = produceTotal + dairyTotal;

        if (volumeDiscount > 0) {
            result.append(String.format("Subtotal:        $%.2f\n", total));
            result.append(String.format("Volume discount: $%.2f\n", volumeDiscount));
            result.append(String.format("Total:           $%.2f", total - volumeDiscount));
        } else {
            result.append(String.format("Total: $%.2f", total));
        }
        return result.toString();
    }
}

