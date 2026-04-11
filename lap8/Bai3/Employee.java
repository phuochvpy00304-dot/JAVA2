package lap8.Bai3;

/**
 * Lớp Employee cơ bản
 */
public class Employee {
    protected String id;
    protected String name;
    protected double baseSalary;
    
    public Employee(String id, String name, double baseSalary) {
        this.id = id;
        this.name = name;
        this.baseSalary = baseSalary;
    }
    
    /**
     * Phương thức cũ - đã lỗi thời
     * @deprecated Sử dụng {@link #calculateSalary()} thay thế
     */
    @Deprecated
    public double getSalary() {
        System.out.println("[DEPRECATED] Phương thức getSalary() đã lỗi thời!");
        return baseSalary;
    }
    
    /**
     * Phương thức mới thay thế
     */
    public double calculateSalary() {
        return baseSalary;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}
