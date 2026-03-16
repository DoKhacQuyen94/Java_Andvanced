package Session7.Baitap05;

class OrderItem {
    Product product;
    int quantity;
    public OrderItem(Product product, int quantity) {
        this.product = product; this.quantity = quantity;
    }
    public double getSubTotal() { return product.price * quantity; }
}