package Session7.Baitap06;

import java.util.List;

interface OrderRepository {
    void save(Order order);
    List<Order> findAll();
}