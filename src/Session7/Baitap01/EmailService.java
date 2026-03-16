package Session7.Baitap01;

class EmailService {
    public void sendConfirmationEmail(Order order) {
        System.out.println("Đã gửi email đến " + order.customer.getEmail() +
                ": Đơn hàng " + order.orderId + " đã được tạo");
    }
}