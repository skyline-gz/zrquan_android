package com.zrquan.mobile.support.volley;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.support.volley.cache.BitmapLruCache;

/**
 * Created by James_Ouyang on 2015/3/5.
 */
public class VolleyContext {
    private static VolleyContext mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleyContext(){
        mRequestQueue = Volley.newRequestQueue(ZrquanApplication.getInstance().getApplicationContext());
        mImageLoader = new ImageLoader(this.mRequestQueue, new BitmapLruCache());
    }

    public static VolleyContext getInstance(){
        if(mInstance == null){
            mInstance = new VolleyContext();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }
}
