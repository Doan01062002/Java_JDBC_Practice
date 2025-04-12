import presentation.AuthController;
import presentation.DepartmentController;
import presentation.EmployeeController;
import business.modal.User;
import util.InputUtils;

/**
 * Lớp chính để chạy ứng dụng quản lý nhân sự
 */
public class Main {
    private static final AuthController authController = new AuthController();
    private static final DepartmentController departmentController = new DepartmentController();
    private static final EmployeeController employeeController = new EmployeeController();

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        while (true) {
            User user = authController.handleLogin();

            while (true) {
                System.out.println("\n=== HỆ THỐNG QUẢN LÝ NHÂN SỰ ===");
                System.out.println("1. Quản lý phòng ban");
                System.out.println("2. Quản lý nhân viên");
                System.out.println("3. Đăng xuất");

                int choice = InputUtils.getIntInput("Chọn chức năng: ", 1, 3);

                switch (choice) {
                    case 1:
                        if (user.getRole().equals("ADMIN") || user.getRole().equals("HR")) {
                            departmentController.showMenu();
                        } else {
                            System.out.println("Bạn không có quyền truy cập chức năng này!");
                        }
                        break;
                    case 2:
                        employeeController.showMenu();
                        break;
                    case 3:
                        authController.handleLogout();
                        break;
                }

                if (choice == 3) {
                    break;
                }
            }
        }
    }
}