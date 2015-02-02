package com.zrquan.mobile.support.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
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

    public static String getChatMessageDate(long paramLong) {
        SimpleDateFormat localSimpleDateFormat = sMDHMFormat;
        Date localDate = new Date(paramLong);
        return localSimpleDateFormat.format(localDate);
    }

    public static String getHMDate(long paramLong) {
        SimpleDateFormat localSimpleDateFormat = sHMFormat;
        Date localDate = new Date(paramLong);
        return localSimpleDateFormat.format(localDate);
    }

    public static long getMillisByHMDHMSF(String paramString) {
        try {
            return sYMDHMSFormat.parse(paramString).getTime();
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return 0L;
    }

    public static String getSaveMessageDate(long paramLong) {
        SimpleDateFormat localSimpleDateFormat = sYMDHMSFormat;
        Date localDate = new Date(paramLong);
        return localSimpleDateFormat.format(localDate);
    }

    public static String getTestSendPictureFileNameDate() {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HHmmss", Locale.CHINA);
        Date localDate = new Date(System.currentTimeMillis());
        return localSimpleDateFormat.format(localDate);
    }

    public static String getYMDDate(long paramLong) {
        if (isToday(paramLong))
            return getHMDate(paramLong);
        SimpleDateFormat localSimpleDateFormat = sYMDFormat;
        Date localDate = new Date(paramLong);
        return localSimpleDateFormat.format(localDate);
    }

    public static String getYMDHMDate(long paramLong) {
        SimpleDateFormat localSimpleDateFormat = sYMDHMFormat;
        Date localDate = new Date(paramLong);
        return localSimpleDateFormat.format(localDate);
    }

    public static String getYMDHMSDate(long paramLong) {
        SimpleDateFormat localSimpleDateFormat = sYMDHMSFormat;
        Date localDate = new Date(paramLong);
        return localSimpleDateFormat.format(localDate);
    }

    public static boolean isToday(long paramLong) {
        if (paramLong == 0L)
            return false;
        Calendar localCalendar = Calendar.getInstance(Locale.getDefault());
        int i = localCalendar.get(Calendar.YEAR);
        int j = localCalendar.get(Calendar.MONTH);
        int k = localCalendar.get(Calendar.DATE);
        localCalendar.setTimeInMillis(paramLong);
        return ((i == localCalendar.get(Calendar.YEAR))
                && (j == localCalendar.get(Calendar.MONTH))
                && (k == localCalendar.get(Calendar.DATE)));
    }
}

