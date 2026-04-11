package Lap5.Bai2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Bài 2: Lưu trữ và khôi phục đối tượng từ file
 */
public class Main {
    private static final String FILE_NAME = "Lap5/Bai2/students.dat";

    public static void main(String[] args) {
        // Tạo danh sách sinh viên
        List<Student> students = new ArrayList<>();
        students.add(new Student("SV001", "Nguyen Van A", 3.5));
        students.add(new Student("SV002", "Tran Thi B", 3.8));
        students.add(new Student("SV003", "Le Van C", 3.2));
        students.add(new Student("SV004", "Pham Thi D", 3.9));

        // Ghi danh sách vào file
        saveToFile(students);

        // Đọc danh sách từ file
        loadFromFile();
    }

    /**
     * Lưu danh sách sinh viên vào file nhị phân
     */
    public static void saveToFile(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("=== LUU DU LIEU ===");
            System.out.println("Da luu " + students.size() + " sinh vien vao file.");
            for (Student student : students) {
                System.out.println("  - " + student);
            }
            System.out.println();
        } catch (IOException e) {
            System.err.println("Loi khi luu file: " + e.getMessage());
        }
    }

    /**
     * Đọc danh sách sinh viên từ file nhị phân
     */
    @SuppressWarnings("unchecked")
    public static void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            List<Student> students = (List<Student>) ois.readObject();
            System.out.println("=== DOC DU LIEU ===");
            System.out.println("Da doc " + students.size() + " sinh vien tu file:");
            for (Student student : students) {
                System.out.println("  - " + student);
            }
        } catch (IOException e) {
            System.err.println("Loi khi doc file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Loi: Khong tim thay lop Student: " + e.getMessage());
        }
    }
}
