package business.modal;

/**
 * Lớp đại diện cho thông tin người dùng hệ thống
 */
public class User {
    private String username;
    private String password;
    private boolean status;
    private String role;

    public User() {}

    public User(String username, String password, boolean status, String role) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "User{username='" + username + "', password='[PROTECTED]', status=" + status + ", role='" + role + "'}";
    }
}