package Session13.baitap03;

import Session13.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HospitalDAO {

    public boolean xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection conn = null;
        PreparedStatement psCheck = null;
        PreparedStatement psUpdateWallet = null;
        PreparedStatement psUpdateBed = null;
        PreparedStatement psUpdatePatient = null;

        try {
            conn = DatabaseManager.getConnection();
            // Bắt đầu Transaction: Tắt chế độ tự động lưu
            conn.setAutoCommit(false);

            // --- BẪY 1: KIỂM TRA LOGIC NGHIỆP VỤ (SỐ DƯ TẠM ỨNG) ---
            String sqlCheck = "SELECT balance FROM Patient_Wallet WHERE patient_id = ?";
            psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, maBenhNhan);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                double soDuHienTai = rs.getDouble("balance");
                if (soDuHienTai < tienVienPhi) {
                    // Nếu thiếu tiền, ném ngoại lệ để dừng ngay lập tức và Rollback
                    throw new Exception("LỖI NGHIỆP VỤ: Số dư tạm ứng không đủ để thanh toán viện phí!");
                }
            } else {
                throw new Exception("LỖI: Không tìm thấy ví tiền của bệnh nhân mã " + maBenhNhan);
            }

            // --- THAO TÁC 1: TRỪ TIỀN VIỆN PHÍ ---
            String sql1 = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            psUpdateWallet = conn.prepareStatement(sql1);
            psUpdateWallet.setDouble(1, tienVienPhi);
            psUpdateWallet.setInt(2, maBenhNhan);
            int row1 = psUpdateWallet.executeUpdate();
            // BẪY 2: Kiểm tra Row Affected
            if (row1 == 0) throw new SQLException("LỖI DỮ LIỆU ẢO: Không thể trừ tiền (Bệnh nhân không tồn tại)");

            // --- THAO TÁC 2: GIẢI PHÓNG GIƯỜNG BỆNH ---
            // Giả sử bảng Patient có cột bed_id để liên kết
            String sql2 = "UPDATE Beds SET status = 'Empty' WHERE bed_id = (SELECT bed_id FROM Patients WHERE id = ?)";
            psUpdateBed = conn.prepareStatement(sql2);
            psUpdateBed.setInt(1, maBenhNhan);
            int row2 = psUpdateBed.executeUpdate();
            // BẪY 2: Nếu bệnh nhân không có giường hoặc sai ID, row2 sẽ bằng 0
            if (row2 == 0) throw new SQLException("LỖI DỮ LIỆU ẢO: Không thể giải phóng giường bệnh");

            // --- THAO TÁC 3: CẬP NHẬT TRẠNG THÁI XUẤT VIỆN ---
            String sql3 = "UPDATE Patients SET status = 'Discharged' WHERE id = ?";
            psUpdatePatient = conn.prepareStatement(sql3);
            psUpdatePatient.setInt(1, maBenhNhan);
            int row3 = psUpdatePatient.executeUpdate();
            // BẪY 2: Kiểm tra Row Affected
            if (row3 == 0) throw new SQLException("LỖI DỮ LIỆU ẢO: Không thể cập nhật trạng thái xuất viện");

            // --- CHỐT GIAO DỊCH ---
            conn.commit();
            System.out.println("Thành công: Bệnh nhân " + maBenhNhan + " đã hoàn tất thủ tục xuất viện.");
            return true;

        } catch (Exception e) {
            // Nếu gặp bất kỳ lỗi nào (Bẫy 1, Bẫy 2 hoặc lỗi mạng), thực hiện Rollback ngay
            System.err.println("GIAO DỊCH THẤT BẠI. Đang khôi phục dữ liệu...");
            System.err.println("Lý do: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            // Đóng tất cả kết nối và đưa AutoCommit về mặc định
            try {
                if (psCheck != null) psCheck.close();
                if (psUpdateWallet != null) psUpdateWallet.close();
                if (psUpdateBed != null) psUpdateBed.close();
                if (psUpdatePatient != null) psUpdatePatient.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}