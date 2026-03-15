package Session06.Baitap05;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Ticket {
    String ticketId;
    String roomName;
    boolean isSold;
    boolean isHeld;
    long holdExpiryTime;
    boolean isVIP;
    String holderName;

    public Ticket(String ticketId, String roomName, boolean isVIP) {
        this.ticketId = ticketId;
        this.roomName = roomName;
        this.isVIP = isVIP;
        this.isSold = false;
        this.isHeld = false;
        this.holdExpiryTime = 0;
        this.holderName = "";
    }
}

class TicketPool {
    String roomName;
    List<Ticket> tickets;

    public TicketPool(String roomName, int capacity, int vipCount) {
        this.roomName = roomName;
        this.tickets = new ArrayList<>();
        for (int i = 1; i <= capacity; i++) {
            boolean isVIP = (i <= vipCount);
            String id = roomName + "-" + String.format("%03d", i);
            tickets.add(new Ticket(id, roomName, isVIP));
        }
    }

    public synchronized Ticket holdTicket(String counterName, boolean preferVIP) {
        for (Ticket t : tickets) {
            if (!t.isSold && !t.isHeld && t.isVIP == preferVIP) {
                t.isHeld = true;
                t.holderName = counterName;
                t.holdExpiryTime = System.currentTimeMillis() + 5000;

                String type = t.isVIP ? "(VIP)" : "(Thường)";
                System.out.println(counterName + ": Đã giữ vé " + t.ticketId + " " + type + ". Vui lòng thanh toán trong 5s");
                return t;
            }
        }
        return null;
    }

    public synchronized boolean sellHeldTicket(Ticket t, String counterName) {
        if (t.isHeld && !t.isSold && counterName.equals(t.holderName)) {
            t.isSold = true;
            t.isHeld = false;
            System.out.println(counterName + ": Thanh toán thành công vé " + t.ticketId);
            return true;
        }
        return false;
    }
    public synchronized void releaseExpiredTickets() {
        long now = System.currentTimeMillis();
        for (Ticket t : tickets) {
            if (t.isHeld && !t.isSold && now > t.holdExpiryTime) {
                System.out.println("TimeoutManager: Vé " + t.ticketId + " hết hạn giữ, đã trả lại kho");
                t.isHeld = false;
                t.holderName = "";
                t.holdExpiryTime = 0;
            }
        }
    }

    public synchronized boolean hasAvailableTickets() {
        for (Ticket t : tickets) {
            if (!t.isSold && !t.isHeld) return true;
        }
        return false;
    }
}

class BookingCounter implements Runnable {
    String counterName;
    TicketPool[] pools;
    Random random;
    int successfulSales = 0;

    public BookingCounter(String counterName, TicketPool[] pools) {
        this.counterName = counterName;
        this.pools = pools;
        this.random = new Random();
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            boolean wantsVIP = random.nextBoolean();
            TicketPool targetPool = pools[random.nextInt(pools.length)];

            Ticket myTicket = targetPool.holdTicket(counterName, wantsVIP);

            if (myTicket != null) {
                int thinkingTime = 2000 + random.nextInt(5000);
                try {
                    Thread.sleep(thinkingTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                boolean isSuccess = targetPool.sellHeldTicket(myTicket, counterName);
                if (isSuccess) {
                    successfulSales++;
                } else {
                    System.out.println(counterName + ": Giao dịch thất bại do quá hạn thời gian cho vé " + myTicket.ticketId);
                }
            } else {
                System.out.println(counterName + ": Hiện tại không còn vé trống ở phòng " + targetPool.roomName + " (các vé có thể đang bị quầy khác giữ), chờ...");
            }

            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
    }
}

class TimeoutManager implements Runnable {
    TicketPool[] pools;
    public static volatile boolean isSystemRunning = true;

    public TimeoutManager(TicketPool[] pools) {
        this.pools = pools;
    }

    @Override
    public void run() {
        while (isSystemRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            for (TicketPool pool : pools) {
                pool.releaseExpiredTickets();
            }
        }
    }
}

public class CinemaOnline {
    public static void main(String[] args) {
        TicketPool poolA = new TicketPool("A", 5, 2);
        TicketPool poolB = new TicketPool("B", 10, 3);
        TicketPool poolC = new TicketPool("C", 10, 2);
        TicketPool[] allPools = {poolA, poolB, poolC};

        Thread timeoutThread = new Thread(new TimeoutManager(allPools));
        timeoutThread.setDaemon(true);
        timeoutThread.start();

        Thread[] counters = new Thread[5];
        BookingCounter[] bookingTasks = new BookingCounter[5];

        for (int i = 0; i < 5; i++) {
            bookingTasks[i] = new BookingCounter("Quầy " + (i + 1), allPools);
            counters[i] = new Thread(bookingTasks[i]);
            counters[i].start();
        }

        for (int i = 0; i < 5; i++) {
            try {
                counters[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        TimeoutManager.isSystemRunning = false;

        System.out.println("\n--- TỔNG KẾT CA LÀM VIỆC ---");
        for (int i = 0; i < 5; i++) {
            System.out.println(bookingTasks[i].counterName + " bán được: " + bookingTasks[i].successfulSales + " vé");
        }
    }
}