package com.zrquan.mobile.ui.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.zrquan.mobile.R;

public class DemoSettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_demo);
    }

    public void onBtnBackClick(View view) {
        finish();
    }
}
