package ASM.model;

/**
 * Enum định nghĩa các trạng thái của bản sao tài liệu
 */
public enum CopyStatus {
    GOOD("Còn tốt"),           // Bản sao còn tốt
    DAMAGED("Hư hỏng"),        // Bản sao bị hư hỏng
    LOST("Mất");               // Bản sao bị mất
    
    private final String description;
    
    // Constructor
    CopyStatus(String description) {
        this.description = description;
    }
    
    // Lấy mô tả trạng thái
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return description;
    }
}
