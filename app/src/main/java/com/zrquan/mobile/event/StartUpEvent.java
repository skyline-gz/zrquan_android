package com.zrquan.mobile.event;

import com.zrquan.mobile.modal.Account;
import com.zrquan.mobile.support.db.DatabaseHelper;

public class StartUpEvent {
    public DatabaseHelper databaseHelper;
    public Account account;

    public StartUpEvent (DatabaseHelper databaseHelper, Account account) {
        this.databaseHelper = databaseHelper;
        this.account = account;
    }
}
