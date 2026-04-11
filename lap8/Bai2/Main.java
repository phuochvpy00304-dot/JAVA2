package lap8.Bai2;

import java.util.ArrayList;
import java.util.List;

/**
 * Bài 2: Ứng dụng quản lý sinh viên với Record
 */
public class Main {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        
        try {
            // Tạo danh sách sinh viên
            students.add(new Student("SV001", "Nguyễn Văn A", 3.5));
            students.add(new Student("SV002", "Trần Thị B", 2.8));
            students.add(new Student("SV003", "Lê Văn C", 3.8));
            students.add(new Student("SV004", "Phạm Thị D", 3.2));
            students.add(new Student("SV005", "Hoàng Văn E", 2.5));
            
            System.out.println("=== DANH SÁCH TẤT CẢ SINH VIÊN ===");
            for (Student student : students) {
                System.out.println(student);
            }
            
            // Lọc và in danh sách sinh viên đủ điều kiện học bổng
            System.out.println("\n=== SINH VIÊN ĐỦ ĐIỀU KIỆN HỌC BỔNG (GPA >= 3.2) ===");
            List<Student> scholarshipStudents = students.stream()
                    .filter(Student::isScholarshipEligible)
                    .toList();
            
            if (scholarshipStudents.isEmpty()) {
                System.out.println("Không có sinh viên nào đủ điều kiện học bổng.");
            } else {
                for (Student student : scholarshipStudents) {
                    System.out.printf("- %s (GPA: %.2f)\n", 
                        student.name(), student.gpa());
                }
                System.out.println("\nTổng số sinh viên đủ điều kiện: " + 
                    scholarshipStudents.size());
            }
            
            // Test validation
            System.out.println("\n=== TEST VALIDATION ===");
            try {
                Student invalidStudent = new Student("SV006", "Test", 4.5);
            } catch (IllegalArgumentException e) {
                System.out.println("Lỗi khi tạo sinh viên: " + e.getMessage());
            }
            
            try {
                Student invalidStudent = new Student("SV007", "Test", -1.0);
            } catch (IllegalArgumentException e) {
                System.out.println("Lỗi khi tạo sinh viên: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
