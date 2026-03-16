package Session7.Baitap02;

public class OrderCalculator {
    private DiscountStrategy discountStrategy;

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculateFinalPrice(double totalAmount) {
        if (discountStrategy == null) return totalAmount;
        return discountStrategy.applyDiscount(totalAmount);
    }
}