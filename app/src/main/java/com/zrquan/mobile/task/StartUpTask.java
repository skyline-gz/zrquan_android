package com.zrquan.mobile.task;

import android.content.Context;

import com.zrquan.mobile.event.StartUpEvent;
import com.zrquan.mobile.support.db.DatabaseHelper;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.ui.common.AsyncTask;

import de.greenrobot.event.EventBus;

public class StartUpTask extends AsyncTask<Object, Void, Void> {
    @Override
    protected Void doInBackground(Object[] paramArrayOfObject) {
        LogUtils.d("StartUpTask");
        DatabaseHelper databaseHelper = new DatabaseHelper((Context)paramArrayOfObject[0]);
        //this line responsible to call onCreate()
        databaseHelper.getWritableDatabase();
        EventBus.getDefault().post(new StartUpEvent(databaseHelper));
        return null;
    }
}
