package Session01.baitap04;

import java.io.IOException;

public class ExceptionSimulation {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu chương trình ---");
        try {
            System.out.println("Hàm main (A) đang gọi hàm processUserData (B)...");
            processUserData("Thông tin người dùng: Quyền, Tuổi: 20");
            System.out.println("Lưu dữ liệu thành công toàn tập!");

        } catch (IOException e) {
            System.out.println("\n[!] ĐÃ BẮT ĐƯỢC LỖI TẠI HÀM MAIN (A)!");
            System.out.println("Nguyên nhân: " + e.getMessage());
            System.out.println("Xử lý ngoại lệ thành công. Ngăn chặn JVM bị crash.");
        }

        System.out.println("\n--- Chương trình kết thúc an toàn ---");
    }
    public static void processUserData(String data) throws IOException {
        System.out.println("Hàm processUserData (B) đang gọi hàm saveToFile (C)...");
        saveToFile(data);
        System.out.println("Hoàn tất xử lý tại hàm B."); // Không chạy tới đây
    }
    public static void saveToFile(String data) throws IOException {
        System.out.println("Hàm saveToFile (C) đang cố gắng ghi file...");
        throw new IOException("Không thể ghi file - Ổ cứng đã đầy hoặc bị lỗi quyền truy cập!");
    }
}