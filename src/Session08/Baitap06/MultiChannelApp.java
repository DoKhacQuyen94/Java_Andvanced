package Session08.Baitap06;

import java.util.Scanner;

interface DiscountStrategy {
    double calculateDiscount(double price);
    String getDescription();
}

interface PaymentMethod {
    void processPayment(double amount);
}

interface NotificationService {
    void sendNotification(String message);
}

// --- Nhóm Giảm giá ---
class WebsiteDiscount implements DiscountStrategy {
    public double calculateDiscount(double price) { return price * 0.10; }
    public String getDescription() { return "giảm giá 10%"; }
}
class FirstTimeDiscount implements DiscountStrategy {
    public double calculateDiscount(double price) { return price * 0.15; }
    public String getDescription() { return "giảm giá 15% (lần đầu)"; }
}
class MemberDiscount implements DiscountStrategy {
    public double calculateDiscount(double price) { return price * 0.05; }
    public String getDescription() { return "giảm 5% cho thành viên"; }
}
class NoDiscount implements DiscountStrategy {
    public double calculateDiscount(double price) { return 0; }
    public String getDescription() { return "không giảm giá"; }
}

// --- Nhóm Thanh toán ---
class CreditCardPayment implements PaymentMethod {
    public void processPayment(double amount) { System.out.printf("Xử lý thanh toán thẻ tín dụng: %,.0f\n", amount); }
}
class MomoPayment implements PaymentMethod {
    public void processPayment(double amount) { System.out.printf("Xử lý thanh toán MoMo: %,.0f\n", amount); }
}
class CODPayment implements PaymentMethod {
    public void processPayment(double amount) { System.out.printf("Thanh toán tiền mặt (COD): %,.0f\n", amount); }
}

// --- Nhóm Thông báo ---
class EmailNotification implements NotificationService {
    public void sendNotification(String msg) { System.out.println("Gửi email: " + msg); }
}
class PushNotification implements NotificationService {
    public void sendNotification(String msg) { System.out.println("Gửi push notification: " + msg); }
}
class PrintReceipt implements NotificationService {
    public void sendNotification(String msg) { System.out.println("In biên lai: " + msg); }
}

interface SalesChannelFactory {
    DiscountStrategy createDiscountStrategy();
    PaymentMethod createPaymentMethod();
    NotificationService createNotificationService();
}

class WebsiteFactory implements SalesChannelFactory {
    public DiscountStrategy createDiscountStrategy() { return new WebsiteDiscount(); }
    public PaymentMethod createPaymentMethod() { return new CreditCardPayment(); }
    public NotificationService createNotificationService() { return new EmailNotification(); }
}

class MobileAppFactory implements SalesChannelFactory {
    public DiscountStrategy createDiscountStrategy() { return new FirstTimeDiscount(); }
    public PaymentMethod createPaymentMethod() { return new MomoPayment(); }
    public NotificationService createNotificationService() { return new PushNotification(); }
}

class POSFactory implements SalesChannelFactory {
    public DiscountStrategy createDiscountStrategy() { return new MemberDiscount(); }
    public PaymentMethod createPaymentMethod() { return new CODPayment(); }
    public NotificationService createNotificationService() { return new PrintReceipt(); }
}

// KÊNH MỚI: Thêm vào vô cùng dễ dàng, không chạm vào code cũ
class KioskFactory implements SalesChannelFactory {
    public DiscountStrategy createDiscountStrategy() { return new NoDiscount(); }
    public PaymentMethod createPaymentMethod() { return new CreditCardPayment(); }
    public NotificationService createNotificationService() { return new PrintReceipt(); }
}

class OrderService {
    private DiscountStrategy discount;
    private PaymentMethod payment;
    private NotificationService notification;

    // Nhận Factory qua Constructor (Dependency Injection)
    public OrderService(SalesChannelFactory factory) {
        this.discount = factory.createDiscountStrategy();
        this.payment = factory.createPaymentMethod();
        this.notification = factory.createNotificationService();
    }

    public void processOrder(String productName, double price, int quantity) {
        System.out.println("Tạo đơn hàng");
        System.out.println("Sản phẩm: " + productName + " giá " + String.format("%,.0f", price) + ", số lượng " + quantity);

        double totalOrigin = price * quantity;
        double discountAmount = discount.calculateDiscount(totalOrigin);
        double finalPrice = totalOrigin - discountAmount;

        System.out.printf("Áp dụng %s: %,.0f\n", discount.getDescription(), discountAmount);
        payment.processPayment(finalPrice);
        notification.sendNotification("Đơn hàng thành công");
    }
}

public class MultiChannelApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- HỆ THỐNG BÁN HÀNG ĐA KÊNH ---");
            System.out.println("1. Website | 2. Mobile App | 3. POS | 4. Kiosk (Kênh mới) | 5. Thoát");
            System.out.print("Chọn kênh bán hàng: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            if (choice == 5) {
                System.out.println("Đã thoát.");
                break;
            }

            SalesChannelFactory factory = null;

            switch (choice) {
                case 1:
                    System.out.println("\nBạn đã chọn kênh Website");
                    factory = new WebsiteFactory();
                    break;
                case 2:
                    System.out.println("\nBạn đã chọn kênh Mobile App");
                    factory = new MobileAppFactory();
                    break;
                case 3:
                    System.out.println("\nBạn đã chọn kênh POS");
                    factory = new POSFactory();
                    break;
                case 4:
                    System.out.println("\nBạn đã chọn kênh Kiosk");
                    factory = new KioskFactory();
                    break;
                default:
                    System.out.println("Lựa chọn sai!");
                    continue;
            }

            OrderService orderService = new OrderService(factory);

            System.out.print("Nhập tên sản phẩm: ");
            String name = scanner.nextLine();
            System.out.print("Nhập giá sản phẩm: ");
            double price = scanner.nextDouble();
            System.out.print("Nhập số lượng: ");
            int qty = scanner.nextInt();

            orderService.processOrder(name, price, qty);
        }
        scanner.close();
    }
}