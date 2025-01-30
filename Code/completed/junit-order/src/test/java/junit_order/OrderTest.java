package junit_order;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void getItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Apple", 1.25));
        items.add(new Item("Banana", 1.5));

        Order unit = new Order(items);
        assertEquals(items, unit.getItems());
        assertNotSame(items, unit.getItems());
    }

    @Test
    void getTotal() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Apple", 1.25));
        items.add(new Item("Banana", 1.5));

        Order unit = new Order(items);
        assertEquals(2.75, unit.getTotal());
    }

    @Test
    void testToString() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Apple", 1.25));
        items.add(new Item("Banana", 1.5));

        Order unit = new Order(items);
        assertEquals(unit.toString(), "Order([Item(Apple, 1,25), Item(Banana, 1,50)]), total = 2,75");
    }
}