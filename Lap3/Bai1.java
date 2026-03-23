import java.util.ArrayList;
import java.util.List;

public class Bai1 {
    public static void main(String[] args) {
        // Tạo ArrayList chứa tối thiểu 10 tên
        List<String> names = new ArrayList<>();
        names.add("An");
        names.add("Binh");
        names.add("Cuong");
        names.add("Dung");
        names.add("Hoang");
        names.add("Khanh");
        names.add("Linh");
        names.add("Minh");
        names.add("Nguyen");
        names.add("Phuong");
        names.add("Quang");
        names.add("Tuan");

        // In toàn bộ danh sách bằng forEach
        System.out.println("=== Danh sach ten ===");
        names.forEach(name -> System.out.println(name));

        // Lọc và in các tên có độ dài > 5
        System.out.println("\n=== Ten co do dai > 5 ===");
        names.stream()
             .filter(name -> name.length() > 5)
             .forEach(name -> System.out.println(name));

        // Sắp xếp tên theo thứ tự A–Z và in ra
        System.out.println("\n=== Sap xep theo thu tu A-Z ===");
        names.stream()
             .sorted()
             .forEach(name -> System.out.println(name));

        // Sắp xếp theo độ dài tên (tăng dần) và in ra
        System.out.println("\n=== Sap xep theo do dai ten (tang dan) ===");
        names.stream()
             .sorted((n1, n2) -> Integer.compare(n1.length(), n2.length()))
             .forEach(name -> System.out.println(name));
    }
}
