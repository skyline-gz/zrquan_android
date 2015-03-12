package com.zrquan.mobile.ui.common;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.zrquan.mobile.R;
import com.zrquan.mobile.support.util.LogUtils;

public class CommonActivity extends Activity {
    protected final String LOG_TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止屏幕翻转的检测
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        LogUtils.d(LOG_TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onPause() {
        LogUtils.d(LOG_TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        LogUtils.d(LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtils.d(LOG_TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.d(LOG_TAG, "onRestart");
    }
}
