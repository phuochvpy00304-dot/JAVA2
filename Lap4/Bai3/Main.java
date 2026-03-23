package Bai3;

import Bai1.Student;
import Bai2.Employee;

public class Main {
    public static void main(String[] args) {
        // Su dung GenericManager cho Student
        System.out.println("=== QUAN LY SINH VIEN ===");
        GenericManager<Student> studentManager = new GenericManager<>();
        
        studentManager.themDoiTuong(new Student("SV001", "Nguyen Van A", 3.5));
        studentManager.themDoiTuong(new Student("SV002", "Tran Thi B", 3.8));
        studentManager.themDoiTuong(new Student("SV003", "Le Van C", 3.2));
        
        System.out.println("So luong sinh vien: " + studentManager.laySoLuong());
        
        for (Student s : studentManager.getList()) {
            s.inThongTin();
        }
        
        // Su dung GenericManager cho Employee
        System.out.println("\n=== QUAN LY NHAN VIEN ===");
        GenericManager<Employee> employeeManager = new GenericManager<>();
        
        employeeManager.themDoiTuong(new Employee("NV001", "Pham Van D", 15000000));
        employeeManager.themDoiTuong(new Employee("NV002", "Hoang Thi E", 18000000));
        employeeManager.themDoiTuong(new Employee("NV003", "Vo Van F", 20000000));
        
        System.out.println("So luong nhan vien: " + employeeManager.laySoLuong());
        
        for (Employee e : employeeManager.getList()) {
            e.inThongTin();
        }
        
        // Su dung GenericManager cho String
        System.out.println("\n=== QUAN LY CHUOI ===");
        GenericManager<String> stringManager = new GenericManager<>();
        
        stringManager.themDoiTuong("Java");
        stringManager.themDoiTuong("Python");
        stringManager.themDoiTuong("C++");
        
        System.out.println("So luong chuoi: " + stringManager.laySoLuong());
        stringManager.hienThiDanhSach();
    }
}
