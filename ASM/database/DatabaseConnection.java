package ASM.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Lop quan ly ket noi den co so du lieu
 * Su dung SQL Server
 */
public class DatabaseConnection {
    // Thong tin ket noi database
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=LibraryManagement;encrypt=false;trustServerCertificate=true";
    private static final String USER = "vanphuoc406"; // Thay doi username cua ban
    private static final String PASSWORD = "310105"; // Thay doi password cua ban

    private static Connection connection = null;

    /**
     * Lay ket noi den database
     * Su dung Singleton pattern
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load SQL Server JDBC Driver
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
     * Dong ket noi database
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
     * Kiem tra database da duoc khoi tao chua
     * Luu y: Chay file LibraryManagement_Complete.sql truoc khi su dung
     */
    public static void checkDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Kiem tra cac bang chinh co ton tai khong
            String checkQuery = """
                    SELECT COUNT(*) as table_count
                    FROM INFORMATION_SCHEMA.TABLES
                    WHERE TABLE_NAME IN ('Document', 'Copy', 'Member', 'Borrowing', 'BorrowingDetail')
                    """;
            var rs = stmt.executeQuery(checkQuery);

            if (rs.next() && rs.getInt("table_count") == 5) {
                System.out.println("Database da duoc khoi tao day du.");
            } else {
                System.out.println("Canh bao: Database chua day du!");
                System.out.println("Vui long chay file LibraryManagement_Complete.sql truoc.");
            }

        } catch (SQLException e) {
            System.err.println("Loi kiem tra database: " + e.getMessage());
            System.err.println("Vui long chay file LibraryManagement_Complete.sql de khoi tao database.");
        }
    }

    /**
     * Test ket noi database
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