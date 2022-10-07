package com.shilinwei.videomonitor.api;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.shilinwei.videomonitor.activity.BaseActivity;
import com.shilinwei.videomonitor.activity.LoginActivity;
import com.shilinwei.videomonitor.util.StringUtils;



public class Api extends BaseActivity {
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
        if(url.indexOf("weather") > -1) {
            requestUrl = ApiConfig.BASE_URl1 + url;
        }else {
            requestUrl = ApiConfig.BASE_URl + url;
        }
        mParams = params;
        return api;
    }

    public void postRequest(Context context, final TtitCallback callback) {
        SharedPreferences sp = context.getSharedPreferences("sp_ttit", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        if(token == null || token.length() <=0 && requestUrl.indexOf(ApiConfig.LOGIN) == -1) {
            navigateTo(LoginActivity.class);
        }

        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        for (String key : mParams.keySet()) {
            formBody.add(key, mParams.get(key));
        }
        RequestBody body= formBody.build();

        //第三步创建Rquest
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("contentType", "application/x-www-form-urlencoded")
                .addHeader("x-access-token", token)
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

    public void getRequest(Context context, final TtitCallback callback) {

        String url = getAppendUrl(requestUrl, mParams);
        Request request = new Request.Builder()
                .url(url)
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

