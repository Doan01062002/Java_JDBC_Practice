package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Lớp tiện ích để xử lý ngày tháng
 */
public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatDate(LocalDate date) {
        if (date == null) return "";
        return date.format(FORMATTER);
    }

    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Ngày không hợp lệ, định dạng phải là dd/MM/yyyy");
        }
    }
}