package Lap5.Bai3;

/**
 * Bài 3: Áp dụng I/O Streams trong mô hình Model-Service-Main
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== CHUONG TRINH QUAN LY SINH VIEN ===\n");

        // Khởi tạo service (tự động load dữ liệu từ file)
        StudentService service = new StudentService();

        // Hiển thị dữ liệu hiện có
        service.displayStudents();

        // Thêm sinh viên mới
        System.out.println("\n=== THEM SINH VIEN MOI ===");
        service.addStudent(new Student("SV005", "Nguyen Thi F", 3.7));
        service.addStudent(new Student("SV006", "Tran Van G", 3.4));

        // Hiển thị lại danh sách
        service.displayStudents();

        // Lưu dữ liệu vào file khi kết thúc
        System.out.println("\n=== LUU DU LIEU ===");
        service.saveData();
        
        System.out.println("\nChuong trinh ket thuc.");
    }
}
