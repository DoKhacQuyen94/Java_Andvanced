package Session08.Baitap05;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Observer {
    void update(int temperature);
}

interface Subject {
    void attach(Observer o);
    void notifyObservers();
}

class TemperatureSensor implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private int temperature;

    public void attach(Observer o) { observers.add(o); }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature);
        }
    }

    // Khi cập nhật nhiệt độ, tự động báo cho các thiết bị
    public void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println("\nCảm biến: Nhiệt độ = " + temp + "°C");
        notifyObservers();
    }
}

class Light {
    private String status = "Đang bật";

    public void turnOff() {
        status = "Tắt";
        System.out.println("Đèn: Tắt");
    }
    public String getStatus() { return status; }
}

class AirConditioner implements Observer {
    private int currentTemp = 25;

    public void setTemperature(int temp) {
        this.currentTemp = temp;
        System.out.println("Điều hòa: Nhiệt độ = " + temp + "°C");
    }

    @Override
    public void update(int temperature) {
        // Giữ nguyên 28 độ, nhưng có thể code thêm logic tự giảm nhiệt độ ở đây
    }

    public String getStatus() { return currentTemp + "°C"; }
}

class Fan implements Observer {
    private String speed = "Tắt";

    public void setSpeed(String speed) {
        this.speed = speed;
        System.out.println("Quạt: Chạy tốc độ " + speed);
    }

    @Override
    public void update(int temperature) {
        if (temperature > 30) {
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ MẠNH");
            this.speed = "Mạnh"; // Tự động cập nhật trạng thái
        }
    }

    public String getStatus() { return speed; }
}

interface Command {
    void execute();
}

class SleepModeCommand implements Command {
    private Light light;
    private AirConditioner ac;
    private Fan fan;

    public SleepModeCommand(Light light, AirConditioner ac, Fan fan) {
        this.light = light;
        this.ac = ac;
        this.fan = fan;
    }

    @Override
    public void execute() {
        System.out.println("SleepMode: Tắt đèn");
        light.turnOff();

        System.out.println("SleepMode: Điều hòa set 28°C");
        ac.setTemperature(28);

        System.out.println("SleepMode: Quạt tốc độ thấp");
        fan.setSpeed("Thấp");
    }
}

public class SmartHomeApp5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Khởi tạo thiết bị
        Light light = new Light();
        AirConditioner ac = new AirConditioner();
        Fan fan = new Fan();

        // Khởi tạo cảm biến và đăng ký thiết bị theo dõi
        TemperatureSensor sensor = new TemperatureSensor();
        sensor.attach(ac);
        sensor.attach(fan);

        // Cài đặt nút bấm cho chế độ ngủ
        Command sleepMode = new SleepModeCommand(light, ac, fan);

        while (true) {
            System.out.println("\n--- HỆ THỐNG NHÀ THÔNG MINH ---");
            System.out.println("1. Kích hoạt chế độ ngủ");
            System.out.println("2. Thay đổi nhiệt độ phòng (Giả lập)");
            System.out.println("3. Xem trạng thái thiết bị");
            System.out.println("4. Thoát");
            System.out.print("Chọn thao tác: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    sleepMode.execute(); // Chạy Macro Command
                    break;

                case 2:
                    System.out.print("Nhập nhiệt độ môi trường hiện tại (°C): ");
                    int temp = scanner.nextInt();
                    sensor.setTemperature(temp); // Kích hoạt Observer
                    break;

                case 3:
                    System.out.println("\n--- TRẠNG THÁI HIỆN TẠI ---");
                    System.out.println("- Đèn: " + light.getStatus());
                    System.out.println("- Điều hòa: " + ac.getStatus());
                    System.out.println("- Quạt: " + fan.getStatus());
                    break;

                case 4:
                    System.out.println("Đã thoát.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }
}