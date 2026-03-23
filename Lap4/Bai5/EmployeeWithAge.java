package Bai5;

public class EmployeeWithAge {
    private String id;
    private String name;
    private double salary;
    private int age;
    
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 65;

    public EmployeeWithAge(String id, String name, double salary, int age) 
            throws AgeOutOfRangeException {
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new AgeOutOfRangeException(age, MIN_AGE, MAX_AGE);
        }
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public void inThongTin() {
        System.out.println("ID: " + id + ", Name: " + name + 
                ", Salary: " + salary + ", Age: " + age);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }
}
