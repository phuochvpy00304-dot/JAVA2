package repository;

import connect.DBConnect;
import entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository quản lý các thao tác CRUD với bảng employee
 */
public class EmployeeRepository {
    
    /**
     * Thêm một nhân viên mới
     */
    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO employee (name, salary) VALUES (?, ?)";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getName());
            pstmt.setFloat(2, employee.getSalary());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhân viên: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Cập nhật thông tin nhân viên theo id
     */
    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE employee SET name = ?, salary = ? WHERE id = ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getName());
            pstmt.setFloat(2, employee.getSalary());
            pstmt.setInt(3, employee.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật nhân viên: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Xóa nhân viên theo id
     */
    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nhân viên: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lấy danh sách toàn bộ nhân viên
     */
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT id, name, salary FROM employee ORDER BY id";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getFloat("salary")
                );
                employees.add(emp);
            }
            
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
        }
        
        return employees;
    }
    
    /**
     * Tìm kiếm nhân viên theo id
     */
    public Employee findEmployeeById(int id) {
        String sql = "SELECT id, name, salary FROM employee WHERE id = ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getFloat("salary")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
        }
        
        return null;
    }
}
