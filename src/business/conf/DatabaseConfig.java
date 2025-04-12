package business.conf;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Lớp cấu hình kết nối cơ sở dữ liệu
 */
public class DatabaseConfig {
    private static final String PROPERTIES_FILE = "db.properties";
    private static Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException("Không tìm thấy file cấu hình database: " + PROPERTIES_FILE);
            }
            properties.load(input);
            Class.forName(properties.getProperty("jdbc.driver"));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Lỗi khởi tạo cấu hình database", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, password);
    }
}