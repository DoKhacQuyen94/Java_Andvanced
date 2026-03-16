package Session7.Baitap05;

class InvoiceGenerator {
    public void print(Order order, double total, double discountAmt) {
        System.out.println("\n=== HÓA ĐƠN ===");
        System.out.println("Khách: " + order.customer.name);
        for(OrderItem item : order.items) {
            System.out.println(item.product.name + " - SL: " + item.quantity + " - Tiền: " + (long)item.getSubTotal());
        }
        System.out.println("Tổng tiền: " + (long)total);
        System.out.println("Giảm giá: " + (long)discountAmt);
        System.out.println("Cần thanh toán: " + (long)order.finalAmount);
    }
}