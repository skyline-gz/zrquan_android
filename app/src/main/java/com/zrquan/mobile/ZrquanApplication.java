package com.zrquan.mobile;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.zrquan.mobile.modal.Account;
import com.zrquan.mobile.support.db.DatabaseHelper;
import com.zrquan.mobile.support.util.FileUtils;
import com.zrquan.mobile.support.util.LogUtils;

public class ZrquanApplication extends Application {

    /**
     * Log or request TAG
     */
    public static final String TAG = "ZrquanApplication";

    //singleton
    private static ZrquanApplication zrquanApplication = null;

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;

    //本人账户对象
    private Account mAccount = null;

    public Account getAccount() {
        return mAccount;
    }

    public void setAccount(Account mAccount) {
        this.mAccount = mAccount;
    }

    /**
     * @return ZrquanApplication singleton instance
     */
    public static synchronized ZrquanApplication getInstance() {
        return zrquanApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        zrquanApplication = this;
        mAccount = new Account();
//        testReadSql();
    }

    public void testReadSql() {
        String create_sql = FileUtils.readFile("sql/create_industries.sql", null, true, zrquanApplication).toString();
        String insert_sqls = FileUtils.readFile("sql/insert_industries.sql", null, true, zrquanApplication).toString();
        DatabaseHelper databaseHelper = new DatabaseHelper(zrquanApplication, "zrquan");
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE INDUSTRIES");
        sqLiteDatabase.execSQL(create_sql);

//        try {
        String[] queries = insert_sqls.split(";");
        for(String query : queries){
            sqLiteDatabase.execSQL(query);
        }
//        } catch (Exception e) {
//
//        }
//        databaseHelper.getWritableDatabase().execSQL(sqls);
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
