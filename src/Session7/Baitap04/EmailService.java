package Session7.Baitap04;

class EmailService implements NotificationService {
    public void send(String message, String recipient) {
        System.out.println("Gửi email: " + message + " đến " + recipient);
    }
}