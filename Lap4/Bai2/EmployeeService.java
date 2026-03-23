package Bai2;

import java.util.ArrayList;

public class EmployeeService {
    private ArrayList<Employee> employees;

    public EmployeeService() {
        employees = new ArrayList<>();
    }

    public void themNhanVien(String id, String name, double salary) 
            throws DuplicateEmployeeException, InvalidSalaryException {
        
        // Kiem tra trung ID
        for (Employee emp : employees) {
            if (emp.getId().equals(id)) {
                throw new DuplicateEmployeeException(id);
            }
        }
        
        // Kiem tra luong am
        if (salary < 0) {
            throw new InvalidSalaryException(salary);
        }
        
        Employee employee = new Employee(id, name, salary);
        employees.add(employee);
        System.out.println("Them nhan vien thanh cong!");
    }

    public Employee timNhanVienTheoMa(String id) {
        for (Employee emp : employees) {
            if (emp.getId().equals(id)) {
                return emp;
            }
        }
        return null;
    }

    public void hienThiDanhSach() {
        if (employees.isEmpty()) {
            System.out.println("Danh sach nhan vien rong!");
            return;
        }
        System.out.println("\n=== DANH SACH NHAN VIEN ===");
        for (Employee emp : employees) {
            emp.inThongTin();
        }
    }
}
