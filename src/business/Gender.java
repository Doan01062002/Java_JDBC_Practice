package business;

/**
 * Enum đại diện cho giới tính nhân viên
 */
public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ"),
    OTHER("Khác");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
}