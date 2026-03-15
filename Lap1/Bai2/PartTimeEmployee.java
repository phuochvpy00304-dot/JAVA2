public class PartTimeEmployee extends Employee {
    private double workingHours;
    private double hourlyRate;

    public PartTimeEmployee(String id, String name, double basicSalary, double workingHours, double hourlyRate) {
        super(id, name, basicSalary);
        setWorkingHours(workingHours);
        setHourlyRate(hourlyRate);
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        if (workingHours < 0) {
            throw new IllegalArgumentException("Số giờ làm việc phải >= 0");
        }
        this.workingHours = workingHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("Lương theo giờ phải >= 0");
        }
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double income() {
        return workingHours * hourlyRate;
    }

    @Override
    public String toString() {
        return "PartTimeEmployee{id='" + getId() + "', name='" + getName() + 
               "', basicSalary=" + getBasicSalary() + ", workingHours=" + workingHours + 
               ", hourlyRate=" + hourlyRate + "}";
    }
}
