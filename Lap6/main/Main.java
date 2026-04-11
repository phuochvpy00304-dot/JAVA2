package main;

import connect.DBConnect;
import entity.Student;
import entity.Tree;
import repository.StudentRepository;
import repository.TreeRepository;

import java.util.List;

/**
 * Main class - Demo các chức năng CRUD
 */
public class Main {
    
    public static void main(String[] args) {
        // Test kết nối
        System.out.println("=== TEST KẾT NỐI DATABASE ===");
        DBConnect.testConnection();
        System.out.println();
        
        // Demo Student Repository
        demoStudentRepository();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Demo Tree Repository
        demoTreeRepository();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Demo Bài 5 - AI Generated Queries
        demoBai5();
    }
    
    /**
     * Demo các chức năng của StudentRepository
     */
    private static void demoStudentRepository() {
        StudentRepository studentRepo = new StudentRepository();
        
        System.out.println("=== STUDENT REPOSITORY DEMO ===\n");
        
        // 1. Lấy tất cả sinh viên
        System.out.println("1. Danh sách tất cả sinh viên:");
        List<Student> allStudents = studentRepo.findAll();
        displayStudents(allStudents);
        
        // 2. Thêm sinh viên mới
        System.out.println("\n2. Thêm sinh viên mới:");
        Student newStudent = new Student("Võ Thị Hoa", "Nữ", 3.6);
        if (studentRepo.insert(newStudent)) {
            System.out.println("✓ Thêm thành công: " + newStudent);
        }
        
        // 3. Tìm sinh viên theo ID
        System.out.println("\n3. Tìm sinh viên theo ID = 1:");
        Student student = studentRepo.findById(1);
        if (student != null) {
            System.out.println("✓ Tìm thấy: " + student);
        }
        
        // 4. Cập nhật sinh viên
        System.out.println("\n4. Cập nhật sinh viên ID = 1:");
        if (student != null) {
            student.setGpa(3.95);
            if (studentRepo.update(student)) {
                System.out.println("✓ Cập nhật thành công: " + student);
            }
        }
        
        // 5. Hiển thị lại danh sách sau khi thêm và cập nhật
        System.out.println("\n5. Danh sách sinh viên sau khi thêm và cập nhật:");
        allStudents = studentRepo.findAll();
        displayStudents(allStudents);
    }
    
    /**
     * Demo các chức năng của TreeRepository
     */
    private static void demoTreeRepository() {
        TreeRepository treeRepo = new TreeRepository();
        
        System.out.println("=== TREE REPOSITORY DEMO ===\n");
        
        // 1. Lấy tất cả nodes
        System.out.println("1. Danh sách tất cả nodes:");
        List<Tree> allTrees = treeRepo.findAll();
        displayTrees(allTrees);
        
        // 2. Thêm node mới
        System.out.println("\n2. Thêm node mới:");
        Tree newTree = new Tree("Node 2.2", 3, 2);
        if (treeRepo.insert(newTree)) {
            System.out.println("✓ Thêm thành công: " + newTree);
        }
        
        // 3. Tìm node theo ID
        System.out.println("\n3. Tìm node theo ID = 2:");
        Tree tree = treeRepo.findById(2);
        if (tree != null) {
            System.out.println("✓ Tìm thấy: " + tree);
        }
        
        // 4. Cập nhật node
        System.out.println("\n4. Cập nhật node ID = 2:");
        if (tree != null) {
            tree.setNodeName("Node 1 (Updated)");
            if (treeRepo.update(tree)) {
                System.out.println("✓ Cập nhật thành công: " + tree);
            }
        }
        
        // 5. Hiển thị lại danh sách
        System.out.println("\n5. Danh sách nodes sau khi thêm và cập nhật:");
        allTrees = treeRepo.findAll();
        displayTrees(allTrees);
    }
    
    /**
     * Demo Bài 5 - Sử dụng AI để sinh SQL và code JDBC
     */
    private static void demoBai5() {
        System.out.println("=== BÀI 5: AI GENERATED QUERIES ===\n");
        
        StudentRepository studentRepo = new StudentRepository();
        TreeRepository treeRepo = new TreeRepository();
        
        // Query 1: Tìm sinh viên có GPA > 3.0
        System.out.println("1. Tìm sinh viên có GPA >= 3.0:");
        List<Student> highGpaStudents = studentRepo.findByMinGpa(3.0);
        displayStudents(highGpaStudents);
        
        // Query 2: Tìm các node con của Root (parent_id = 1)
        System.out.println("\n2. Tìm các node con của Root (parent_id = 1):");
        List<Tree> childNodes = treeRepo.findByParentId(1);
        displayTrees(childNodes);
        
        // Query 3: Đếm số node ở level 2
        System.out.println("\n3. Đếm số node ở level 2:");
        int count = treeRepo.countByLevel(2);
        System.out.println("✓ Số lượng nodes ở level 2: " + count);
        
        System.out.println("\n--- PHÂN TÍCH CODE AI ---");
        System.out.println("✓ Cú pháp SQL: Đúng, sử dụng parameterized queries");
        System.out.println("✓ Exception handling: Có try-catch SQLException");
        System.out.println("✓ Đóng tài nguyên: Có, sử dụng try-with-resources");
        System.out.println("✓ Tối ưu: PreparedStatement tránh SQL injection, connection pooling có thể cải thiện");
    }
    
    /**
     * Hiển thị danh sách sinh viên
     */
    private static void displayStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("  (Không có dữ liệu)");
            return;
        }
        
        System.out.println("  " + "-".repeat(70));
        System.out.printf("  %-5s %-25s %-10s %-10s%n", "ID", "Tên", "Giới tính", "GPA");
        System.out.println("  " + "-".repeat(70));
        
        for (Student s : students) {
            System.out.printf("  %-5d %-25s %-10s %-10.2f%n",
                    s.getStudentId(), s.getStudentName(), s.getGender(), s.getGpa());
        }
        
        System.out.println("  " + "-".repeat(70));
        System.out.println("  Tổng số: " + students.size() + " sinh viên");
    }
    
    /**
     * Hiển thị danh sách tree nodes
     */
    private static void displayTrees(List<Tree> trees) {
        if (trees.isEmpty()) {
            System.out.println("  (Không có dữ liệu)");
            return;
        }
        
        System.out.println("  " + "-".repeat(70));
        System.out.printf("  %-5s %-25s %-12s %-10s%n", "ID", "Tên Node", "Parent ID", "Level");
        System.out.println("  " + "-".repeat(70));
        
        for (Tree t : trees) {
            System.out.printf("  %-5d %-25s %-12s %-10d%n",
                    t.getNodeId(), t.getNodeName(),
                    t.getParentId() != null ? t.getParentId() : "NULL", t.getLevel());
        }
        
        System.out.println("  " + "-".repeat(70));
        System.out.println("  Tổng số: " + trees.size() + " nodes");
    }
}
