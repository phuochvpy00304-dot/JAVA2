public class FullTimeEmployee extends Employee {
    private double bonus;
    private double penalty;

    public FullTimeEmployee(String id, String name, double basicSalary, double bonus, double penalty) {
        super(id, name, basicSalary);
        setBonus(bonus);
        setPenalty(penalty);
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("Thưởng phải >= 0");
        }
        this.bonus = bonus;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        if (penalty < 0) {
            throw new IllegalArgumentException("Phạt phải >= 0");
        }
        this.penalty = penalty;
    }

    @Override
    public double income() {
        return getBasicSalary() + bonus - penalty;
    }

    @Override
    public String toString() {
        return "FullTimeEmployee{id='" + getId() + "', name='" + getName() + 
               "', basicSalary=" + getBasicSalary() + ", bonus=" + bonus + 
               ", penalty=" + penalty + "}";
    }
}
