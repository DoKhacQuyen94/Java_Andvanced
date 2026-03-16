package Session7.Baitap05;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Product> products = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        // Inject dependencies ban đầu
        OrderRepository repo = new DatabaseOrderRepository();
        NotificationService notifier = new EmailNotification();
        OrderService orderService = new OrderService(repo, notifier);

        while (true) {
            System.out.println("\n1.Thêm SP | 2.Thêm Khách | 3.Tạo Đơn | 4.Xem Đơn | 5.Doanh thu | 0.Thoát");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) {
                System.out.print("Mã, Tên, Giá: ");
                products.add(new Product(sc.next(), sc.next(), sc.nextDouble(), "Chung"));
                System.out.println("Đã thêm SP.");
            } else if (choice == 2) {
                System.out.print("Tên, Email, ĐT: ");
                customers.add(new Customer(sc.next(), sc.next(), sc.next()));
                System.out.println("Đã thêm khách.");
            } else if (choice == 3) {
                if(products.isEmpty() || customers.isEmpty()) continue;
                Order order = new Order("ORD" + (repo.findAll().size() + 1), customers.get(0));
                order.items.add(new OrderItem(products.get(0), 1));

                // Demo chọn linh hoạt Strategy và Payment (OCP)
                orderService.checkout(order, new PercentageDiscount(10), new CreditCardPayment());
            } else if (choice == 5) {
                double revenue = repo.findAll().stream().mapToDouble(o -> o.finalAmount).sum();
                System.out.println("Tổng doanh thu: " + (long)revenue);
            } else if (choice == 0) break;
        }
    }
}