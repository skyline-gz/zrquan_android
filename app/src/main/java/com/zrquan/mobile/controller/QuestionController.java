package com.zrquan.mobile.controller;

import com.android.volley.VolleyError;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zrquan.mobile.event.Question.PullDownEvent;
import com.zrquan.mobile.event.Question.PullUpEvent;
import com.zrquan.mobile.model.Discussion;
import com.zrquan.mobile.model.Question;
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

public class QuestionController {

    public static void getIdsAndInitialList(int userId, String sortType) {
        // pass second argument as "null" for GET requests
        Map<String, String> params = new HashMap<>();
        params.put("userId", Integer.toString(userId));
        final String url =
                "http://192.168.1.104:3000/home/questions?userId=" + userId + "&sortType=" + sortType;

        LogUtils.i("服务器URL:" + url);
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LogUtils.i("收到请求的回复了");

                    JSONArray idArray = response.getJSONArray("ids");
                    if (idArray != null && idArray.length() != 0) {
                        // 转所有的post id 成数组
                        Integer[] questionIds = new Gson().fromJson(idArray.toString(), Integer[].class);
                        LogUtils.d(questionIds.toString());

                        JSONArray initialResult = response.getJSONArray("initial_result");
                        List<Question> initialList = new ArrayList<>();
                        Gson gson = new GsonBuilder()
                                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .create();
                        LogUtils.d("开始循环");
                        for (int i = 0; i < initialResult.length(); i ++ ) {
                            Question q = gson.fromJson(initialResult.getJSONObject(i).toString(), Question.class);
                            initialList.add(q);
                        }
                        LogUtils.i("讨论数:" + initialList.size());
                        EventBus.getDefault().post(new PullDownEvent(questionIds, initialList));
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
                    List<Question> partialList = new ArrayList<>();
                    Gson gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create();
                    LogUtils.d("开始循环");
                    for (int i = 0; i < partialResult.length(); i ++ ) {
                        Question q = gson.fromJson(partialResult.getJSONObject(i).toString(), Question.class);
                        partialList.add(q);
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
