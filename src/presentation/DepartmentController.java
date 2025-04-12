package presentation;

import business.modal.Department;
import business.DepartmentStatus;
import business.DatabaseException;
import validate.ValidationException;
import business.service.DepartmentService;
import util.InputUtils;
import business.Pagination;

import java.util.List;
import java.util.Scanner;

/**
 * Lớp Controller cho chức năng quản lý phòng ban
 */
public class DepartmentController {
    private final DepartmentService departmentService;
    private final Scanner scanner;

    public DepartmentController() {
        this.departmentService = new DepartmentService();
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ PHÒNG BAN ===");
            System.out.println("1. Xem danh sách phòng ban");
            System.out.println("2. Thêm phòng ban mới");
            System.out.println("3. Cập nhật phòng ban");
            System.out.println("4. Xóa phòng ban");
            System.out.println("5. Tìm kiếm phòng ban");
            System.out.println("0. Quay lại");

            int choice = InputUtils.getIntInput("Chọn chức năng: ", 0, 5);

            switch (choice) {
                case 1:
                    listDepartments();
                    break;
                case 2:
                    addDepartment();
                    break;
                case 3:
                    updateDepartment();
                    break;
                case 4:
                    deleteDepartment();
                    break;
                case 5:
                    searchDepartment();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listDepartments() {
        try {
            int page = 1;
            Pagination<Department> pagination;

            do {
                pagination = departmentService.getAllDepartments(page);

                System.out.println("\n=== DANH SÁCH PHÒNG BAN ===");
                System.out.printf("%-5s %-30s %-50s %-15s\n", "ID", "Tên phòng ban", "Mô tả", "Trạng thái");

                for (Department dept : pagination.getItems()) {
                    System.out.printf("%-5d %-30s %-50s %-15s\n",
                            dept.getDepartmentId(),
                            dept.getDepartmentName(),
                            dept.getDescription(),
                            dept.getStatus().getDescription());
                }

                System.out.printf("\nTrang %d/%d - Tổng %d phòng ban\n",
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

    private void addDepartment() {
        System.out.println("\n=== THÊM PHÒNG BAN MỚI ===");

        try {
            Department department = new Department();

            department.setDepartmentName(InputUtils.prompt("Nhập tên phòng ban (10-100 ký tự): "));
            department.setDescription(InputUtils.prompt("Nhập mô tả (tối đa 255 ký tự): "));

            System.out.println("Chọn trạng thái:");
            System.out.println("1. Hoạt động");
            System.out.println("2. Không hoạt động");
            int statusChoice = InputUtils.getIntInput("Chọn: ", 1, 2);
            department.setStatus(statusChoice == 1 ? DepartmentStatus.ACTIVE : DepartmentStatus.INACTIVE);

            departmentService.addDepartment(department);
            System.out.println("Thêm phòng ban thành công!");
        } catch (ValidationException | DatabaseException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void updateDepartment() {
        System.out.println("\n=== CẬP NHẬT PHÒNG BAN ===");

        try {
            int departmentId = InputUtils.getIntInput("Nhập ID phòng ban cần cập nhật: ", 1, Integer.MAX_VALUE);
            Department department = new Department();
            department.setDepartmentId(departmentId);

            System.out.println("Nhấn Enter để giữ nguyên giá trị hiện tại.");
            String newName = InputUtils.prompt("Nhập tên phòng ban mới: ");
            if (!newName.isEmpty()) {
                department.setDepartmentName(newName);
            } else {
                department.setDepartmentName(null);
            }

            String newDescription = InputUtils.prompt("Nhập mô tả mới: ");
            if (!newDescription.isEmpty()) {
                department.setDescription(newDescription);
            }

            System.out.println("Chọn trạng thái mới:");
            System.out.println("1. Hoạt động");
            System.out.println("2. Không hoạt động");
            System.out.println("0. Giữ nguyên");
            int statusChoice = InputUtils.getIntInput("Chọn: ", 0, 2);
            if (statusChoice != 0) {
                department.setStatus(statusChoice == 1 ? DepartmentStatus.ACTIVE : DepartmentStatus.INACTIVE);
            }

            departmentService.updateDepartment(department);
            System.out.println("Cập nhật phòng ban thành công!");
        } catch (ValidationException | DatabaseException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void deleteDepartment() {
        System.out.println("\n=== XÓA PHÒNG BAN ===");

        try {
            int departmentId = InputUtils.getIntInput("Nhập ID phòng ban cần xóa: ", 1, Integer.MAX_VALUE);
            System.out.println("Bạn có chắc chắn muốn xóa phòng ban này? (y/n)");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("y")) {
                boolean success = departmentService.deleteDepartment(departmentId);
                if (success) {
                    System.out.println("Xóa phòng ban thành công!");
                } else {
                    System.out.println("Không tìm thấy phòng ban với ID: " + departmentId);
                }
            } else {
                System.out.println("Đã hủy thao tác xóa.");
            }
        } catch (DatabaseException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void searchDepartment() {
        System.out.println("\n=== TÌM KIẾM PHÒNG BAN ===");

        try {
            String keyword = InputUtils.prompt("Nhập từ khóa tìm kiếm (tên hoặc mô tả): ");
            List<Department> results = departmentService.searchDepartments(keyword);

            if (results.isEmpty()) {
                System.out.println("Không tìm thấy phòng ban nào phù hợp.");
                return;
            }

            System.out.printf("%-5s %-30s %-50s %-15s\n", "ID", "Tên phòng ban", "Mô tả", "Trạng thái");
            for (Department dept : results) {
                System.out.printf("%-5d %-30s %-50s %-15s\n",
                        dept.getDepartmentId(),
                        dept.getDepartmentName(),
                        dept.getDescription(),
                        dept.getStatus().getDescription());
            }

            System.out.println("Tổng cộng: " + results.size() + " phòng ban được tìm thấy.");
        } catch (DatabaseException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}