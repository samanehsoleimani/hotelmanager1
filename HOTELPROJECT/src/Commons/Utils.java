package common;

import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
    public static String formatPrice(int price) {
        return NumberFormat.getNumberInstance(Locale.US).format(price) + " تومان";
    }

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}