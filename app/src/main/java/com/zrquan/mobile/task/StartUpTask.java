package com.zrquan.mobile.task;

import android.content.Context;

import com.zrquan.mobile.controller.AccountController;
import com.zrquan.mobile.dao.AccountDao;
import com.zrquan.mobile.event.StartUpEvent;
import com.zrquan.mobile.event.VerifyAccountEvent;
import com.zrquan.mobile.modal.Account;
import com.zrquan.mobile.support.db.DatabaseHelper;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.ui.common.AsyncTask;

import de.greenrobot.event.EventBus;

public class StartUpTask extends AsyncTask<Object, Void, Void> {
    @Override
    protected Void doInBackground(Object[] paramArrayOfObject) {
        LogUtils.d("StartUpTask");
        Context context = (Context)paramArrayOfObject[0];
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        //this line responsible to call onCreate()
        databaseHelper.getWritableDatabase();
        Account account = new AccountDao().loadAccount(context);

        if (account.isValid()) {
            AccountController.verifyAccount(account.getAccessToken());
        } else {
            EventBus.getDefault().post(new VerifyAccountEvent(VerifyAccountEvent.FA_ACCESS_TOKEN_NOT_EXIT));
        }

        EventBus.getDefault().post(new StartUpEvent(databaseHelper, account));
        return null;
    }
}
