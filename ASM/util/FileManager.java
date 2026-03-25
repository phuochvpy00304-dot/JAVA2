package ASM.util;

import ASM.model.Document;
import ASM.model.Copy;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Lớp quản lý việc lưu và đọc dữ liệu từ file
 * Sử dụng ObjectInputStream và ObjectOutputStream
 */
public class FileManager {
    private static final String DOCUMENT_FILE = "documents.dat";
    private static final String COPY_FILE = "copies.dat";
    
    /**
     * Lưu danh sách tài liệu vào file
     */
    public static void saveDocuments(Map<String, Document> documents) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DOCUMENT_FILE))) {
            oos.writeObject(documents);
            System.out.println("✓ Đã lưu " + documents.size() + " tài liệu vào file.");
        } catch (IOException e) {
            System.err.println("✗ Lỗi khi lưu tài liệu: " + e.getMessage());
        }
    }
    
    /**
     * Đọc danh sách tài liệu từ file
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Document> loadDocuments() {
        File file = new File(DOCUMENT_FILE);
        if (!file.exists()) {
            System.out.println("⚠ File tài liệu chưa tồn tại. Tạo dữ liệu mới.");
            return new HashMap<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DOCUMENT_FILE))) {
            Map<String, Document> documents = (Map<String, Document>) ois.readObject();
            System.out.println("✓ Đã tải " + documents.size() + " tài liệu từ file.");
            return documents;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Lỗi khi đọc tài liệu: " + e.getMessage());
            return new HashMap<>();
        }
    }
    
    /**
     * Lưu danh sách bản sao vào file
     */
    public static void saveCopies(Map<String, Copy> copies) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(COPY_FILE))) {
            oos.writeObject(copies);
            System.out.println("✓ Đã lưu " + copies.size() + " bản sao vào file.");
        } catch (IOException e) {
            System.err.println("✗ Lỗi khi lưu bản sao: " + e.getMessage());
        }
    }
    
    /**
     * Đọc danh sách bản sao từ file
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Copy> loadCopies() {
        File file = new File(COPY_FILE);
        if (!file.exists()) {
            System.out.println("⚠ File bản sao chưa tồn tại. Tạo dữ liệu mới.");
            return new HashMap<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(COPY_FILE))) {
            Map<String, Copy> copies = (Map<String, Copy>) ois.readObject();
            System.out.println("✓ Đã tải " + copies.size() + " bản sao từ file.");
            return copies;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Lỗi khi đọc bản sao: " + e.getMessage());
            return new HashMap<>();
        }
    }
    
    /**
     * Khôi phục quan hệ giữa Document và Copy sau khi load từ file
     */
    public static void restoreRelationships(Map<String, Document> documents, 
                                           Map<String, Copy> copies) {
        // Xóa danh sách copies cũ trong documents
        documents.values().forEach(doc -> doc.getCopies().clear());
        
        // Thiết lập lại quan hệ
        copies.values().forEach(copy -> {
            Document doc = documents.get(copy.getDocumentId());
            if (doc != null) {
                doc.addCopy(copy);
            }
        });
        
        System.out.println("✓ Đã khôi phục quan hệ giữa tài liệu và bản sao.");
    }
}
