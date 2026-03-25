package ASM.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Lop dai dien cho Tai lieu trong thu vien
 * Moi tai lieu co the co nhieu ban sao (quan he 1-N)
 */
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;              // Ma tai lieu (duy nhat)
    private String title;           // Tieu de
    private String author;          // Tac gia
    private String category;        // The loai
    private List<Copy> copies;      // Danh sach cac ban sao (quan he 1-N)
    
    // Constructor mac dinh
    public Document() {
        this.copies = new ArrayList<>();
    }
    
    // Constructor day du
    public Document(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.copies = new ArrayList<>();
    }
    
    // Getters va Setters
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
    
    // Them ban sao vao tai lieu
    public void addCopy(Copy copy) {
        this.copies.add(copy);
        copy.setDocument(this);  // Thiet lap quan he 2 chieu
    }
    
    // Xoa ban sao khoi tai lieu
    public void removeCopy(Copy copy) {
        this.copies.remove(copy);
    }
    
    @Override
    public String toString() {
        return String.format("Document[ID=%s, Title=%s, Author=%s, Category=%s, Copies=%d]",
                id, title, author, category, copies.size());
    }
}
