package lap8.Bai2;

/**
 * Record Student - Mô hình hóa dữ liệu sinh viên
 * Record tự động tạo constructor, getter, equals, hashCode, toString
 * Đảm bảo tính bất biến (immutable)
 */
public record Student(String id, String name, double gpa) {
    
    /**
     * Compact constructor - Kiểm tra dữ liệu đầu vào
     */
    public Student {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException(
                "GPA không hợp lệ: " + gpa + ". GPA phải trong khoảng [0.0, 4.0]"
            );
        }
        
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID không được để trống");
        }
        
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tên không được để trống");
        }
    }
    
    /**
     * Phương thức kiểm tra điều kiện học bổng
     * @return true nếu GPA >= 3.2
     */
    public boolean isScholarshipEligible() {
        return gpa >= 3.2;
    }
    
    /**
     * Override toString để hiển thị thông tin đẹp hơn
     */
    @Override
    public String toString() {
        return String.format("Student[ID=%s, Name=%s, GPA=%.2f]", id, name, gpa);
    }
}
