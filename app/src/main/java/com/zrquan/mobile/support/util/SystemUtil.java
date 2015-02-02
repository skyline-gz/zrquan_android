package com.zrquan.mobile.support.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.lang.Integer;
import java.util.List;
import java.util.Locale;

public class SystemUtil {
    public static String BRAND_MODEL;

    static {
        String str1 = Build.BRAND;
        if ((str1 != null) && (str1.length() > 0)) {
            str1 = str1.substring(0, 1).toUpperCase(Locale.CHINA) + str1.substring(1);
        }
        String str2 = Build.MODEL;
        if ((str2 != null) && (str2.length() > 0)) {
            str2 = str2.substring(0, 1).toUpperCase(Locale.CHINA) + str2.substring(1);
        }
        BRAND_MODEL = str1 + " " + str2;
    }

    public static void call(Context paramContext, String paramString) {
        Intent localIntent = new Intent("android.intent.action.CALL");
        StringBuilder localStringBuilder1 = new StringBuilder();
        localIntent.setData(Uri.parse("tel:" + paramString));
        try {
            paramContext.startActivity(localIntent);
        } catch (Exception localException) {
            Log.w("Call number " + paramString + " failed.", localException);
        }
    }

    public static void copy(Context paramContext, String paramString) {
        ((ClipboardManager) paramContext.getSystemService(Context.CLIPBOARD_SERVICE)).setText(paramString);
        Toast.makeText(paramContext, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
    }

    public static boolean isServiceExists(Context paramContext, String paramString) {
        List localList = ((ActivityManager) paramContext.getSystemService(Context.ACTIVITY_SERVICE))
                .getRunningServices(Integer.MAX_VALUE);
        if (localList.isEmpty())
            return false;
        int i = localList.size();
        for (int j = 0; j < i; j++) {
            ComponentName localComponentName = ((RunningServiceInfo) localList.get(j)).service;
            System.out.println(localComponentName.getClassName());
            if (localComponentName.getClassName().equals(paramString))
                return true;
        }
        return false;
    }

    public static void openURL(Context paramContext, String paramString) {
        String str = paramString;
        if ((!str.toLowerCase().startsWith("http://")) && (!str.toLowerCase().startsWith("https://"))) {
            str = "http://" + str;
        }
        Uri localUri = Uri.parse(str);
        Intent localIntent = new Intent("android.intent.action.VIEW", localUri);
        paramContext.startActivity(Intent.createChooser(localIntent, "Choose browser"));
    }

    public static void sendSms(Context paramContext, String paramString) {
        Intent localIntent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + paramString));
        localIntent.putExtra("sms_body", "");
        paramContext.startActivity(localIntent);
    }
}
