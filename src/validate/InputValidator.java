package validate;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Lớp tiện ích để xác thực dữ liệu đầu vào
 */
public class InputValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10,11}$");
    private static final Pattern EMPLOYEE_ID_PATTERN = Pattern.compile("^E\\d{6}$");

    public static void validateUsername(String username) throws ValidationException {
        if (username == null || username.trim().isEmpty() || username.length() < 5 || username.length() > 50) {
            throw new ValidationException("Tên đăng nhập phải từ 5-50 ký tự");
        }
    }

    public static void validatePassword(String password) throws ValidationException {
        if (password == null || password.length() < 8 || password.length() > 50) {
            throw new ValidationException("Mật khẩu phải từ 8-50 ký tự");
        }
    }

    public static void validateDepartmentName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty() || name.length() < 10 || name.length() > 100) {
            throw new ValidationException("Tên phòng ban phải từ 10-100 ký tự");
        }
    }

    public static void validateDepartmentDescription(String description) throws ValidationException {
        if (description != null && description.length() > 255) {
            throw new ValidationException("Mô tả không được vượt quá 255 ký tự");
        }
    }

    public static void validateEmployeeId(String employeeId) throws ValidationException {
        if (employeeId == null || !EMPLOYEE_ID_PATTERN.matcher(employeeId).matches()) {
            throw new ValidationException("Mã nhân viên phải có định dạng E + 6 số (ví dụ: E123456)");
        }
    }

    public static void validateEmployeeName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty() || name.length() < 5 || name.length() > 50) {
            throw new ValidationException("Tên nhân viên phải từ 5-50 ký tự");
        }
    }

    public static void validateEmail(String email) throws ValidationException {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Email không hợp lệ");
        }
    }

    public static void validatePhone(String phone) throws ValidationException {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            throw new ValidationException("Số điện thoại phải có 10-11 số");
        }
    }

    public static void validateSalaryGrade(int grade) throws ValidationException {
        if (grade < 1 || grade > 10) {
            throw new ValidationException("Bậc lương phải từ 1-10");
        }
    }

    public static void validateSalary(double salary) throws ValidationException {
        if (salary < 0) {
            throw new ValidationException("Lương không được âm");
        }
    }

    public static void validateBirthDate(LocalDate birthDate) throws ValidationException {
        if (birthDate == null || birthDate.isAfter(LocalDate.now().minusYears(18))) {
            throw new ValidationException("Nhân viên phải từ 18 tuổi trở lên");
        }
    }

    public static void validateAddress(String address) throws ValidationException {
        if (address != null && address.length() > 255) {
            throw new ValidationException("Địa chỉ không được vượt quá 255 ký tự");
        }
    }
}