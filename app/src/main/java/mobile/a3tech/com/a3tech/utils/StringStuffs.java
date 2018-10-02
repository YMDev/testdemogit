package mobile.a3tech.com.a3tech.utils;

/**
 * Created by SPIA on 11/04/2017.
 */

public class StringStuffs {
    public static boolean isProbablyArabic(String s) {
        for (int i = 0; i < s.length();) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    public static boolean isEmpty(String value){
        return value == null || value.length() == 0;
    }
}
