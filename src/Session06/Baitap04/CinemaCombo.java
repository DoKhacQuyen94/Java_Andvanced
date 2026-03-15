package Session06.Baitap04;

import java.util.ArrayList;
import java.util.List;

class Ticket {
    String ticketId;
    String roomName;
    boolean isSold;

    public Ticket(String ticketId, String roomName) {
        this.ticketId = ticketId;
        this.roomName = roomName;
        this.isSold = false;
    }
}

class TicketPool {
    String roomName;
    List<Ticket> tickets;

    public TicketPool(String roomName, int numberOfTickets) {
        this.roomName = roomName;
        this.tickets = new ArrayList<>();
        for (int i = 1; i <= numberOfTickets; i++) {
            String id = roomName + "-" + String.format("%03d", i);
            tickets.add(new Ticket(id, roomName));
        }
    }

    public synchronized Ticket trySellTicket() {
        for (Ticket t : tickets) {
            if (!t.isSold) {
                t.isSold = true;
                return t;
            }
        }
        return null; // Hết vé
    }

    public synchronized void returnTicket(Ticket t) {
        if (t != null) {
            t.isSold = false;
        }
    }

    public synchronized int getRemainingCount() {
        int count = 0;
        for (Ticket t : tickets) {
            if (!t.isSold) count++;
        }
        return count;
    }
}

class BookingCounter implements Runnable {
    String counterName;
    TicketPool roomA;
    TicketPool roomB;
    boolean isReverseOrder;
    int soldComboCount = 0;

    public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB, boolean isReverseOrder) {
        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
        this.isReverseOrder = isReverseOrder;
    }

    public boolean sellCombo(TicketPool firstLock, TicketPool secondLock) {
        synchronized (firstLock) {
            System.out.println(counterName + ": Đã lấy khóa phòng " + firstLock.roomName + " - Đang chờ phòng " + secondLock.roomName + "...");

            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            synchronized (secondLock) {
                Ticket t1 = firstLock.trySellTicket();
                Ticket t2 = secondLock.trySellTicket();
                if (t1 != null && t2 != null) {
                    System.out.println(counterName + " bán combo thành công: " + t1.ticketId + " & " + t2.ticketId);
                    soldComboCount++;
                    return true;
                } else {
                    firstLock.returnTicket(t1);
                    secondLock.returnTicket(t2);
                    System.out.println(counterName + ": Hết vé phòng " + (t1 == null ? firstLock.roomName : secondLock.roomName) + ", bán combo thất bại. Đã hoàn tiền!");
                    return false;
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            TicketPool lock1 = roomA;
            TicketPool lock2 = roomB;
            if (isReverseOrder) {
                lock1 = roomB;
                lock2 = roomA;
            }

            // Thực hiện bán combo
            boolean success = sellCombo(lock1, lock2);

            if (!success) {
                break;
            }

            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}

public class CinemaCombo {
    private static final boolean IS_SAFE_MODE = false;

    public static void main(String[] args) {
        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);

        BookingCounter counter1;
        BookingCounter counter2;

        if (IS_SAFE_MODE) {
            System.out.println("--- CHẾ ĐỘ AN TOÀN: Các quầy đều khóa theo thứ tự A -> B ---");
            counter1 = new BookingCounter("Quầy 1", roomA, roomB, false);
            counter2 = new BookingCounter("Quầy 2", roomA, roomB, false);
        } else {
            System.out.println("--- CHẾ ĐỘ DEADLOCK: Quầy 1 khóa A->B, Quầy 2 khóa B->A ---");
            counter1 = new BookingCounter("Quầy 1", roomA, roomB, false);
            counter2 = new BookingCounter("Quầy 2", roomA, roomB, true);
        }

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        t1.start();
        t2.start();

        try {
            t1.join(3000);
            t2.join(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (t1.isAlive() || t2.isAlive()) {
            System.out.println("\n[CẢNH BÁO] Chương trình đã bị treo (DEADLOCK)!");
            System.exit(1);
        }

        System.out.println("\nKết thúc chương trình");
        System.out.println("Quầy 1 bán được: " + counter1.soldComboCount + " combo");
        System.out.println("Quầy 2 bán được: " + counter2.soldComboCount + " combo");
        System.out.println("Vé còn lại phòng A: " + roomA.getRemainingCount());
        System.out.println("Vé còn lại phòng B: " + roomB.getRemainingCount());
    }
}
