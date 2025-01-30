// Copyright (c) 2024 Dr. Matthias Hölzl. All rights reserved.

package invoice.v2;

import invoice.data.Item;
import invoice.data.Order;
import invoice.data.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvoiceGeneratorV2Test {
    private static Map<String, Product> products;

    @BeforeAll
    static void setUp() {
        products = new HashMap<>();
        products.put("Lettuce", new Product(1.00, "Produce"));
        products.put("Onions", new Product(1.10, "Produce"));
        products.put("Tomatoes", new Product(1.00, "Produce"));
        products.put("Milk", new Product(2.50, "Dairy"));
        products.put("Cheese", new Product(3.00, "Dairy"));
        products.put("Ice Cream", new Product(3.50, "Dairy"));
    }

    @Test
    void testJohnWithTwoLettuces() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Lettuce", 2));
        Order order = new Order("John", items);
        String expected = """
                Order for John:
                  - Lettuce:        2 à 1,00 = $2,00
                Total: $2,00""";
        assertEquals(expected, InvoiceGenerator.generateInvoice(order, products));
    }

    @Test
    void testMaryWithFiveLettuces() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Lettuce", 5));
        Order order = new Order("Mary", items);
        String expected = """
                Order for Mary:
                  - Lettuce:        5 à 1,00 = $4,50 \
                (discount: $0,50)
                Total: $4,50""";
        assertEquals(expected, InvoiceGenerator.generateInvoice(order, products));
    }

    @Test
    void testJillWithTenLettuces() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Lettuce", 10));
        Order order = new Order("Jill", items);
        String expected = """
                Order for Jill:
                  - Lettuce:       10 à 1,00 = $9,00 \
                (discount: $1,00)
                Subtotal:        $9,00
                Volume discount: $0,45
                Total:           $8,55""";
        assertEquals(expected, InvoiceGenerator.generateInvoice(order, products));
    }

    @Test
    void testJamesWithMixedProduce() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Lettuce", 4));
        items.add(new Item("Onions", 3));
        items.add(new Item("Tomatoes", 2));
        Order order = new Order("James", items);
        String expected = """
                Order for James:
                  - Lettuce:        4 à 1,00 = $3,60 (discount: $0,40)
                  - Onions:         3 à 1,10 = $3,30
                  - Tomatoes:       2 à 1,00 = $2,00
                Total: $8,90""";
        assertEquals(expected, InvoiceGenerator.generateInvoice(order, products));
    }

    @Test
    void testBobWithMilkAndCheese() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Milk", 2));
        items.add(new Item("Cheese", 1));
        Order order = new Order("Bob", items);
        String expected = """
                Order for Bob:
                  - Milk:           2 à 2,50 = $5,00
                  - Cheese:         1 à 3,00 = $3,00
                Total: $8,00""";
        assertEquals(expected, InvoiceGenerator.generateInvoice(order, products));
    }

    @Test
    void testAliceWithIceCreamAndCheese() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Ice Cream", 5));
        items.add(new Item("Cheese", 1));
        Order order = new Order("Alice", items);
        String expected = """
                Order for Alice:
                  - Ice Cream:      5 à 3,50 = $16,63 (discount: $0,88)
                  - Cheese:         1 à 3,00 = $3,00
                Total: $19,63""";
        assertEquals(expected, InvoiceGenerator.generateInvoice(order, products));
    }

    @Test
    void testSamWithMixedDairy() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Milk", 4));
        items.add(new Item("Ice Cream", 4));
        items.add(new Item("Cheese", 2));
        Order order = new Order("Sam", items);
        String expected = """
                Order for Sam:
                  - Milk:           4 à 2,50 = $10,00
                  - Ice Cream:      4 à 3,50 = $14,00
                  - Cheese:         2 à 3,00 = $6,00
                Subtotal:        $30,00
                Volume discount: $3,00
                Total:           $27,00""";
        assertEquals(expected, InvoiceGenerator.generateInvoice(order, products));
    }

    @Test
    void testJanesMilkyWay() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Cheese", 8));
        items.add(new Item("Ice Cream", 8));
        items.add(new Item("Milk", 2));
        Order order = new Order("Jane's Milky Way", items);
        String expected = """
                Order for Jane's Milky Way:
                  - Cheese:         8 à 3,00 = $22,80 (discount: $1,20)
                  - Ice Cream:      8 à 3,50 = $26,60 (discount: $1,40)
                  - Milk:           2 à 2,50 = $5,00
                Subtotal:        $54,40
                Volume discount: $5,44
                Total:           $48,96""";
        assertEquals(expected, InvoiceGenerator.generateInvoice(order, products));
    }
}