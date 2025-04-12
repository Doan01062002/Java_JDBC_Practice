package business.dao;

import business.conf.DatabaseConfig;
import business.modal.Department;
import business.DepartmentStatus;
import business.DatabaseException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp DAO cho chức năng quản lý phòng ban
 */
public class DepartmentDAO {
    public List<Department> getAllDepartments(int page, int itemsPerPage) throws DatabaseException {
        List<Department> departments = new ArrayList<>();
        String sql = "{CALL sp_get_all_departments(?, ?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, page);
            cs.setInt(2, itemsPerPage);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Department department = new Department();
                    department.setDepartmentId(rs.getInt("department_id"));
                    department.setDepartmentName(rs.getString("department_name"));
                    department.setDescription(rs.getString("description"));
                    department.setStatus(DepartmentStatus.valueOf(rs.getString("status")));
                    departments.add(department);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy danh sách phòng ban: " + e.getMessage());
        }
        return departments;
    }

    public boolean addDepartment(Department department) throws DatabaseException {
        String sql = "{CALL sp_add_department(?, ?, ?, ?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, department.getDepartmentName());
            cs.setString(2, department.getDescription());
            cs.setString(3, department.getStatus().name());
            cs.registerOutParameter(4, java.sql.Types.INTEGER);

            cs.execute();
            return cs.getInt(4) > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi thêm phòng ban: " + e.getMessage());
        }
    }

    public boolean updateDepartment(Department department) throws DatabaseException {
        String sql = "{CALL sp_update_department(?, ?, ?, ?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, department.getDepartmentId());
            cs.setString(2, department.getDepartmentName());
            cs.setString(3, department.getDescription());
            cs.setString(4, department.getStatus().name());

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi cập nhật phòng ban: " + e.getMessage());
        }
    }

    public boolean deleteDepartment(int departmentId) throws DatabaseException {
        String sql = "{CALL sp_delete_department(?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, departmentId);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi xóa phòng ban: " + e.getMessage());
        }
    }

    public List<Department> searchDepartments(String keyword) throws DatabaseException {
        List<Department> departments = new ArrayList<>();
        String sql = "{CALL sp_search_departments(?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, "%" + keyword + "%");

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Department department = new Department();
                    department.setDepartmentId(rs.getInt("department_id"));
                    department.setDepartmentName(rs.getString("department_name"));
                    department.setDescription(rs.getString("description"));
                    department.setStatus(DepartmentStatus.valueOf(rs.getString("status")));
                    departments.add(department);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi tìm kiếm phòng ban: " + e.getMessage());
        }
        return departments;
    }

    public int countAllDepartments() throws DatabaseException {
        String sql = "{CALL sp_count_departments()}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi đếm phòng ban: " + e.getMessage());
        }
        return 0;
    }
}