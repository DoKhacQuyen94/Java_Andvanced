package Session7.Baitap03;

class CODPayment implements CODPayable {
    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán COD: " + (long)amount + " - Thành công");
    }
}