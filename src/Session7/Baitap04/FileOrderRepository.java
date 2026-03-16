package Session7.Baitap04;

class FileOrderRepository implements OrderRepository {
    public void save(String orderId) {
        System.out.println("Lưu đơn hàng vào file: " + orderId);
    }
}