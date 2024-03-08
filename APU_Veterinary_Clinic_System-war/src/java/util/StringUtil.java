package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {

    public static boolean containsExcessiveWhitespace(String input) {
        int consecutiveSpaces = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                consecutiveSpaces++;
                if (consecutiveSpaces > 1) {
                    return true;
                }
            } else {
                consecutiveSpaces = 0;
            }
        }
        return false;
    }

    public static String convertDateFormat(String date, String format) {
        return date == null || date.isEmpty() ? null : LocalDate.parse(date).format(DateTimeFormatter.ofPattern(format));
    }

    public static String requireNonNullElse(String value, String replacement) {
        return (value != null && !value.trim().isEmpty()) ? value : replacement;
    }

    public static String getConcatenatedString(List<String> values, String separator) {
        if (values == null || values.isEmpty()) {
            return null;
        }
        return String.join(separator + " ", values);
    }

    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Transform based on case and presence of camelCase
        if (input.toUpperCase().equals(input) || input.toLowerCase().equals(input)) {
            // Purely uppercase or lowercase
            return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        } else {
            // Handle camelCase
            return Arrays.stream(input.split("(?=[A-Z])"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                    .collect(Collectors.joining(" "));
        }
    }
}
