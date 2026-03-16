package Session7.Baitap02;


public class Main {
    public static void main(String[] args) {
        double totalAmount = 1000000;
        OrderCalculator calculator = new OrderCalculator();

        // 1. Áp dụng giảm 10%
        calculator.setDiscountStrategy(new PercentageDiscount(10));
        System.out.println("Áp dụng PercentageDiscount 10%: " + (long)calculator.calculateFinalPrice(totalAmount));

        // 2. Áp dụng giảm cố định 50.000
        calculator.setDiscountStrategy(new FixedDiscount(50000));
        System.out.println("Áp dụng FixedDiscount 50.000: " + (long)calculator.calculateFinalPrice(totalAmount));

        // 3. Áp dụng không giảm giá
        calculator.setDiscountStrategy(new NoDiscount());
        System.out.println("Áp dụng NoDiscount: " + (long)calculator.calculateFinalPrice(totalAmount));

    }
}