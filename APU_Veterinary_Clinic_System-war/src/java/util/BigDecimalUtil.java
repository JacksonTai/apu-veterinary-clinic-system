package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {

    /**
     * Rounds a BigDecimal value to the specified number of decimal places.
     *
     * @param value       The BigDecimal value to round.
     * @param decimalPlaces The desired number of decimal places.
     * @return The rounded BigDecimal value.
     */
    public static BigDecimal round(BigDecimal value, int decimalPlaces) {
        return value.setScale(decimalPlaces, RoundingMode.HALF_UP);
    }

}
