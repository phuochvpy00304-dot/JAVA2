package lap8.Bai3;

/**
 * Lớp Manager kế thừa Employee
 */
public class Manager extends Employee {
    private double bonus;
    
    public Manager(String id, String name, double baseSalary, double bonus) {
        super(id, name, baseSalary);
        this.bonus = bonus;
    }
    
    /**
     * Override phương thức cũ
     * @deprecated Sử dụng {@link #calculateSalary()} thay thế
     */
    @Deprecated
    @Override
    public double getSalary() {
        System.out.println("[DEPRECATED] Manager.getSalary() đã lỗi thời!");
        return baseSalary + bonus;
    }
    
    /**
     * Override phương thức mới
     */
    @Override
    public double calculateSalary() {
        return baseSalary + bonus;
    }
    
    public double getBonus() {
        return bonus;
    }
}
