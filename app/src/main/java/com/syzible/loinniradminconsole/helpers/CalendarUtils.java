package com.syzible.loinniradminconsole.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ed on 04/10/2017.
 */

public class CalendarUtils {
    public static String dateFormat = "HH:mm dd-MM-yyyy";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

    public static String getFormattedDate(long milliseconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return simpleDateFormat.format(calendar.getTime());
    }
}
