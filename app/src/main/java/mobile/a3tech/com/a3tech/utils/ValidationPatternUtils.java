package mobile.a3tech.com.a3tech.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationPatternUtils {

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean isValideEmail(final String email){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email != null && email.matches(emailPattern) && email.length() > 0){
            return true;
        }else{
            return false;
        }
    }
}
