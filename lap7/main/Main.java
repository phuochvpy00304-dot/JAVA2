package main;

import connect.DBConnect;
import entity.Employee;
import repository.EmployeeRepository;

import java.util.List;

/**
 * Chương trình chính demo các chức năng CRUD
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== LAB 6: JDBC - QUẢN LÝ NHÂN VIÊN ===\n");
        
        // Kiểm tra kết nối
        System.out.println("1. KIỂM TRA KẾT NỐI:");
        DBConnect.testConnection();
        System.out.println();
        
        // Khởi tạo repository
        EmployeeRepository repository = new EmployeeRepository();
        
        // Hiển thị danh sách ban đầu
        System.out.println("2. DANH SÁCH NHÂN VIÊN BAN ĐẦU:");
        displayAllEmployees(repository);
        
        // Thêm nhân viên mới
        System.out.println("\n3. THÊM NHÂN VIÊN MỚI:");
        Employee newEmp = new Employee("Võ Thị Phương", 17500000);
        if (repository.addEmployee(newEmp)) {
            System.out.println("✓ Thêm nhân viên thành công!");
            displayAllEmployees(repository);
        } else {
            System.out.println("✗ Thêm nhân viên thất bại!");
        }
        
        // Tìm kiếm nhân viên theo id
        System.out.println("\n4. TÌM KIẾM NHÂN VIÊN THEO ID:");
        int searchId = 2;
        Employee foundEmp = repository.findEmployeeById(searchId);
        if (foundEmp != null) {
            System.out.println("✓ Tìm thấy: " + foundEmp);
        } else {
            System.out.println("✗ Không tìm thấy nhân viên có id = " + searchId);
        }
        
        // Cập nhật thông tin nhân viên
        System.out.println("\n5. CẬP NHẬT THÔNG TIN NHÂN VIÊN:");
        if (foundEmp != null) {
            foundEmp.setSalary(19000000);
            if (repository.updateEmployee(foundEmp)) {
                System.out.println("✓ Cập nhật thành công!");
                displayAllEmployees(repository);
            } else {
                System.out.println("✗ Cập nhật thất bại!");
            }
        }
        
        // Xóa nhân viên
        System.out.println("\n6. XÓA NHÂN VIÊN:");
        int deleteId = 3;
        if (repository.deleteEmployee(deleteId)) {
            System.out.println("✓ Xóa nhân viên id=" + deleteId + " thành công!");
            displayAllEmployees(repository);
        } else {
            System.out.println("✗ Xóa nhân viên thất bại!");
        }
        
        System.out.println("\n=== KẾT THÚC CHƯƠNG TRÌNH ===");
    }
    
    /**
     * Hiển thị danh sách tất cả nhân viên
     */
    private static void displayAllEmployees(EmployeeRepository repository) {
        List<Employee> employees = repository.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        
        System.out.println("┌──────┬────────────────────────┬──────────────┐");
        System.out.println("│  ID  │         Tên            │    Lương     │");
        System.out.println("├──────┼────────────────────────┼──────────────┤");
        
        for (Employee emp : employees) {
            System.out.printf("│ %-4d │ %-22s │ %,12.0f │%n", 
                            emp.getId(), emp.getName(), emp.getSalary());
        }
        
        System.out.println("└──────┴────────────────────────┴──────────────┘");
        System.out.println("Tổng số: " + employees.size() + " nhân viên");
    }
}
