package com.zrquan.mobile.task;

import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.support.db.DatabaseHelper;
import com.zrquan.mobile.ui.common.AsyncTask;

public class StartUpTask extends AsyncTask<Void, Integer, Void> {
    @Override
    protected Void doInBackground(Void[] paramArrayOfVoid) {
        DatabaseHelper databaseHelper = new DatabaseHelper(ZrquanApplication.getInstance());
        return null;
    }
}
