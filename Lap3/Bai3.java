import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bai3 {
    public static void main(String[] args) {
        // Tạo danh sách nhân viên
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "An Nguyen", 12_000_000));
        employees.add(new Employee(2, "Binh Tran", 18_000_000));
        employees.add(new Employee(3, "Cuong Le", 15_000_000));
        employees.add(new Employee(4, "Dung Pham", 20_000_000));
        employees.add(new Employee(5, "Hoang Vo", 14_000_000));
        employees.add(new Employee(6, "Anna Smith", 25_000_000));
        employees.add(new Employee(7, "Khanh Do", 16_000_000));
        employees.add(new Employee(8, "Alex Johnson", 22_000_000));

        // Tính tổng lương tất cả nhân viên
        double totalSalary = employees.stream()
                                      .mapToDouble(Employee::getSalary)
                                      .sum();
        System.out.println("=== Tong luong tat ca nhan vien ===");
        System.out.printf("Tong luong: %.0f VND\n", totalSalary);

        // Tính lương trung bình
        double averageSalary = employees.stream()
                                        .mapToDouble(Employee::getSalary)
                                        .average()
                                        .orElse(0.0);
        System.out.println("\n=== Luong trung binh ===");
        System.out.printf("Luong trung binh: %.0f VND\n", averageSalary);

        // Nhân viên có lương cao nhất
        Optional<Employee> highestPaidEmployee = employees.stream()
                                                          .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println("\n=== Nhan vien co luong cao nhat ===");
        highestPaidEmployee.ifPresent(emp -> 
            System.out.println(emp.getName() + " - Luong: " + emp.getSalary() + " VND")
        );
    }
}
