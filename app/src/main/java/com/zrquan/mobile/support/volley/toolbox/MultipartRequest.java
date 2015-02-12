package com.zrquan.mobile.support.volley.toolbox;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.zrquan.mobile.support.util.FileUtils;
import com.zrquan.mobile.support.util.LogUtils;

public class MultipartRequest extends Request <JSONObject> {
    private final static String LOG_TAG = "MultipartRequest";
    private final static int DEFAULT_SOCKET_TIMEOUT_MS = 10000;
    private final static int DEFAULT_RETRY_TIMES = 0;

    private Response.Listener<JSONObject> mListener;

    private Map<String, String> mStringPartMap;
    private Map<String, File> mFilePartMap;
    private MultipartEntity mMultipartEntity;

    public MultipartRequest(String url, Map<String, String> stringPartMap, Map<String, File> filePartMap,
                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mStringPartMap = stringPartMap;
        mFilePartMap = filePartMap;
        mMultipartEntity = new MultipartEntity();
        buildMultipartEntity();

        setRetryPolicy(new DefaultRetryPolicy( DEFAULT_SOCKET_TIMEOUT_MS, DEFAULT_RETRY_TIMES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        setShouldCache(false);
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
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
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
            File file = fileEntry.getValue();
            mMultipartEntity.addPart(fileEntry.getKey(), new FileBody(file, FileUtils.getMimeType(file.getPath())));
        }
    }
}
