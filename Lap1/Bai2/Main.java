public class Main {
    public static void main(String[] args) {
        // Tạo danh sách nhân viên
        Employee[] employees = new Employee[5];
        
        employees[0] = new FullTimeEmployee("E001", "Nguyễn Văn A", 10000000, 2000000, 500000);
        employees[1] = new FullTimeEmployee("E002", "Trần Thị B", 12000000, 3000000, 0);
        employees[2] = new PartTimeEmployee("E003", "Lê Văn C", 0, 120, 50000);
        employees[3] = new PartTimeEmployee("E004", "Phạm Thị D", 0, 80, 45000);
        employees[4] = new Employee("E005", "Hoàng Văn E", 8000000);
        
        // In danh sách nhân viên và thu nhập
        System.out.println("=== DANH SÁCH NHÂN VIÊN ===");
        for (Employee employee : employees) {
            System.out.println(employee);
            System.out.printf("Thu nhập: %.0f VNĐ\n", employee.income());
            System.out.println("---");
        }
        
        // Tìm nhân viên có thu nhập cao nhất
        Employee maxIncomeEmployee = employees[0];
        for (Employee employee : employees) {
            if (employee.income() > maxIncomeEmployee.income()) {
                maxIncomeEmployee = employee;
            }
        }
        
        System.out.println("\n=== NHÂN VIÊN CÓ THU NHẬP CAO NHẤT ===");
        System.out.println(maxIncomeEmployee);
        System.out.printf("Thu nhập: %.0f VNĐ\n", maxIncomeEmployee.income());
    }
}
