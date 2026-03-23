package Bai2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeService service = new EmployeeService();
        
        while (true) {
            System.out.println("\n=== QUAN LY NHAN VIEN ===");
            System.out.println("1. Them nhan vien");
            System.out.println("2. Tim nhan vien theo ma");
            System.out.println("3. Hien thi danh sach");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice == 0) {
                System.out.println("Tam biet!");
                break;
            }
            
            try {
                switch (choice) {
                    case 1:
                        System.out.print("Nhap ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Nhap ten: ");
                        String name = scanner.nextLine();
                        System.out.print("Nhap luong: ");
                        double salary = scanner.nextDouble();
                        scanner.nextLine();
                        
                        service.themNhanVien(id, name, salary);
                        break;
                    case 2:
                        System.out.print("Nhap ID can tim: ");
                        String searchId = scanner.nextLine();
                        Employee emp = service.timNhanVienTheoMa(searchId);
                        if (emp != null) {
                            System.out.println("Tim thay nhan vien:");
                            emp.inThongTin();
                        } else {
                            System.out.println("Khong tim thay nhan vien voi ID: " + searchId);
                        }
                        break;
                    case 3:
                        service.hienThiDanhSach();
                        break;
                    default:
                        System.out.println("Lua chon khong hop le!");
                }
            } catch (DuplicateEmployeeException e) {
                System.out.println("LOI: " + e.getMessage());
            } catch (InvalidSalaryException e) {
                System.out.println("LOI: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("LOI: Du lieu nhap khong hop le!");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
}
