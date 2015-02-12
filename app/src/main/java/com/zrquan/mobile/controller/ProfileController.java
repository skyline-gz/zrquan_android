package com.zrquan.mobile.controller;

import com.android.volley.VolleyError;
import com.zrquan.mobile.support.util.UrlUtils;
import com.zrquan.mobile.support.volley.VolleyMultipartRequest;
import com.zrquan.mobile.support.volley.VolleyRequestBase;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProfileController {

    public static void uploadAvatar(String avatarPath) {
        String url = UrlUtils.getUrl("upload/upload_avatar");
        Map<String, String> stringPartMap = new HashMap<>();
        stringPartMap.put("handle_mode", "save");

        File avatarFile = new File(avatarPath);
        Map<String, File> filePartMap = new HashMap<>();
        filePartMap.put("picture", avatarFile);

        VolleyMultipartRequest.request(url, null, stringPartMap, filePartMap, new VolleyRequestBase.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
