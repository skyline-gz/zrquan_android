package com.zrquan.mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.event.StartUpEvent;
import com.zrquan.mobile.task.StartUpTask;
import com.zrquan.mobile.ui.common.CommonActivity;

import de.greenrobot.event.EventBus;

public class SplashActivity extends CommonActivity {

    /** Minimal Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    //标记是否等待　SPLASH_DISPLAY_LENGTH　完成
    private boolean bReadyWaiting = false;
    //标记是否StartUpTask已经完成
    private boolean bReadyBgTask = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bReadyWaiting = true;
                checkAndstartMainActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        StartUpTask startUpTask = new StartUpTask();
        startUpTask.executeOnMultiThreads(new Object[]{ZrquanApplication.getInstance()});
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(StartUpEvent startUpEvent) {
        bReadyBgTask = true;
        ZrquanApplication.getInstance().setDatabaseHelper(startUpEvent.databaseHelper);
        checkAndstartMainActivity();
    }

    private void checkAndstartMainActivity() {
        synchronized (this) {
            //只有两种任务均完成才跳转到主页面
            if(bReadyBgTask && bReadyWaiting) {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
                overridePendingTransition(R.anim.main_enter, R.anim.main_exit );
            }
        }
    }
}
