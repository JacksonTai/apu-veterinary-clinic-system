package constant;

public class GlobalConstant {

    public static final String MY_PHONE_REGEX = "(\\+?6?01)[0|1|2|3|4|6|7|8|9]\\-*[0-9]{7,8}";
    public static final String STAFF_EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@avcs\\.com$";
    public static final String FULL_NAME_REGEX = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)?$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!.])(?=\\S+$).{8,}$";

}
