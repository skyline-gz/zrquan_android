package com.zrquan.mobile.dao;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.support.util.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountDao {

    public static void sendVerifyCode(String phoneNum) {
        // pass second argument as "null" for GET requests
        Map<String, String> params = new HashMap <>();
        params.put("mobile", phoneNum);
        final String URL = UrlUtils.getUrlWithParams("account/send_verify_code", params);
        JsonObjectRequest req = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        ZrquanApplication.getInstance().addToRequestQueue(req);
    }
}
