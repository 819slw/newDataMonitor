package com.shilinwei.videomonitor.api;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.shilinwei.videomonitor.util.StringUtils;



public class Api {
    private static OkHttpClient client;

    private static String requestUrl;
    private static HashMap<String, String> mParams;
    public static Api api = new Api();

    public Api() {

    }

    public static Api config(String url, HashMap<String, String> params) {
        client = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketManager.getSSLSocketFactory())//配置
                .hostnameVerifier(SSLSocketManager.getHostnameVerifier())//配置
                .build();
        requestUrl = ApiConfig.BASE_URl + url;
        mParams = params;
        return api;
    }

//    public void postRequest(Context context, final TtitCallback callback) {
        public void postRequest(final TtitCallback callback) {
//        SharedPreferences sp = context.getSharedPreferences("sp_ttit", MODE_PRIVATE);


//        JSONObject jsonObject = new JSONObject(mParams);
//        String jsonStr = jsonObject.toString();
//        RequestBody requestBodyJson =
//                RequestBody.create(MediaType.parse("application/json;charset=utf-8")
//                        , jsonStr);


            FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
            for (String key : mParams.keySet()) {
                formBody.add(key, mParams.get(key));
            }
            RequestBody body= formBody.build();





        //第三步创建Rquest
        Request request = new Request.Builder()
                .url(requestUrl)
//                .addHeader("contentType", "application/json;charset=UTF-8")
                .addHeader("contentType", "application/x-www-form-urlencoded")
                .addHeader("x-access-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ4enhfbHQiLCJyb2xlX2lkIjoiYWJjZCIsImV4cCI6MTY2NTAzODUyNn0.L13Du-v1uUcp3nD0plMUT_T5SMHrIfUdQ7WyNYomnLc")
//                .post(requestBodyJson)
                .post(body)
                .build();


        //第四步创建call回调对象
        final Call call = client.newCall(request);
        //第五步发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                callback.onSuccess(result);
            }
        });
    }

//    public void getRequest(Context context, final TtitCallback callback) {
        public void getRequest(final TtitCallback callback) {
//        SharedPreferences sp = context.getSharedPreferences("sp_ttit", MODE_PRIVATE);
//        String token = sp.getString("token", "");
        String url = getAppendUrl(requestUrl, mParams);
        Request request = new Request.Builder()
                .url(url)
//                .addHeader("token", token)
                .addHeader("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ4enhfbHQiLCJyb2xlX2lkIjoiYWJjZCIsImV4cCI6MTY2NTAzODUyNn0.L13Du-v1uUcp3nD0plMUT_T5SMHrIfUdQ7WyNYomnLc")
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.getString("code");
                    if (code.equals("401")) {
//                        Intent in = new Intent(context, LoginActivity.class);
//                        context.startActivity(in);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(result);
            }
        });
    }

    private String getAppendUrl(String url, Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> entry = iterator.next();
                if (StringUtils.isEmpty(buffer.toString())) {
                    buffer.append("?");
                } else {
                    buffer.append("&");
                }
                buffer.append(entry.getKey()).append("=").append(entry.getValue());
            }
            url += buffer.toString();
        }
        return url;
    }
}

