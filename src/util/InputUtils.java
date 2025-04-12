package util;

import java.util.Scanner;

/**
 * Lớp tiện ích để xử lý đầu vào từ người dùng
 */
public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public static String prompt(String message, boolean isPassword) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public static int getIntInput(String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) return input;
                System.out.println("Vui lòng nhập số từ " + min + " đến " + max);
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ");
            }
        }
    }

    public static double getDoubleInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ");
            }
        }
    }
}