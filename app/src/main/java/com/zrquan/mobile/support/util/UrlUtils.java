package com.zrquan.mobile.support.util;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class UrlUtils {
    /** url and para separator **/
    public static final String URL_AND_PARA_SEPARATOR = "?";
    /** parameters separator **/
    public static final String PARAMETERS_SEPARATOR   = "&";
    /** paths separator **/
    public static final String PATHS_SEPARATOR        = "/";
    /** equal sign **/
    public static final String EQUAL_SIGN             = "=";
    public static final String HOST = "127.0.0.1";
    public static final String HOST_HTTP = "http://" + HOST;

    private static final String TAG = "NetUtils";

    /**
     * join url and params
     *
     * <pre>
     * getUrlWithParams(null, {(a, b)})                        =   "?a=b";
     * getUrlWithParams("baidu.com", {})                       =   "baidu.com";
     * getUrlWithParams("baidu.com", {(a, b), (i, j)})         =   "baidu.com?a=b&i=j";
     * getUrlWithParams("baidu.com", {(a, b), (i, j), (c, d)}) =   "baidu.com?a=b&i=j&c=d";
     * </pre>
     *
     * @param url url
     * @param paramsMap params map, key is para name, value is para value
     * @return if url is null, process it as empty string
     */
    public static String getUrlWithParams(String url, Map<String, String> paramsMap) {
        StringBuilder urlWithParams = new StringBuilder();
        urlWithParams.append(HOST_HTTP).append("/").append(StringUtils.isEmpty(url) ? "" : url);
        String params = joinParams(paramsMap);
        if (!StringUtils.isEmpty(params)) {
            urlWithParams.append(URL_AND_PARA_SEPARATOR).append(params);
        }
        return urlWithParams.toString();
    }

    /**
     * join url and encoded params
     *
     * @param url
     * @param paramsMap
     * @return
     * @see #getUrlWithParams(String, Map)
     * @see StringUtils#utf8Encode(String)
     */
    public static String getUrlWithValueEncodeParams(String url, Map<String, String> paramsMap) {
        StringBuilder urlWithParams = new StringBuilder();
        urlWithParams.append(HOST_HTTP).append("/").append(StringUtils.isEmpty(url) ? "" : url);
        String params = joinParamsWithEncodedValue(paramsMap);
        if (!StringUtils.isEmpty(params)) {
            urlWithParams.append(URL_AND_PARA_SEPARATOR).append(params);
        }
        return urlWithParams.toString();
    }

    /**
     * join params
     *
     * @param paramsMap params map, key is para name, value is para value
     * @return join key and value with {@link #EQUAL_SIGN}, join keys with {@link #PARAMETERS_SEPARATOR}
     */
    public static String joinParams(Map<String, String> paramsMap) {
        if (paramsMap == null || paramsMap.size() == 0) {
            return null;
        }

        StringBuilder params = new StringBuilder();
        Iterator<Map.Entry<String, String>> ite = paramsMap.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>)ite.next();
            params.append(entry.getKey()).append(EQUAL_SIGN).append(entry.getValue());
            if (ite.hasNext()) {
                params.append(PARAMETERS_SEPARATOR);
            }
        }
        return params.toString();
    }

    /**
     * join params with encoded value
     *
     * @param paramsMap
     * @return joined params string
     * @see #joinParams(Map)
     * @see StringUtils#utf8Encode(String)
     */
    public static String joinParamsWithEncodedValue(Map<String, String> paramsMap) {
        StringBuilder params = new StringBuilder("");
        if (paramsMap != null && paramsMap.size() > 0) {
            Iterator<Map.Entry<String, String>> ite = paramsMap.entrySet().iterator();
            try {
                while (ite.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>)ite.next();
                    params.append(entry.getKey()).append(EQUAL_SIGN).append(StringUtils.utf8Encode(entry.getValue()));
                    if (ite.hasNext()) {
                        params.append(PARAMETERS_SEPARATOR);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return params.toString();
    }

    /**
     * append a key and value pair to url
     *
     * @param url
     * @param paraKey
     * @param paraValue
     * @return
     */
    public static String appendParaToUrl(String url, String paraKey, String paraValue) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        if (!url.contains(URL_AND_PARA_SEPARATOR)) {
            sb.append(URL_AND_PARA_SEPARATOR);
        } else {
            sb.append(PARAMETERS_SEPARATOR);
        }
        return sb.append(paraKey).append(EQUAL_SIGN).append(paraValue).toString();
    }
}
