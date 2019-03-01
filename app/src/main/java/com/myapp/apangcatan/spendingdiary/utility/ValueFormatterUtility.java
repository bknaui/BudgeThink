package com.myapp.apangcatan.spendingdiary.utility;

import java.text.DecimalFormat;

public class ValueFormatterUtility {

    public static String convertToCurrencyFormat(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return formatter.format(amount);

    }
}
