package Session06.Baitap06;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SystemState {
    public volatile boolean isRunning = false;
    public volatile boolean isPaused = false;
    public final Object pauseLock = new Object();
}

class Ticket {
    String id;
    boolean isSold;
    int price = 250000;

    public Ticket(String id) {
        this.id = id;
        this.isSold = false;
    }
}

class TicketPool {
    String roomName;
    List<Ticket> tickets = new ArrayList<>();
    int soldCount = 0;
    long revenue = 0;
    int lastId = 0;

    public TicketPool(String roomName, int count) {
        this.roomName = roomName;
        addTickets(count);
    }

    public synchronized void addTickets(int count) {
        for (int i = 0; i < count; i++) {
            lastId++;
            tickets.add(new Ticket(roomName + "-" + String.format("%03d", lastId)));
        }
        System.out.println("Đã thêm " + count + " vé vào phòng " + roomName);
        notifyAll();
    }

    public synchronized Ticket sellTicket() {
        for (Ticket t : tickets) {
            if (!t.isSold) {
                t.isSold = true;
                soldCount++;
                revenue += t.price;
                return t;
            }
        }
        return null;
    }

    public synchronized int getTotalTickets() { return tickets.size(); }
}

class BookingCounter implements Runnable {
    String name;
    List<TicketPool> pools;
    SystemState state;
    Random random = new Random();

    public BookingCounter(String name, List<TicketPool> pools, SystemState state) {
        this.name = name;
        this.pools = pools;
        this.state = state;
    }

    @Override
    public void run() {
        System.out.println(name + " bắt đầu làm việc...");
        while (state.isRunning) {
            synchronized (state.pauseLock) {
                while (state.isPaused) {
                    try {
                        state.pauseLock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return; // Thoát nếu bị ngắt
                    }
                }
            }

            TicketPool targetPool = pools.get(random.nextInt(pools.size()));
            Ticket t = targetPool.sellTicket();

            if (t != null) {
                // Đã ẩn log in vé liên tục để console đỡ bị trôi, chỉ in khi cần thiết
                // System.out.println(name + " đã bán " + t.id);
            }

            try {
                Thread.sleep(500 + random.nextInt(1000)); // Giả lập thời gian bán
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class DeadlockDetector {
    public static void scan() {
        System.out.println("Đang quét deadlock...");
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = bean.findDeadlockedThreads();

        if (deadlockedThreads != null && deadlockedThreads.length > 0) {
            System.out.println("[CẢNH BÁO] Phát hiện " + deadlockedThreads.length + " luồng đang bị Deadlock!");
        } else {
            System.out.println("Tình trạng hệ thống an toàn. Không phát hiện deadlock.");
        }
    }
}

public class CinemaSimulationManager {
    static List<TicketPool> pools = new ArrayList<>();
    static SystemState state = new SystemState();
    static ExecutorService executor;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- HỆ THỐNG QUẢN LÝ RẠP CHIẾU PHIM ---");
            System.out.println("1. Bắt đầu mô phỏng");
            System.out.println("2. Tạm dừng mô phỏng");
            System.out.println("3. Tiếp tục mô phỏng");
            System.out.println("4. Thêm vé vào phòng");
            System.out.println("5. Xem thống kê");
            System.out.println("6. Phát hiện deadlock");
            System.out.println("7. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    startSimulation(scanner);
                    break;
                case 2:
                    pauseSimulation();
                    break;
                case 3:
                    resumeSimulation();
                    break;
                case 4:
                    addTicketsManual(scanner);
                    break;
                case 5:
                    viewStatistics();
                    break;
                case 6:
                    DeadlockDetector.scan();
                    break;
                case 7:
                    stopSystem();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 7);
        scanner.close();
    }

    static void startSimulation(Scanner sc) {
        if (state.isRunning) {
            System.out.println("Hệ thống đang chạy rồi!");
            return;
        }

        System.out.print("Nhập số phòng: ");
        int numRooms = sc.nextInt();
        System.out.print("Nhập số vé mỗi phòng ban đầu: ");
        int numTickets = sc.nextInt();
        System.out.print("Nhập số quầy bán vé: ");
        int numCounters = sc.nextInt();

        pools.clear();
        for (int i = 0; i < numRooms; i++) {
            char roomChar = (char) ('A' + i);
            pools.add(new TicketPool(String.valueOf(roomChar), numTickets));
        }

        System.out.println("Đã khởi tạo hệ thống với " + numRooms + " phòng, tổng " + (numRooms * numTickets) + " vé, " + numCounters + " quầy.");

        state.isRunning = true;
        state.isPaused = false;

        executor = Executors.newFixedThreadPool(numCounters);
        for (int i = 1; i <= numCounters; i++) {
            executor.submit(new BookingCounter("Quầy " + i, pools, state));
        }
    }

    static void pauseSimulation() {
        if (!state.isRunning) return;
        state.isPaused = true;
        System.out.println("Đã gửi tín hiệu tạm dừng tất cả quầy bán vé.");
    }

    static void resumeSimulation() {
        if (!state.isRunning) return;
        state.isPaused = false;
        synchronized (state.pauseLock) {
            state.pauseLock.notifyAll();
        }
        System.out.println("Đã tiếp tục hoạt động bán vé.");
    }

    static void addTicketsManual(Scanner sc) {
        if (pools.isEmpty()) {
            System.out.println("Hệ thống chưa được khởi tạo!");
            return;
        }
        System.out.print("Nhập tên phòng (VD: A, B): ");
        String room = sc.next().toUpperCase();
        System.out.print("Nhập số lượng vé cần thêm: ");
        int count = sc.nextInt();

        for (TicketPool p : pools) {
            if (p.roomName.equals(room)) {
                p.addTickets(count);
                return;
            }
        }
        System.out.println("Không tìm thấy phòng " + room);
    }

    static void viewStatistics() {
        if (pools.isEmpty()) {
            System.out.println("Hệ thống chưa có dữ liệu!");
            return;
        }
        System.out.println("\n=== THỐNG KÊ HIỆN TẠI ===");
        long totalRevenue = 0;
        for (TicketPool p : pools) {
            System.out.println(" Phòng " + p.roomName + ": Đã bán " + p.soldCount + "/" + p.getTotalTickets() + " vé");
            totalRevenue += p.revenue;
        }
        System.out.printf(" Tổng doanh thu: %,d VNĐ\n", totalRevenue);
    }

    static void stopSystem() {
        System.out.println("Đang dừng hệ thống...");
        state.isRunning = false;
        if (executor != null) {
            executor.shutdownNow();
            try {
                executor.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Kết thúc chương trình.");
    }
}