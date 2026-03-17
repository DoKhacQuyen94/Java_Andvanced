package Session08.Baitap03;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

class Light {
    public void turnOn() { System.out.println("Đèn: Bật"); }
    public void turnOff() { System.out.println("Đèn: Tắt"); }
}

class AirConditioner {
    private int temperature = 25; // Nhiệt độ mặc định

    public void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println("Điều hòa: Nhiệt độ = " + temp);
    }
    public int getTemperature() { return temperature; }
}

interface Command {
    void execute();
    void undo();
}

class LightOnCommand implements Command {
    private Light light;
    public LightOnCommand(Light light) { this.light = light; }

    public void execute() { light.turnOn(); }
    public void undo() {
        System.out.print("Undo: ");
        light.turnOff();
    }
}

class LightOffCommand implements Command {
    private Light light;
    public LightOffCommand(Light light) { this.light = light; }

    public void execute() { light.turnOff(); }
    public void undo() {
        System.out.print("Undo: ");
        light.turnOn();
        System.out.println("(quay lại trạng thái trước)");
    }
}

class ACSetTempCommand implements Command {
    private AirConditioner ac;
    private int newTemp;
    private int prevTemp; // Lưu trạng thái cũ để undo

    public ACSetTempCommand(AirConditioner ac, int newTemp) {
        this.ac = ac;
        this.newTemp = newTemp;
    }

    public void execute() {
        prevTemp = ac.getTemperature(); // Lưu lại trước khi đổi
        ac.setTemperature(newTemp);
    }

    public void undo() {
        System.out.print("Undo: ");
        ac.setTemperature(prevTemp);
        System.out.println("(nhiệt độ cũ)");
    }
}

class RemoteControl {
    private Map<Integer, Command> buttons = new HashMap<>();
    private Stack<Command> undoStack = new Stack<>(); // Lưu lịch sử

    public void setCommand(int slot, Command command) {
        buttons.put(slot, command);
        System.out.println("Đã gán " + command.getClass().getSimpleName() + " cho nút " + slot);
    }

    public void pressButton(int slot) {
        Command cmd = buttons.get(slot);
        if (cmd != null) {
            cmd.execute();
            undoStack.push(cmd); // Đẩy lệnh vừa chạy vào Stack
        } else {
            System.out.println("Nút " + slot + " chưa được gán lệnh.");
        }
    }

    public void pressUndo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop(); // Lấy lệnh gần nhất ra
            cmd.undo();
        } else {
            System.out.println("Không có thao tác nào để Undo.");
        }
    }
}

public class SmartHomeApp3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Khởi tạo thiết bị và Remote
        Light livingRoomLight = new Light();
        AirConditioner bedroomAC = new AirConditioner();
        RemoteControl remote = new RemoteControl();

        while (true) {
            System.out.println("\n--- MENU REMOTE CONTROL ---");
            System.out.println("1. Gán lệnh cho nút | 2. Nhấn nút | 3. Undo | 4. Thoát");
            System.out.print("Chọn: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Chọn slot nút (1, 2, 3...): ");
                    int slot = scanner.nextInt();
                    System.out.println("1. Bật đèn | 2. Tắt đèn | 3. Set điều hòa");
                    System.out.print("Chọn chức năng: ");
                    int cmdType = scanner.nextInt();

                    if (cmdType == 1) remote.setCommand(slot, new LightOnCommand(livingRoomLight));
                    else if (cmdType == 2) remote.setCommand(slot, new LightOffCommand(livingRoomLight));
                    else if (cmdType == 3) {
                        System.out.print("Nhập nhiệt độ muốn set: ");
                        int temp = scanner.nextInt();
                        remote.setCommand(slot, new ACSetTempCommand(bedroomAC, temp));
                    }
                    break;

                case 2:
                    System.out.print("Nhập số của nút muốn nhấn: ");
                    int btn = scanner.nextInt();
                    remote.pressButton(btn);
                    break;

                case 3:
                    remote.pressUndo();
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