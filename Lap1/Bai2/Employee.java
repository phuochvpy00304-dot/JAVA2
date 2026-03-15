public class Employee {
    private String id;
    private String name;
    private double basicSalary;

    public Employee(String id, String name, double basicSalary) {
        setId(id);
        this.name = name;
        setBasicSalary(basicSalary);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID không được rỗng");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        if (basicSalary < 0) {
            throw new IllegalArgumentException("Lương cơ bản phải >= 0");
        }
        this.basicSalary = basicSalary;
    }

    public double income() {
        return basicSalary;
    }

    @Override
    public String toString() {
        return "Employee{id='" + id + "', name='" + name + "', basicSalary=" + basicSalary + "}";
    }
}
