package com.zrquan.mobile;

import android.app.Application;

import com.zrquan.mobile.modal.Account;

public class ZrquanApplication extends Application {

    public Account getAccount() {
        return mAccount;
    }

    public void setAccount(Account mAccount) {
        this.mAccount = mAccount;
    }

    //singleton
    private static ZrquanApplication zrquanApplication = null;

    //本人账户对象
    private Account mAccount = null;

    @Override
    public void onCreate() {
        super.onCreate();
        zrquanApplication = this;
        mAccount = new Account();
    }
}
