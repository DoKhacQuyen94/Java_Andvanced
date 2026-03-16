package Session7.Baitap06;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo các dịch vụ lõi
        OrderService orderService = new OrderService();

        // Giả lập dữ liệu
        Customer customer = new Customer("Đỗ Khắc Quyền", "quinn@ptit.edu.vn");
        Product laptop = new Product("P001", "Macbook M3", 35000000);
        Product mouse = new Product("P002", "Magic Mouse", 2000000);

        // --- TRƯỜNG HỢP 1: MUA TRÊN WEBSITE ---
        Order webOrder = new Order("WEB-001", customer);
        webOrder.addItem(new OrderItem(laptop, 1));
        webOrder.addItem(new OrderItem(mouse, 1));

        System.out.println("Khách hàng đang mua sắm trên Website...");
        orderService.checkout(webOrder, new WebsiteFactory());

        // --- TRƯỜNG HỢP 2: MUA TRÊN MOBILE APP ---
        Order appOrder = new Order("APP-999", customer);
        appOrder.addItem(new OrderItem(laptop, 1));

        System.out.println("Khách hàng đang mua sắm trên Mobile App...");
        orderService.checkout(appOrder, new MobileAppFactory());
    }
}