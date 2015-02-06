package com.zrquan.mobile;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.zrquan.mobile.model.Account;
import com.zrquan.mobile.support.db.DatabaseHelper;

public class ZrquanApplication extends Application {

    /**
     * Log or request TAG
     */
    public static final String TAG = "ZrquanApplication";

    //全局应用实例
    private static ZrquanApplication zrquanApplication = null;

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;

    //全局账户对象
    private Account mAccount = null;

    public Account getAccount() {
        return mAccount;
    }

    public void setAccount(Account mAccount) {
        this.mAccount = mAccount;
    }

    //全局数据库对象，全局应只使用唯一的dbHelper对象来保证db读写安全
    private DatabaseHelper dbHelperInstance = null;

    public DatabaseHelper getDatabaseHelper() {
        return dbHelperInstance;
    }

    public void setDatabaseHelper(DatabaseHelper dbHelperInstance) {
        this.dbHelperInstance = dbHelperInstance;
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
