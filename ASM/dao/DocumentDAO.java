package ASM.dao;

import ASM.model.Document;
import ASM.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object cho Document
 * Thuc hien cac thao tac CRUD voi database
 */
public class DocumentDAO {
    
    /**
     * Them tai lieu moi vao database (alias cho insert)
     */
    public boolean create(Document document) throws SQLException {
        return insert(document);
    }
    
    /**
     * Them tai lieu moi vao database
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
                System.out.println("Da them tai lieu vao database: " + document.getTitle());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Loi khi them tai lieu: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Cap nhat thong tin tai lieu
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
                System.out.println("Da cap nhat tai lieu: " + document.getTitle());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Loi khi cap nhat tai lieu: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Xoa tai lieu theo ID
     * Cascade delete se tu dong xoa cac ban sao lien quan
     */
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM DOCUMENT WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Da xoa tai lieu ID: " + id);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Loi khi xoa tai lieu: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Tim tai lieu theo ID
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
            System.err.println("Loi khi tim tai lieu: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Lay tat ca tai lieu
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
            System.err.println("Loi khi lay danh sach tai lieu: " + e.getMessage());
        }
        return documents;
    }
    
    /**
     * Tim kiem tai lieu theo nhieu tieu chi
     */
    public List<Document> search(String title, String author, String category) {
        List<Document> documents = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM DOCUMENT WHERE 1=1");
        
        // Xay dung cau query dong
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
            System.err.println("Loi khi tim kiem: " + e.getMessage());
        }
        return documents;
    }
    
    /**
     * Sap xep tai lieu theo cot
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
            System.err.println("Loi khi sap xep: " + e.getMessage());
        }
        return documents;
    }
    
    /**
     * Phan trang danh sach tai lieu
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
            System.err.println("Loi khi phan trang: " + e.getMessage());
        }
        return documents;
    }
    
    /**
     * Dem tong so tai lieu
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
            System.err.println("Loi khi dem tai lieu: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Tim kiem tai lieu theo tieu de
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
     * Tim kiem tai lieu theo tac gia
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
     * Lay tat ca tai lieu da sap xep theo tieu de
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
     * Lay tai lieu kem so luong ban sao (su dung JOIN)
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
                System.out.println(doc + " - So ban sao: " + copyCount);
                documents.add(doc);
            }
        } catch (SQLException e) {
            System.err.println("Loi khi JOIN: " + e.getMessage());
        }
        return documents;
    }
    
    /**
     * Trich xuat Document tu ResultSet
     */
    private Document extractDocumentFromResultSet(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        String category = rs.getString("category");
        
        return new Document(id, title, author, category);
    }
}
