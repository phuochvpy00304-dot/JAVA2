package ASM.exception;

/**
 * Exception được ném ra khi phát hiện mã ID bị trùng lặp
 * Sử dụng để kiểm tra tính duy nhất của ID tài liệu và bản sao
 */
public class DuplicateIdException extends Exception {
    
    // Constructor với thông báo lỗi
    public DuplicateIdException(String message) {
        super(message);
    }
    
    // Constructor với thông báo và nguyên nhân
    public DuplicateIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
