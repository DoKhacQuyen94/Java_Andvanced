package Session7.Baitap06;

public class MobileAppFactory implements SalesChannelFactory {
    @Override
    public DiscountStrategy createDiscount() {
        return amount -> amount * 0.85; // Giảm 15% cho Mobile
    }

    @Override
    public PaymentMethod createPayment() {
        return amount -> System.out.println("-> [Ví Momo] Đã thanh toán " + (long)amount + " qua App.");
    }

    @Override
    public NotificationService createNotification() {
        return (msg, rec) -> System.out.println("-> [Push Notification] Điện thoại rung: " + msg);
    }
}