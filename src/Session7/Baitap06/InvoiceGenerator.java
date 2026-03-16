package Session7.Baitap06;

class InvoiceGenerator {
    public void print(Order order, double total, double discountAmt) {
        System.out.println("\n=== HÓA ĐƠN ===");
        System.out.println("Khách: " + order.getCustomer().getName());
        for(OrderItem item : order.getItems()) {
            System.out.println(item.getProduct().getName() + " - SL: " + item.getQuantity() + " - Tiền: " + (long)item.getSubTotal());
        }
        System.out.println("Tổng tiền: " + (long)total);
        System.out.println("Giảm giá: " + (long)discountAmt);
        System.out.println("Cần thanh toán: " + (long) order.getFinalAmount());
    }
}