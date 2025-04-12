package business.modal;

import business.DepartmentStatus;

/**
 * Lớp đại diện cho thông tin phòng ban
 */
public class Department {
    private int departmentId;
    private String departmentName;
    private String description;
    private DepartmentStatus status;

    public Department() {}

    public Department(int departmentId, String departmentName, String description, DepartmentStatus status) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.description = description;
        this.status = status;
    }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public DepartmentStatus getStatus() { return status; }
    public void setStatus(DepartmentStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Department{departmentId=" + departmentId + ", departmentName='" + departmentName +
                "', description='" + description + "', status=" + status + '}';
    }
}