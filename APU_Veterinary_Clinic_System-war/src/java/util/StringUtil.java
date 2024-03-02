package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StringUtil {

    public static String getCommaSeparatedString(String[] values) {
        StringBuilder builder = new StringBuilder();
        for (String value : values) {
            builder.append(value).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

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

    public static boolean containsNumeric(String input) {
        return containsCharacter(input, Character::isDigit);
    }

    public static boolean containsAlphabetic(String input) {
        return containsCharacter(input, Character::isLetter);
    }

    public static boolean containsWhitespace(String input) {
        return containsCharacter(input, Character::isWhitespace);
    }

    public static boolean containsUpperCase(String input) {
        return containsCharacter(input, Character::isUpperCase);
    }

    public static boolean containsLowerCase(String input) {
        return containsCharacter(input, Character::isLowerCase);
    }

    public static boolean containsCharacter(String input, Predicate<Character> condition) {
        for (int i = 0; i < input.length(); i++) {
            if (condition.test(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String toDateFormat(final Date date, final String format) {
        return date == null ? null : new SimpleDateFormat(format).format(date);
    }

    public static String convertDateFormat(String date, String format) {
        return date == null || date.isEmpty() ? null : LocalDate.parse(date).format(DateTimeFormatter.ofPattern(format));
    }

    public static String requireNonNullElse(String value, String replacement) {
        return (value != null && !value.trim().isEmpty()) ? value : replacement;
    }

    public static String getConcatenatedString(List<String> values, String separator) {
        return String.join(separator + " ", values);
    }

    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(input.charAt(0)));
        for (int i = 1; i < input.length(); i++) {
            char ch = input.charAt(i);
            // Add space before consecutive uppercase letters (except the first word)
            if (Character.isUpperCase(ch) && i > 1 && !Character.isUpperCase(input.charAt(i - 1))) {
                sb.append(' ');
            }
            sb.append(ch);
        }
        return sb.toString();
    }
}
