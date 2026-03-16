package Session7.Baitap05;

class EmailNotification implements NotificationService {
    @Override public void send(String m, String r) { System.out.println("Gửi Email đến " + r + ": " + m); }
}