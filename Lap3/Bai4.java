import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bai4 {
    public static void main(String[] args) {
        // Tạo List ít nhất 10 sinh viên (đủ 3 loại)
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Nguyen Van An", StudentType.REGULAR, 3.5));
        students.add(new Student(2, "Tran Thi Binh", StudentType.PART_TIME, 3.0));
        students.add(new Student(3, "Le Van Cuong", StudentType.INTERNATIONAL, 3.8));
        students.add(new Student(4, "Pham Thi Dung", StudentType.REGULAR, 2.9));
        students.add(new Student(5, "Vo Van Hoang", StudentType.PART_TIME, 3.2));
        students.add(new Student(6, "John Smith", StudentType.INTERNATIONAL, 3.6));
        students.add(new Student(7, "Do Thi Khanh", StudentType.REGULAR, 3.7));
        students.add(new Student(8, "Mary Johnson", StudentType.INTERNATIONAL, 3.3));
        students.add(new Student(9, "Nguyen Van Linh", StudentType.PART_TIME, 2.8));
        students.add(new Student(10, "David Lee", StudentType.INTERNATIONAL, 3.9));

        // Lọc danh sách sinh viên INTERNATIONAL có gpa >= 3.2
        System.out.println("=== Sinh vien INTERNATIONAL co GPA >= 3.2 ===");
        students.stream()
                .filter(s -> s.getType() == StudentType.INTERNATIONAL)
                .filter(s -> s.getGpa() >= 3.2)
                .forEach(System.out::println);

        // Sắp xếp sinh viên theo gpa giảm dần và lấy top 3
        System.out.println("\n=== Top 3 sinh vien co GPA cao nhat ===");
        students.stream()
                .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()))
                .limit(3)
                .forEach(System.out::println);

        // Tạo danh sách chỉ gồm name của sinh viên PART_TIME
        System.out.println("\n=== Danh sach ten sinh vien PART_TIME ===");
        List<String> partTimeNames = students.stream()
                                             .filter(s -> s.getType() == StudentType.PART_TIME)
                                             .map(Student::getName)
                                             .collect(Collectors.toList());
        partTimeNames.forEach(System.out::println);
    }
}
