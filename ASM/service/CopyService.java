package ASM.service;

import ASM.model.Copy;
import ASM.model.CopyStatus;
import ASM.model.Document;
import ASM.exception.DuplicateIdException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service quan ly cac thao tac CRUD cho Ban sao tai lieu
 */
public class CopyService {
    // Map luu tru ban sao voi key la ID
    private Map<String, Copy> copies;
    private DocumentService documentService;
    
    public CopyService(DocumentService documentService) {
        this.copies = new HashMap<>();
        this.documentService = documentService;
    }
    
    /**
     * Them ban sao moi cho tai lieu
     * @throws DuplicateIdException neu ID ban sao da ton tai
     */
    public void addCopy(Copy copy) throws DuplicateIdException {
        if (copies.containsKey(copy.getId())) {
            throw new DuplicateIdException("Ma ban sao '" + copy.getId() + "' da ton tai!");
        }
        
        // Kiem tra tai lieu co ton tai khong
        Document document = documentService.findById(copy.getDocumentId());
        if (document == null) {
            System.out.println("Khong tim thay tai lieu voi ID: " + copy.getDocumentId());
            return;
        }
        
        copies.put(copy.getId(), copy);
        document.addCopy(copy);  // Them vao danh sach ban sao cua tai lieu
        System.out.println("Da them ban sao: " + copy.getId());
    }
    
    /**
     * Cap nhat thong tin ban sao
     */
    public void updateCopy(Copy copy) {
        if (copies.containsKey(copy.getId())) {
            copies.put(copy.getId(), copy);
            System.out.println("Da cap nhat ban sao: " + copy.getId());
        } else {
            System.out.println("Khong tim thay ban sao voi ID: " + copy.getId());
        }
    }
    
    /**
     * Xoa ban sao theo ID
     */
    public void deleteCopy(String id) {
        Copy copy = copies.remove(id);
        if (copy != null) {
            // Xoa khoi danh sach ban sao cua tai lieu
            Document document = documentService.findById(copy.getDocumentId());
            if (document != null) {
                document.removeCopy(copy);
            }
            System.out.println("Da xoa ban sao: " + id);
        } else {
            System.out.println("Khong tim thay ban sao voi ID: " + id);
        }
    }
    
    /**
     * Tim ban sao theo ID
     */
    public Copy findById(String id) {
        return copies.get(id);
    }
    
    /**
     * Lay tat ca ban sao
     */
    public List<Copy> getAllCopies() {
        return new ArrayList<>(copies.values());
    }
    
    /**
     * Lay tat ca ban sao cua mot tai lieu
     */
    public List<Copy> getCopiesByDocumentId(String documentId) {
        return copies.values().stream()
                .filter(copy -> copy.getDocumentId().equals(documentId))
                .collect(Collectors.toList());
    }
    
    /**
     * Lay ban sao theo trang thai
     */
    public List<Copy> getCopiesByStatus(CopyStatus status) {
        return copies.values().stream()
                .filter(copy -> copy.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    /**
     * Cap nhat trang thai nhieu ban sao cung luc theo dieu kien
     */
    public void updateStatusBatch(String documentId, CopyStatus oldStatus, CopyStatus newStatus) {
        List<Copy> toUpdate = copies.values().stream()
                .filter(copy -> copy.getDocumentId().equals(documentId))
                .filter(copy -> copy.getStatus() == oldStatus)
                .collect(Collectors.toList());
        
        toUpdate.forEach(copy -> copy.setStatus(newStatus));
        System.out.println("Da cap nhat " + toUpdate.size() + " ban sao tu " + 
                          oldStatus + " sang " + newStatus);
    }
    
    /**
     * Thong ke so luong ban sao theo trang thai
     */
    public Map<CopyStatus, Long> getStatisticsByStatus() {
        return copies.values().stream()
                .collect(Collectors.groupingBy(Copy::getStatus, Collectors.counting()));
    }
    
    /**
     * Hien thi tat ca ban sao cua mot tai lieu
     */
    public void displayCopiesByDocument(String documentId) {
        List<Copy> documentCopies = getCopiesByDocumentId(documentId);
        
        if (documentCopies.isEmpty()) {
            System.out.println("Tai lieu nay chua co ban sao nao.");
            return;
        }
        
        System.out.println("\n=== BAN SAO CUA TAI LIEU " + documentId + " ===");
        documentCopies.forEach(System.out::println);
    }
    
    // Setter va Getter cho viec luu/load du lieu
    public void setCopies(Map<String, Copy> copies) {
        this.copies = copies;
    }
    
    public Map<String, Copy> getCopies() {
        return copies;
    }
}
