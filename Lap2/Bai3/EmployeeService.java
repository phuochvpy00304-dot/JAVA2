import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private List<Employee> employees;

    public EmployeeService() {
        this.employees = new ArrayList<>();
    }

    // Thêm một nhân viên vào danh sách
    public void addEmployee(Employee employee) {
        employees.add(employee);
        System.out.println("Đã thêm nhân viên: " + employee.getName());
    }

    // Hiển thị danh sách nhân viên
    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("Danh sách nhân viên trống!");
            return;
        }
        System.out.println("\n=== DANH SÁCH NHÂN VIÊN ===");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    // Tìm nhân viên theo mã
    public Employee findEmployeeById(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    // Cập nhật lương nhân viên theo mã
    public void updateSalary(String id, double newSalary) {
        Employee employee = findEmployeeById(id);
        if (employee != null) {
            employee.setSalary(newSalary);
            System.out.println("Đã cập nhật lương cho nhân viên " + employee.getName());
        } else {
            System.out.println("Không tìm thấy nhân viên với mã: " + id);
        }
    }
}
