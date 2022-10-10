package com.shilinwei.videomonitor.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.api.Api;
import com.shilinwei.videomonitor.api.ApiConfig;
import com.shilinwei.videomonitor.api.TtitCallback;
import com.shilinwei.videomonitor.entity.LoginResponseEntity;
import com.shilinwei.videomonitor.util.MD5Util;

import java.util.HashMap;

public class DeviceInspectActivity extends BaseActivity {

    private String deviceSerial;
    private String deviceName;

    private EditText et_deviceName;

    private CheckBox et_osdName;

    private Button bt_editSendl;
    private Button bt_inspectSend;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_inspect);

        mContext = this;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        deviceSerial =extras.get("deviceSerial").toString();
        deviceName =extras.get("deviceName").toString();


        et_deviceName = findViewById(R.id.et_deviceName);
        et_deviceName.setText(deviceName);

        et_osdName = findViewById(R.id.et_osdName);


        bt_editSendl = findViewById(R.id.bt_editSend);
        bt_editSendl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> params = new HashMap<>();
                params.put("deviceSerial", deviceSerial);
                params.put("deviceName", et_deviceName.getText().toString());
                params.put("osd", et_osdName.isChecked() ? "1" : "0");

                Api.config(ApiConfig.updateDevice, params).postRequest(mContext,new TtitCallback() {
                    @Override
                    public void onSuccess(String res) {
                        showToastSync("更新成功");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        showToastSync("更新失败，请重试");
                    }
                });
            }
        });


        bt_inspectSend = findViewById(R.id.bt_inspectSend);
        bt_inspectSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> params = new HashMap<>();
                params.put("deviceSerial", deviceSerial);
                Api.config(ApiConfig.resetDevice, params).postRequest(mContext,new TtitCallback() {
                    @Override
                    public void onSuccess(String res) {
                        showToastSync("操作成功");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        showToastSync("操作成功，请重试");
                    }
                });
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
}