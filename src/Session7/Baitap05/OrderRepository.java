package Session7.Baitap05;

import java.util.List;

interface OrderRepository {
    void save(Order order);
    List<Order> findAll();
}