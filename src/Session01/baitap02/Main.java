package Session01.baitap02;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập số người dùng: ");
        int numberUser = Integer.parseInt(sc.nextLine());
        System.out.print("Nhập số nhóm muốn chia ra: ");
        int numberGroup = Integer.parseInt(sc.nextLine());
        try{
            int group = numberUser / numberGroup;
            System.out.println("Chia ra được: "+group);
        }catch(ArithmeticException e){
            System.out.println("Không thể chia cho 0!");
        }finally {
            System.out.println("Đang dọn dẹp tài nguyên");
            sc.close();
        }
    }
}

