package com.zrquan.mobile;

import android.app.Application;

public class ZrquanApplication extends Application {

    //singleton
    private static ZrquanApplication zrquanApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        zrquanApplication = this;
    }
}
