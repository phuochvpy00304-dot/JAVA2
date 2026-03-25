package ASM.model;

import java.io.Serializable;

/**
 * Lop dai dien cho Ban sao cua tai lieu
 * Moi ban sao thuoc ve mot tai lieu va co trang thai rieng
 */
public class Copy implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;              // Ma ban sao (duy nhat)
    private String documentId;      // Ma tai lieu (FK)
    private CopyStatus status;      // Trang thai ban sao
    private transient Document document;  // Tham chieu den tai lieu (khong luu vao file)
    
    // Constructor mac dinh
    public Copy() {
        this.status = CopyStatus.GOOD;  // Mac dinh la con tot
    }
    
    // Constructor day du
    public Copy(String id, String documentId, CopyStatus status) {
        this.id = id;
        this.documentId = documentId;
        this.status = status;
    }
    
    // Getters va Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getDocumentId() {
        return documentId;
    }
    
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    
    public CopyStatus getStatus() {
        return status;
    }
    
    public void setStatus(CopyStatus status) {
        this.status = status;
    }
    
    public Document getDocument() {
        return document;
    }
    
    public void setDocument(Document document) {
        this.document = document;
        if (document != null) {
            this.documentId = document.getId();
        }
    }
    
    @Override
    public String toString() {
        return String.format("Copy[ID=%s, DocumentID=%s, Status=%s]",
                id, documentId, status);
    }
}
