package Bai4;

import Bai1.Student;
import Bai2.Employee;

public class Main {
    public static void main(String[] args) {
        // Test voi danh sach rong
        System.out.println("=== TEST VOI DANH SACH RONG ===");
        GenericManager<Student> emptyManager = new GenericManager<>();
        
        try {
            Student first = emptyManager.layPhanTuDauTien();
            first.inThongTin();
        } catch (EmptyListException e) {
            System.out.println("LOI: " + e.getMessage());
        }
        
        try {
            Student last = emptyManager.layPhanTuCuoiCung();
            last.inThongTin();
        } catch (EmptyListException e) {
            System.out.println("LOI: " + e.getMessage());
        }
        
        // Test voi danh sach co du lieu
        System.out.println("\n=== TEST VOI DANH SACH CO DU LIEU ===");
        GenericManager<Student> studentManager = new GenericManager<>();
        
        studentManager.themDoiTuong(new Student("SV001", "Nguyen Van A", 3.5));
        studentManager.themDoiTuong(new Student("SV002", "Tran Thi B", 3.8));
        studentManager.themDoiTuong(new Student("SV003", "Le Van C", 3.2));
        
        try {
            System.out.println("\nPhan tu dau tien:");
            Student first = studentManager.layPhanTuDauTien();
            first.inThongTin();
            
            System.out.println("\nPhan tu cuoi cung:");
            Student last = studentManager.layPhanTuCuoiCung();
            last.inThongTin();
        } catch (EmptyListException e) {
            System.out.println("LOI: " + e.getMessage());
        }
        
        // Test voi Employee
        System.out.println("\n=== TEST VOI NHAN VIEN ===");
        GenericManager<Employee> employeeManager = new GenericManager<>();
        
        employeeManager.themDoiTuong(new Employee("NV001", "Pham Van D", 15000000));
        employeeManager.themDoiTuong(new Employee("NV002", "Hoang Thi E", 18000000));
        
        try {
            System.out.println("\nNhan vien dau tien:");
            Employee first = employeeManager.layPhanTuDauTien();
            first.inThongTin();
            
            System.out.println("\nNhan vien cuoi cung:");
            Employee last = employeeManager.layPhanTuCuoiCung();
            last.inThongTin();
        } catch (EmptyListException e) {
            System.out.println("LOI: " + e.getMessage());
        }
    }
}
