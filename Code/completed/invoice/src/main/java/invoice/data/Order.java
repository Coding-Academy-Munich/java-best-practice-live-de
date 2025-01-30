package invoice.data;

import java.util.List;

public class Order {
    String customerName;
    List<Item> items;

    public Order(String customerName, List<Item> items) {
        this.customerName = customerName;
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Item> getItems() {
        return items;
    }
}
