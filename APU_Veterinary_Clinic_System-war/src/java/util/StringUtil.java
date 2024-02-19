package util;

public class StringUtil {

    public static String getCommaSeparatedString(String[] values) {
        StringBuilder builder = new StringBuilder();
        for (String value : values) {
            builder.append(value).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
