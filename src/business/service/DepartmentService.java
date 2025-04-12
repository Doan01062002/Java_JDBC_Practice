package business.service;

import business.dao.DepartmentDAO;
import business.modal.Department;
import business.DepartmentStatus;
import business.DatabaseException;
import validate.ValidationException;
import validate.InputValidator;
import business.Pagination;

import java.util.List;

/**
 * Lớp Service cho chức năng quản lý phòng ban
 */
public class DepartmentService {
    private final DepartmentDAO departmentDAO;

    public DepartmentService() {
        this.departmentDAO = new DepartmentDAO();
    }

    public Pagination<Department> getAllDepartments(int page) throws DatabaseException {
        int itemsPerPage = 5;
        List<Department> departments = departmentDAO.getAllDepartments(page, itemsPerPage);
        int totalItems = departmentDAO.countAllDepartments();

        return new Pagination<>(departments, page, itemsPerPage, totalItems);
    }

    public void addDepartment(Department department) throws ValidationException, DatabaseException {
        InputValidator.validateDepartmentName(department.getDepartmentName());
        InputValidator.validateDepartmentDescription(department.getDescription());

        if (department.getStatus() == null) {
            department.setStatus(DepartmentStatus.ACTIVE);
        }

        if (!departmentDAO.addDepartment(department)) {
            throw new DatabaseException("Không thể thêm phòng ban");
        }
    }

    public void updateDepartment(Department department) throws ValidationException, DatabaseException {
        InputValidator.validateDepartmentName(department.getDepartmentName());
        InputValidator.validateDepartmentDescription(department.getDescription());

        if (!departmentDAO.updateDepartment(department)) {
            throw new DatabaseException("Không thể cập nhật phòng ban");
        }
    }

    public boolean deleteDepartment(int departmentId) throws DatabaseException {
        return departmentDAO.deleteDepartment(departmentId);
    }

    public List<Department> searchDepartments(String keyword) throws DatabaseException {
        return departmentDAO.searchDepartments(keyword);
    }

    public int countAllDepartments() throws DatabaseException {
        return departmentDAO.countAllDepartments();
    }
}