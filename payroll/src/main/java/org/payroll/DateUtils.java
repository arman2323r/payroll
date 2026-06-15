package org.payroll;

import com.ibm.icu.util.PersianCalendar;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateUtils {

    private static final DateTimeFormatter MILADI_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter SHAMSI_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter MILADI_YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    private static final DateTimeFormatter SHAMSI_YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM");


    public static LocalDate shamsiToMiladi(String shamsiDate) {
        if (shamsiDate == null || shamsiDate.isBlank()) return null;
        try {
            String[] parts = shamsiDate.split("/");
            if (parts.length != 3) throw new IllegalArgumentException("فرمت تاریخ شمسی باید yyyy/MM/dd باشد");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            PersianCalendar pc = new PersianCalendar(year, month - 1, day);
            return LocalDate.of(pc.get(com.ibm.icu.util.Calendar.YEAR),
                    pc.get(com.ibm.icu.util.Calendar.MONTH) + 1,
                    pc.get(com.ibm.icu.util.Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            throw new IllegalArgumentException("خطا در تبدیل تاریخ شمسی: " + shamsiDate, e);
        }
    }

    public static String miladiToShamsi(LocalDate miladiDate) {
        if (miladiDate == null) return null;
        PersianCalendar pc = new PersianCalendar();
        pc.set(miladiDate.getYear(), miladiDate.getMonthValue() - 1, miladiDate.getDayOfMonth());
        int year = pc.get(com.ibm.icu.util.Calendar.YEAR);
        int month = pc.get(com.ibm.icu.util.Calendar.MONTH) + 1;
        int day = pc.get(com.ibm.icu.util.Calendar.DAY_OF_MONTH);
        return String.format("%04d/%02d/%02d", year, month, day);
    }


    public static int shamsiYearToMiladi(int shamsiYear) {
        PersianCalendar pc = new PersianCalendar(shamsiYear, 0, 1);
        return pc.get(com.ibm.icu.util.Calendar.YEAR);
    }

    public static int miladiYearToShamsi(int miladiYear) {
        PersianCalendar pc = new PersianCalendar();
        pc.set(miladiYear, 0, 1);
        return pc.get(com.ibm.icu.util.Calendar.YEAR);
    }


    public static LocalDate shamsiYearMonthToFirstMiladiDay(String shamsiYearMonth) {
        if (shamsiYearMonth == null || shamsiYearMonth.isBlank()) return null;
        String[] parts = shamsiYearMonth.split("/");
        if (parts.length != 2) throw new IllegalArgumentException("فرمت باید yyyy/MM باشد");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        PersianCalendar pc = new PersianCalendar(year, month - 1, 1);
        return LocalDate.of(pc.get(com.ibm.icu.util.Calendar.YEAR),
                pc.get(com.ibm.icu.util.Calendar.MONTH) + 1,
                1);
    }

    public static LocalDate miladiYearMonthToFirstDay(String miladiYearMonth) {
        if (miladiYearMonth == null || miladiYearMonth.isBlank()) return null;
        String[] parts = miladiYearMonth.split("-");
        if (parts.length != 2) throw new IllegalArgumentException("فرمت باید yyyy-MM باشد");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        return LocalDate.of(year, month, 1);
    }

    public static String toMiladiYearMonth(LocalDate date) {
        if (date == null) return null;
        return date.format(MILADI_YEAR_MONTH_FORMATTER);
    }

    public static String toShamsiYearMonth(LocalDate date) {
        if (date == null) return null;
        String fullShamsi = miladiToShamsi(date);
        if (fullShamsi == null) return null;
        return fullShamsi.substring(0, 7);
    }

    public static boolean isValidShamsiDate(String shamsiDate) {
        try {
            LocalDate miladi = shamsiToMiladi(shamsiDate);
            return miladi != null;
        } catch (Exception e) {
            return false;
        }
    }
}