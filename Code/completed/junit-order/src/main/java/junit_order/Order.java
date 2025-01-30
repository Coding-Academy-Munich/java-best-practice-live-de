package junit_order;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<Item> items;

    public Order(List<Item> itemList) {
        items = new ArrayList<>(itemList);
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return String.format("Order(%s), total = %.2f", items.toString(), getTotal());
    }
}


