import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeService service = new EmployeeService();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== QUẢN LÝ NHÂN VIÊN ===");
            System.out.println("1. Thêm nhân viên");
            System.out.println("2. Hiển thị danh sách nhân viên");
            System.out.println("3. Tìm nhân viên theo mã");
            System.out.println("4. Cập nhật lương nhân viên");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống

            switch (choice) {
                case 1:
                    System.out.print("Nhập mã nhân viên: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhập tên nhân viên: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập lương: ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine();

                    Employee employee = new Employee(id, name, salary);
                    service.addEmployee(employee);
                    break;

                case 2:
                    service.displayEmployees();
                    break;

                case 3:
                    System.out.print("Nhập mã nhân viên cần tìm: ");
                    String searchId = scanner.nextLine();
                    Employee found = service.findEmployeeById(searchId);
                    if (found != null) {
                        System.out.println("Tìm thấy: " + found);
                    } else {
                        System.out.println("Không tìm thấy nhân viên với mã: " + searchId);
                    }
                    break;

                case 4:
                    System.out.print("Nhập mã nhân viên cần cập nhật lương: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Nhập lương mới: ");
                    double newSalary = scanner.nextDouble();
                    scanner.nextLine();

                    service.updateSalary(updateId, newSalary);
                    break;

                case 0:
                    System.out.println("Thoát chương trình!");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 0);

        scanner.close();
    }
}
