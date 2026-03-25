package ASM.dao;

import ASM.model.Document;
import ASM.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object cho Document
 * Thực hiện các thao tác CRUD với database
 */
public class DocumentDAO {
    
    /**
     * Thêm tài liệu mới vào database (alias cho insert)
     */
    public boolean create(Document document) throws SQLException {
        return insert(document);
    }
    
    /**
     * Thêm tài liệu mới vào database
     */
    public boolean insert(Document document) throws SQLException {
        String sql = "INSERT INTO DOCUMENT (id, title, author, category) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, document.getId());
            pstmt.setString(2, document.getTitle());
            pstmt.setString(3, document.getAuthor());
            pstmt.setString(4, document.getCategory());
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✓ Đã thêm tài liệu vào database: " + document.getTitle());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi thêm tài liệu: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Cập nhật thông tin tài liệu
     */
    public boolean update(Document document) throws SQLException {
        String sql = "UPDATE DOCUMENT SET title = ?, author = ?, category = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, document.getTitle());
            pstmt.setString(2, document.getAuthor());
            pstmt.setString(3, document.getCategory());
            pstmt.setString(4, document.getId());
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✓ Đã cập nhật tài liệu: " + document.getTitle());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi cập nhật tài liệu: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Xóa tài liệu theo ID
     * Cascade delete sẽ tự động xóa các bản sao liên quan
     */
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM DOCUMENT WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✓ Đã xóa tài liệu ID: " + id);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi xóa tài liệu: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Tìm tài liệu theo ID
     */
    public Document findById(String id) {
        String sql = "SELECT * FROM DOCUMENT WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractDocumentFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi tìm tài liệu: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Lấy tất cả tài liệu
     */
    public List<Document> findAll() throws SQLException {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM DOCUMENT";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                documents.add(extractDocumentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi lấy danh sách tài liệu: " + e.getMessage());
        }
        return documents;
    }
    
    /**
     * Tìm kiếm tài liệu theo nhiều tiêu chí
     */
    public List<Document> search(String title, String author, String category) {
        List<Document> documents = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM DOCUMENT WHERE 1=1");
        
        // Xây dựng câu query động
        if (title != null && !title.isEmpty()) {
            sql.append(" AND title LIKE ?");
        }
        if (author != null && !author.isEmpty()) {
            sql.append(" AND author LIKE ?");
        }
        if (category != null && !category.isEmpty()) {
            sql.append(" AND category = ?");
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            if (title != null && !title.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + title + "%");
            }
            if (author != null && !author.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + author + "%");
            }
            if (category != null && !category.isEmpty()) {
                pstmt.setString(paramIndex++, category);
            }
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                documents.add(extractDocumentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi tìm kiếm: " + e.getMessage());
        }
        return documents;
    }
    
    /**
     * Sắp xếp tài liệu theo cột
     */
    public List<Document> findAllSorted(String sortBy, boolean ascending) {
        List<Document> documents = new ArrayList<>();
        String order = ascending ? "ASC" : "DESC";
        String sql = "SELECT * FROM DOCUMENT ORDER BY " + sortBy + " " + order;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                documents.add(extractDocumentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi sắp xếp: " + e.getMessage());
        }
        return documents;
    }
    
    /**
     * Phân trang danh sách tài liệu
     */
    public List<Document> findWithPagination(int page, int pageSize) {
        List<Document> documents = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM DOCUMENT LIMIT ? OFFSET ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, offset);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                documents.add(extractDocumentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi phân trang: " + e.getMessage());
        }
        return documents;
    }
    
    /**
     * Đếm tổng số tài liệu
     */
    public int count() {
        String sql = "SELECT COUNT(*) FROM DOCUMENT";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi đếm tài liệu: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Tìm kiếm tài liệu theo tiêu đề
     */
    public List<Document> searchByTitle(String keyword) throws SQLException {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM DOCUMENT WHERE LOWER(title) LIKE LOWER(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                documents.add(extractDocumentFromResultSet(rs));
            }
        }
        return documents;
    }
    
    /**
     * Tìm kiếm tài liệu theo tác giả
     */
    public List<Document> searchByAuthor(String author) throws SQLException {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM DOCUMENT WHERE LOWER(author) LIKE LOWER(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + author + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                documents.add(extractDocumentFromResultSet(rs));
            }
        }
        return documents;
    }
    
    /**
     * Lấy tất cả tài liệu đã sắp xếp theo tiêu đề
     */
    public List<Document> findAllSortedByTitle() throws SQLException {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM DOCUMENT ORDER BY title";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                documents.add(extractDocumentFromResultSet(rs));
            }
        }
        return documents;
    }
    
    /**
     * Lấy tài liệu kèm số lượng bản sao (sử dụng JOIN)
     */
    public List<Document> findAllWithCopyCount() {
        List<Document> documents = new ArrayList<>();
        String sql = """
            SELECT d.*, COUNT(c.id) as copy_count
            FROM DOCUMENT d
            LEFT JOIN DOCUMENT_COPY c ON d.id = c.document_id
            GROUP BY d.id, d.title, d.author, d.category
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Document doc = extractDocumentFromResultSet(rs);
                int copyCount = rs.getInt("copy_count");
                System.out.println(doc + " - Số bản sao: " + copyCount);
                documents.add(doc);
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi khi JOIN: " + e.getMessage());
        }
        return documents;
    }
    
    /**
     * Trích xuất Document từ ResultSet
     */
    private Document extractDocumentFromResultSet(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        String category = rs.getString("category");
        
        return new Document(id, title, author, category);
    }
}
