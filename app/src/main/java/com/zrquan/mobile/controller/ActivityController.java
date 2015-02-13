package com.zrquan.mobile.controller;

import com.android.volley.VolleyError;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zrquan.mobile.event.Activity.PullDownEvent;
import com.zrquan.mobile.event.Activity.PullUpEvent;
import com.zrquan.mobile.model.Activity;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.UrlUtils;
import com.zrquan.mobile.support.volley.VolleyJsonRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class ActivityController {

    public static void getIdsAndInitialList(int userId) {
        // pass second argument as "null" for GET requests
        Map<String, String> params = new HashMap<>();
        params.put("user_id", Integer.toString(userId));
        final String url =
                "http://192.168.1.104:3000//activities?user_id=" + userId;

        LogUtils.i("服务器URL:" + url);
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JsonObject response) {
                try {
                    LogUtils.i("收到请求的回复了");

                    JsonArray idArray = response.getAsJsonArray("ids");
                    if (idArray != null && idArray.size() != 0) {
                        // 转所有的post id 成数组
                        Integer[] activityIds = new Gson().fromJson(idArray.toString(), Integer[].class);
                        LogUtils.d(activityIds.toString());

                        JsonArray initialResult = response.getAsJsonArray("initial_result");
                        List<Activity> initialList = new ArrayList<>();
                        Gson gson = new GsonBuilder()
                                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .create();
                        LogUtils.d("开始循环");
                        for (int i = 0; i < initialResult.size(); i ++ ) {
                            Activity a = gson.fromJson(initialResult.get(i), Activity.class);
                            initialList.add(a);
                        }
                        LogUtils.i("讨论数:" + initialList.size());
                        EventBus.getDefault().post(new PullDownEvent(activityIds, initialList));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {}
        });
    }

    public static void getPartialList(Integer[] activityIds, String sortType) {
        // pass second argument as "null" for GET requests
        Map<String, String> params = new HashMap<>();
        String queryString = "";
        for (int i=0; i<activityIds.length; i++) {
            if (i == 0) {
                queryString = UrlUtils.joinParams("activity_id[]", activityIds[i].toString());
            } else {
                queryString = queryString + UrlUtils.PARAMETERS_SEPARATOR +
                        UrlUtils.joinParams("activity_id[]", activityIds[i].toString());
            }
        }
        queryString = queryString + UrlUtils.PARAMETERS_SEPARATOR;
        final String url = "http://192.168.1.104:3000/activities?" + queryString;

        LogUtils.i("服务器URL:" + url);
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JsonObject response) {
                try {
                    LogUtils.i("收到请求的回复了");
                    JsonArray partialResult = response.getAsJsonArray("partial_result");
                    List<Activity> partialList = new ArrayList<>();
                    Gson gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create();
                    LogUtils.d("开始循环");
                    for (int i = 0; i < partialResult.size(); i ++ ) {
                        Activity a = gson.fromJson(partialResult.get(i), Activity.class);
                        partialList.add(a);
                    }
                    LogUtils.i("讨论数:" + partialList.size());
                    EventBus.getDefault().post(new PullUpEvent(partialList));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {}
        });
    }
}
