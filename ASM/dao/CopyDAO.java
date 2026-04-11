package ASM.dao;

import ASM.model.Copy;
import ASM.model.CopyStatus;
import ASM.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Data Access Object cho Copy
 * Thuc hien cac thao tac CRUD voi database
 */
public class CopyDAO {
    
    /**
     * Them ban sao moi vao database (alias cho insert)
     */
    public boolean create(Copy copy) throws SQLException {
        return insert(copy);
    }
    
    /**
     * Them ban sao moi vao database
     */
    public boolean insert(Copy copy) throws SQLException {
        String sql = "INSERT INTO copies (id, document_id, status) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, copy.getId());
            pstmt.setString(2, copy.getDocumentId());
            pstmt.setString(3, copy.getStatus().name());
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Da them ban sao vao database: " + copy.getId());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Loi khi them ban sao: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Cap nhat trang thai ban sao
     */
    public boolean update(Copy copy) throws SQLException {
        String sql = "UPDATE copies SET status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, copy.getStatus().name());
            pstmt.setString(2, copy.getId());
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Da cap nhat ban sao: " + copy.getId());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Loi khi cap nhat ban sao: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Xoa ban sao theo ID
     */
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM copies WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Da xoa ban sao ID: " + id);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Loi khi xoa ban sao: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Tim ban sao theo ID
     */
    public Copy findById(String id) {
        String sql = "SELECT * FROM copies WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractCopyFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Loi khi tim ban sao: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Lay tat ca ban sao
     */
    public List<Copy> findAll() throws SQLException {
        List<Copy> copies = new ArrayList<>();
        String sql = "SELECT * FROM copies";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                copies.add(extractCopyFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Loi khi lay danh sach ban sao: " + e.getMessage());
        }
        return copies;
    }
    
    /**
     * Lay tat ca ban sao cua mot tai lieu
     */
    public List<Copy> findByDocumentId(String documentId) throws SQLException {
        List<Copy> copies = new ArrayList<>();
        String sql = "SELECT * FROM copies WHERE document_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, documentId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                copies.add(extractCopyFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Loi khi lay ban sao theo tai lieu: " + e.getMessage());
        }
        return copies;
    }
    
    /**
     * Lay ban sao theo trang thai
     */
    public List<Copy> findByStatus(CopyStatus status) {
        List<Copy> copies = new ArrayList<>();
        String sql = "SELECT * FROM copies WHERE status = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status.name());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                copies.add(extractCopyFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Loi khi lay ban sao theo trang thai: " + e.getMessage());
        }
        return copies;
    }
    
    /**
     * Cap nhat trang thai nhieu ban sao cung luc (su dung Transaction)
     * Thay doi trang thai cua tat ca ban sao thuoc mot tai lieu
     */
    public boolean updateStatusBatch(String documentId, CopyStatus oldStatus, CopyStatus newStatus) {
        String sql = "UPDATE copies SET status = ? WHERE document_id = ? AND status = ?";
        Connection conn = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            // Tat auto-commit de su dung transaction
            conn.setAutoCommit(false);
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newStatus.name());
            pstmt.setString(2, documentId);
            pstmt.setString(3, oldStatus.name());
            
            int rows = pstmt.executeUpdate();
            
            // Commit transaction
            conn.commit();
            System.out.println("Da cap nhat " + rows + " ban sao tu " + 
                             oldStatus + " sang " + newStatus);
            
            pstmt.close();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Loi khi cap nhat batch: " + e.getMessage());
            // Rollback neu co loi
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Da rollback transaction.");
                } catch (SQLException ex) {
                    System.err.println("Loi khi rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            // Bat lai auto-commit
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("Loi khi bat auto-commit: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Them nhieu ban sao cung luc (su dung Transaction)
     */
    public boolean insertBatch(List<Copy> copies) {
        String sql = "INSERT INTO copies (id, document_id, status) VALUES (?, ?, ?)";
        Connection conn = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);  // Bat dau transaction
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            for (Copy copy : copies) {
                pstmt.setString(1, copy.getId());
                pstmt.setString(2, copy.getDocumentId());
                pstmt.setString(3, copy.getStatus().name());
                pstmt.addBatch();  // Them vao batch
            }
            
            int[] results = pstmt.executeBatch();  // Thuc thi batch
            conn.commit();  // Commit transaction
            
            System.out.println("Da them " + results.length + " ban sao vao database.");
            
            pstmt.close();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Loi khi them batch: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Da rollback transaction.");
                } catch (SQLException ex) {
                    System.err.println("Loi khi rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("Loi khi bat auto-commit: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Thong ke so luong ban sao theo trang thai
     */
    public Map<CopyStatus, Long> getStatisticsByStatus() throws SQLException {
        Map<CopyStatus, Long> stats = new HashMap<>();
        String sql = """
            SELECT status, COUNT(*) as count
            FROM copies
            GROUP BY status
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String status = rs.getString("status");
                long count = rs.getLong("count");
                stats.put(CopyStatus.valueOf(status), count);
            }
        }
        return stats;
    }
    
    /**
     * Hien thi thong ke (phuong thuc cu - giu lai de tuong thich)
     */
    public void displayStatisticsByStatus() {
        String sql = """
            SELECT status, COUNT(*) as count
            FROM copies
            GROUP BY status
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== THONG KE BAN SAO THEO TRANG THAI ===");
            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("count");
                System.out.println(CopyStatus.valueOf(status) + ": " + count + " ban");
            }
        } catch (SQLException e) {
            System.err.println("Loi khi thong ke: " + e.getMessage());
        }
    }
    
    /**
     * Lay thong tin ban sao kem thong tin tai lieu (su dung JOIN)
     */
    public void displayCopiesWithDocumentInfo() {
        String sql = """
            SELECT c.id, c.status, d.title, d.author
            FROM copies c
            INNER JOIN documents d ON c.document_id = d.id
            ORDER BY d.title
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== BAN SAO VA THONG TIN TAI LIEU ===");
            while (rs.next()) {
                String copyId = rs.getString("id");
                String status = rs.getString("status");
                String title = rs.getString("title");
                String author = rs.getString("author");
                
                System.out.printf("Ban sao %s [%s] - Tai lieu: %s (Tac gia: %s)%n",
                        copyId, CopyStatus.valueOf(status), title, author);
            }
        } catch (SQLException e) {
            System.err.println("Loi khi JOIN: " + e.getMessage());
        }
    }
    
    /**
     * Trich xuat Copy tu ResultSet
     */
    private Copy extractCopyFromResultSet(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String documentId = rs.getString("document_id");
        String statusStr = rs.getString("status");
        CopyStatus status = CopyStatus.valueOf(statusStr);
        
        return new Copy(id, documentId, status);
    }
}
