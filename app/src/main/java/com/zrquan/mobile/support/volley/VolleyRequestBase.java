package com.zrquan.mobile.support.volley;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public abstract class VolleyRequestBase {

    protected static final String ACCESS_TOKEN_HEADER_KEY = "Zrquan-Token";

    protected volatile static String accessToken;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        VolleyRequestBase.accessToken = accessToken;
    }

    public static interface ResponseHandler {
        public void onResponse(JSONObject response);

        public void onErrorResponse(VolleyError error);
    }
}
