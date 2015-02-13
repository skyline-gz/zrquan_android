package com.zrquan.mobile.controller;

import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import com.google.gson.JsonObject;
import com.zrquan.mobile.event.ProfileEvent;
import com.zrquan.mobile.support.enums.EventCode;
import com.zrquan.mobile.support.enums.EventType;
import com.zrquan.mobile.support.enums.ServerCode;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.UrlUtils;
import com.zrquan.mobile.support.volley.VolleyMultipartRequest;
import com.zrquan.mobile.support.volley.VolleyRequestBase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class ProfileController {

    public static void uploadAvatar(final String avatarPath) {
        String url = UrlUtils.getUrl("upload/upload_avatar");

        Map<String, String> stringPartMap = new HashMap<>();
        stringPartMap.put("handle_mode", "save");

        File avatarFile = new File(avatarPath);
        Map<String, File> filePartMap = new HashMap<>();
        filePartMap.put("picture", avatarFile);

        VolleyMultipartRequest.request(url, null, stringPartMap, filePartMap, new VolleyRequestBase.ResponseHandler() {
            @Override
            public void onResponse(JsonObject response) {
                String code = response.get("code").getAsString();
                ProfileEvent profileEvent = new ProfileEvent();
                profileEvent.setEventType(EventType.PE_NET_UPLOAD_AVATAR_END);
                if (code.equals(ServerCode.S_OK.name())) {
                    profileEvent.setEventCode(EventCode.S_OK);
                    profileEvent.setServerCode(ServerCode.S_OK);
                    JsonObject results = response.get("results").getAsJsonObject();
                    String avatarPath = results.get("url").getAsString();
                    profileEvent.setAvatarPath(avatarPath);
                } else {
                    profileEvent.setEventCode(EventCode.FA_SERVER_ERROR);
                    profileEvent.setServerCode(ServerCode.valueOf(code));
                }
                EventBus.getDefault().post(profileEvent);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError) {
                    LogUtils.d("服务器正忙，请稍后再上传");
                }
            }
        });
    }
}
