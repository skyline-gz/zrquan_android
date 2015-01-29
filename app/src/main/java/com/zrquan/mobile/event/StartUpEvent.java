package com.zrquan.mobile.event;

import com.zrquan.mobile.support.db.DatabaseHelper;

public class StartUpEvent {
    public DatabaseHelper databaseHelper;

    public StartUpEvent (DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
}
