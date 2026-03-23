import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bai2 {
    public static void main(String[] args) {
        // Tạo List ít nhất 8 nhân viên
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "An Nguyen", 12_000_000));
        employees.add(new Employee(2, "Binh Tran", 18_000_000));
        employees.add(new Employee(3, "Cuong Le", 15_000_000));
        employees.add(new Employee(4, "Dung Pham", 20_000_000));
        employees.add(new Employee(5, "Hoang Vo", 14_000_000));
        employees.add(new Employee(6, "Anna Smith", 25_000_000));
        employees.add(new Employee(7, "Khanh Do", 16_000_000));
        employees.add(new Employee(8, "Alex Johnson", 22_000_000));

        // Lọc nhân viên có salary >= 15_000_000
        System.out.println("=== Nhan vien co luong >= 15,000,000 ===");
        employees.stream()
                 .filter(emp -> emp.getSalary() >= 15_000_000)
                 .forEach(System.out::println);

        // Sắp xếp nhân viên theo salary giảm dần
        System.out.println("\n=== Sap xep theo luong giam dan ===");
        employees.stream()
                 .sorted((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()))
                 .forEach(System.out::println);

        // Lấy danh sách tên nhân viên (List) từ danh sách nhân viên (map)
        System.out.println("\n=== Danh sach ten nhan vien ===");
        List<String> employeeNames = employees.stream()
                                               .map(Employee::getName)
                                               .collect(Collectors.toList());
        employeeNames.forEach(System.out::println);

        // Đếm số nhân viên có tên bắt đầu bằng chữ "A" (không phân biệt hoa thường)
        long count = employees.stream()
                              .filter(emp -> emp.getName().toLowerCase().startsWith("a"))
                              .count();
        System.out.println("\n=== So nhan vien co ten bat dau bang 'A': " + count + " ===");
    }
}
