package presentation;

import business.modal.User;
import business.DatabaseException;
import validate.ValidationException;
import business.service.AuthService;
import util.InputUtils;

/**
 * Lớp Controller cho chức năng xác thực
 */
public class AuthController {
    private final AuthService authService;

    public AuthController() {
        this.authService = new AuthService();
    }

    public User handleLogin() {
        System.out.println("=== ĐĂNG NHẬP HỆ THỐNG ===");

        while (true) {
            try {
                String username = InputUtils.prompt("Nhập tên đăng nhập: ");
                String password = InputUtils.prompt("Nhập mật khẩu: ", true);

                User user = authService.login(username, password);
                System.out.println("Đăng nhập thành công với quyền: " + user.getRole());
                return user;
            } catch (ValidationException | DatabaseException e) {
                System.out.println("Lỗi: " + e.getMessage());
                System.out.println("Vui lòng thử lại.");
            }
        }
    }

    public void handleLogout() {
        System.out.println("Đăng xuất thành công. Tạm biệt!");
    }
}