package presentation;

import business.modal.Employee;
import business.EmployeeStatus;
import business.Gender;
import business.DatabaseException;
import validate.ValidationException;
import business.service.EmployeeService;
import util.DateUtils;
import util.InputUtils;
import business.Pagination;

import java.util.List;
import java.util.Scanner;

/**
 * Lớp Controller cho chức năng quản lý nhân viên
 */
public class EmployeeController {
    private final EmployeeService employeeService;
    private final Scanner scanner;

    public EmployeeController() {
        this.employeeService = new EmployeeService();
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ NHÂN VIÊN ===");
            System.out.println("1. Xem danh sách nhân viên");
            System.out.println("2. Thêm nhân viên mới");
            System.out.println("3. Cập nhật nhân viên");
            System.out.println("4. Xóa nhân viên");
            System.out.println("5. Tìm kiếm nhân viên");
            System.out.println("6. Sắp xếp nhân viên");
            System.out.println("7. Thống kê nhân viên");
            System.out.println("0. Quay lại");

            int choice = InputUtils.getIntInput("Chọn chức năng: ", 0, 7);

            switch (choice) {
                case 1:
                    listEmployees();
                    break;
                case 2:
                    addEmployee();
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    deleteEmployee();
                    break;
                case 5:
                    searchEmployee();
                    break;
                case 6:
                    sortEmployees();
                    break;
                case 7:
                    showStatistics();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listEmployees() {
        try {
            int page = 1;
            Pagination<Employee> pagination;

            do {
                pagination = employeeService.getAllEmployees(page);

                System.out.println("\n=== DANH SÁCH NHÂN VIÊN ===");
                System.out.printf("%-7s %-20s %-25s %-12s %-8s %-10s %-15s %-12s %-20s\n",
                        "Mã NV", "Tên NV", "Email", "Điện thoại", "Giới tính",
                        "Bậc lương", "Lương", "Ngày sinh", "Trạng thái");

                for (Employee emp : pagination.getItems()) {
                    System.out.printf("%-7s %-20s %-25s %-12s %-8s %-10d %-15.2f %-12s %-20s\n",
                            emp.getEmployeeId(),
                            emp.getEmployeeName(),
                            emp.getEmail(),
                            emp.getPhone(),
                            emp.getGender().getDescription(),
                            emp.getSalaryGrade(),
                            emp.getSalary(),
                            DateUtils.formatDate(emp.getBirthDate()),
                            emp.getStatus().getDescription());
                }

                System.out.printf("\nTrang %d/%d - Tổng %d nhân viên\n",
                        pagination.getCurrentPage(),
                        pagination.getTotalPages(),
                        pagination.getTotalItems());

                if (pagination.hasPrevious()) {
                    System.out.print("1. Trang trước | ");
                }
                if (pagination.hasNext()) {
                    System.out.print("2. Trang sau | ");
                }
                System.out.println("0. Thoát");

                if (pagination.hasPrevious() || pagination.hasNext()) {
                    int navChoice = InputUtils.getIntInput("Chọn: ", 0, 2);

                    if (navChoice == 1 && pagination.hasPrevious()) {
                        page--;
                    } else if (navChoice == 2 && pagination.hasNext()) {
                        page++;
                    } else if (navChoice == 0) {
                        break;
                    }
                } else {
                    InputUtils.prompt("Nhấn Enter để tiếp tục...");
                    break;
                }
            } while (true);

        } catch (DatabaseException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void addEmployee() {
        System.out.println("\n=== THÊM NHÂN VIÊN MỚI ===");

        try {
            Employee employee = new Employee();

            employee.setEmployeeId(InputUtils.prompt("Nhập mã nhân viên (E + 6 số): "));
            employee.setEmployeeName(InputUtils.prompt("Nhập tên nhân viên (5-50 ký tự): "));
            employee.setEmail(InputUtils.prompt("Nhập email: "));
            employee.setPhone(InputUtils.prompt("Nhập số điện thoại (10-11 số): "));

            System.out.println("Chọn giới tính:");
            System.out.println("1. Nam");
            System.out.println("2. Nữ");
            System.out.println("3. Khác");
            int genderChoice = InputUtils.getIntInput("Chọn: ", 1, 3);
            employee.setGender(genderChoice == 1 ? Gender.MALE : genderChoice == 2 ? Gender.FEMALE : Gender.OTHER);

            employee.setSalaryGrade(InputUtils.getIntInput("Nhập bậc lương (1-10): ", 1, 10));
            employee.setSalary(InputUtils.getDoubleInput("Nhập lương cơ bản: "));
            employee.setBirthDate(DateUtils.parseDate(InputUtils.prompt("Nhập ngày sinh (dd/MM/yyyy): ")));
            employee.setAddress(InputUtils.prompt("Nhập địa chỉ: "));

            System.out.println("Chọn trạng thái:");
            System.out.println("1. Đang làm việc");
            System.out.println("2. Nghỉ việc");
            System.out.println("3. Nghỉ phép");
            System.out.println("4. Nghỉ chế độ");
            int statusChoice = InputUtils.getIntInput("Chọn: ", 1, 4);
            switch (statusChoice) {
                case 1:
                    employee.setStatus(EmployeeStatus.ACTIVE);
                    break;
                case 2:
                    employee.setStatus(EmployeeStatus.INACTIVE);
                    break;
                case 3:
                    employee.setStatus(EmployeeStatus.ONLEAVE);
                    break;
                case 4:
                    employee.setStatus(EmployeeStatus.POLICYLEAVE);
                    break;
            }

            employee.setDepartmentId(InputUtils.getIntInput("Nhập ID phòng ban: ", 1, Integer.MAX_VALUE));

            employeeService.addEmployee(employee);
            System.out.println("Thêm nhân viên thành công!");
        } catch (ValidationException | DatabaseException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void updateEmployee() {
        System.out.println("\n=== CẬP NHẬT NHÂN VIÊN ===");

        try {
            String employeeId = InputUtils.prompt("Nhập mã nhân viên cần cập nhật: ");
            Employee employee = employeeService.getEmployeeById(employeeId);
            if (employee == null) {
                System.out.println("Không tìm thấy nhân viên với mã: " + employeeId);
                return;
            }

            System.out.println("Nhấn Enter để giữ nguyên giá trị hiện tại.");

            String newName = InputUtils.prompt("Nhập tên mới (" + employee.getEmployeeName() + "): ");
            if (!newName.isEmpty()) {
                employee.setEmployeeName(newName);
            }

            String newEmail = InputUtils.prompt("Nhập email mới (" + employee.getEmail() + "): ");
            if (!newEmail.isEmpty()) {
                employee.setEmail(newEmail);
            }

            String newPhone = InputUtils.prompt("Nhập số điện thoại mới (" + employee.getPhone() + "): ");
            if (!newPhone.isEmpty()) {
                employee.setPhone(newPhone);
            }

            System.out.println("Chọn giới tính mới (hiện tại: " + employee.getGender().getDescription() + "):");
            System.out.println("1. Nam");
            System.out.println("2. Nữ");
            System.out.println("3. Khác");
            System.out.println("0. Giữ nguyên");
            int genderChoice = InputUtils.getIntInput("Chọn: ", 0, 3);
            if (genderChoice != 0) {
                employee.setGender(genderChoice == 1 ? Gender.MALE : genderChoice == 2 ? Gender.FEMALE : Gender.OTHER);
            }

            String newSalaryGrade = InputUtils.prompt("Nhập bậc lương mới (" + employee.getSalaryGrade() + "): ");
            if (!newSalaryGrade.isEmpty()) {
                employee.setSalaryGrade(Integer.parseInt(newSalaryGrade));
            }

            String newSalary = InputUtils.prompt("Nhập lương mới (" + employee.getSalary() + "): ");
            if (!newSalary.isEmpty()) {
                employee.setSalary(Double.parseDouble(newSalary));
            }

            String newBirthDate = InputUtils.prompt("Nhập ngày sinh mới (" + DateUtils.formatDate(employee.getBirthDate()) + "): ");
            if (!newBirthDate.isEmpty()) {
                employee.setBirthDate(DateUtils.parseDate(newBirthDate));
            }

            String newAddress = InputUtils.prompt("Nhập địa chỉ mới (" + employee.getAddress() + "): ");
            if (!newAddress.isEmpty()) {
                employee.setAddress(newAddress);
            }

            System.out.println("Chọn trạng thái mới (hiện tại: " + employee.getStatus().getDescription() + "):");
            System.out.println("1. Đang làm việc");
            System.out.println("2. Nghỉ việc");
            System.out.println("3. Nghỉ phép");
            System.out.println("4. Nghỉ chế độ");
            System.out.println("0. Giữ nguyên");
            int statusChoice = InputUtils.getIntInput("Chọn: ", 0, 4);
            if (statusChoice != 0) {
                switch (statusChoice) {
                    case 1:
                        employee.setStatus(EmployeeStatus.ACTIVE);
                        break;
                    case 2:
                        employee.setStatus(EmployeeStatus.INACTIVE);
                        break;
                    case 3:
                        employee.setStatus(EmployeeStatus.ONLEAVE);
                        break;
                    case 4:
                        employee.setStatus(EmployeeStatus.POLICYLEAVE);
                        break;
                }
            }

            String newDeptId = InputUtils.prompt("Nhập ID phòng ban mới (" + employee.getDepartmentId() + "): ");
            if (!newDeptId.isEmpty()) {
                employee.setDepartmentId(Integer.parseInt(newDeptId));
            }

            employeeService.updateEmployee(employee);
            System.out.println("Cập nhật nhân viên thành công!");
        } catch (ValidationException | DatabaseException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void deleteEmployee() {
        System.out.println("\n=== XÓA NHÂN VIÊN ===");

        try {
            String employeeId = InputUtils.prompt("Nhập mã nhân viên cần xóa: ");
            System.out.println("Bạn có chắc chắn muốn xóa nhân viên này? (y/n)");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("y")) {
                boolean success = employeeService.deleteEmployee(employeeId);
                if (success) {
                    System.out.println("Xóa nhân viên thành công!");
                } else {
                    System.out.println("Không tìm thấy nhân viên với mã: " + employeeId);
                }
            } else {
                System.out.println("Đã hủy thao tác xóa.");
            }
        } catch (DatabaseException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void searchEmployee() {
        System.out.println("\n=== TÌM KIẾM NHÂN VIÊN ===");

        try {
            String keyword = InputUtils.prompt("Nhập từ khóa tìm kiếm (mã, tên, email, hoặc số điện thoại): ");
            List<Employee> results = employeeService.searchEmployees(keyword);

            if (results.isEmpty()) {
                System.out.println("Không tìm thấy nhân viên nào phù hợp.");
                return;
            }

            System.out.printf("%-7s %-20s %-25s %-12s %-8s %-10s %-15s %-12s %-20s\n",
                    "Mã NV", "Tên NV", "Email", "Điện thoại", "Giới tính",
                    "Bậc lương", "Lương", "Ngày sinh", "Trạng thái");

            for (Employee emp : results) {
                System.out.printf("%-7s %-20s %-25s %-12s %-8s %-10d %-15.2f %-12s %-20s\n",
                        emp.getEmployeeId(),
                        emp.getEmployeeName(),
                        emp.getEmail(),
                        emp.getPhone(),
                        emp.getGender().getDescription(),
                        emp.getSalaryGrade(),
                        emp.getSalary(),
                        DateUtils.formatDate(emp.getBirthDate()),
                        emp.getStatus().getDescription());
            }

            System.out.println("Tổng cộng: " + results.size() + " nhân viên được tìm thấy.");
        } catch (DatabaseException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void sortEmployees() {
        System.out.println("\n=== SẮP XẾP NHÂN VIÊN ===");
        System.out.println("Chọn tiêu chí sắp xếp:");
        System.out.println("1. Theo tên (A-Z)");
        System.out.println("2. Theo lương (tăng dần)");
        System.out.println("3. Theo ngày sinh (trẻ đến già)");

        int sortChoice = InputUtils.getIntInput("Chọn: ", 1, 3);

        try {
            List<Employee> sortedEmployees = employeeService.sortEmployees(sortChoice);

            System.out.printf("%-7s %-20s %-25s %-12s %-8s %-10s %-15s %-12s %-20s\n",
                    "Mã NV", "Tên NV", "Email", "Điện thoại", "Giới tính",
                    "Bậc lương", "Lương", "Ngày sinh", "Trạng thái");

            for (Employee emp : sortedEmployees) {
                System.out.printf("%-7s %-20s %-25s %-12s %-8s %-10d %-15.2f %-12s %-20s\n",
                        emp.getEmployeeId(),
                        emp.getEmployeeName(),
                        emp.getEmail(),
                        emp.getPhone(),
                        emp.getGender().getDescription(),
                        emp.getSalaryGrade(),
                        emp.getSalary(),
                        DateUtils.formatDate(emp.getBirthDate()),
                        emp.getStatus().getDescription());
            }

            System.out.println("Tổng cộng: " + sortedEmployees.size() + " nhân viên.");
        } catch (DatabaseException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void showStatistics() {
        System.out.println("\n=== THỐNG KÊ NHÂN VIÊN ===");

        try {
            int totalEmployees = employeeService.countAllEmployees();
            int activeEmployees = employeeService.countEmployeesByStatus(EmployeeStatus.ACTIVE);
            int maleEmployees = employeeService.countEmployeesByGender(Gender.MALE);
            int femaleEmployees = employeeService.countEmployeesByGender(Gender.FEMALE);
            double avgSalary = employeeService.getAverageSalary();

            System.out.println("Tổng số nhân viên: " + totalEmployees);
            System.out.println("Số nhân viên đang làm việc: " + activeEmployees);
            System.out.println("Số nhân viên nam: " + maleEmployees);
            System.out.println("Số nhân viên nữ: " + femaleEmployees);
            System.out.println("Lương trung bình: " + String.format("%.2f", avgSalary));

            InputUtils.prompt("Nhấn Enter để tiếp tục...");
        } catch (DatabaseException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}