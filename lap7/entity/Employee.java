package entity;

/**
 * Lớp ánh xạ với bảng employee trong CSDL
 */
public class Employee {
    private int id;
    private String name;
    private float salary;
    
    // Constructor mặc định
    public Employee() {
    }
    
    // Constructor đầy đủ tham số
    public Employee(int id, String name, float salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
    
    // Constructor không có id (dùng khi thêm mới)
    public Employee(String name, float salary) {
        this.name = name;
        this.salary = salary;
    }
    
    // Getters và Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public float getSalary() {
        return salary;
    }
    
    public void setSalary(float salary) {
        this.salary = salary;
    }
    
    @Override
    public String toString() {
        return String.format("Employee[id=%d, name=%s, salary=%.2f VNĐ]", 
                           id, name, salary);
    }
}
