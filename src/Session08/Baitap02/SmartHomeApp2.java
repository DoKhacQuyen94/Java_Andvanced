package Session08.Baitap02;

import java.util.Scanner;

// --- Interface mới (Target) ---
interface TemperatureSensor {
    double getTemperatureCelsius();
}

// --- Cảm biến cũ (Adaptee) ---
class OldThermometer {
    public int getTemperatureFahrenheit() {
        return 78; // Giả lập trả về 78°F
    }
}

// --- Lớp chuyển đổi (Adapter) ---
class ThermometerAdapter implements TemperatureSensor {
    private OldThermometer oldSensor;

    // Đưa cảm biến cũ vào Adapter
    public ThermometerAdapter(OldThermometer oldSensor) {
        this.oldSensor = oldSensor;
    }

    // Chuyển đổi logic bên trong, bên ngoài chỉ thấy độ C
    @Override
    public double getTemperatureCelsius() {
        int f = oldSensor.getTemperatureFahrenheit();
        double c = (f - 32) * 5.0 / 9.0;
        System.out.printf("Nhiệt độ hiện tại: %.1f°C (chuyển đổi từ %d°F)\n", c, f);
        return c;
    }
}
class SmartHomeFacade {
    private TemperatureSensor sensor;

    public SmartHomeFacade(TemperatureSensor sensor) {
        this.sensor = sensor;
    }

    // Gom các lệnh khi ra khỏi nhà
    public void leaveHome() {
        System.out.println("FACADE: Tắt đèn");
        System.out.println("FACADE: Tắt quạt");
        System.out.println("FACADE: Tắt điều hòa");
    }

    // Gom các lệnh khi đi ngủ
    public void sleepMode() {
        System.out.println("FACADE: Tắt đèn");
        System.out.println("FACADE: Điều hòa set 28°C");
        System.out.println("FACADE: Quạt chạy tốc độ thấp");
    }

    // Lấy nhiệt độ thông qua Interface chung
    public void getCurrentTemperature() {
        sensor.getTemperatureCelsius();
    }
}

public class SmartHomeApp2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Khởi tạo Adapter bọc lấy cảm biến cũ
        OldThermometer oldSensor = new OldThermometer();
        TemperatureSensor adapter = new ThermometerAdapter(oldSensor);

        // Khởi tạo Facade quản lý toàn bộ
        SmartHomeFacade facade = new SmartHomeFacade(adapter);

        while (true) {
            System.out.println("\n--- MENU FACADE & ADAPTER ---");
            System.out.println("1. Xem nhiệt độ | 2. Chế độ rời nhà | 3. Chế độ ngủ | 4. Thoát");
            System.out.print("Chọn: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    facade.getCurrentTemperature();
                    break;
                case 2:
                    facade.leaveHome();
                    break;
                case 3:
                    facade.sleepMode();
                    break;
                case 4:
                    System.out.println("Đã thoát.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn sai.");
            }
        }
    }
}