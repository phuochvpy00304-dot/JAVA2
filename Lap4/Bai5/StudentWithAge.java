package Bai5;

public class StudentWithAge {
    private String id;
    private String name;
    private double gpa;
    private int age;
    
    private static final int MIN_AGE = 16;
    private static final int MAX_AGE = 60;

    public StudentWithAge(String id, String name, double gpa, int age) 
            throws AgeOutOfRangeException {
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new AgeOutOfRangeException(age, MIN_AGE, MAX_AGE);
        }
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.age = age;
    }

    public void inThongTin() {
        System.out.println("ID: " + id + ", Name: " + name + 
                ", GPA: " + gpa + ", Age: " + age);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public int getAge() {
        return age;
    }
}
