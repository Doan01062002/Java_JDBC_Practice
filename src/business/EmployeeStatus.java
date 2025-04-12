package business;

/**
 * Enum đại diện cho trạng thái nhân viên
 */
public enum EmployeeStatus {
    ACTIVE("Đang làm việc"),
    INACTIVE("Nghỉ việc"),
    ONLEAVE("Nghỉ phép"),
    POLICYLEAVE("Nghỉ chế độ");

    private final String description;

    EmployeeStatus(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
}