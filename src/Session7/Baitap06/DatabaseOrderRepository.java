package Session7.Baitap06;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOrderRepository implements OrderRepository {
    private List<Order> db = new ArrayList<>();
    @Override public void save(Order o) { db.add(o); System.out.println("Đã lưu vào DB: " + o.getOrderId()); }
    @Override public List<Order> findAll() { return db; }
}