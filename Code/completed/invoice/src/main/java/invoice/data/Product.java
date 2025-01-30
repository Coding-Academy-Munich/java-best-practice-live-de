package invoice.data;

public class Product {
    public double price;
    public String department;

    public Product(double price, String department) {
        this.price = price;
        this.department = department;
    }

    public double getPrice() {
        return price;
    }

    public String getDepartment() {
        return department;
    }
}
