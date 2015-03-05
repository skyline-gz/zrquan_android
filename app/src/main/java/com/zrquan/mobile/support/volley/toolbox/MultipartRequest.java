package com.zrquan.mobile.support.volley.toolbox;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.zrquan.mobile.support.util.AndroidIOUtils;
import com.zrquan.mobile.support.util.LogUtils;

public class MultipartRequest extends Request<JsonObject> {
    private final static String LOG_TAG = "MultipartRequest";

    private final static int DEFAULT_SOCKET_TIMEOUT_MS = 20000;
    private final static int DEFAULT_RETRY_TIMES = 0;

    private Response.Listener<JsonObject> mListener;

    private Map<String, String> mStringPartMap;
    private Map<String, File> mFilePartMap;
    private MultipartEntity mMultipartEntity;

    public MultipartRequest(String url, Map<String, String> stringPartMap, Map<String, File> filePartMap,
                            Response.Listener<JsonObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mStringPartMap = stringPartMap;
        mFilePartMap = filePartMap;
        mMultipartEntity = new MultipartEntity();
        buildMultipartEntity();

        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_SOCKET_TIMEOUT_MS, DEFAULT_RETRY_TIMES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public String getBodyContentType() {
        return mMultipartEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mMultipartEntity.writeTo(bos);
            String entityContentAsString = new String(bos.toByteArray());
            LogUtils.d(LOG_TAG, entityContentAsString);
        } catch (IOException e) {
            LogUtils.e(LOG_TAG, "IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected void deliverResponse(JsonObject response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<JsonObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    private void buildMultipartEntity() {
        try {
            for (Map.Entry<String, String> stringEntry : mStringPartMap.entrySet()) {
                mMultipartEntity.addPart(stringEntry.getKey(), new StringBody(stringEntry.getValue()));
            }
        } catch (UnsupportedEncodingException e) {
            LogUtils.e(LOG_TAG, "UnsupportedEncodingException");
        }

        for (Map.Entry<String, File> fileEntry : mFilePartMap.entrySet()) {
            File file = fileEntry.getValue();
            mMultipartEntity.addPart(fileEntry.getKey(), new FileBody(file, AndroidIOUtils.getMimeType(file.getPath())));
        }
    }
}
