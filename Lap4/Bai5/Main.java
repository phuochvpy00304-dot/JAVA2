package Lap4.Bai5;

import java.util.Scanner;

/**
 * Demo su dung AgeOutOfRangeException
 */
public class Main {
    private static final int STUDENT_MIN_AGE = 16;
    private static final int STUDENT_MAX_AGE = 60;
    private static final int EMPLOYEE_MIN_AGE = 18;
    private static final int EMPLOYEE_MAX_AGE = 65;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== DEMO AGEOUTOFRANGEEXCEPTION ===\n");
        
        // Demo 1: Nhap tuoi sinh vien
        System.out.println("1. Nhap thong tin sinh vien:");
        inputStudentAge(scanner);
        
        System.out.println("\n" + "=".repeat(40) + "\n");
        
        // Demo 2: Nhap tuoi nhan vien
        System.out.println("2. Nhap thong tin nhan vien:");
        inputEmployeeAge(scanner);
        
        scanner.close();
    }

    private static void inputStudentAge(Scanner scanner) {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Nhap ten sinh vien: ");
                String name = scanner.nextLine();
                
                System.out.print("Nhap tuoi sinh vien: ");
                int age = Integer.parseInt(scanner.nextLine());
                
                validateStudentAge(age);
                
                System.out.println("✓ Sinh vien hop le: " + name + ", " + age + " tuoi");
                valid = true;
                
            } catch (AgeOutOfRangeException e) {
                System.out.println("\n✗ LOI: " + e.getMessage());
                System.out.println(e.getDetailedMessage());
                System.out.println("\nVui long nhap lai!\n");
            } catch (NumberFormatException e) {
                System.out.println("✗ LOI: Tuoi phai la so nguyen!\n");
            }
        }
    }

    private static void inputEmployeeAge(Scanner scanner) {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Nhap ten nhan vien: ");
                String name = scanner.nextLine();
                
                System.out.print("Nhap tuoi nhan vien: ");
                int age = Integer.parseInt(scanner.nextLine());
                
                validateEmployeeAge(age);
                
                System.out.println("✓ Nhan vien hop le: " + name + ", " + age + " tuoi");
                valid = true;
                
            } catch (AgeOutOfRangeException e) {
                System.out.println("\n✗ LOI: " + e.getMessage());
                System.out.println(e.getDetailedMessage());
                System.out.println("\nVui long nhap lai!\n");
            } catch (NumberFormatException e) {
                System.out.println("✗ LOI: Tuoi phai la so nguyen!\n");
            }
        }
    }

    private static void validateStudentAge(int age) throws AgeOutOfRangeException {
        if (age < STUDENT_MIN_AGE || age > STUDENT_MAX_AGE) {
            throw new AgeOutOfRangeException(age, STUDENT_MIN_AGE, STUDENT_MAX_AGE);
        }
    }

    private static void validateEmployeeAge(int age) throws AgeOutOfRangeException {
        if (age < EMPLOYEE_MIN_AGE || age > EMPLOYEE_MAX_AGE) {
            throw new AgeOutOfRangeException(age, EMPLOYEE_MIN_AGE, EMPLOYEE_MAX_AGE);
        }
    }
}
