package Session01.baitap06;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class SimpleLogger {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void error(String context, Exception e) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.err.println("[ERROR] " + timestamp + " - [" + context + "] " + e.getClass().getSimpleName() + ": " + e.getMessage());
    }

    public static void info(String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.println("[INFO] " + timestamp + " - " + message);
    }
}
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
            throw new InvalidAgeException("Tuổi cung cấp (" + age + ") không hợp lệ. Yêu cầu từ 18 đến 120.");
        }
        this.age = age;
    }

    public void printUserInfo() {
        if (this.name != null && !this.name.trim().isEmpty()) {
            System.out.println("-> Thông tin: " + this.name + " | " + this.age + " tuổi.");
        } else {
            System.out.println("-> Thông tin: [Khách vãng lai - Chưa có tên] | " + this.age + " tuổi.");
        }
    }
}
class FileService {
    public void saveUserData(User user) throws IOException {
        if (user == null) {
            throw new IllegalArgumentException("Dữ liệu User truyền vào không được rỗng (null).");
        }
        throw new IOException("Phân vùng đĩa cứng C:// đã đầy hoặc mất quyền ghi!");
    }
}
public class Main {
    public static void main(String[] args) {
        SimpleLogger.info("Hệ thống khởi động...");
        User user1 = new User("Nguyễn Văn A");
        User userNull = new User(null);
        FileService fileService = new FileService();
        SimpleLogger.info("Đang in thông tin người dùng...");
        user1.printUserInfo();
        userNull.printUserInfo();

        SimpleLogger.info("Bắt đầu quy trình lưu trữ...");
        try {
            user1.setAge(20);
            fileService.saveUserData(user1);
            SimpleLogger.info("Quy trình hoàn tất xuất sắc!");

        } catch (InvalidAgeException e) {
            SimpleLogger.error("Nghiệp vụ Đăng ký", e);

        } catch (IOException e) {
            SimpleLogger.error("Ghi File Hệ thống", e);

        } catch (IllegalArgumentException e) {
            SimpleLogger.error("Kiểm tra Tham số", e);

        } finally {
            SimpleLogger.info("Đóng các luồng tài nguyên (nếu có)...");
        }
        SimpleLogger.info("Hệ thống kết thúc an toàn.");
    }
}