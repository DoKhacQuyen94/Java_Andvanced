package Session7.Baitap06;

public class StorePOSFactory implements SalesChannelFactory {

    @Override
    public DiscountStrategy createDiscount() {
        // Tại quầy thường giảm giá cố định cho thành viên, ví dụ: 50.000đ
        return amount -> Math.max(0, amount - 50000);
    }

    @Override
    public PaymentMethod createPayment() {
        // Tại quầy dùng phương thức thanh toán tiền mặt (COD) hoặc quẹt thẻ POS
        return amount -> System.out.println("-> [Máy POS] Vui lòng quẹt thẻ hoặc trả tiền mặt: " + (long)amount);
    }

    @Override
    public NotificationService createNotification() {
        // Tại quầy không gửi email mà in hóa đơn giấy
        return (msg, rec) -> System.out.println("-> [Máy In] Đang in hóa đơn vật lý cho khách hàng tại quầy...");
    }
}