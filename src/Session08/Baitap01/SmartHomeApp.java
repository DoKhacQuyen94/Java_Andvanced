package Session08.Baitap01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class HardwareConnection {
    private static HardwareConnection instance;
    private boolean isConnected = false;

    // Chặn dùng 'new' từ bên ngoài
    private HardwareConnection() {}

    // Lấy instance duy nhất
    public static HardwareConnection getInstance() {
        if (instance == null) instance = new HardwareConnection();
        return instance;
    }

    public void connect() {
        if (!isConnected) {
            System.out.println("HardwareConnection: Đã kết nối phần cứng.");
            isConnected = true;
        }
    }

    public void disconnect() {
        if (isConnected) {
            System.out.println("HardwareConnection: Đã ngắt kết nối phần cứng.");
            isConnected = false;
        }
    }
}

// --- Interface chung ---
interface Device {
    void turnOn();
    void turnOff();
}

// --- Các thiết bị cụ thể ---
class Light implements Device {
    public void turnOn() { System.out.println("Đèn: Bật sáng."); }
    public void turnOff() { System.out.println("Đèn: Tắt."); }
}

class Fan implements Device {
    public void turnOn() { System.out.println("Quạt: Bật."); }
    public void turnOff() { System.out.println("Quạt: Tắt."); }
}

class AirConditioner implements Device {
    public void turnOn() { System.out.println("Điều hòa: Bật."); }
    public void turnOff() { System.out.println("Điều hòa: Tắt."); }
}

// --- Factory tổng ---
abstract class DeviceFactory {
    public abstract Device createDevice();
}

// --- Các Factory con ---
class LightFactory extends DeviceFactory {
    public Device createDevice() {
        System.out.println("LightFactory: Đã tạo đèn mới.");
        return new Light();
    }
}

class FanFactory extends DeviceFactory {
    public Device createDevice() {
        System.out.println("FanFactory: Đã tạo quạt mới.");
        return new Fan();
    }
}

class AirConditionerFactory extends DeviceFactory {
    public Device createDevice() {
        System.out.println("AirConditionerFactory: Đã tạo điều hòa mới.");
        return new AirConditioner();
    }
}

public class SmartHomeApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Device> devices = new ArrayList<>();
        HardwareConnection connection = null;

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Kết nối phần cứng | 2. Tạo thiết bị | 3. Bật/tắt | 4. Thoát");
            System.out.print("Chọn: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Gọi Singleton
                    connection = HardwareConnection.getInstance();
                    connection.connect();
                    break;

                case 2:
                    System.out.print("Chọn loại (1. Đèn | 2. Quạt | 3. Điều hòa): ");
                    int type = scanner.nextInt();
                    DeviceFactory factory = null;

                    if (type == 1) factory = new LightFactory();
                    else if (type == 2) factory = new FanFactory();
                    else if (type == 3) factory = new AirConditionerFactory();
                    else {
                        System.out.println("Lỗi chọn loại.");
                        break;
                    }

                    // Gọi Factory để tạo
                    devices.add(factory.createDevice());
                    break;

                case 3:
                    if (devices.isEmpty()) {
                        System.out.println("Chưa có thiết bị!");
                        break;
                    }
                    System.out.print("Chọn thiết bị (1-" + devices.size() + "): ");
                    int devIndex = scanner.nextInt() - 1;

                    if (devIndex >= 0 && devIndex < devices.size()) {
                        System.out.print("Hành động (1. Bật | 2. Tắt): ");
                        int action = scanner.nextInt();
                        if (action == 1) devices.get(devIndex).turnOn();
                        else if (action == 2) devices.get(devIndex).turnOff();
                    } else {
                        System.out.println("Sai thiết bị.");
                    }
                    break;

                case 4:
                    if (connection != null) connection.disconnect();
                    scanner.close();
                    return;
            }
        }
    }
}