package com.zrquan.mobile;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.zrquan.mobile.model.Account;
import com.zrquan.mobile.support.db.DatabaseHelper;

public class ZrquanApplication extends Application {

    /**
     * Log or request TAG
     */
    public static final String TAG = "ZrquanApplication";

    //全局应用实例
    private static ZrquanApplication zrquanApplication = null;

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

        //创建默认的ImageLoader配置参数，默认开启硬盘内存和硬盘缓存
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions).build();

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }
}
