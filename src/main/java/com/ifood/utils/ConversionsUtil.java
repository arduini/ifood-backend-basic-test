package com.ifood.utils;

import java.math.BigDecimal;
import java.util.Date;

public class ConversionsUtil {

    public static BigDecimal convertKelvinToCelsius(final Double tempFarenheit) {
        return new BigDecimal(tempFarenheit - 273.15);
    }

    public static Date convertSecondsToDate(final Long seconds) {
        Date date = new Date(seconds * 1000);
        return date;
    }
}
