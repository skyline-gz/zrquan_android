package com.zrquan.mobile.support.volley.toolbox;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.zrquan.mobile.support.util.LogUtils;

public class MultipartRequest<T> extends Request <T> {
    private final static String LOG_TAG = "MultipartRequest";

    private Response.Listener<T> mListener;

    private Map<String, String> mStringPartMap;
    private Map<String, File> mFilePartMap;
    private HttpEntity mHttpEntity;

    public MultipartRequest(String url, Map<String, String> stringPartMap, Map<String, File> filePartMap,
                            Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mStringPartMap = stringPartMap;
        mFilePartMap = filePartMap;
        buildMultipartEntity();
    }

    @Override
    public String getBodyContentType() {
        return mHttpEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mHttpEntity.writeTo(bos);
            String entityContentAsString = new String(bos.toByteArray());
            LogUtils.d(LOG_TAG, entityContentAsString);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    private void buildMultipartEntity() {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        for (Map.Entry<String, String> stringEntry : mStringPartMap.entrySet()) {
            multipartEntityBuilder.addTextBody(stringEntry.getKey(), stringEntry.getValue());
        }
        for (Map.Entry<String, File> fileEntry : mFilePartMap.entrySet()) {
            multipartEntityBuilder.addBinaryBody(fileEntry.getKey(), fileEntry.getValue());
        }
        mHttpEntity = multipartEntityBuilder.build();
    }
}
