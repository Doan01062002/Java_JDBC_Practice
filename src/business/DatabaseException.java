package business;

/**
 * Lớp ngoại lệ cho các lỗi liên quan đến cơ sở dữ liệu
 */
public class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}