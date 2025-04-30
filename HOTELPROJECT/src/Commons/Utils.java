package common;

import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
    public static String formatPrice(int price) {
        return NumberFormat.getNumberInstance(Locale.US).format(price) + " تومان";
    }

    public static String alignText(String text, int length) {
        return String.format("%-" + length + "s", text);
    }
}
//
