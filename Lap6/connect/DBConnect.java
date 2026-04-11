package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Lớp quản lý kết nối JDBC với SQL Server
 */
public class DBConnect {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=lab06_jdbc1;encrypt=false;trustServerCertificate=true";
    private static final String USER = "vanphuoc406";
    private static final String PASSWORD = "310105";
    private static Connection connection = null;

    /**
     * Tạo kết nối đến database
     * 
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Ket noi database thanh cong!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Khong tim thay SQL Server JDBC Driver!");
            System.err.println("Them dependency: mssql-jdbc vao project");
        } catch (SQLException e) {
            System.err.println("Loi ket noi database: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Đóng kết nối
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Da dong ket noi database.");
            }
        } catch (SQLException e) {
            System.err.println("Loi khi dong ket noi: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra database đã được khởi tạo đầy đủ chưa
     */
    public static void checkDatabase() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            String sql = "SELECT COUNT(*) as table_count\n" +
                    "FROM INFORMATION_SCHEMA.TABLES\n" +
                    "WHERE TABLE_NAME IN ('student', 'tree')";

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next() && rs.getInt("table_count") == 2) {
                System.out.println("Database da duoc khoi tao day du.");
            } else {
                System.out.println("Canh bao: Database chua day du!");
                System.out.println("Vui long chay file create_database.sql truoc.");
            }
        } catch (SQLException e) {
            System.err.println("Loi kiem tra database: " + e.getMessage());
            System.err.println("Vui long chay file create_database.sql de khoi tao database.");
        }
    }

    /**
     * Test kết nối
     */
    public static void testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Test ket noi thanh cong!");
            try {
                System.out.println("Database: " + conn.getCatalog());
                System.out.println("URL: " + conn.getMetaData().getURL());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Test ket noi that bai!");
        }
    }
}
