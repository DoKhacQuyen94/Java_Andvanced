package Session7.Baitap03;

class MomoPayment implements EWalletPayable {
    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + (long)amount + " - Thành công");
    }
}