package ASM.exception;

/**
 * Exception duoc nem ra khi phat hien ma ID bi trung lap
 * Su dung de kiem tra tinh duy nhat cua ID tai lieu va ban sao
 */
public class DuplicateIdException extends Exception {
    
    // Constructor voi thong bao loi
    public DuplicateIdException(String message) {
        super(message);
    }
    
    // Constructor voi thong bao va nguyen nhan
    public DuplicateIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
