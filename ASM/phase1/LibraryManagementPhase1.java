package ASM.phase1;

import ASM.model.*;
import ASM.service.*;
import ASM.util.FileManager;
import ASM.exception.DuplicateIdException;
import java.util.*;

/**
 * GIAI ĐOẠN 1: Quản lý thư viện với lưu trữ file
 * Chức năng: CRUD tài liệu và bản sao, lưu/đọc từ file
 */
public class LibraryManagementPhase1 {
    private DocumentService documentService;
    private CopyService copyService;
    private Scanner scanner;
    
    public LibraryManagementPhase1() {
        this.documentService = new DocumentService();
        this.copyService = new CopyService(documentService);
        this.scanner = new Scanner(System.in);
        
        // Load dữ liệu từ file khi khởi động
        loadData();
    }
    
    /**
     * Load dữ liệu từ file
     */
    private void loadData() {
        System.out.println("\n=== ĐANG TẢI DỮ LIỆU ===");
        Map<String, Document> documents = FileManager.loadDocuments();
        Map<String, Copy> copies = FileManager.loadCopies();
        
        documentService.setDocuments(documents);
        copyService.setCopies(copies);
        
        // Khôi phục quan hệ giữa Document và Copy
        FileManager.restoreRelationships(documents, copies);
    }
    
    /**
     * Lưu dữ liệu vào file
     */
    private void saveData() {
        System.out.println("\n=== ĐANG LƯU DỮ LIỆU ===");
        FileManager.saveDocuments(documentService.getDocuments());
        FileManager.saveCopies(copyService.getCopies());
    }
    
    /**
     * Menu chính
     */
    public void showMenu() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║   HỆ THỐNG QUẢN LÝ THƯ VIỆN - PHASE 1 ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Quản lý Tài liệu                   ║");
            System.out.println("║  2. Quản lý Bản sao                    ║");
            System.out.println("║  3. Tìm kiếm & Thống kê                ║");
            System.out.println("║  4. Lưu dữ liệu                        ║");
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
                case 4:
                    saveData();
                    break;
                case 0:
                    saveData();
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
                    documentService.displayAllDocuments();
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
            documentService.addDocument(document);
        } catch (DuplicateIdException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }
    
    /**
     * Cập nhật tài liệu
     */
    private void updateDocument() {
        System.out.println("\n--- CẬP NHẬT TÀI LIỆU ---");
        System.out.print("Nhập mã tài liệu cần sửa: ");
        String id = scanner.nextLine();
        
        Document document = documentService.findById(id);
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
        
        documentService.updateDocument(document);
    }
    
    /**
     * Xóa tài liệu
     */
    private void deleteDocument() {
        System.out.println("\n--- XÓA TÀI LIỆU ---");
        System.out.print("Nhập mã tài liệu cần xóa: ");
        String id = scanner.nextLine();
        
        documentService.deleteDocument(id);
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
            copyService.addCopy(copy);
        } catch (DuplicateIdException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }
    
    /**
     * Cập nhật trạng thái bản sao
     */
    private void updateCopyStatus() {
        System.out.println("\n--- CẬP NHẬT TRẠNG THÁI BẢN SAO ---");
        System.out.print("Nhập mã bản sao: ");
        String id = scanner.nextLine();
        
        Copy copy = copyService.findById(id);
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
        
        copyService.updateCopy(copy);
    }
    
    /**
     * Xóa bản sao
     */
    private void deleteCopy() {
        System.out.println("\n--- XÓA BẢN SAO ---");
        System.out.print("Nhập mã bản sao cần xóa: ");
        String id = scanner.nextLine();
        
        copyService.deleteCopy(id);
    }
    
    /**
     * Xem bản sao theo tài liệu
     */
    private void viewCopiesByDocument() {
        System.out.print("Nhập mã tài liệu: ");
        String documentId = scanner.nextLine();
        
        copyService.displayCopiesByDocument(documentId);
    }
    
    /**
     * Xem tất cả bản sao
     */
    private void viewAllCopies() {
        List<Copy> copies = copyService.getAllCopies();
        
        if (copies.isEmpty()) {
            System.out.println("Không có bản sao nào.");
            return;
        }
        
        System.out.println("\n=== TẤT CẢ BẢN SAO ===");
        copies.forEach(System.out::println);
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
        
        List<Document> results = documentService.searchByTitle(keyword);
        
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy tài liệu nào.");
        } else {
            System.out.println("\n=== KẾT QUẢ TÌM KIẾM ===");
            results.forEach(System.out::println);
        }
    }
    
    /**
     * Tìm kiếm theo tác giả
     */
    private void searchByAuthor() {
        System.out.print("Nhập tên tác giả: ");
        String author = scanner.nextLine();
        
        List<Document> results = documentService.searchByAuthor(author);
        
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy tài liệu nào.");
        } else {
            System.out.println("\n=== KẾT QUẢ TÌM KIẾM ===");
            results.forEach(System.out::println);
        }
    }
    
    /**
     * Sắp xếp tài liệu
     */
    private void sortDocuments() {
        List<Document> sorted = documentService.sortByTitle();
        
        System.out.println("\n=== TÀI LIỆU ĐÃ SẮP XẾP THEO TÊN ===");
        sorted.forEach(System.out::println);
    }
    
    /**
     * Thống kê theo trạng thái
     */
    private void statisticsByStatus() {
        Map<CopyStatus, Long> stats = copyService.getStatisticsByStatus();
        
        System.out.println("\n=== THỐNG KÊ BẢN SAO THEO TRẠNG THÁI ===");
        stats.forEach((status, count) -> 
            System.out.println(status + ": " + count + " bản"));
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
        LibraryManagementPhase1 app = new LibraryManagementPhase1();
        app.showMenu();
    }
}
