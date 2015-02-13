package com.zrquan.mobile.support.volley;

import com.android.volley.VolleyError;

import com.google.gson.JsonObject;

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
        public void onResponse(JsonObject response);

        public void onErrorResponse(VolleyError error);
    }
}
