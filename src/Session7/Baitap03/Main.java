package Session7.Baitap03;

public class Main {
    public static void main(String[] args) {
        PaymentProcessor codProcessor = new PaymentProcessor(new CODPayment());
        codProcessor.process(500000);

        PaymentProcessor cardProcessor = new PaymentProcessor(new CreditCardPayment());
        cardProcessor.process(1000000);

        PaymentProcessor momoProcessor = new PaymentProcessor(new MomoPayment());
        momoProcessor.process(750000);

        System.out.println("--- Kiểm tra LSP ---");
        PaymentMethod myPayment = new CreditCardPayment();
        myPayment.pay(1000000);

        myPayment = new MomoPayment();
        myPayment.pay(1000000);
    }
}