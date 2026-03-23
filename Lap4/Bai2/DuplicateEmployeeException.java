package Bai2;

public class DuplicateEmployeeException extends Exception {
    private String employeeId;

    public DuplicateEmployeeException(String employeeId) {
        super("Nhan vien voi ID " + employeeId + " da ton tai!");
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
