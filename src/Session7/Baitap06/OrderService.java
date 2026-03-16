package Session7.Baitap06;

public class OrderService {

    // OrderService hoàn toàn không biết WebsiteFactory hay MobileFactory là gì
    // Nó chỉ làm việc với SalesChannelFactory (Nguyên lý DIP)
    public void checkout(Order order, SalesChannelFactory channelFactory) {
        DiscountStrategy discount = channelFactory.createDiscount();
        PaymentMethod payment = channelFactory.createPayment();
        NotificationService notifier = channelFactory.createNotification();

        // 1. Tính tổng tiền từ danh sách món hàng
        double rawTotal = 0;
        for (OrderItem item : order.getItems()) {
            rawTotal += item.getSubTotal();
        }

        // 2. Áp dụng giảm giá tùy theo kênh
        double finalPrice = discount.applyDiscount(rawTotal);
        order.setFinalAmount(finalPrice);

        // 3. Thực hiện thanh toán và thông báo
        System.out.println("--- ĐANG XỬ LÝ ĐƠN HÀNG: " + order.getOrderId() + " ---");
        payment.pay(finalPrice);
        notifier.send("Đơn hàng của bạn đã đặt thành công!", order.getCustomer().getEmail());

        System.out.println("Tổng gốc: " + (long)rawTotal + " | Sau giảm giá: " + (long)finalPrice);
        System.out.println("------------------------------------------\n");
    }
}