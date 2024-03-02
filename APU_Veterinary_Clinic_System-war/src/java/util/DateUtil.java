package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DateUtil {

    public static LocalDate parseDate(String date, String format) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }

    public static List<LocalDate> getNextFourMondaysDates() {
        LocalDate currentDate = LocalDate.now();

        // If today is not Monday, find the next Monday
        if (!currentDate.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            currentDate = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        List<LocalDate> weekDates = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            weekDates.add(currentDate);
            currentDate = currentDate.plusDays(7); // Move to the next Monday
        }
        return weekDates;
    }

    public static List<LocalDate> generateWeekDates(LocalDate startDate) {
        List<LocalDate> weekDates = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            weekDates.add(startDate.plusDays(i));
        }
        return weekDates;
    }

}
