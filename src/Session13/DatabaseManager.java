package Session13;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/Doctor";
    private static final String USER = "root";
    private static final String PASS = "09042006";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy thư viện MySQL Connector!");
            throw new SQLException(e);
        }
    }
}