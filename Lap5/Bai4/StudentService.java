package Lap5.Bai4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service với xử lý đầy đủ các tình huống lỗi
 */
public class StudentService {
    private static final String FILE_NAME = "Lap5/Bai4/students.dat";
    private List<Student> students;

    public StudentService() {
        this.students = new ArrayList<>();
        loadData();
    }

    /**
     * Đọc dữ liệu từ file với xử lý đầy đủ các lỗi
     */
    private void loadData() {
        File file = new File(FILE_NAME);
        
        // Xử lý: File không tồn tại
        if (!file.exists()) {
            System.out.println("[THONG BAO] File chua ton tai. Khoi tao danh sach rong.");
            System.out.println("File se duoc tao khi luu du lieu lan dau.\n");
            return;
        }

        // Xử lý: File rỗng
        if (file.length() == 0) {
            System.out.println("[CANH BAO] File rong. Khoi tao danh sach rong.\n");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();
            
            // Xử lý: Dữ liệu không đúng định dạng
            if (!(obj instanceof List)) {
                System.err.println("[LOI] Du lieu trong file khong dung dinh dang List.");
                System.out.println("Khoi tao danh sach rong.\n");
                return;
            }
            
            students = (List<Student>) obj;
            System.out.println("[THANH CONG] Da tai " + students.size() + " sinh vien tu file.\n");
            
        } catch (EOFException e) {
            System.err.println("[LOI] File bi loi hoac rong: " + e.getMessage());
            System.out.println("Khoi tao danh sach rong.\n");
        } catch (StreamCorruptedException e) {
            System.err.println("[LOI] File bi hong hoac khong dung dinh dang: " + e.getMessage());
            System.out.println("Khoi tao danh sach rong.\n");
        } catch (IOException e) {
            System.err.println("[LOI] Loi khi doc file: " + e.getMessage());
            System.out.println("Khoi tao danh sach rong.\n");
        } catch (ClassNotFoundException e) {
            System.err.println("[LOI] Khong tim thay lop Student: " + e.getMessage());
            System.out.println("Khoi tao danh sach rong.\n");
        } catch (ClassCastException e) {
            System.err.println("[LOI] Du lieu trong file khong phai la danh sach Student.");
            System.out.println("Khoi tao danh sach rong.\n");
        }
    }

    /**
     * Ghi dữ liệu vào file với xử lý lỗi
     */
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("[THANH CONG] Da luu " + students.size() + " sinh vien vao file.");
        } catch (FileNotFoundException e) {
            System.err.println("[LOI] Khong the tao file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[LOI] Loi khi ghi file: " + e.getMessage());
        }
    }

    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Da them: " + student);
    }

    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("Danh sach sinh vien rong.");
            return;
        }
        System.out.println("=== DANH SACH SINH VIEN ===");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i));
        }
    }

    public List<Student> getStudents() {
        return students;
    }
}
