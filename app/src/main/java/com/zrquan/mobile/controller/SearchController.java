package com.zrquan.mobile.controller;

import com.android.volley.VolleyError;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zrquan.mobile.event.Discussion.PullUpEvent;
import com.zrquan.mobile.model.DiscussionFeed;
import com.zrquan.mobile.model.UserSearch;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.UrlUtils;
import com.zrquan.mobile.support.volley.VolleyJsonRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class SearchController {

    public static void performSearch(String keyWord) {
        // pass second argument as "null" for GET requests
        Map<String, String> params = new HashMap<>();
        params.put("q", keyWord);
        params.put("type", "User");
        final String url =
                "http://192.168.1.104:3000/search?q=" + keyWord + "&type=User";

        LogUtils.i("服务器URL:" + url);
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JsonObject response) {
                try {
                    LogUtils.d("收到请求的回复了");
                    JsonArray result = response.get("result").getAsJsonArray();
                    List<UserSearch> initialList = new ArrayList<>();
                    Gson gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create();
                    for (int i = 0; i < result.size(); i ++ ) {
                        UserSearch d = gson.fromJson(result.get(i), UserSearch.class);
                        initialList.add(d);
                    }
//                    LogUtils.i("讨论数:" + initialList.size());
//                    EventBus.getDefault().post(new PullDownEvent(discussionIds, initialList));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {}
        });
    }
}
