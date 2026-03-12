package Session05.fastfood.model;

public class Dessert extends MenuItem {
    public Dessert(String id, String name, double price, int stock) {
        super(id, name, price, stock);
    }

    @Override
    public double calculatePrice() {
        return getPrice();
    }
}
