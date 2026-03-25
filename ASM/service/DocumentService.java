package ASM.service;

import ASM.model.Document;
import ASM.model.Copy;
import ASM.exception.DuplicateIdException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service quản lý các thao tác CRUD cho Tài liệu
 * Sử dụng Map để lưu trữ và tra cứu nhanh theo ID
 */
public class DocumentService {
    // Map lưu trữ tài liệu với key là ID
    private Map<String, Document> documents;

    public DocumentService() {
        this.documents = new HashMap<>();
    }

    /**
     * Thêm tài liệu mới
     * 
     * @throws DuplicateIdException nếu ID đã tồn tại
     */
    public void addDocument(Document document) throws DuplicateIdException {
        if (documents.containsKey(document.getId())) {
            throw new DuplicateIdException("Mã tài liệu '" + document.getId() + "' đã tồn tại!");
        }
        documents.put(document.getId(), document);
        System.out.println("✓ Đã thêm tài liệu: " + document.getTitle());
    }

    /**
     * Cập nhật thông tin tài liệu
     */
    public void updateDocument(Document document) {
        if (documents.containsKey(document.getId())) {
            documents.put(document.getId(), document);
            System.out.println("✓ Đã cập nhật tài liệu: " + document.getTitle());
        } else {
            System.out.println("✗ Không tìm thấy tài liệu với ID: " + document.getId());
        }
    }

    /**
     * Xóa tài liệu theo ID
     */
    public void deleteDocument(String id) {
        Document removed = documents.remove(id);
        if (removed != null) {
            System.out.println("✓ Đã xóa tài liệu: " + removed.getTitle());
        } else {
            System.out.println("✗ Không tìm thấy tài liệu với ID: " + id);
        }
    }

    /**
     * Tìm tài liệu theo ID
     */
    public Document findById(String id) {
        return documents.get(id);
    }

    /**
     * Lấy tất cả tài liệu
     */
    public List<Document> getAllDocuments() {
        return new ArrayList<>(documents.values());
    }

    /**
     * Tìm kiếm tài liệu theo tiêu đề (không phân biệt hoa thường)
     */
    public List<Document> searchByTitle(String keyword) {
        return documents.values().stream()
                .filter(doc -> doc.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Tìm kiếm tài liệu theo tác giả
     */
    public List<Document> searchByAuthor(String author) {
        return documents.values().stream()
                .filter(doc -> doc.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Tìm kiếm tài liệu theo thể loại
     */
    public List<Document> searchByCategory(String category) {
        return documents.values().stream()
                .filter(doc -> doc.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    /**
     * Sắp xếp tài liệu theo tiêu đề
     */
    public List<Document> sortByTitle() {
        return documents.values().stream()
                .sorted(Comparator.comparing(Document::getTitle))
                .collect(Collectors.toList());
    }

    /**
     * Sắp xếp tài liệu theo tác giả
     */
    public List<Document> sortByAuthor() {
        return documents.values().stream()
                .sorted(Comparator.comparing(Document::getAuthor))
                .collect(Collectors.toList());
    }

    /**
     * Phân trang danh sách tài liệu
     * 
     * @param page     Số trang (bắt đầu từ 1)
     * @param pageSize Số lượng tài liệu mỗi trang
     */
    public List<Document> getDocumentsWithPagination(int page, int pageSize) {
        return documents.values().stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    /**
     * Đếm tổng số tài liệu
     */
    public int getTotalDocuments() {
        return documents.size();
    }

    /**
     * Hiển thị tất cả tài liệu
     */
    public void displayAllDocuments() {
        if (documents.isEmpty()) {
            System.out.println("Không có tài liệu nào.");
            return;
        }

        System.out.println("\n=== DANH SÁCH TÀI LIỆU ===");
        documents.values().forEach(doc -> {
            System.out.println(doc);
            System.out.println("  Số bản sao: " + doc.getCopies().size());
        });
    }

    // Setter cho việc load dữ liệu từ file
    public void setDocuments(Map<String, Document> documents) {
        this.documents = documents;
    }

    // Getter cho việc lưu dữ liệu vào file
    public Map<String, Document> getDocuments() {
        return documents;
    }
}
