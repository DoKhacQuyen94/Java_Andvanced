package Session7.Baitap01;

public class Main {
    public static void main(String[] args) {
        // 1. Tạo sản phẩm
        Product sp01 = new Product("SP01", "Laptop", 15000000);
        Product sp02 = new Product("SP02", "Chuột", 300000);

        // 2. Tạo khách hàng
        Customer customer = new Customer("Nguyễn Văn A", "a@example.com");
        System.out.println("Đã thêm khách hàng: " + customer.getCustomerName());

        // 3. Tạo đơn hàng
        Order order = new Order("ORD001", customer);
        order.addProduct(sp01, 1);
        order.addProduct(sp02, 2);
        System.out.println("Đơn hàng " + order.orderId + " được tạo");

        // 4. Tính tổng tiền
        OrderCalculator calculator = new OrderCalculator();
        double total = calculator.calculateTotal(order);
        System.out.println("Tổng tiền: " + (long)total);

        // 5. Lưu đơn hàng
        OrderRepository repository = new OrderRepository();
        repository.save(order);

        // 6. Gửi email
        EmailService emailService = new EmailService();
        emailService.sendConfirmationEmail(order);
    }
}