package Session7.Baitap05;

import java.util.ArrayList;
import java.util.List;

class Order {
    String orderId;
    Customer customer;
    List<OrderItem> items = new ArrayList<>();
    double finalAmount;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
    }
}