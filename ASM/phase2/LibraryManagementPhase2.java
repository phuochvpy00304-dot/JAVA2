package ASM.phase2;

import ASM.model.*;
import ASM.dao.*;
import ASM.database.DatabaseConnection;
import java.sql.*;
import java.util.*;

/**
 * GIAI ĐOẠN 2: Quản lý thư viện với cơ sở dữ liệu
 * Chức năng: CRUD tài liệu và bản sao, lưu/đọc từ database
 */
public class LibraryManagementPhase2 {
    private DocumentDAO documentDAO;
    private CopyDAO copyDAO;
    private Scanner scanner;
    
    public LibraryManagementPhase2() {
        this.documentDAO = new DocumentDAO();
        this.copyDAO = new CopyDAO();
        this.scanner = new Scanner(System.in);
        
        // Khởi tạo database
        initializeDatabase();
    }
    
    /**
     * Khởi tạo database và tạo bảng nếu chưa có
     */
    private void initializeDatabase() {
        System.out.println("\n=== KHỞI TẠO CƠ SỞ DỮ LIỆU ===");
        try (Connection conn = DatabaseConnection.getConnection()) {
            createTables(conn);
            System.out.println("✓ Kết nối database thành công!");
        } catch (SQLException e) {
            System.err.println("✗ Lỗi kết nối database: " + e.getMessage());
        }
    }
    
    /**
     * Tạo các bảng trong database
     */
    private void createTables(Connection conn) throws SQLException {
        String createDocumentsTable = 
            "CREATE TABLE IF NOT EXISTS documents (" +
            "id VARCHAR(50) PRIMARY KEY, " +
            "title VARCHAR(255) NOT NULL, " +
            "author VARCHAR(255) NOT NULL, " +
            "category VARCHAR(100) NOT NULL)";
        
        String createCopiesTable = 
            "CREATE TABLE IF NOT EXISTS copies (" +
            "id VARCHAR(50) PRIMARY KEY, " +
            "document_id VARCHAR(50) NOT NULL, " +
            "status VARCHAR(20) NOT NULL, " +
            "FOREIGN KEY (document_id) REFERENCES documents(id) ON DELETE CASCADE)";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createDocumentsTable);
            stmt.execute(createCopiesTable);
        }
    }
    
    /**
     * Menu chính
     */
    public void showMenu() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║   HỆ THỐNG QUẢN LÝ THƯ VIỆN - PHASE 2 ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Quản lý Tài liệu                   ║");
            System.out.println("║  2. Quản lý Bản sao                    ║");
            System.out.println("║  3. Tìm kiếm & Thống kê                ║");
            System.out.println("║  0. Thoát                              ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Chọn chức năng: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    documentMenu();
                    break;
                case 2:
                    copyMenu();
                    break;
                case 3:
                    searchAndStatisticsMenu();
                    break;
                case 0:
                    System.out.println("Tạm biệt!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    
    /**
     * Menu quản lý tài liệu
     */
    private void documentMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ TÀI LIỆU ---");
            System.out.println("1. Thêm tài liệu");
            System.out.println("2. Sửa tài liệu");
            System.out.println("3. Xóa tài liệu");
            System.out.println("4. Xem tất cả tài liệu");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    addDocument();
                    break;
                case 2:
                    updateDocument();
                    break;
                case 3:
                    deleteDocument();
                    break;
                case 4:
                    viewAllDocuments();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    
    /**
     * Thêm tài liệu mới
     */
    private void addDocument() {
        System.out.println("\n--- THÊM TÀI LIỆU MỚI ---");
        
        System.out.print("Mã tài liệu: ");
        String id = scanner.nextLine();
        
        System.out.print("Tiêu đề: ");
        String title = scanner.nextLine();
        
        System.out.print("Tác giả: ");
        String author = scanner.nextLine();
        
        System.out.print("Thể loại: ");
        String category = scanner.nextLine();
        
        Document document = new Document(id, title, author, category);
        
        try {
            documentDAO.create(document);
            System.out.println("✓ Thêm tài liệu thành công!");
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Cập nhật tài liệu
     */
    private void updateDocument() {
        System.out.println("\n--- CẬP NHẬT TÀI LIỆU ---");
        System.out.print("Nhập mã tài liệu cần sửa: ");
        String id = scanner.nextLine();
        
        try {
            Document document = documentDAO.findById(id);
            if (document == null) {
                System.out.println("✗ Không tìm thấy tài liệu!");
                return;
            }
            
            System.out.println("Thông tin hiện tại: " + document);
            
            System.out.print("Tiêu đề mới (Enter để giữ nguyên): ");
            String title = scanner.nextLine();
            if (!title.isEmpty()) document.setTitle(title);
            
            System.out.print("Tác giả mới (Enter để giữ nguyên): ");
            String author = scanner.nextLine();
            if (!author.isEmpty()) document.setAuthor(author);
            
            System.out.print("Thể loại mới (Enter để giữ nguyên): ");
            String category = scanner.nextLine();
            if (!category.isEmpty()) document.setCategory(category);
            
            documentDAO.update(document);
            System.out.println("✓ Cập nhật thành công!");
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Xóa tài liệu
     */
    private void deleteDocument() {
        System.out.println("\n--- XÓA TÀI LIỆU ---");
        System.out.print("Nhập mã tài liệu cần xóa: ");
        String id = scanner.nextLine();
        
        try {
            documentDAO.delete(id);
            System.out.println("✓ Xóa thành công!");
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Xem tất cả tài liệu
     */
    private void viewAllDocuments() {
        try {
            List<Document> documents = documentDAO.findAll();
            
            if (documents.isEmpty()) {
                System.out.println("Không có tài liệu nào.");
                return;
            }
            
            System.out.println("\n=== TẤT CẢ TÀI LIỆU ===");
            documents.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Menu quản lý bản sao
     */
    private void copyMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ BẢN SAO ---");
            System.out.println("1. Thêm bản sao");
            System.out.println("2. Sửa trạng thái bản sao");
            System.out.println("3. Xóa bản sao");
            System.out.println("4. Xem bản sao theo tài liệu");
            System.out.println("5. Xem tất cả bản sao");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    addCopy();
                    break;
                case 2:
                    updateCopyStatus();
                    break;
                case 3:
                    deleteCopy();
                    break;
                case 4:
                    viewCopiesByDocument();
                    break;
                case 5:
                    viewAllCopies();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    
    /**
     * Thêm bản sao mới
     */
    private void addCopy() {
        System.out.println("\n--- THÊM BẢN SAO MỚI ---");
        
        System.out.print("Mã bản sao: ");
        String id = scanner.nextLine();
        
        System.out.print("Mã tài liệu: ");
        String documentId = scanner.nextLine();
        
        System.out.println("Trạng thái:");
        System.out.println("1. " + CopyStatus.GOOD);
        System.out.println("2. " + CopyStatus.DAMAGED);
        System.out.println("3. " + CopyStatus.LOST);
        System.out.print("Chọn trạng thái: ");
        int statusChoice = getIntInput();
        
        CopyStatus status = CopyStatus.GOOD;
        switch (statusChoice) {
            case 2: status = CopyStatus.DAMAGED; break;
            case 3: status = CopyStatus.LOST; break;
        }
        
        Copy copy = new Copy(id, documentId, status);
        
        try {
            copyDAO.create(copy);
            System.out.println("✓ Thêm bản sao thành công!");
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Cập nhật trạng thái bản sao
     */
    private void updateCopyStatus() {
        System.out.println("\n--- CẬP NHẬT TRẠNG THÁI BẢN SAO ---");
        System.out.print("Nhập mã bản sao: ");
        String id = scanner.nextLine();
        
        try {
            Copy copy = copyDAO.findById(id);
            if (copy == null) {
                System.out.println("✗ Không tìm thấy bản sao!");
                return;
            }
            
            System.out.println("Trạng thái hiện tại: " + copy.getStatus());
            System.out.println("Trạng thái mới:");
            System.out.println("1. " + CopyStatus.GOOD);
            System.out.println("2. " + CopyStatus.DAMAGED);
            System.out.println("3. " + CopyStatus.LOST);
            System.out.print("Chọn: ");
            int choice = getIntInput();
            
            switch (choice) {
                case 1: copy.setStatus(CopyStatus.GOOD); break;
                case 2: copy.setStatus(CopyStatus.DAMAGED); break;
                case 3: copy.setStatus(CopyStatus.LOST); break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    return;
            }
            
            copyDAO.update(copy);
            System.out.println("✓ Cập nhật thành công!");
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Xóa bản sao
     */
    private void deleteCopy() {
        System.out.println("\n--- XÓA BẢN SAO ---");
        System.out.print("Nhập mã bản sao cần xóa: ");
        String id = scanner.nextLine();
        
        try {
            copyDAO.delete(id);
            System.out.println("✓ Xóa thành công!");
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Xem bản sao theo tài liệu
     */
    private void viewCopiesByDocument() {
        System.out.print("Nhập mã tài liệu: ");
        String documentId = scanner.nextLine();
        
        try {
            List<Copy> copies = copyDAO.findByDocumentId(documentId);
            
            if (copies.isEmpty()) {
                System.out.println("Không có bản sao nào cho tài liệu này.");
                return;
            }
            
            System.out.println("\n=== BẢN SAO CỦA TÀI LIỆU " + documentId + " ===");
            copies.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Xem tất cả bản sao
     */
    private void viewAllCopies() {
        try {
            List<Copy> copies = copyDAO.findAll();
            
            if (copies.isEmpty()) {
                System.out.println("Không có bản sao nào.");
                return;
            }
            
            System.out.println("\n=== TẤT CẢ BẢN SAO ===");
            copies.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Menu tìm kiếm và thống kê
     */
    private void searchAndStatisticsMenu() {
        while (true) {
            System.out.println("\n--- TÌM KIẾM & THỐNG KÊ ---");
            System.out.println("1. Tìm tài liệu theo tiêu đề");
            System.out.println("2. Tìm tài liệu theo tác giả");
            System.out.println("3. Sắp xếp tài liệu theo tên");
            System.out.println("4. Thống kê bản sao theo trạng thái");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    searchByTitle();
                    break;
                case 2:
                    searchByAuthor();
                    break;
                case 3:
                    sortDocuments();
                    break;
                case 4:
                    statisticsByStatus();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    
    /**
     * Tìm kiếm theo tiêu đề
     */
    private void searchByTitle() {
        System.out.print("Nhập từ khóa tiêu đề: ");
        String keyword = scanner.nextLine();
        
        try {
            List<Document> results = documentDAO.searchByTitle(keyword);
            
            if (results.isEmpty()) {
                System.out.println("Không tìm thấy tài liệu nào.");
            } else {
                System.out.println("\n=== KẾT QUẢ TÌM KIẾM ===");
                results.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Tìm kiếm theo tác giả
     */
    private void searchByAuthor() {
        System.out.print("Nhập tên tác giả: ");
        String author = scanner.nextLine();
        
        try {
            List<Document> results = documentDAO.searchByAuthor(author);
            
            if (results.isEmpty()) {
                System.out.println("Không tìm thấy tài liệu nào.");
            } else {
                System.out.println("\n=== KẾT QUẢ TÌM KIẾM ===");
                results.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Sắp xếp tài liệu
     */
    private void sortDocuments() {
        try {
            List<Document> sorted = documentDAO.findAllSortedByTitle();
            
            System.out.println("\n=== TÀI LIỆU ĐÃ SẮP XẾP THEO TÊN ===");
            sorted.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Thống kê theo trạng thái
     */
    private void statisticsByStatus() {
        try {
            Map<CopyStatus, Long> stats = copyDAO.getStatisticsByStatus();
            
            System.out.println("\n=== THỐNG KÊ BẢN SAO THEO TRẠNG THÁI ===");
            stats.forEach((status, count) -> 
                System.out.println(status + ": " + count + " bản"));
        } catch (SQLException e) {
            System.out.println("✗ Lỗi: " + e.getMessage());
        }
    }
    
    /**
     * Đọc số nguyên từ input
     */
    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Main method để chạy chương trình
     */
    public static void main(String[] args) {
        LibraryManagementPhase2 app = new LibraryManagementPhase2();
        app.showMenu();
    }
}
