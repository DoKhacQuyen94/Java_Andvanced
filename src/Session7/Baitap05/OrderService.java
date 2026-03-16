package Session7.Baitap05;

class OrderService {
    private OrderRepository repo;
    private NotificationService notifier;

    public OrderService(OrderRepository repo, NotificationService notifier) {
        this.repo = repo;
        this.notifier = notifier;
    }

    public void checkout(Order order, DiscountStrategy discount, PaymentMethod payment) {
        double rawTotal = order.items.stream().mapToDouble(OrderItem::getSubTotal).sum();
        order.finalAmount = discount.applyDiscount(rawTotal);

        payment.pay(order.finalAmount);
        repo.save(order);
        notifier.send("Đơn hàng " + order.orderId + " thành công", order.customer.email);

        new InvoiceGenerator().print(order, rawTotal, rawTotal - order.finalAmount);
    }
}