package com.zrquan.mobile.ui.common;

import android.os.Build;
import android.util.Log;

//使用　THREAD_POOL_EXECUTOR　取代SERIAL_EXECUTOR　版本的AsyncTask
//支持同时执行五个线程任务: see http://blog.csdn.net/hitlion2008/article/details/7983449
public abstract class AsyncTask<Params, Progress, Result> extends android.os.AsyncTask<Params, Progress, Result> {
    private final String LOG_TAG = getClass().getName();

    public android.os.AsyncTask<Params, Progress, Result> executeOnMultiThreads(Params[] paramArrayOfParams) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                return super.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, paramArrayOfParams);
            return super.execute(paramArrayOfParams);
        } catch (Exception e) {
            Log.e(this.LOG_TAG, String.valueOf(e));
        }
        return null;
    }
}