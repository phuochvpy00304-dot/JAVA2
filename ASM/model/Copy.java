package ASM.model;

import java.io.Serializable;

/**
 * Lớp đại diện cho Bản sao của tài liệu
 * Mỗi bản sao thuộc về một tài liệu và có trạng thái riêng
 */
public class Copy implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;              // Mã bản sao (duy nhất)
    private String documentId;      // Mã tài liệu (FK)
    private CopyStatus status;      // Trạng thái bản sao
    private transient Document document;  // Tham chiếu đến tài liệu (không lưu vào file)
    
    // Constructor mặc định
    public Copy() {
        this.status = CopyStatus.GOOD;  // Mặc định là còn tốt
    }
    
    // Constructor đầy đủ
    public Copy(String id, String documentId, CopyStatus status) {
        this.id = id;
        this.documentId = documentId;
        this.status = status;
    }
    
    // Getters và Setters
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
