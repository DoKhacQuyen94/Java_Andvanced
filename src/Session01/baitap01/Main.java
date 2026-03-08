package Session01.baitap01;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Nhập năm sinh của bạn: ");
            int n = Integer.parseInt(sc.nextLine());
            System.out.printf("Năm nay bạn: %d tuổi\n", 2026-n);
        }catch (NumberFormatException e){
            System.out.println("Bạn nhập sai kiểu rồi vui lòng nhập lại");
        }finally {
            System.out.println("Thực hiện dọn dẹp tài nguyên");
            sc.close();
        }
    }
}
