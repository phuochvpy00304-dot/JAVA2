package Bai2;

public class InvalidSalaryException extends Exception {
    private double salary;

    public InvalidSalaryException(double salary) {
        super("Luong khong hop le: " + salary + ". Luong phai lon hon hoac bang 0!");
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
}
