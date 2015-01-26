package com.zrquan.mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ui.common.CommonActivity;

public class SplashActivity extends CommonActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
                overridePendingTransition(R.anim.main_enter, R.anim.main_exit );
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
