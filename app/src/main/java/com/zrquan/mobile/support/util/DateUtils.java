package com.zrquan.mobile.support.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final long UTC2CHINA = 28800000L;
    private static SimpleDateFormat sHMFormat;
    private static SimpleDateFormat sMDHMFormat;
    private static SimpleDateFormat sSendPictureFileNameFormat;
    private static SimpleDateFormat sYMDFormat;
    private static SimpleDateFormat sYMDHMFormat;
    private static SimpleDateFormat sYMDHMSFormat;

    static {
        SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        sYMDHMSFormat = localSimpleDateFormat1;
        SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        sSendPictureFileNameFormat = localSimpleDateFormat2;
        SimpleDateFormat localSimpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        sYMDHMFormat = localSimpleDateFormat3;
        SimpleDateFormat localSimpleDateFormat4 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        sYMDFormat = localSimpleDateFormat4;
        SimpleDateFormat localSimpleDateFormat5 = new SimpleDateFormat("HH:mm", Locale.CHINA);
        sHMFormat = localSimpleDateFormat5;
        SimpleDateFormat localSimpleDateFormat6 = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
        sMDHMFormat = localSimpleDateFormat6;
    }

    public static String getMDHMDate(long paramLong) {
        SimpleDateFormat localSimpleDateFormat = sMDHMFormat;
        Date localDate = new Date(paramLong);
        return localSimpleDateFormat.format(localDate);
    }

    public static String getHMDate(long paramLong) {
        SimpleDateFormat localSimpleDateFormat = sHMFormat;
        Date localDate = new Date(paramLong);
        return localSimpleDateFormat.format(localDate);
    }

    public static String getTestSendPictureFileNameDate() {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HHmmss", Locale.CHINA);
        Date localDate = new Date(System.currentTimeMillis());
        return localSimpleDateFormat.format(localDate);
    }

}

