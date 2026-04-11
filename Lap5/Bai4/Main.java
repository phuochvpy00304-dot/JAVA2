package Lap5.Bai4;

/**
 * Bài 4: Xử lý các tình huống lỗi khi thao tác với file
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== CHUONG TRINH QUAN LY SINH VIEN (XU LY LOI) ===\n");

        // Test case 1: File không tồn tại (lần chạy đầu tiên)
        System.out.println("--- Test 1: Khoi dong lan dau (file chua ton tai) ---");
        StudentService service = new StudentService();
        service.displayStudents();

        // Thêm dữ liệu
        System.out.println("\n--- Them sinh vien ---");
        service.addStudent(new Student("SV001", "Nguyen Van A", 3.5));
        service.addStudent(new Student("SV002", "Tran Thi B", 3.8));
        service.displayStudents();

        // Lưu dữ liệu
        System.out.println("\n--- Luu du lieu ---");
        service.saveData();

        // Test case 2: Đọc lại dữ liệu (file đã tồn tại)
        System.out.println("\n--- Test 2: Doc lai du lieu ---");
        StudentService service2 = new StudentService();
        service2.displayStudents();

        System.out.println("\n=== KET THUC ===");
        System.out.println("Chuong trinh da xu ly tat ca cac truong hop loi ma khong bi dung dot ngot.");
    }
}
