package junit_order;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void getName() {
        Item unit = new Item("Apple", 1.2);
        assertEquals("Apple", unit.getName());
    }

    @Test
    void getPrice() {
        Item unit = new Item("Apple", 1.2);
        assertEquals(1.2, unit.getPrice());
    }

    @Test
    void setPriceForPositiveValue() {
        Item unit = new Item("Apple", 1.2);
        unit.setPrice(1.5);
        assertEquals(1.5, unit.getPrice());
    }

    @Test
    void setPriceForNegativeValue() {
        Item unit = new Item("Apple", 1.2);
        unit.setPrice(-1.5);
        assertEquals(1.5, unit.getPrice());
    }

    @Test
    void testToString() {
        Item unit = new Item("Apple", 1.2);
        assertEquals("Item(Apple, 1,20)", unit.toString());
    }
}