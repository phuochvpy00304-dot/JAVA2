package Bai1;

import java.util.ArrayList;

public class StudentService {
    private ArrayList<Student> students;

    public StudentService() {
        students = new ArrayList<>();
    }

    public void themSinhVien(String id, String name, double gpa) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID khong duoc rong!");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ten khong duoc rong!");
        }
        if (gpa < 0 || gpa > 4) {
            throw new IllegalArgumentException("GPA phai nam trong khoang 0-4!");
        }
        
        Student student = new Student(id, name, gpa);
        students.add(student);
        System.out.println("Them sinh vien thanh cong!");
    }

    public void hienThiDanhSach() {
        if (students.isEmpty()) {
            System.out.println("Danh sach sinh vien rong!");
            return;
        }
        System.out.println("\n=== DANH SACH SINH VIEN ===");
        for (Student student : students) {
            student.inThongTin();
        }
    }
}
