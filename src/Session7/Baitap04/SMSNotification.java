package Session7.Baitap04;

class SMSNotification implements NotificationService {
    public void send(String message, String recipient) {
        System.out.println("Gửi SMS: " + message + " đến " + recipient);
    }
}