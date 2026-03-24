package Session13.baitap04;

import java.util.List;
import java.util.ArrayList;

public class BenhNhanDTO {
    private int maBenhNhan;
    private String tenBenhNhan;

    private List<DichVu> dsDichVu;

    public BenhNhanDTO() {
        // Khởi tạo sẵn ArrayList để tránh lỗi NullPointerException khi gọi .add()
        this.dsDichVu = new ArrayList<>();
    }

    // Getters and Setters
    public int getMaBenhNhan() { return maBenhNhan; }
    public void setMaBenhNhan(int maBenhNhan) { this.maBenhNhan = maBenhNhan; }

    public String getTenBenhNhan() { return tenBenhNhan; }
    public void setTenBenhNhan(String tenBenhNhan) { this.tenBenhNhan = tenBenhNhan; }

    public List<DichVu> getDsDichVu() { return dsDichVu; }
    public void setDsDichVu(List<DichVu> dsDichVu) { this.dsDichVu = dsDichVu; }

    // Ghi đè toString để dễ dàng debug/kiểm tra dữ liệu in ra màn hình console
    @Override
    public String toString() {
        return "Bệnh nhân: " + tenBenhNhan + " (ID: " + maBenhNhan + ") - Số dịch vụ: " + dsDichVu.size();
    }
}