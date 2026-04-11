package lap8.Bai3;

import java.lang.reflect.Method;

/**
 * Bài 3: Annotations và Reflection
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== BÀI 3: ANNOTATIONS VÀ REFLECTION ===\n");
        
        // 1. Test @Override và @Deprecated
        System.out.println("1. Test @Override và @Deprecated:");
        Employee emp = new Employee("E001", "Nguyễn Văn A", 5000000);
        Manager mgr = new Manager("M001", "Trần Thị B", 8000000, 2000000);
        
        System.out.println("Employee - Phương thức mới:");
        System.out.printf("Lương: %.2f\n", emp.calculateSalary());
        
        System.out.println("\nEmployee - Phương thức cũ (deprecated):");
        System.out.printf("Lương: %.2f\n", emp.getSalary());
        
        System.out.println("\nManager - Phương thức mới:");
        System.out.printf("Lương: %.2f\n", mgr.calculateSalary());
        
        System.out.println("\nManager - Phương thức cũ (deprecated):");
        System.out.printf("Lương: %.2f\n", mgr.getSalary());
        
        // 2. Test custom annotation với Reflection
        System.out.println("\n\n2. Đọc annotation @Developer bằng Reflection:");
        readDeveloperAnnotation(EmployeeService.class);
        
        // 3. Sử dụng EmployeeService
        System.out.println("\n\n3. Sử dụng EmployeeService:");
        EmployeeService service = new EmployeeService();
        service.addEmployee(emp);
        service.addEmployee(mgr);
        service.addEmployee(new Employee("E002", "Lê Văn C", 6000000));
        
        service.displayAllEmployees();
        System.out.printf("\nTổng lương: %.2f\n", service.calculateTotalSalary());
    }
    
    /**
     * Sử dụng Reflection để đọc annotation @Developer
     */
    private static void readDeveloperAnnotation(Class<?> clazz) {
        System.out.println("Đọc annotation từ class: " + clazz.getSimpleName());
        
        if (clazz.isAnnotationPresent(Developer.class)) {
            Developer dev = clazz.getAnnotation(Developer.class);
            System.out.println("✓ Tìm thấy @Developer annotation:");
            System.out.println("  - Developer Name: " + dev.name());
            System.out.println("  - Version: " + dev.version());
        } else {
            System.out.println("✗ Không tìm thấy @Developer annotation");
        }
        
        // Hiển thị thêm thông tin về class
        System.out.println("\nThông tin thêm:");
        System.out.println("  - Package: " + clazz.getPackageName());
        System.out.println("  - Methods: ");
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println("    + " + method.getName());
        }
    }
}
