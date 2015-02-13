package com.zrquan.mobile.support.util;

import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public class GsonUtils {

    // see Issue 431: Populate existing object
    // https://code.google.com/p/google-gson/issues/detail?id=431
    public static <T> void populate(GsonBuilder builder, JsonObject jsonObject, Class<T> type, final T into) {
        builder.registerTypeAdapter(type, new InstanceCreator<T>() {
            @Override public T createInstance(Type t) { return into; }
        }).create().fromJson(jsonObject, type);
    }
}
