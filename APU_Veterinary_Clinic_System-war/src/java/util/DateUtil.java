package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DateUtil {

    public static LocalDate parseDate(String date, String format) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }

    public static List<LocalDate> getNextWeekMondayDates(int weekCount) {
        LocalDate start = LocalDate.now();
        // Adjust start date to next Monday if today is not Monday
        if (start.getDayOfWeek() != DayOfWeek.MONDAY) {
            start = start.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }
        return generateMondays(start, weekCount);
    }

    public static List<LocalDate> getThisAndNextWeekMondaysDates(int weekCount) {
        // Find the Monday of this week, including today if it's Monday
        LocalDate start = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return generateMondays(start, weekCount);
    }

    private static List<LocalDate> generateMondays(LocalDate start, int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> start.plusWeeks(i))
                .collect(Collectors.toList());
    }

    public static List<LocalDate> generateWeekDates(LocalDate startDate) {
        List<LocalDate> weekDates = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            weekDates.add(startDate.plusDays(i));
        }
        return weekDates;
    }

}
