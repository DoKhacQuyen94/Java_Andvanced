package Session7.Baitap04;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Cấu hình 1 ---");
        OrderRepository fileRepo = new FileOrderRepository();
        NotificationService emailSvc = new EmailService();
        OrderService service1 = new OrderService(fileRepo, emailSvc);
        service1.processOrder("ORD001", "a@example.com");
        System.out.println("\n--- Cấu hình 2 (Thay thế linh kiện) ---");
        OrderRepository dbRepo = new DatabaseOrderRepository();
        NotificationService smsSvc = new SMSNotification();
        OrderService service2 = new OrderService(dbRepo, smsSvc);
        service2.processOrder("ORD002", "0912345678");
    }
}