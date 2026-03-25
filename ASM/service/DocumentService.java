package ASM.service;

import ASM.model.Document;
import ASM.exception.DuplicateIdException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service quan ly cac thao tac CRUD cho Tai lieu
 * Su dung Map de luu tru va tra cuu nhanh theo ID
 */
public class DocumentService {
    // Map luu tru tai lieu voi key la ID
    private Map<String, Document> documents;

    public DocumentService() {
        this.documents = new HashMap<>();
    }

    /**
     * Them tai lieu moi
     * 
     * @throws DuplicateIdException neu ID da ton tai
     */
    public void addDocument(Document document) throws DuplicateIdException {
        if (documents.containsKey(document.getId())) {
            throw new DuplicateIdException("Ma tai lieu '" + document.getId() + "' da ton tai!");
        }
        documents.put(document.getId(), document);
        System.out.println("Da them tai lieu: " + document.getTitle());
    }

    /**
     * Cap nhat thong tin tai lieu
     */
    public void updateDocument(Document document) {
        if (documents.containsKey(document.getId())) {
            documents.put(document.getId(), document);
            System.out.println("Da cap nhat tai lieu: " + document.getTitle());
        } else {
            System.out.println("Khong tim thay tai lieu voi ID: " + document.getId());
        }
    }

    /**
     * Xoa tai lieu theo ID
     */
    public void deleteDocument(String id) {
        Document removed = documents.remove(id);
        if (removed != null) {
            System.out.println("Da xoa tai lieu: " + removed.getTitle());
        } else {
            System.out.println("Khong tim thay tai lieu voi ID: " + id);
        }
    }

    /**
     * Tim tai lieu theo ID
     */
    public Document findById(String id) {
        return documents.get(id);
    }

    /**
     * Lay tat ca tai lieu
     */
    public List<Document> getAllDocuments() {
        return new ArrayList<>(documents.values());
    }

    /**
     * Tim kiem tai lieu theo tieu de (khong phan biet hoa thuong)
     */
    public List<Document> searchByTitle(String keyword) {
        return documents.values().stream()
                .filter(doc -> doc.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Tim kiem tai lieu theo tac gia
     */
    public List<Document> searchByAuthor(String author) {
        return documents.values().stream()
                .filter(doc -> doc.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Tim kiem tai lieu theo the loai
     */
    public List<Document> searchByCategory(String category) {
        return documents.values().stream()
                .filter(doc -> doc.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    /**
     * Sap xep tai lieu theo tieu de
     */
    public List<Document> sortByTitle() {
        return documents.values().stream()
                .sorted(Comparator.comparing(Document::getTitle))
                .collect(Collectors.toList());
    }

    /**
     * Sap xep tai lieu theo tac gia
     */
    public List<Document> sortByAuthor() {
        return documents.values().stream()
                .sorted(Comparator.comparing(Document::getAuthor))
                .collect(Collectors.toList());
    }

    /**
     * Phan trang danh sach tai lieu
     * 
     * @param page     So trang (bat dau tu 1)
     * @param pageSize So luong tai lieu moi trang
     */
    public List<Document> getDocumentsWithPagination(int page, int pageSize) {
        return documents.values().stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    /**
     * Dem tong so tai lieu
     */
    public int getTotalDocuments() {
        return documents.size();
    }

    /**
     * Hien thi tat ca tai lieu
     */
    public void displayAllDocuments() {
        if (documents.isEmpty()) {
            System.out.println("Khong co tai lieu nao.");
            return;
        }

        System.out.println("\n=== DANH SACH TAI LIEU ===");
        documents.values().forEach(doc -> {
            System.out.println(doc);
            System.out.println("  So ban sao: " + doc.getCopies().size());
        });
    }

    // Setter cho viec load du lieu tu file
    public void setDocuments(Map<String, Document> documents) {
        this.documents = documents;
    }

    // Getter cho viec luu du lieu vao file
    public Map<String, Document> getDocuments() {
        return documents;
    }
}
