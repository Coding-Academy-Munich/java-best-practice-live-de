// Copyright (c) 2024 Dr. Matthias Hölzl. All rights reserved.

package invoice.data;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class InvoiceData {

    public static Map<String, Product> products = new HashMap<>();
    public static List<Order> orders = new ArrayList<>();

    static {
        products.put("Lettuce", new Product(1.00, "Produce"));
        products.put("Onions", new Product(1.10, "Produce"));
        products.put("Tomatoes", new Product(1.00, "Produce"));
        products.put("Milk", new Product(2.50, "Dairy"));
        products.put("Cheese", new Product(3.00, "Dairy"));
        products.put("Ice Cream", new Product(3.50, "Dairy"));
        products.put("Bread", new Product(1.00, "Bakery"));
        products.put("Cake", new Product(10.00, "Bakery"));
        products.put("Hammer", new Product(10.00, "Hardware"));
        products.put("Nails", new Product(0.10, "Hardware"));
        products.put("Ladder", new Product(100.00, "Hardware"));
        products.put("Drill", new Product(5.00, "Hardware"));

        orders.add(new Order("John", List.of(new Item("Lettuce", 2))));
        orders.add(new Order("Mary", List.of(new Item("Lettuce", 5))));
        orders.add(new Order("Jill", List.of(new Item("Lettuce", 10))));
        orders.add(new Order("James", List.of(
            new Item("Lettuce", 4),
            new Item("Onions", 3),
            new Item("Tomatoes", 2)
        )));
        orders.add(new Order("Bob", List.of(
            new Item("Milk", 2),
            new Item("Cheese", 1)
        )));
        orders.add(new Order("Alice", List.of(
            new Item("Ice Cream", 5),
            new Item("Cheese", 1)
        )));
        orders.add(new Order("Sam", List.of(
            new Item("Milk", 4),
            new Item("Ice Cream", 4),
            new Item("Cheese", 2)
        )));
        orders.add(new Order("Jane's Milky Way", List.of(
            new Item("Cheese", 8),
            new Item("Ice Cream", 8),
            new Item("Milk", 2)
        )));
    }
}