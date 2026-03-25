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
 * Thực hiện các thao tác CRUD với database
 */
public class CopyDAO {
    
    /**
     * Thêm bản sao mới vào database (alias cho insert)
     */
    public boolean create(Copy copy) throws SQLException {
        return insert(copy);
    }
    
    /**
     * Thêm bản sao mới vào database
     */
    public boolean insert(Copy copy) throws SQLException {
        String sql = "INSERT INTO DOCUMENT_COPY (id, document_id, status) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, copy.getId());
            pstmt.setString(2, copy.getDocumentId());
            pstmt.setString(3, copy.getStatus().name());
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✓ Đã thêm bản sao vào database: " + copy.getId());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi thêm bản sao: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Cập nhật trạng thái bản sao
     */
    public boolean update(Copy copy) throws SQLException {
        String sql = "UPDATE DOCUMENT_COPY SET status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, copy.getStatus().name());
            pstmt.setString(2, copy.getId());
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✓ Đã cập nhật bản sao: " + copy.getId());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi cập nhật bản sao: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Xóa bản sao theo ID
     */
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM DOCUMENT_COPY WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✓ Đã xóa bản sao ID: " + id);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi xóa bản sao: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Tìm bản sao theo ID
     */
    public Copy findById(String id) {
        String sql = "SELECT * FROM DOCUMENT_COPY WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractCopyFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi tìm bản sao: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Lấy tất cả bản sao
     */
    public List<Copy> findAll() throws SQLException {
        List<Copy> copies = new ArrayList<>();
        String sql = "SELECT * FROM DOCUMENT_COPY";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                copies.add(extractCopyFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi lấy danh sách bản sao: " + e.getMessage());
        }
        return copies;
    }
    
    /**
     * Lấy tất cả bản sao của một tài liệu
     */
    public List<Copy> findByDocumentId(String documentId) throws SQLException {
        List<Copy> copies = new ArrayList<>();
        String sql = "SELECT * FROM DOCUMENT_COPY WHERE document_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, documentId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                copies.add(extractCopyFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi lấy bản sao theo tài liệu: " + e.getMessage());
        }
        return copies;
    }
    
    /**
     * Lấy bản sao theo trạng thái
     */
    public List<Copy> findByStatus(CopyStatus status) {
        List<Copy> copies = new ArrayList<>();
        String sql = "SELECT * FROM DOCUMENT_COPY WHERE status = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status.name());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                copies.add(extractCopyFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi lấy bản sao theo trạng thái: " + e.getMessage());
        }
        return copies;
    }
    
    /**
     * Cập nhật trạng thái nhiều bản sao cùng lúc (sử dụng Transaction)
     * Thay đổi trạng thái của tất cả bản sao thuộc một tài liệu
     */
    public boolean updateStatusBatch(String documentId, CopyStatus oldStatus, CopyStatus newStatus) {
        String sql = "UPDATE DOCUMENT_COPY SET status = ? WHERE document_id = ? AND status = ?";
        Connection conn = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            // Tắt auto-commit để sử dụng transaction
            conn.setAutoCommit(false);
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newStatus.name());
            pstmt.setString(2, documentId);
            pstmt.setString(3, oldStatus.name());
            
            int rows = pstmt.executeUpdate();
            
            // Commit transaction
            conn.commit();
            System.out.println("✓ Đã cập nhật " + rows + " bản sao từ " + 
                             oldStatus + " sang " + newStatus);
            
            pstmt.close();
            return true;
            
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi cập nhật batch: " + e.getMessage());
            // Rollback nếu có lỗi
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("⚠ Đã rollback transaction.");
                } catch (SQLException ex) {
                    System.err.println("✗ Lỗi khi rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            // Bật lại auto-commit
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("✗ Lỗi khi bật auto-commit: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Thêm nhiều bản sao cùng lúc (sử dụng Transaction)
     */
    public boolean insertBatch(List<Copy> copies) {
        String sql = "INSERT INTO DOCUMENT_COPY (id, document_id, status) VALUES (?, ?, ?)";
        Connection conn = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);  // Bắt đầu transaction
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            for (Copy copy : copies) {
                pstmt.setString(1, copy.getId());
                pstmt.setString(2, copy.getDocumentId());
                pstmt.setString(3, copy.getStatus().name());
                pstmt.addBatch();  // Thêm vào batch
            }
            
            int[] results = pstmt.executeBatch();  // Thực thi batch
            conn.commit();  // Commit transaction
            
            System.out.println("✓ Đã thêm " + results.length + " bản sao vào database.");
            
            pstmt.close();
            return true;
            
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi thêm batch: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("⚠ Đã rollback transaction.");
                } catch (SQLException ex) {
                    System.err.println("✗ Lỗi khi rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("✗ Lỗi khi bật auto-commit: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Thống kê số lượng bản sao theo trạng thái
     */
    public Map<CopyStatus, Long> getStatisticsByStatus() throws SQLException {
        Map<CopyStatus, Long> stats = new HashMap<>();
        String sql = """
            SELECT status, COUNT(*) as count
            FROM DOCUMENT_COPY
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
     * Hiển thị thống kê (phương thức cũ - giữ lại để tương thích)
     */
    public void displayStatisticsByStatus() {
        String sql = """
            SELECT status, COUNT(*) as count
            FROM DOCUMENT_COPY
            GROUP BY status
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== THỐNG KÊ BẢN SAO THEO TRẠNG THÁI ===");
            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("count");
                System.out.println(CopyStatus.valueOf(status) + ": " + count + " bản");
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi thống kê: " + e.getMessage());
        }
    }
    
    /**
     * Lấy thông tin bản sao kèm thông tin tài liệu (sử dụng JOIN)
     */
    public void displayCopiesWithDocumentInfo() {
        String sql = """
            SELECT c.id, c.status, d.title, d.author
            FROM DOCUMENT_COPY c
            INNER JOIN DOCUMENT d ON c.document_id = d.id
            ORDER BY d.title
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== BẢN SAO VÀ THÔNG TIN TÀI LIỆU ===");
            while (rs.next()) {
                String copyId = rs.getString("id");
                String status = rs.getString("status");
                String title = rs.getString("title");
                String author = rs.getString("author");
                
                System.out.printf("Bản sao %s [%s] - Tài liệu: %s (Tác giả: %s)%n",
                        copyId, CopyStatus.valueOf(status), title, author);
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi JOIN: " + e.getMessage());
        }
    }
    
    /**
     * Trích xuất Copy từ ResultSet
     */
    private Copy extractCopyFromResultSet(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String documentId = rs.getString("document_id");
        String statusStr = rs.getString("status");
        CopyStatus status = CopyStatus.valueOf(statusStr);
        
        return new Copy(id, documentId, status);
    }
}
