package ASM.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Lớp quản lý kết nối đến cơ sở dữ liệu
 * Sử dụng SQL Server
 */
public class DatabaseConnection {
    // Thông tin kết nối database
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=LibraryManagement;encrypt=false;trustServerCertificate=true";
    private static final String USER = "vanphuoc406"; // Thay đổi username của bạn
    private static final String PASSWORD = "310105"; // Thay đổi password của bạn

    private static Connection connection = null;

    /**
     * Lấy kết nối đến database
     * Sử dụng Singleton pattern
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load SQL Server JDBC Driver
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✓ Kết nối database thành công!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Không tìm thấy SQL Server JDBC Driver!");
            System.err.println("  Thêm dependency: mssql-jdbc vào project");
        } catch (SQLException e) {
            System.err.println("✗ Lỗi kết nối database: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Đóng kết nối database
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Đã đóng kết nối database.");
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi đóng kết nối: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra database đã được khởi tạo chưa
     * Lưu ý: Chạy file LibraryManagement_Complete.sql trước khi sử dụng
     */
    public static void checkDatabase() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            // Kiểm tra các bảng chính có tồn tại không
            String checkQuery = """
                    SELECT COUNT(*) as table_count
                    FROM INFORMATION_SCHEMA.TABLES
                    WHERE TABLE_NAME IN ('Document', 'Copy', 'Member', 'Borrowing', 'BorrowingDetail')
                    """;
            var rs = stmt.executeQuery(checkQuery);

            if (rs.next() && rs.getInt("table_count") == 5) {
                System.out.println("✓ Database đã được khởi tạo đầy đủ.");
            } else {
                System.out.println("⚠ Cảnh báo: Database chưa đầy đủ!");
                System.out.println("  Vui lòng chạy file LibraryManagement_Complete.sql trước.");
            }

        } catch (SQLException e) {
            System.err.println("✗ Lỗi kiểm tra database: " + e.getMessage());
            System.err.println("  Vui lòng chạy file LibraryManagement_Complete.sql để khởi tạo database.");
        }
    }

    /**
     * Test kết nối database
     */
    public static void testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("✓ Test kết nối thành công!");
            try {
                System.out.println("  Database: " + conn.getCatalog());
                System.out.println("  URL: " + conn.getMetaData().getURL());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("✗ Test kết nối thất bại!");
        }
    }
}
