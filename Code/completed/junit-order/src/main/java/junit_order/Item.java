package junit_order;

public class Item {
    private final String name;
    private double price; // always positive

    public Item(String name, double price) {
        this.name = name;
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double value) {
        if (value < 0) {
            value = -value;
        }
        price = value;
    }

    @Override
    public String toString() {
        return String.format("Item(%s, %.2f)", name, price);
    }
}
