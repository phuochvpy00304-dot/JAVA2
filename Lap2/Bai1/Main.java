import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Tạo danh sách sinh viên
        List<Student> students = new ArrayList<>();
        
        students.add(new Student("SV001", "Nguyễn Văn A", StudentType.REGULAR));
        students.add(new Student("SV002", "Trần Thị B", StudentType.REGULAR));
        students.add(new Student("SV003", "Lê Văn C", StudentType.PART_TIME));
        students.add(new Student("SV004", "Phạm Thị D", StudentType.PART_TIME));
        students.add(new Student("SV005", "John Smith", StudentType.INTERNATIONAL));
        students.add(new Student("SV006", "Maria Garcia", StudentType.INTERNATIONAL));
        
        // Hiển thị danh sách toàn bộ sinh viên
        System.out.println("=== DANH SÁCH TOÀN BỘ SINH VIÊN ===");
        for (Student student : students) {
            System.out.println(student);
        }
        
        // Hiển thị danh sách sinh viên theo từng loại
        System.out.println("\n=== DANH SÁCH SINH VIÊN THEO TỪNG LOẠI ===");
        for (StudentType type : StudentType.values()) {
            System.out.println("\n" + type + ":");
            for (Student student : students) {
                if (student.getType() == type) {
                    System.out.println("  " + student);
                }
            }
        }
        
        // Đếm số lượng sinh viên của mỗi loại
        System.out.println("\n=== SỐ LƯỢNG SINH VIÊN THEO TỪNG LOẠI ===");
        for (StudentType type : StudentType.values()) {
            int count = 0;
            for (Student student : students) {
                if (student.getType() == type) {
                    count++;
                }
            }
            System.out.println(type + ": " + count + " sinh viên");
        }
    }
}
