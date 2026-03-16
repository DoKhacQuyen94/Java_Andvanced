package Session7.Baitap06;

class CreditCardPayment implements PaymentMethod {
    @Override public void pay(double amt) { System.out.println("Xử lý thẻ tín dụng: " + (long)amt); }
}