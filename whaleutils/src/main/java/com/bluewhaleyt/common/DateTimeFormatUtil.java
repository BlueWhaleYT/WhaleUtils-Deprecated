package com.bluewhaleyt.common;


import android.icu.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormatUtil {

    public static final String FORMAT_DATE =  "dd/MM/yyyy";
    public static final String FORMAT_TIME =  "HH:mm:ss";
    public static final String FORMAT_DATE_TIME =  "dd/MM/yyyy HH:mm:ss";

    public static String getDateStyle1(Date date) {
        if (SDKUtil.isAtLeastSDK24()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日 E");
            return dateFormat.format(date);
        }
        return null;
    }

}
