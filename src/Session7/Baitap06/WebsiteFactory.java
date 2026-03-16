package Session7.Baitap06;

public class WebsiteFactory implements SalesChannelFactory {
    @Override
    public DiscountStrategy createDiscount() {
        return amount -> amount * 0.9; // Giảm 10% cho Website
    }

    @Override
    public PaymentMethod createPayment() {
        return amount -> System.out.println("-> [Cổng thanh toán Online] Đã trừ " + (long)amount + " từ thẻ tín dụng.");
    }

    @Override
    public NotificationService createNotification() {
        return (msg, rec) -> System.out.println("-> [Email] Gửi tới " + rec + ": " + msg);
    }
}