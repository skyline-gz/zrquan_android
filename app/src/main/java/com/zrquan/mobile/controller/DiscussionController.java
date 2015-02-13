package com.zrquan.mobile.controller;

import com.android.volley.VolleyError;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.zrquan.mobile.event.Discussion.PullDownEvent;
import com.zrquan.mobile.event.Discussion.PullUpEvent;
import com.zrquan.mobile.model.DiscussionFeed;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.UrlUtils;
import com.zrquan.mobile.support.volley.VolleyJsonRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class DiscussionController {

    public static void getIdsAndInitialList(int userId, String sortType) {
        // pass second argument as "null" for GET requests
        Map<String, String> params = new HashMap<>();
        params.put("user_id", Integer.toString(userId));
        final String url =
                "http://192.168.1.104:3000/home/posts?user_id=" + userId + "&sort=" + sortType;

        LogUtils.i("服务器URL:" + url);
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JsonObject response) {
                try {
                    LogUtils.i("收到请求的回复了");

                    JsonArray idArray = response.get("ids").getAsJsonArray();
                    if (idArray != null && idArray.size() != 0) {
                        // 转所有的post id 成数组
                        Integer[] discussionIds = new Gson().fromJson(idArray, Integer[].class);
//                        LogUtils.d(discussionIds.toString());

                        JsonArray initialResult = response.get("initial_result").getAsJsonArray();
                        List<DiscussionFeed> initialList = new ArrayList<>();
                        Gson gson = new GsonBuilder()
                                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .create();
                        LogUtils.d("开始循环");
                        for (int i = 0; i < initialResult.size(); i ++ ) {
                            DiscussionFeed d = gson.fromJson(initialResult.get(i), DiscussionFeed.class);
                            initialList.add(d);
                        }
                        LogUtils.i("讨论数:" + initialList.size());
                        EventBus.getDefault().post(new PullDownEvent(discussionIds, initialList));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {}
        });
    }

    public static void getPartialList(Integer[] postIds, String sortType) {
        // pass second argument as "null" for GET requests
        Map<String, String> params = new HashMap<>();
        String queryString = "";
        for (int i=0; i<postIds.length; i++) {
            if (i == 0) {
                queryString = UrlUtils.joinParams("post_id[]", postIds[i].toString());
            } else {
                queryString = queryString + UrlUtils.PARAMETERS_SEPARATOR +
                        UrlUtils.joinParams("post_id[]", postIds[i].toString());
            }
        }
        queryString = queryString + UrlUtils.PARAMETERS_SEPARATOR + "sort=" + sortType;
        final String url = "http://192.168.1.104:3000/home/posts?" + queryString;

        LogUtils.i("服务器URL:" + url);
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JsonObject response) {
                try {
                    LogUtils.i("收到请求的回复了");
                    JsonArray partialResult = response.get("partial_result").getAsJsonArray();
                    List<DiscussionFeed> partialList = new ArrayList<>();
                    Gson gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create();
                    LogUtils.d("开始循环");
                    for (int i = 0; i < partialResult.size(); i ++ ) {
                        DiscussionFeed d = gson.fromJson(partialResult.get(i), DiscussionFeed.class);
                        partialList.add(d);
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
