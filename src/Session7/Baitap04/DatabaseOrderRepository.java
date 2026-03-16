package Session7.Baitap04;

class DatabaseOrderRepository implements OrderRepository {
    public void save(String orderId) {
        System.out.println("Lưu đơn hàng vào database: " + orderId);
    }
}