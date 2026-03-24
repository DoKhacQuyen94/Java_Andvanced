package Session13.baitap02;

import Session13.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class thanhToan {
    public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            conn = DatabaseManager.getConnection();
            conn.setAutoCommit(false);

            String sqlDeductWallet = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            ps1 = conn.prepareStatement(sqlDeductWallet);
            ps1.setDouble(1, amount);
            ps1.setInt(2, patientId);
            ps1.executeUpdate();

            String sqlUpdateInvoice = "UPDATE Invoicesss SET status = 'PAID' WHERE invoice_id = ?";
            ps2 = conn.prepareStatement(sqlUpdateInvoice);
            ps2.setInt(1, invoiceId);
            ps2.executeUpdate();
            conn.commit();
            System.out.println("Thanh toán hoàn tất!");

        } catch (SQLException e) {
            System.err.println("Lỗi hệ thống: Không thể hoàn tất thanh toán. Chi tiết: " + e.getMessage());

            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Đã thực hiện Rollback: Số dư trong ví bệnh nhân được giữ nguyên.");
                } catch (SQLException ex) {
                    // Lỗi khi đang cố gắng rollback (hiếm gặp nhưng vẫn cần bọc lại)
                    System.err.println("Lỗi nghiêm trọng khi thực hiện Rollback: " + ex.getMessage());
                }
            }
        } finally {
            try {
                if (ps1 != null) ps1.close();
                if (ps2 != null) ps2.close();
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
