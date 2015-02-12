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
    private MultipartEntity mMultipartEntity;

    public MultipartRequest(String url, Map<String, String> stringPartMap, Map<String, File> filePartMap,
                            Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mStringPartMap = stringPartMap;
        mFilePartMap = filePartMap;
        mMultipartEntity = new MultipartEntity();
        buildMultipartEntity();
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
        try {
            for (Map.Entry<String, String> stringEntry : mStringPartMap.entrySet()) {
                mMultipartEntity.addPart(stringEntry.getKey(), new StringBody(stringEntry.getValue()));
            }
        } catch (UnsupportedEncodingException e) {
            VolleyLog.e("UnsupportedEncodingException");
        }

        for (Map.Entry<String, File> fileEntry : mFilePartMap.entrySet()) {
            mMultipartEntity.addPart(fileEntry.getKey(), new FileBody(fileEntry.getValue()));
        }
    }
}
