package com.droidevils.algodroid.utility;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberFormat {
    public static double roundDecimal(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(d));
    }
}
