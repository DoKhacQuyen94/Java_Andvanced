package Session13.baitap04;

import Session13.DatabaseManager;

import java.sql.*;
import java.util.*;

public class DashboardDAO {

    public List<BenhNhanDTO> getDashboardData() {
        Map<Integer, BenhNhanDTO> mapBenhNhan = new LinkedHashMap<>();

        String sql = "SELECT bn.id AS bnId, bn.ten, dv.id AS dvId, dv.tenDichVu, dv.gia " +
                "FROM BenhNhan bn " +
                "LEFT JOIN DichVuSuDung dv ON bn.id = dv.maBenhNhan " +
                "ORDER BY bn.id";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int maBN = rs.getInt("bnId");

                BenhNhanDTO dto = mapBenhNhan.get(maBN);

                if (dto == null) {
                    dto = new BenhNhanDTO();
                    dto.setMaBenhNhan(maBN);
                    dto.setTenBenhNhan(rs.getString("ten"));
                    dto.setDsDichVu(new ArrayList<>());
                    mapBenhNhan.put(maBN, dto);
                }

                int maDV = rs.getInt("dvId");
                if (!rs.wasNull()) {
                    DichVu dv = new DichVu();
                    dv.setMaDichVu(maDV);
                    dv.setTenDichVu(rs.getString("tenDichVu"));
                    dv.setGia(rs.getDouble("gia"));

                    dto.getDsDichVu().add(dv);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(mapBenhNhan.values());
    }
}