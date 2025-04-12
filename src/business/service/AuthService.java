package business.service;

import business.dao.AuthDAO;
import business.modal.User;
import business.DatabaseException;
import validate.ValidationException;
import validate.InputValidator;

/**
 * Lớp Service cho chức năng xác thực
 */
public class AuthService {
    private final AuthDAO authDAO;

    public AuthService() {
        this.authDAO = new AuthDAO();
    }

    public User login(String username, String password) throws ValidationException, DatabaseException {
        InputValidator.validateUsername(username);
        InputValidator.validatePassword(password);

        User user = authDAO.login(username, password);
        if (user == null) {
            throw new ValidationException("Tên đăng nhập hoặc mật khẩu không đúng");
        }

        if (!user.isStatus()) {
            throw new ValidationException("Tài khoản đã bị khóa");
        }

        return user;
    }
}