package Session7.Baitap06;

class PercentageDiscount implements DiscountStrategy {
    private double pct;
    public PercentageDiscount(double pct) { this.pct = pct; }
    @Override public double applyDiscount(double amt) { return amt * (1 - pct/100); }
}
