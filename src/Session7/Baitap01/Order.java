package Session7.Baitap01;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Order {
    String orderId;
    Customer customer;
    Map<Product, Integer> products = new HashMap<>();

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
    }

    public void addProduct(Product product, int quantity) {
        products.put(product, quantity);
        System.out.println("Đã thêm sản phẩm");
    }
}
