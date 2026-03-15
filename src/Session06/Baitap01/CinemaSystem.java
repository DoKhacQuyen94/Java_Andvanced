package Session06.Baitap01;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public synchronized Ticket sellTicket() {
        for (Ticket t : tickets) {
            if (!t.isSold) {
                t.isSold = true;
                return t;
            }
        }
        return null;
    }
    public synchronized boolean hasTickets() {
        for (Ticket t : tickets) {
            if (!t.isSold) {
                return true;
            }
        }
        return false;
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
    int soldCount;
    Random random;

    public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB) {
        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
        this.soldCount = 0;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (roomA.hasTickets() || roomB.hasTickets()) {
            boolean chooseRoomA = random.nextBoolean();
            TicketPool targetRoom = chooseRoomA ? roomA : roomB;
            TicketPool otherRoom = chooseRoomA ? roomB : roomA;

            System.out.println(counterName + " bán vé phòng " + targetRoom.roomName);
            Ticket ticket = targetRoom.sellTicket();
            if (ticket == null) {
                ticket = otherRoom.sellTicket();
            }
            if (ticket != null) {
                soldCount++;
                System.out.println(counterName + " đã bán vé " + ticket.ticketId);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
public class CinemaSystem {
    public static void main(String[] args) {
        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);
        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, roomB);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomA, roomB);
        Thread thread1 = new Thread(counter1);
        Thread thread2 = new Thread(counter2);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nKết thúc chương trình");
        System.out.println("Quầy 1 bán được: " + counter1.soldCount + " vé");
        System.out.println("Quầy 2 bán được: " + counter2.soldCount + " vé");
        System.out.println("Vé còn lại phòng A: " + roomA.getRemainingCount());
        System.out.println("Vé còn lại phòng B: " + roomB.getRemainingCount());
    }
}