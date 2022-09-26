package com.shilinwei.videomonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shilinwei.videomonitor.activity.HomeActivity;

public class MainActivity extends AppCompatActivity {


    public void goSecond(View v){
        //Intent去设置要跳转的页面
        Intent intent = new Intent(this, HomeActivity.class);
        //跳转
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}