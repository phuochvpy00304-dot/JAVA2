package ASM.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp đại diện cho Tài liệu trong thư viện
 * Mỗi tài liệu có thể có nhiều bản sao (quan hệ 1-N)
 */
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;              // Mã tài liệu (duy nhất)
    private String title;           // Tiêu đề
    private String author;          // Tác giả
    private String category;        // Thể loại
    private List<Copy> copies;      // Danh sách các bản sao (quan hệ 1-N)
    
    // Constructor mặc định
    public Document() {
        this.copies = new ArrayList<>();
    }
    
    // Constructor đầy đủ
    public Document(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.copies = new ArrayList<>();
    }
    
    // Getters và Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public List<Copy> getCopies() {
        return copies;
    }
    
    public void setCopies(List<Copy> copies) {
        this.copies = copies;
    }
    
    // Thêm bản sao vào tài liệu
    public void addCopy(Copy copy) {
        this.copies.add(copy);
        copy.setDocument(this);  // Thiết lập quan hệ 2 chiều
    }
    
    // Xóa bản sao khỏi tài liệu
    public void removeCopy(Copy copy) {
        this.copies.remove(copy);
    }
    
    @Override
    public String toString() {
        return String.format("Document[ID=%s, Title=%s, Author=%s, Category=%s, Copies=%d]",
                id, title, author, category, copies.size());
    }
}
