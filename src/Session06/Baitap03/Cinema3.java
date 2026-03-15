package Session06.Baitap03;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class Cinema3  {
//    public static void main(String[] args) {
//        List<String> listName = new ArrayList<String>();
//        listName.add("Đỗ Khắc Quyền");
//        listName.add("Vũ Văn Đoàn");
//        listName.add("Nguyễn Việt Nam");
//        listName.add("Phạm Thành Đạt");
//        listName.add("Nguyễn Thị Kim Lệ");
//        listName.add("Nguyễn Việt Anh");
//        listName.add("Đỗ Đức Cương");
//        displayName inName= new displayName(listName);
//        Thread thread1 = new Thread(inName);
//        thread1.start();
//    }
//}
//class displayName implements Runnable{
//    List<String> list;
//    Random random;
//    public displayName(List<String> list){
//        this.list = list;
//        this.random = new Random();
//    }
//    @Override
//    public void run() {
//        for (int i = 0; i < list.size(); i++) {
//            try {
//                Thread.sleep(1000);
//            }catch (InterruptedException e){
//                Thread.currentThread().interrupt();
//            }
//            System.out.println(list.get(random.nextInt(list.size())));
//        }
//    }
//}

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
    int lastTicketIndex = 0;

    public TicketPool(String roomName, int numberOfTickets) {
        this.roomName = roomName;
        this.tickets = new ArrayList<>();
        this.lastTicketIndex = 0;
        addTickets(numberOfTickets);
    }
    public synchronized void addTickets(int count){
        for (int i = 1; i <= count; i++) {
            lastTicketIndex++;
            String id = roomName + "-" + String.format("%03d", lastTicketIndex);
            tickets.add(new Ticket(id, roomName));
        }
        notifyAll();
    }
    public synchronized Ticket sellTicket(String counterName) {
        while (!hasTickets()) {
            if (TicketSupplier.isFinished) {
                return null;
            }
            try {
                System.out.println(counterName + ": Hết vé phòng " + roomName + ", đang chờ...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
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

    public synchronized void notifyClosing() {
        notifyAll();
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
        while (true) {
            boolean chooseRoomA = random.nextBoolean();
            TicketPool targetRoom = chooseRoomA ? roomA : roomB;
            TicketPool otherRoom = chooseRoomA ? roomB : roomA;
            Ticket ticket = targetRoom.sellTicket(counterName);
            if (ticket == null) {
                ticket = otherRoom.sellTicket(counterName);
            }

            if (ticket != null) {
                soldCount++;
                System.out.println(counterName + " đã bán vé " + ticket.ticketId);
            } else {
                break;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
class TicketSupplier implements Runnable{
    TicketPool roomA;
    TicketPool roomB;
    int supplyCount;
    int interval;
    int rounds;
    public static volatile boolean isFinished = false;
    public TicketSupplier(TicketPool roomA, TicketPool roomB, int supplyCount, int interval, int rounds) {
        this.roomA = roomA;
        this.roomB = roomB;
        this.supplyCount = supplyCount;
        this.interval = interval;
        this.rounds = rounds;
    }
    @Override
    public void run() {
        for (int i = 0; i < rounds; i++) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            roomA.addTickets(supplyCount);
            roomB.addTickets(supplyCount);
            System.out.println("\n--- Nhà cung cấp: Đã thêm " + supplyCount + " vé vào phòng A ---");
            System.out.println("--- Nhà cung cấp: Đã thêm " + supplyCount + " vé vào phòng B ---\n");
        }
        isFinished = true;
        roomA.notifyClosing();
        roomB.notifyClosing();
    }

}
public class Cinema3 {
    public static void main(String[] args) {
        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);
        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, roomB);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomA, roomB);
        TicketSupplier supplier = new TicketSupplier(roomA, roomB, 3, 3000, 2);
        Thread thread1 = new Thread(counter1);
        Thread thread2 = new Thread(counter2);
        Thread tSupplier = new Thread(supplier);
        thread1.start();
        thread2.start();
        tSupplier.start();
        try {
            thread1.join();
            thread2.join();
            tSupplier.join();
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