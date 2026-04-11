package ASM.phase2;

import ASM.model.*;
import ASM.dao.*;
import ASM.database.DatabaseConnection;
import java.sql.*;
import java.util.*;

/**
 * GIAI DOAN 2: Quan ly thu vien voi co so du lieu
 * Chuc nang: CRUD tai lieu va ban sao, luu/doc tu database
 */
public class LibraryManagementPhase2 {
    private DocumentDAO documentDAO;
    private CopyDAO copyDAO;
    private Scanner scanner;
    
    public LibraryManagementPhase2() {
        this.documentDAO = new DocumentDAO();
        this.copyDAO = new CopyDAO();
        this.scanner = new Scanner(System.in);
        
        // Khoi tao database
        initializeDatabase();
    }
    
    /**
     * Khoi tao database va tao bang neu chua co
     */
    private void initializeDatabase() {
        System.out.println("\n=== KHOI TAO CO SO DU LIEU ===");
        try (Connection conn = DatabaseConnection.getConnection()) {
            createTables(conn);
        //    System.out.println("Ket noi database thanh cong!");
        } catch (SQLException e) {
            System.err.println("Loi ket noi database: " + e.getMessage());
        }
    }
    
    /**
     * Tao cac bang trong database
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
     * Menu chinh
     */
    public void showMenu() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║   HE THONG QUAN LY THU VIEN - PHASE 2  ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Quan ly Tai lieu                   ║");
            System.out.println("║  2. Quan ly Ban sao                    ║");
            System.out.println("║  3. Tim kiem & Thong ke                ║");
            System.out.println("║  0. Thoat                              ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Chon chuc nang: ");
            
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
                    System.out.println("Tam biet!");
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
    
    /**
     * Menu quan ly tai lieu
     */
    private void documentMenu() {
        while (true) {
            System.out.println("\n--- QUAN LY TAI LIEU ---");
            System.out.println("1. Them tai lieu");
            System.out.println("2. Sua tai lieu");
            System.out.println("3. Xoa tai lieu");
            System.out.println("4. Xem tat ca tai lieu");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            
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
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
    
    /**
     * Them tai lieu moi
     */
    private void addDocument() {
        System.out.println("\n--- THEM TAI LIEU MOI ---");
        
        System.out.print("Ma tai lieu: ");
        String id = scanner.nextLine();
        
        System.out.print("Tieu de: ");
        String title = scanner.nextLine();
        
        System.out.print("Tac gia: ");
        String author = scanner.nextLine();
        
        System.out.print("The loai: ");
        String category = scanner.nextLine();
        
        Document document = new Document(id, title, author, category);
        
        try {
            documentDAO.create(document);
            System.out.println("Them tai lieu thanh cong!");
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Cap nhat tai lieu
     */
    private void updateDocument() {
        System.out.println("\n--- CAP NHAT TAI LIEU ---");
        System.out.print("Nhap ma tai lieu can sua: ");
        String id = scanner.nextLine();
        
        try {
            Document document = documentDAO.findById(id);
            if (document == null) {
                System.out.println("Khong tim thay tai lieu!");
                return;
            }
            
            System.out.println("Thong tin hien tai: " + document);
            
            System.out.print("Tieu de moi (Enter de giu nguyen): ");
            String title = scanner.nextLine();
            if (!title.isEmpty()) document.setTitle(title);
            
            System.out.print("Tac gia moi (Enter de giu nguyen): ");
            String author = scanner.nextLine();
            if (!author.isEmpty()) document.setAuthor(author);
            
            System.out.print("The loai moi (Enter de giu nguyen): ");
            String category = scanner.nextLine();
            if (!category.isEmpty()) document.setCategory(category);
            
            documentDAO.update(document);
            System.out.println("Cap nhat thanh cong!");
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Xoa tai lieu
     */
    private void deleteDocument() {
        System.out.println("\n--- XOA TAI LIEU ---");
        System.out.print("Nhap ma tai lieu can xoa: ");
        String id = scanner.nextLine();
        
        try {
            documentDAO.delete(id);
            System.out.println("Xoa thanh cong!");
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Xem tat ca tai lieu
     */
    private void viewAllDocuments() {
        try {
            List<Document> documents = documentDAO.findAll();
            
            if (documents.isEmpty()) {
                System.out.println("Khong co tai lieu nao.");
                return;
            }
            
            System.out.println("\n=== TAT CA TAI LIEU ===");
            documents.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Menu quan ly ban sao
     */
    private void copyMenu() {
        while (true) {
            System.out.println("\n--- QUAN LY BAN SAO ---");
            System.out.println("1. Them ban sao");
            System.out.println("2. Sua trang thai ban sao");
            System.out.println("3. Xoa ban sao");
            System.out.println("4. Xem ban sao theo tai lieu");
            System.out.println("5. Xem tat ca ban sao");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            
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
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
    
    /**
     * Them ban sao moi
     */
    private void addCopy() {
        System.out.println("\n--- THEM BAN SAO MOI ---");
        
        System.out.print("Ma ban sao: ");
        String id = scanner.nextLine();
        
        System.out.print("Ma tai lieu: ");
        String documentId = scanner.nextLine();
        
        System.out.println("Trang thai:");
        System.out.println("1. " + CopyStatus.GOOD);
        System.out.println("2. " + CopyStatus.DAMAGED);
        System.out.println("3. " + CopyStatus.LOST);
        System.out.print("Chon trang thai: ");
        int statusChoice = getIntInput();
        
        CopyStatus status = CopyStatus.GOOD;
        switch (statusChoice) {
            case 2: status = CopyStatus.DAMAGED; break;
            case 3: status = CopyStatus.LOST; break;
        }
        
        Copy copy = new Copy(id, documentId, status);
        
        try {
            copyDAO.create(copy);
            System.out.println("Them ban sao thanh cong!");
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Cap nhat trang thai ban sao
     */
    private void updateCopyStatus() {
        System.out.println("\n--- CAP NHAT TRANG THAI BAN SAO ---");
        System.out.print("Nhap ma ban sao: ");
        String id = scanner.nextLine();
        
        try {
            Copy copy = copyDAO.findById(id);
            if (copy == null) {
                System.out.println("Khong tim thay ban sao!");
                return;
            }
            
            System.out.println("Trang thai hien tai: " + copy.getStatus());
            System.out.println("Trang thai moi:");
            System.out.println("1. " + CopyStatus.GOOD);
            System.out.println("2. " + CopyStatus.DAMAGED);
            System.out.println("3. " + CopyStatus.LOST);
            System.out.print("Chon: ");
            int choice = getIntInput();
            
            switch (choice) {
                case 1: copy.setStatus(CopyStatus.GOOD); break;
                case 2: copy.setStatus(CopyStatus.DAMAGED); break;
                case 3: copy.setStatus(CopyStatus.LOST); break;
                default:
                    System.out.println("Lua chon khong hop le!");
                    return;
            }
            
            copyDAO.update(copy);
            System.out.println("Cap nhat thanh cong!");
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Xoa ban sao
     */
    private void deleteCopy() {
        System.out.println("\n--- XOA BAN SAO ---");
        System.out.print("Nhap ma ban sao can xoa: ");
        String id = scanner.nextLine();
        
        try {
            copyDAO.delete(id);
            System.out.println("Xoa thanh cong!");
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Xem ban sao theo tai lieu
     */
    private void viewCopiesByDocument() {
        System.out.print("Nhap ma tai lieu: ");
        String documentId = scanner.nextLine();
        
        try {
            List<Copy> copies = copyDAO.findByDocumentId(documentId);
            
            if (copies.isEmpty()) {
                System.out.println("Khong co ban sao nao cho tai lieu nay.");
                return;
            }
            
            System.out.println("\n=== BAN SAO CUA TAI LIEU " + documentId + " ===");
            copies.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Xem tat ca ban sao
     */
    private void viewAllCopies() {
        try {
            List<Copy> copies = copyDAO.findAll();
            
            if (copies.isEmpty()) {
                System.out.println("Khong co ban sao nao.");
                return;
            }
            
            System.out.println("\n=== TAT CA BAN SAO ===");
            copies.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Menu tim kiem va thong ke
     */
    private void searchAndStatisticsMenu() {
        while (true) {
            System.out.println("\n--- TIM KIEM & THONG KE ---");
            System.out.println("1. Tim tai lieu theo tieu de");
            System.out.println("2. Tim tai lieu theo tac gia");
            System.out.println("3. Sap xep tai lieu theo ten");
            System.out.println("4. Thong ke ban sao theo trang thai");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            
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
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
    
    /**
     * Tim kiem theo tieu de
     */
    private void searchByTitle() {
        System.out.print("Nhap tu khoa tieu de: ");
        String keyword = scanner.nextLine();
        
        try {
            List<Document> results = documentDAO.searchByTitle(keyword);
            
            if (results.isEmpty()) {
                System.out.println("Khong tim thay tai lieu nao.");
            } else {
                System.out.println("\n=== KET QUA TIM KIEM ===");
                results.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Tim kiem theo tac gia
     */
    private void searchByAuthor() {
        System.out.print("Nhap ten tac gia: ");
        String author = scanner.nextLine();
        
        try {
            List<Document> results = documentDAO.searchByAuthor(author);
            
            if (results.isEmpty()) {
                System.out.println("Khong tim thay tai lieu nao.");
            } else {
                System.out.println("\n=== KET QUA TIM KIEM ===");
                results.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Sap xep tai lieu
     */
    private void sortDocuments() {
        try {
            List<Document> sorted = documentDAO.findAllSortedByTitle();
            
            System.out.println("\n=== TAI LIEU DA SAP XEP THEO TEN ===");
            sorted.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Thong ke theo trang thai
     */
    private void statisticsByStatus() {
        try {
            Map<CopyStatus, Long> stats = copyDAO.getStatisticsByStatus();
            
            System.out.println("\n=== THONG KE BAN SAO THEO TRANG THAI ===");
            stats.forEach((status, count) -> 
                System.out.println(status + ": " + count + " ban"));
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    /**
     * Doc so nguyen tu input
     */
    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Main method de chay chuong trinh
     */
    public static void main(String[] args) {
        LibraryManagementPhase2 app = new LibraryManagementPhase2();
        app.showMenu();
    }
}
