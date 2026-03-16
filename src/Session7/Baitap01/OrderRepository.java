package Session7.Baitap01;

class OrderRepository {
    public void save(Order order) {
        System.out.println("Đã lưu đơn hàng " + order.orderId);
    }
}