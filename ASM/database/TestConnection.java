package ASM.database;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("=== Testing Database Connection ===");
        
        // Test connection
        DatabaseConnection.testConnection();
        
        // Check database tables
        DatabaseConnection.checkDatabase();
        
        // Close connection
        DatabaseConnection.closeConnection();
    }
}
