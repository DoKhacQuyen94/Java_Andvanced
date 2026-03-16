package Session7.Baitap06;

public class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getter là bắt buộc để OrderItem có thể tính tiền
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}