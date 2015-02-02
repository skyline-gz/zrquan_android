package com.zrquan.mobile.support.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class SDCardUtils {
    public static boolean sdCardAvailable() {
        String str = Environment.getExternalStorageState();
        return str != null && str.equals("mounted");
    }

    public static void setLocalPath(Context paramContext) {
        if (sdCardAvailable())
            Environment.getExternalStorageDirectory().getPath();
    }

    public static String tempSendPictureDir(Context context) {
        if (!sdCardAvailable())
            return null;
        File localFile1 = context.getExternalFilesDir(null);

        if (!localFile1.exists()) {
            localFile1.mkdirs();
        }

        File localFile2 = new File(localFile1.getAbsolutePath() + "/" + "tempsend");
        if (!localFile2.exists()) {
            localFile2.mkdir();
        }
        return localFile2.getAbsolutePath();
    }

    public static String tempSendPictureName() {
        return DateUtil.getTestSendPictureFileNameDate() + " By " + SystemUtil.BRAND_MODEL + ".png";
    }
}
