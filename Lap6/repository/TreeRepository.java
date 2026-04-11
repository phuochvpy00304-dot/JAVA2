package repository;

import connect.DBConnect;
import entity.Tree;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class cho Tree - xử lý CRUD operations
 */
public class TreeRepository {

    /**
     * Lấy tất cả nodes
     */
    public List<Tree> findAll() {
        List<Tree> trees = new ArrayList<>();
        String sql = "SELECT node_id, node_name, parent_id, level FROM tree ORDER BY level, node_id";

        Connection conn = DBConnect.getConnection();
        if (conn == null)
            return trees;

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Tree tree = mapResultSetToTree(rs);
                trees.add(tree);
            }

        } catch (SQLException e) {
            System.err.println("Error finding all trees: " + e.getMessage());
        }

        return trees;
    }

    /**
     * Tìm node theo ID
     */
    public Tree findById(int id) {
        String sql = "SELECT node_id, node_name, parent_id, level FROM tree WHERE node_id = ?";

        Connection conn = DBConnect.getConnection();
        if (conn == null)
            return null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTree(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding tree by ID: " + e.getMessage());
        }

        return null;
    }

    /**
     * Thêm node mới
     */
    public boolean insert(Tree tree) {
        String sql = "INSERT INTO tree (node_name, parent_id, level) VALUES (?, ?, ?)";

        Connection conn = DBConnect.getConnection();
        if (conn == null)
            return false;

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, tree.getNodeName());
            if (tree.getParentId() != null) {
                pstmt.setInt(2, tree.getParentId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            pstmt.setInt(3, tree.getLevel());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        tree.setNodeId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error inserting tree: " + e.getMessage());
        }

        return false;
    }

    /**
     * Cập nhật node
     */
    public boolean update(Tree tree) {
        String sql = "UPDATE tree SET node_name = ?, parent_id = ?, level = ? WHERE node_id = ?";

        Connection conn = DBConnect.getConnection();
        if (conn == null)
            return false;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tree.getNodeName());
            if (tree.getParentId() != null) {
                pstmt.setInt(2, tree.getParentId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            pstmt.setInt(3, tree.getLevel());
            pstmt.setInt(4, tree.getNodeId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating tree: " + e.getMessage());
        }

        return false;
    }

    /**
     * Xóa node theo ID
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM tree WHERE node_id = ?";

        Connection conn = DBConnect.getConnection();
        if (conn == null)
            return false;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting tree: " + e.getMessage());
        }

        return false;
    }

    /**
     * Tìm các node con theo parent_id (Bài 5 - AI generated query)
     */
    public List<Tree> findByParentId(Integer parentId) {
        List<Tree> trees = new ArrayList<>();
        String sql = "SELECT node_id, node_name, parent_id, level FROM tree WHERE parent_id " +
                (parentId == null ? "IS NULL" : "= ?") + " ORDER BY node_id";

        Connection conn = DBConnect.getConnection();
        if (conn == null)
            return trees;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (parentId != null) {
                pstmt.setInt(1, parentId);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    trees.add(mapResultSetToTree(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding trees by parent ID: " + e.getMessage());
        }

        return trees;
    }

    /**
     * Đếm số node theo level (Bài 5 - AI generated query)
     */
    public int countByLevel(int level) {
        String sql = "SELECT COUNT(*) as total FROM tree WHERE level = ?";

        Connection conn = DBConnect.getConnection();
        if (conn == null)
            return 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, level);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error counting trees by level: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Ánh xạ ResultSet sang đối tượng Tree
     */
    private Tree mapResultSetToTree(ResultSet rs) throws SQLException {
        int parentId = rs.getInt("parent_id");
        return new Tree(
                rs.getInt("node_id"),
                rs.getString("node_name"),
                rs.wasNull() ? null : parentId,
                rs.getInt("level"));
    }
}
