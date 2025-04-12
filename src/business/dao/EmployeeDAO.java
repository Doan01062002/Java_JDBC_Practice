package business.dao;

import business.conf.DatabaseConfig;
import business.modal.Employee;
import business.EmployeeStatus;
import business.Gender;
import business.DatabaseException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp DAO cho chức năng quản lý nhân viên
 */
public class EmployeeDAO {
    public List<Employee> getAllEmployees(int page, int itemsPerPage) throws DatabaseException {
        List<Employee> employees = new ArrayList<>();
        String sql = "{CALL sp_get_all_employees(?, ?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, page);
            cs.setInt(2, itemsPerPage);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setEmployeeId(rs.getString("employee_id"));
                    employee.setEmployeeName(rs.getString("employee_name"));
                    employee.setEmail(rs.getString("email"));
                    employee.setPhone(rs.getString("phone"));
                    employee.setGender(Gender.valueOf(rs.getString("gender")));
                    employee.setSalaryGrade(rs.getInt("salary_grade"));
                    employee.setSalary(rs.getDouble("salary"));
                    employee.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    employee.setAddress(rs.getString("address"));
                    employee.setStatus(EmployeeStatus.valueOf(rs.getString("status")));
                    employee.setDepartmentId(rs.getInt("department_id"));
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
        }
        return employees;
    }

    public boolean addEmployee(Employee employee) throws DatabaseException {
        String sql = "{CALL sp_add_employee(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, employee.getEmployeeId());
            cs.setString(2, employee.getEmployeeName());
            cs.setString(3, employee.getEmail());
            cs.setString(4, employee.getPhone());
            cs.setString(5, employee.getGender().name());
            cs.setInt(6, employee.getSalaryGrade());
            cs.setDouble(7, employee.getSalary());
            cs.setDate(8, java.sql.Date.valueOf(employee.getBirthDate()));
            cs.setString(9, employee.getAddress());
            cs.setString(10, employee.getStatus().name());
            cs.setInt(11, employee.getDepartmentId());
            cs.registerOutParameter(12, java.sql.Types.INTEGER);

            cs.execute();
            return cs.getInt(12) > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi thêm nhân viên: " + e.getMessage());
        }
    }

    public Employee getEmployeeById(String employeeId) throws DatabaseException {
        String sql = "{CALL sp_get_employee_by_id(?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, employeeId);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.setEmployeeId(rs.getString("employee_id"));
                    employee.setEmployeeName(rs.getString("employee_name"));
                    employee.setEmail(rs.getString("email"));
                    employee.setPhone(rs.getString("phone"));
                    employee.setGender(Gender.valueOf(rs.getString("gender")));
                    employee.setSalaryGrade(rs.getInt("salary_grade"));
                    employee.setSalary(rs.getDouble("salary"));
                    employee.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    employee.setAddress(rs.getString("address"));
                    employee.setStatus(EmployeeStatus.valueOf(rs.getString("status")));
                    employee.setDepartmentId(rs.getInt("department_id"));
                    return employee;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy thông tin nhân viên: " + e.getMessage());
        }
        return null;
    }

    public boolean updateEmployee(Employee employee) throws DatabaseException {
        String sql = "{CALL sp_update_employee(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, employee.getEmployeeId());
            cs.setString(2, employee.getEmployeeName());
            cs.setString(3, employee.getEmail());
            cs.setString(4, employee.getPhone());
            cs.setString(5, employee.getGender().name());
            cs.setInt(6, employee.getSalaryGrade());
            cs.setDouble(7, employee.getSalary());
            cs.setDate(8, java.sql.Date.valueOf(employee.getBirthDate()));
            cs.setString(9, employee.getAddress());
            cs.setString(10, employee.getStatus().name());
            cs.setInt(11, employee.getDepartmentId());

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi cập nhật nhân viên: " + e.getMessage());
        }
    }

    public boolean deleteEmployee(String employeeId) throws DatabaseException {
        String sql = "{CALL sp_delete_employee(?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, employeeId);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi xóa nhân viên: " + e.getMessage());
        }
    }

    public List<Employee> searchEmployees(String keyword) throws DatabaseException {
        List<Employee> employees = new ArrayList<>();
        String sql = "{CALL sp_search_employees(?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, "%" + keyword + "%");

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setEmployeeId(rs.getString("employee_id"));
                    employee.setEmployeeName(rs.getString("employee_name"));
                    employee.setEmail(rs.getString("email"));
                    employee.setPhone(rs.getString("phone"));
                    employee.setGender(Gender.valueOf(rs.getString("gender")));
                    employee.setSalaryGrade(rs.getInt("salary_grade"));
                    employee.setSalary(rs.getDouble("salary"));
                    employee.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    employee.setAddress(rs.getString("address"));
                    employee.setStatus(EmployeeStatus.valueOf(rs.getString("status")));
                    employee.setDepartmentId(rs.getInt("department_id"));
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
        }
        return employees;
    }

    public List<Employee> sortEmployees(int sortType) throws DatabaseException {
        List<Employee> employees = new ArrayList<>();
        String sql = "{CALL sp_sort_employees(?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, sortType);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setEmployeeId(rs.getString("employee_id"));
                    employee.setEmployeeName(rs.getString("employee_name"));
                    employee.setEmail(rs.getString("email"));
                    employee.setPhone(rs.getString("phone"));
                    employee.setGender(Gender.valueOf(rs.getString("gender")));
                    employee.setSalaryGrade(rs.getInt("salary_grade"));
                    employee.setSalary(rs.getDouble("salary"));
                    employee.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    employee.setAddress(rs.getString("address"));
                    employee.setStatus(EmployeeStatus.valueOf(rs.getString("status")));
                    employee.setDepartmentId(rs.getInt("department_id"));
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi sắp xếp nhân viên: " + e.getMessage());
        }
        return employees;
    }

    public int countAllEmployees() throws DatabaseException {
        String sql = "{CALL sp_count_employees()}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi đếm nhân viên: " + e.getMessage());
        }
        return 0;
    }

    public int countEmployeesByStatus(EmployeeStatus status) throws DatabaseException {
        String sql = "{CALL sp_count_employees_by_status(?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, status.name());

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi đếm nhân viên theo trạng thái: " + e.getMessage());
        }
        return 0;
    }

    public int countEmployeesByGender(Gender gender) throws DatabaseException {
        String sql = "{CALL sp_count_employees_by_gender(?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, gender.name());

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi đếm nhân viên theo giới tính: " + e.getMessage());
        }
        return 0;
    }

    public double getAverageSalary() throws DatabaseException {
        String sql = "{CALL sp_get_average_salary()}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi tính lương trung bình: " + e.getMessage());
        }
        return 0.0;
    }
}