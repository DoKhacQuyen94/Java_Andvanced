package Session7.Baitap05;

import java.util.ArrayList;
import java.util.List;

class DatabaseOrderRepository implements OrderRepository {
    private List<Order> db = new ArrayList<>();
    @Override public void save(Order o) { db.add(o); System.out.println("Đã lưu vào DB: " + o.orderId); }
    @Override public List<Order> findAll() { return db; }
}