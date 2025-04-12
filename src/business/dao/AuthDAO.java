package business.dao;

import business.conf.DatabaseConfig;
import business.modal.User;
import business.DatabaseException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Lớp DAO cho chức năng xác thực
 */
public class AuthDAO {
    public User login(String username, String password) throws DatabaseException {
        String sql = "{CALL sp_login(?, ?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, username);
            cs.setString(2, password);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setStatus(rs.getBoolean("status"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi đăng nhập: " + e.getMessage());
        }
        return null;
    }
}