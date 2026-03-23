package Bai1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentService service = new StudentService();
        
        while (true) {
            System.out.println("\n=== QUAN LY SINH VIEN ===");
            System.out.println("1. Them sinh vien");
            System.out.println("2. Hien thi danh sach");
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
                        System.out.print("Nhap GPA: ");
                        double gpa = scanner.nextDouble();
                        scanner.nextLine();
                        
                        service.themSinhVien(id, name, gpa);
                        break;
                    case 2:
                        service.hienThiDanhSach();
                        break;
                    default:
                        System.out.println("Lua chon khong hop le!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("LOI: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("LOI: Du lieu nhap khong hop le!");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
}
