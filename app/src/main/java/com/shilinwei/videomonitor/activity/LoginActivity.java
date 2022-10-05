package com.shilinwei.videomonitor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shilinwei.videomonitor.MainActivity;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.api.Api;
import com.shilinwei.videomonitor.api.ApiConfig;
import com.shilinwei.videomonitor.api.TtitCallback;
import com.shilinwei.videomonitor.entity.LoginResponseEntity;
import com.shilinwei.videomonitor.util.MD5Util;
import com.shilinwei.videomonitor.util.StringUtils;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {

    private Button buttonLogin;

    private EditText account;
    private EditText password;

    public Context mContext1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext1 = this;

        buttonLogin = findViewById(R.id.bt_login);
        account = findViewById(R.id.et_account);
        password = findViewById(R.id.et_password);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                检查账号和密码是否已经输入
                String accountVal = account.getText().toString().trim();
                String passwordVal = password.getText().toString().trim();

                if (StringUtils.isEmpty(accountVal)) {
                    showToast("账号不能为空");
                    return;
                }

                if (StringUtils.isEmpty(passwordVal)) {
                    showToast("密码不能为空");
                    return;
                }

                HashMap<String, String> params = new HashMap<>();
                params.put("username", accountVal);
                params.put("password", MD5Util.encode(passwordVal));

                Api.config(ApiConfig.LOGIN, params).postRequest(mContext1,new TtitCallback() {
                    @Override
                    public void onSuccess(String res) {
                        LoginResponseEntity loginResponseEntity = new Gson().fromJson(res, LoginResponseEntity.class);
                        if(loginResponseEntity.getCode() == 200) {
                            String auth_token = loginResponseEntity.getData().getAuth_token();
                            setLocalstorage("token", auth_token);
                            setLocalstorage("account", accountVal);
                            setLocalstorage("userInfo", res);
                            navigateTo(HomeActivity.class);
                            showToastSync("登录成功");
                        }else {
                            showToastSync("登录失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        showToastSync("登录失败，请重试");
                    }
                });

            }
        });
    }
}