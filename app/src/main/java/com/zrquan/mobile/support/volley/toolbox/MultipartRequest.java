package com.zrquan.mobile.support.volley.toolbox;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

public class MultipartRequest<T> extends Request <T> {
    private Response.Listener<T> mListener;

    private Map<String, String> mStringMap;
    private Map<String, File> mFileMap;
    private HttpEntity mHttpEntity;

    public MultipartRequest(String url, Response.Listener<T> listener, Response.ErrorListener errorListener,
                            Map<String, String> stringMap, Map<String, File> fileMap) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mFileMap = fileMap;
        mStringMap = stringMap;
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
            Log.e("volley", entityContentAsString);
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
        for (Map.Entry<String, String> stringEntry : mStringMap.entrySet()) {
            multipartEntityBuilder.addTextBody(stringEntry.getKey(), stringEntry.getValue());
        }
        for (Map.Entry<String, File> fileEntry : mFileMap.entrySet()) {
            multipartEntityBuilder.addBinaryBody(fileEntry.getKey(), fileEntry.getValue());
        }
        mHttpEntity = multipartEntityBuilder.build();
    }
}
