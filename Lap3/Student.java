public class Student {
    private int id;
    private String name;
    private StudentType type;
    private double gpa;

    public Student() {
    }

    public Student(int id, String name, StudentType type, double gpa) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.gpa = gpa;
    }

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

    public StudentType getType() {
        return type;
    }

    public void setType(StudentType type) {
        this.type = type;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', type=" + type + ", gpa=" + gpa + "}";
    }
}
