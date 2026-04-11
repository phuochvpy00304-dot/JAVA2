package repository;

import connect.DBConnect;
import entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class cho Student - xử lý CRUD operations
 */
public class StudentRepository {
    
    /**
     * Lấy tất cả sinh viên
     */
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT student_id, student_name, gender, gpa FROM student";
        
        Connection conn = DBConnect.getConnection();
        if (conn == null) return students;
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Student student = mapResultSetToStudent(rs);
                students.add(student);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding all students: " + e.getMessage());
        }
        
        return students;
    }
    
    /**
     * Tìm sinh viên theo ID
     */
    public Student findById(int id) {
        String sql = "SELECT student_id, student_name, gender, gpa FROM student WHERE student_id = ?";
        
        Connection conn = DBConnect.getConnection();
        if (conn == null) return null;
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStudent(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding student by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Thêm sinh viên mới
     */
    public boolean insert(Student student) {
        String sql = "INSERT INTO student (student_name, gender, gpa) VALUES (?, ?, ?)";
        
        Connection conn = DBConnect.getConnection();
        if (conn == null) return false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, student.getStudentName());
            pstmt.setString(2, student.getGender());
            pstmt.setDouble(3, student.getGpa());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Lấy ID vừa được tạo
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        student.setStudentId(rs.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error inserting student: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Cập nhật thông tin sinh viên
     */
    public boolean update(Student student) {
        String sql = "UPDATE student SET student_name = ?, gender = ?, gpa = ? WHERE student_id = ?";
        
        Connection conn = DBConnect.getConnection();
        if (conn == null) return false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getStudentName());
            pstmt.setString(2, student.getGender());
            pstmt.setDouble(3, student.getGpa());
            pstmt.setInt(4, student.getStudentId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Xóa sinh viên theo ID
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM student WHERE student_id = ?";
        
        Connection conn = DBConnect.getConnection();
        if (conn == null) return false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Tìm sinh viên theo GPA tối thiểu (Bài 5 - AI generated query)
     */
    public List<Student> findByMinGpa(double minGpa) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT student_id, student_name, gender, gpa FROM student WHERE gpa >= ? ORDER BY gpa DESC";
        
        Connection conn = DBConnect.getConnection();
        if (conn == null) return students;
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, minGpa);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(mapResultSetToStudent(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding students by min GPA: " + e.getMessage());
        }
        
        return students;
    }
    
    /**
     * Ánh xạ ResultSet sang đối tượng Student
     */
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        return new Student(
            rs.getInt("student_id"),
            rs.getString("student_name"),
            rs.getString("gender"),
            rs.getDouble("gpa")
        );
    }
}
