package lap8.Bai3;

import java.util.ArrayList;
import java.util.List;

/**
 * Service xử lý nghiệp vụ Employee
 * Sử dụng annotation @Developer
 */
@Developer(name = "Nguyen Van A", version = "2.0")
public class EmployeeService {
    private List<Employee> employees;
    
    public EmployeeService() {
        this.employees = new ArrayList<>();
    }
    
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
    
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
    
    public double calculateTotalSalary() {
        return employees.stream()
                .mapToDouble(Employee::calculateSalary)
                .sum();
    }
    
    public void displayAllEmployees() {
        System.out.println("=== DANH SÁCH NHÂN VIÊN ===");
        for (Employee emp : employees) {
            System.out.printf("ID: %s, Name: %s, Salary: %.2f\n",
                emp.getId(), emp.getName(), emp.calculateSalary());
        }
    }
}
