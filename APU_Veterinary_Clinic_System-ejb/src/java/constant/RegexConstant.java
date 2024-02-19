package constant;

public class RegexConstant {

    public static final String MY_PHONE_REGEX = "(\\+?6?01)[0|1|2|3|4|6|7|8|9]\\-*[0-9]{7,8}";
    public static final String EMAIL_REGEX = "/^\\S+@\\S+\\.\\S+$/";
    public static final String FULL_NAME_REGEX = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)?$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!.])(?=\\S+$).{8,}$";

}
