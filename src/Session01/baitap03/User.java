package Session01.baitap03;

import java.util.Scanner;

public class User {
    private int age;
    public User(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0){
            throw new IllegalArgumentException("Tuổi không thể âm!");
        }else {
            this.age = age;
        }
    }
    public static  void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Nhập tuổi của bạn: ");
        int age = input.nextInt();
        User user = new User(age);
        user.setAge(age);
        System.out.println("Tuổi của bạn: "+ user.getAge());

    }
}
