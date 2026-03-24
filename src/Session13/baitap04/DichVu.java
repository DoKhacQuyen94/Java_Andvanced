package Session13.baitap04;

public class DichVu {
    private int maDichVu;
    private String tenDichVu;
    private double gia;

    // Constructors
    public DichVu() {}

    public DichVu(int maDichVu, String tenDichVu, double gia) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.gia = gia;
    }

    public int getMaDichVu() { return maDichVu; }
    public void setMaDichVu(int maDichVu) { this.maDichVu = maDichVu; }

    public String getTenDichVu() { return tenDichVu; }
    public void setTenDichVu(String tenDichVu) { this.tenDichVu = tenDichVu; }

    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
}