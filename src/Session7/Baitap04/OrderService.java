package Session7.Baitap04;

class OrderService {
    private final OrderRepository repository;
    private final NotificationService notification;

    public OrderService(OrderRepository repository, NotificationService notification) {
        this.repository = repository;
        this.notification = notification;
    }

    public void processOrder(String orderId, String contact) {
        repository.save(orderId);
        notification.send("Đơn hàng " + orderId + " đã được tạo", contact);
    }
}