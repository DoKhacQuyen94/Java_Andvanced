package Session01.baitap05;

class InvalidAgeException extends Exception {
    public InvalidAgeException(String msg) {
        super(msg);
    }
}
class User {
    private String name;
    private int age;

    public User(String name) {
        this.name = name;
    }
    public void setAge(int age) throws InvalidAgeException {
        if (age < 18 || age > 120) {
            throw new InvalidAgeException("Lỗi nghiệp vụ đăng ký: Tuổi cung cấp (" + age + ") không hợp lệ. Yêu cầu độ tuổi từ 18 đến 120.");
        }

        this.age = age;
        System.out.println("Hệ thống: Cập nhật tuổi thành công cho tài khoản " + this.name + " (" + this.age + " tuổi).");
    }
}
public class Main {
    public static void main(String[] args) {
        System.out.println("--- TIẾN TRÌNH ĐĂNG KÝ NGƯỜI DÙNG ---");

        User user1 = new User("Khách Hàng A");
        try {
            System.out.println("Đang thiết lập tuổi là 15...");
            user1.setAge(15);
            System.out.println("Đăng ký hoàn tất!");

        } catch (InvalidAgeException e) {
            System.out.println("\n[X] BẮT ĐƯỢC LỖI NGHIỆP VỤ TỪ HỆ THỐNG!");
            System.out.println("Tên Exception: " + e.getClass().getName());
            System.out.println("Thông điệp lỗi: " + e.getMessage());
        }

        System.out.println("\n--- Ứng dụng vẫn chạy tiếp bình thường ---");
    }
}