package Lap5.Bai3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service quản lý danh sách sinh viên với I/O operations
 */
public class StudentService {
    private static final String FILE_NAME = "Lap5/Bai3/students.dat";
    private List<Student> students;

    public StudentService() {
        this.students = new ArrayList<>();
        loadData();
    }

    /**
     * Đọc dữ liệu từ file khi khởi động
     */
    private void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("File chua ton tai. Khoi tao danh sach rong.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
            System.out.println("Da tai " + students.size() + " sinh vien tu file.");
        } catch (IOException e) {
            System.err.println("Loi khi doc file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Loi: Khong tim thay lop: " + e.getMessage());
        }
    }

    /**
     * Ghi dữ liệu vào file
     */
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("Da luu " + students.size() + " sinh vien vao file.");
        } catch (IOException e) {
            System.err.println("Loi khi luu file: " + e.getMessage());
        }
    }

    /**
     * Thêm sinh viên
     */
    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Da them sinh vien: " + student);
    }

    /**
     * Hiển thị danh sách sinh viên
     */
    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("Danh sach sinh vien rong.");
            return;
        }
        System.out.println("\n=== DANH SACH SINH VIEN ===");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i));
        }
    }

    /**
     * Lấy danh sách sinh viên
     */
    public List<Student> getStudents() {
        return students;
    }
}
