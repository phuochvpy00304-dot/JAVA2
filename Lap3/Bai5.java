import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Bai5 {
    public static void main(String[] args) {
        // Tạo danh sách sinh viên
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

        // Đếm số sinh viên theo từng StudentType
        System.out.println("=== So luong sinh vien theo tung loai ===");
        Map<StudentType, Long> countByType = students.stream()
                                                     .collect(Collectors.groupingBy(
                                                         Student::getType,
                                                         Collectors.counting()
                                                     ));
        countByType.forEach((type, count) -> 
            System.out.println(type + ": " + count + " sinh vien")
        );

        // Tính GPA trung bình theo từng StudentType
        System.out.println("\n=== GPA trung binh theo tung loai ===");
        Map<StudentType, Double> avgGpaByType = students.stream()
                                                        .collect(Collectors.groupingBy(
                                                            Student::getType,
                                                            Collectors.averagingDouble(Student::getGpa)
                                                        ));
        avgGpaByType.forEach((type, avgGpa) -> 
            System.out.printf("%s: %.2f\n", type, avgGpa)
        );

        // In loại sinh viên có GPA trung bình cao nhất
        System.out.println("\n=== Loai sinh vien co GPA trung binh cao nhat ===");
        avgGpaByType.entrySet().stream()
                    .max((e1, e2) -> Double.compare(e1.getValue(), e2.getValue()))
                    .ifPresent(entry -> 
                        System.out.printf("%s voi GPA trung binh: %.2f\n", 
                                        entry.getKey(), entry.getValue())
                    );
    }
}
