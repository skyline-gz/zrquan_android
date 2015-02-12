package com.zrquan.mobile.controller;

import com.android.volley.VolleyError;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zrquan.mobile.event.PullDownEvent;
import com.zrquan.mobile.event.PullUpEvent;
import com.zrquan.mobile.model.Discussion;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.UrlUtils;
import com.zrquan.mobile.support.volley.VolleyJsonRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class DiscussionController {

//    public static void getIdsAndInitialList(int userId, int page) {
//        // pass second argument as "null" for GET requests
//        Map<String, String> params = new HashMap<>();
//        params.put("userId", Integer.toString(userId));
//        params.put("page", Integer.toString(page));
//        final String url =
//                "http://192.168.1.104:3000/home/hot_posts?userId=" + userId + "&page=" + page;
//
//        LogUtils.d("服务器URL:" + url);
//        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    LogUtils.d("收到请求的回复了");
//                    JSONArray results = response.getJSONArray("result");
//                    List<Discussion> initialDiscussionList = new ArrayList<>();
//                    Gson gson = new GsonBuilder()
//                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                            .create();
//                    LogUtils.d("开始循环");
//                    for (int i = 0; i < results.length(); i ++ ) {
//                        Discussion d = gson.fromJson(results.getJSONObject(i).toString(), Discussion.class);
//                        initialDiscussionList.add(d);
//                    }
//                    LogUtils.d("讨论数:" + initialDiscussionList.size());
//                    EventBus.getDefault().post(new PullDownEvent(initialDiscussionList));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {}
//        });
//    }

    public static void getIdsAndInitialList(int userId, String sortType) {
        // pass second argument as "null" for GET requests
        Map<String, String> params = new HashMap<>();
        params.put("userId", Integer.toString(userId));
        final String url =
                "http://192.168.1.104:3000/home/posts?userId=" + userId + "&sortType=" + sortType;

        LogUtils.i("服务器URL:" + url);
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LogUtils.i("收到请求的回复了");

                    JSONArray idArray = response.getJSONArray("ids");
                    if (idArray != null && idArray.length() != 0) {
                        // 转所有的post id 成数组
                        Integer[] discussionIds = new Gson().fromJson(idArray.toString(), Integer[].class);
                        LogUtils.d(discussionIds.toString());

                        JSONArray initialResult = response.getJSONArray("initial_result");
                        List<Discussion> initialList = new ArrayList<>();
                        Gson gson = new GsonBuilder()
                                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .create();
                        LogUtils.d("开始循环");
                        for (int i = 0; i < initialResult.length(); i ++ ) {
                            Discussion d = gson.fromJson(initialResult.getJSONObject(i).toString(), Discussion.class);
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
                queryString = UrlUtils.joinParams("postId[]", postIds[i].toString());
            } else {
                queryString = queryString + UrlUtils.PARAMETERS_SEPARATOR +
                        UrlUtils.joinParams("postId[]", postIds[i].toString());
            }
        }
        queryString = queryString + UrlUtils.PARAMETERS_SEPARATOR + "sortType=" + sortType;
        final String url = "http://192.168.1.104:3000/home/posts?" + queryString;

        LogUtils.i("服务器URL:" + url);
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LogUtils.i("收到请求的回复了");
                    JSONArray partialResult = response.getJSONArray("partial_result");
                    List<Discussion> partialList = new ArrayList<>();
                    Gson gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create();
                    LogUtils.d("开始循环");
                    for (int i = 0; i < partialResult.length(); i ++ ) {
                        Discussion d = gson.fromJson(partialResult.getJSONObject(i).toString(), Discussion.class);
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
