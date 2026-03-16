package Session7.Baitap01;

import java.util.Map;

public class OrderCalculator {
    public double calculateTotal(Order order) {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : order.products.entrySet()) {
            total += entry.getKey().getPrice()* entry.getValue();
        }
        return total;
    }
}