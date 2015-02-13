package com.zrquan.mobile.task;

import android.content.Context;

import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.controller.AccountController;
import com.zrquan.mobile.dao.AccountDao;
import com.zrquan.mobile.event.AccountEvent;
import com.zrquan.mobile.event.StartUpEvent;
import com.zrquan.mobile.model.Account;
import com.zrquan.mobile.support.db.DatabaseHelper;
import com.zrquan.mobile.support.enums.EventCode;
import com.zrquan.mobile.support.enums.EventType;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.ui.common.AsyncTask;

import de.greenrobot.event.EventBus;

public class StartUpTask extends AsyncTask<Object, Void, Void> {
    @Override
    protected Void doInBackground(Object[] paramArrayOfObject) {
        LogUtils.d("StartUpTask");
        Context context = (Context)paramArrayOfObject[0];

        //初始化数据库以及从SharedPreferences读取用户账户
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        //this line responsible to call onCreate()
        databaseHelper.getWritableDatabase();
        Account account = new AccountDao().loadAccount(context);

        ZrquanApplication.getInstance().setDatabaseHelper(databaseHelper);
        ZrquanApplication.getInstance().setAccount(account);

        if (account.isValid()) {
            AccountController.verifyAccount(account.getAccessToken());
        } else {
            AccountEvent accountEvent = new AccountEvent();
            accountEvent.setEventType(EventType.AE_NET_VERIFY_JWT);
            accountEvent.setEventCode(EventCode.FA_ACCESS_TOKEN_NOT_EXIT);
            EventBus.getDefault().post(accountEvent);
        }

        EventBus.getDefault().post(new StartUpEvent());
        return null;
    }
}
