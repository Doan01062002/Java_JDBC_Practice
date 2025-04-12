package business.service;

import business.dao.EmployeeDAO;
import business.modal.Employee;
import business.EmployeeStatus;
import business.Gender;
import business.DatabaseException;
import validate.ValidationException;
import validate.InputValidator;
import business.Pagination;

import java.util.List;

/**
 * Lớp Service cho chức năng quản lý nhân viên
 */
public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    public Pagination<Employee> getAllEmployees(int page) throws DatabaseException {
        int itemsPerPage = 10;
        List<Employee> employees = employeeDAO.getAllEmployees(page, itemsPerPage);
        int totalItems = employeeDAO.countAllEmployees();

        return new Pagination<>(employees, page, itemsPerPage, totalItems);
    }

    public void addEmployee(Employee employee) throws ValidationException, DatabaseException {
        InputValidator.validateEmployeeId(employee.getEmployeeId());
        InputValidator.validateEmployeeName(employee.getEmployeeName());
        InputValidator.validateEmail(employee.getEmail());
        InputValidator.validatePhone(employee.getPhone());
        InputValidator.validateSalaryGrade(employee.getSalaryGrade());
        InputValidator.validateSalary(employee.getSalary());
        InputValidator.validateBirthDate(employee.getBirthDate());
        InputValidator.validateAddress(employee.getAddress());

        if (employee.getStatus() == null) {
            employee.setStatus(EmployeeStatus.ACTIVE);
        }

        if (!employeeDAO.addEmployee(employee)) {
            throw new DatabaseException("Không thể thêm nhân viên");
        }
    }

    public Employee getEmployeeById(String employeeId) throws DatabaseException {
        return employeeDAO.getEmployeeById(employeeId);
    }

    public void updateEmployee(Employee employee) throws ValidationException, DatabaseException {
        InputValidator.validateEmployeeId(employee.getEmployeeId());
        InputValidator.validateEmployeeName(employee.getEmployeeName());
        InputValidator.validateEmail(employee.getEmail());
        InputValidator.validatePhone(employee.getPhone());
        InputValidator.validateSalaryGrade(employee.getSalaryGrade());
        InputValidator.validateSalary(employee.getSalary());
        InputValidator.validateBirthDate(employee.getBirthDate());
        InputValidator.validateAddress(employee.getAddress());

        if (!employeeDAO.updateEmployee(employee)) {
            throw new DatabaseException("Không thể cập nhật nhân viên");
        }
    }

    public boolean deleteEmployee(String employeeId) throws DatabaseException {
        return employeeDAO.deleteEmployee(employeeId);
    }

    public List<Employee> searchEmployees(String keyword) throws DatabaseException {
        return employeeDAO.searchEmployees(keyword);
    }

    public List<Employee> sortEmployees(int sortType) throws DatabaseException {
        return employeeDAO.sortEmployees(sortType);
    }

    public int countAllEmployees() throws DatabaseException {
        return employeeDAO.countAllEmployees();
    }

    public int countEmployeesByStatus(EmployeeStatus status) throws DatabaseException {
        return employeeDAO.countEmployeesByStatus(status);
    }

    public int countEmployeesByGender(Gender gender) throws DatabaseException {
        return employeeDAO.countEmployeesByGender(gender);
    }

    public double getAverageSalary() throws DatabaseException {
        return employeeDAO.getAverageSalary();
    }
}