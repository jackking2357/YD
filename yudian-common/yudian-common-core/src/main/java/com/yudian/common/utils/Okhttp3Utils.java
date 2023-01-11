package com.yudian.common.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.apache.commons.collections4.MapUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;


public class Okhttp3Utils {
    private final static OkHttpClient okHttpClient = new OkHttpClient();

    
    public static String get(String url, Map<String, String> headerMap) throws Exception {
        Builder builder = new Builder().url(url);
        if (MapUtils.isNotEmpty(headerMap)) {
            headerMap.forEach((name, value) -> builder.addHeader(name, value));
        }
        Request request = builder.build();
        ResponseBody body = okHttpClient.newCall(request).execute().body();
        return body.string();
    }

    
    public static String getResult(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String string = response.body().string();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map getJSONObjectResult(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String string = response.body().string();
            JSONObject object = JSONObject.parseObject(string);
            Map result = JSONObject.toJavaObject(object, Map.class);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public static String getData(String url, Map<String, String> headerMap) throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        Builder builder = new Builder().url(url);
        if (MapUtils.isNotEmpty(headerMap)) {
            headerMap.forEach((name, value) -> builder.addHeader(name, value));
        }
        Call call = okHttpClient.newCall(builder.build());
        try {
            Response response = call.execute();
            String string = response.body().string();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public static String postData(String url, String json, Map<String, String> headerMap) throws Exception {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Builder builder = new Builder().url(url);
        if (MapUtils.isNotEmpty(headerMap)) {
            headerMap.forEach(builder::addHeader);
        }
        Request request = builder.post(requestBody).build();
        ResponseBody body = okHttpClient.newCall(request).execute().body();
        return resolver(body);
    }

    

    public static String putData(String url, String reqbody, Map<String, String> map) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqbody);
        Builder builder = new Builder().url(url);
        if (MapUtils.isNotEmpty(map)) {
            map.forEach((name, value) -> builder.addHeader(name, value));
        }
        Request request = builder.put(requestBody).build();
        try {
            ResponseBody body = okHttpClient.newCall(request).execute().body();
            return resolver(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    
    public static String deleteData(String url, String reqbody, Map<String, String> map) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Builder builder = new Builder().url(url);
        if (MapUtils.isNotEmpty(map)) {
            map.forEach((name, value) -> builder.addHeader(name, value));
        }
        Call call = okHttpClient.newCall(builder.build());
        try {
            Response response = call.execute();
            String string = response.body().string();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    private static String resolver(ResponseBody responseBody) {
        InputStream is = null;
        String result = null;
        try {
            is = responseBody.byteStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String body = null;
            StringBuilder sb = new StringBuilder();
            while ((body = br.readLine()) != null) {
                sb.append(body);
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}


