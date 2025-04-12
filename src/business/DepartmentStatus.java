package business;

/**
 * Enum đại diện cho trạng thái phòng ban
 */
public enum DepartmentStatus {
    ACTIVE("Hoạt động"),
    INACTIVE("Không hoạt động");

    private final String description;

    DepartmentStatus(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
}