package com.zrquan.mobile.support.volley;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.volley.toolbox.MultipartRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class VolleyMultipartRequest extends VolleyRequestBase {
    protected static String LOG_Tag = "VolleyMultipartRequest";

    public static void request(String url, final Map<String, String> extHeaders
            , Map<String, String> stringPartMap, Map<String, File> filePartMap, final ResponseHandler responseHandler) {

        request(url, extHeaders, stringPartMap, filePartMap,
                new Response.Listener<JsonObject>() {
                    @Override
                    public void onResponse(JsonObject response) {
                        try {
                            //for pretty json output
                            String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(response);
                            LogUtils.d("\nResponse:" + jsonString);
                            responseHandler.onResponse(response);
                        } catch (Exception e) {
                            LogUtils.d(LOG_Tag, e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseHandler.onErrorResponse(error);
                        LogUtils.d(LOG_Tag, "Volley Response Error:", error);
                    }
                });
    }

    public static void request(String url, final Map<String, String> extHeaders
            , Map<String, String> stringPartMap, Map<String, File> filePartMap
            , Response.Listener<JsonObject> listener, Response.ErrorListener errorListener) {

        MultipartRequest req = new MultipartRequest(url, stringPartMap
                , filePartMap, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                if (!TextUtils.isEmpty(accessToken)) {
                    headers.put(ACCESS_TOKEN_HEADER_KEY, accessToken);
                }
                if (extHeaders != null) {
                    headers.putAll(extHeaders);
                }
                LogUtils.d("headers=" + headers);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        ZrquanApplication.getInstance().addToRequestQueue(req);
    }
}
