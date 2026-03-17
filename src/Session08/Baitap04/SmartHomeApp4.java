package Session08.Baitap04;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Observer {
    void update(int temperature);
}

interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers();
}

class TemperatureSensor implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private int temperature;

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature);
        }
    }

    // Khi set nhiệt độ mới, tự động thông báo cho tất cả
    public void setTemperature(int temperature) {
        this.temperature = temperature;
        System.out.println("\nCảm biến: Nhiệt độ = " + temperature);
        notifyObservers();
    }
}

class Fan implements Observer {
    @Override
    public void update(int temperature) {
        if (temperature < 20) {
            System.out.println("Quạt: Nhiệt độ thấp, tự động TẮT");
        } else if (temperature >= 20 && temperature <= 25) {
            System.out.println("Quạt: Nhiệt độ bình thường, chạy tốc độ trung bình");
        } else {
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
        }
    }
}

class Humidifier implements Observer {
    @Override
    public void update(int temperature) {
        System.out.println("Máy tạo ẩm: Điều chỉnh độ ẩm cho nhiệt độ " + temperature);
    }
}

public class SmartHomeApp4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TemperatureSensor sensor = new TemperatureSensor();
        Fan fan = new Fan();
        Humidifier humidifier = new Humidifier();

        boolean isFanAttached = false;
        boolean isHumidifierAttached = false;

        while (true) {
            System.out.println("\n--- MENU OBSERVER PATTERN ---");
            System.out.println("1. Đăng ký/Hủy đăng ký Quạt");
            System.out.println("2. Đăng ký/Hủy đăng ký Máy tạo ẩm");
            System.out.println("3. Thay đổi nhiệt độ phòng");
            System.out.println("4. Thoát");
            System.out.print("Chọn thao tác: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (!isFanAttached) {
                        sensor.attach(fan);
                        isFanAttached = true;
                        System.out.println("Quạt: Đã đăng ký nhận thông báo");
                    } else {
                        sensor.detach(fan);
                        isFanAttached = false;
                        System.out.println("Quạt: Đã hủy đăng ký");
                    }
                    break;

                case 2:
                    if (!isHumidifierAttached) {
                        sensor.attach(humidifier);
                        isHumidifierAttached = true;
                        System.out.println("Máy tạo ẩm: Đã đăng ký nhận thông báo");
                    } else {
                        sensor.detach(humidifier);
                        isHumidifierAttached = false;
                        System.out.println("Máy tạo ẩm: Đã hủy đăng ký");
                    }
                    break;

                case 3:
                    System.out.print("Nhập nhiệt độ mới (°C): ");
                    int temp = scanner.nextInt();
                    sensor.setTemperature(temp); // Kích hoạt chuỗi sự kiện tự động
                    break;

                case 4:
                    System.out.println("Đã thoát hệ thống.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }
}