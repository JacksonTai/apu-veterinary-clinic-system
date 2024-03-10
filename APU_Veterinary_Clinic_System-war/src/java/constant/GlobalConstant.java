package constant;

public class GlobalConstant {

    public static final String MY_PHONE_REGEX = "^01[0-9]{8,9}$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@gmail\\.com$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!.])(?=\\S+$).{8,}$";
    public static final String ALPHABET_WHITESPACE_ONLY_REGEX = "^[a-zA-Z ]+$";

    public static final String[] WEEKDAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    public static final String DMY_SLASH_DATE_FORMAT = "dd/MM/yyyy";
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DASH = "-";

}
