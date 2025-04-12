package validate;

/**
 * Lớp ngoại lệ cho các lỗi xác thực dữ liệu
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}