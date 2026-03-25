package ASM.service;

import ASM.model.Copy;
import ASM.model.CopyStatus;
import ASM.model.Document;
import ASM.exception.DuplicateIdException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service quản lý các thao tác CRUD cho Bản sao tài liệu
 */
public class CopyService {
    // Map lưu trữ bản sao với key là ID
    private Map<String, Copy> copies;
    private DocumentService documentService;
    
    public CopyService(DocumentService documentService) {
        this.copies = new HashMap<>();
        this.documentService = documentService;
    }
    
    /**
     * Thêm bản sao mới cho tài liệu
     * @throws DuplicateIdException nếu ID bản sao đã tồn tại
     */
    public void addCopy(Copy copy) throws DuplicateIdException {
        if (copies.containsKey(copy.getId())) {
            throw new DuplicateIdException("Mã bản sao '" + copy.getId() + "' đã tồn tại!");
        }
        
        // Kiểm tra tài liệu có tồn tại không
        Document document = documentService.findById(copy.getDocumentId());
        if (document == null) {
            System.out.println("✗ Không tìm thấy tài liệu với ID: " + copy.getDocumentId());
            return;
        }
        
        copies.put(copy.getId(), copy);
        document.addCopy(copy);  // Thêm vào danh sách bản sao của tài liệu
        System.out.println("✓ Đã thêm bản sao: " + copy.getId());
    }
    
    /**
     * Cập nhật thông tin bản sao
     */
    public void updateCopy(Copy copy) {
        if (copies.containsKey(copy.getId())) {
            copies.put(copy.getId(), copy);
            System.out.println("✓ Đã cập nhật bản sao: " + copy.getId());
        } else {
            System.out.println("✗ Không tìm thấy bản sao với ID: " + copy.getId());
        }
    }
    
    /**
     * Xóa bản sao theo ID
     */
    public void deleteCopy(String id) {
        Copy copy = copies.remove(id);
        if (copy != null) {
            // Xóa khỏi danh sách bản sao của tài liệu
            Document document = documentService.findById(copy.getDocumentId());
            if (document != null) {
                document.removeCopy(copy);
            }
            System.out.println("✓ Đã xóa bản sao: " + id);
        } else {
            System.out.println("✗ Không tìm thấy bản sao với ID: " + id);
        }
    }
    
    /**
     * Tìm bản sao theo ID
     */
    public Copy findById(String id) {
        return copies.get(id);
    }
    
    /**
     * Lấy tất cả bản sao
     */
    public List<Copy> getAllCopies() {
        return new ArrayList<>(copies.values());
    }
    
    /**
     * Lấy tất cả bản sao của một tài liệu
     */
    public List<Copy> getCopiesByDocumentId(String documentId) {
        return copies.values().stream()
                .filter(copy -> copy.getDocumentId().equals(documentId))
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy bản sao theo trạng thái
     */
    public List<Copy> getCopiesByStatus(CopyStatus status) {
        return copies.values().stream()
                .filter(copy -> copy.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    /**
     * Cập nhật trạng thái nhiều bản sao cùng lúc theo điều kiện
     */
    public void updateStatusBatch(String documentId, CopyStatus oldStatus, CopyStatus newStatus) {
        List<Copy> toUpdate = copies.values().stream()
                .filter(copy -> copy.getDocumentId().equals(documentId))
                .filter(copy -> copy.getStatus() == oldStatus)
                .collect(Collectors.toList());
        
        toUpdate.forEach(copy -> copy.setStatus(newStatus));
        System.out.println("✓ Đã cập nhật " + toUpdate.size() + " bản sao từ " + 
                          oldStatus + " sang " + newStatus);
    }
    
    /**
     * Thống kê số lượng bản sao theo trạng thái
     */
    public Map<CopyStatus, Long> getStatisticsByStatus() {
        return copies.values().stream()
                .collect(Collectors.groupingBy(Copy::getStatus, Collectors.counting()));
    }
    
    /**
     * Hiển thị tất cả bản sao của một tài liệu
     */
    public void displayCopiesByDocument(String documentId) {
        List<Copy> documentCopies = getCopiesByDocumentId(documentId);
        
        if (documentCopies.isEmpty()) {
            System.out.println("Tài liệu này chưa có bản sao nào.");
            return;
        }
        
        System.out.println("\n=== BẢN SAO CỦA TÀI LIỆU " + documentId + " ===");
        documentCopies.forEach(System.out::println);
    }
    
    // Setter và Getter cho việc lưu/load dữ liệu
    public void setCopies(Map<String, Copy> copies) {
        this.copies = copies;
    }
    
    public Map<String, Copy> getCopies() {
        return copies;
    }
}
