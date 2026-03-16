package Session7.Baitap03;

class CreditCardPayment implements CardPayable {
    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng: " + (long)amount + " - Thành công");
    }
}