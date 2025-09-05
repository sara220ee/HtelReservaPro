package model;

import java.time.format.DateTimeFormatter;

public class ReservationDateFormatter {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String format(java.time.LocalDate date) {
        return date.format(formatter);
    }

    public static void setFormat(String pattern) {
        formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }
}