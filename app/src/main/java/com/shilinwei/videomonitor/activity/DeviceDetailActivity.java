package com.shilinwei.videomonitor.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.flyco.tablayout.SlidingTabLayout;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.adapter.MyPagerAdapter;
import com.shilinwei.videomonitor.fragment.DetailControlFragment;
import com.shilinwei.videomonitor.fragment.DetailHistoryFragment;
import com.shilinwei.videomonitor.fragment.DetailPlaybackFragment;
import com.shilinwei.videomonitor.fragment.DetailWeatherFragment;

import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;

import java.util.ArrayList;

public class DeviceDetailActivity extends AppCompatActivity {

    private SurfaceView viewById;
    private EZPlayer player;
    private String deviceSerial;


    private String[] mTitles = {"云控", "天气", "历史", "回放"};
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        deviceSerial =(String) extras.get("deviceSerial");
        EkPlayerInit();
        initTabs();

    }

    public void initTabs() {
        viewPager = findViewById(R.id.viewpager);
        slidingTabLayout = findViewById(R.id.slidingTabLayout);

//        添加fragment
        fragments.add(DetailControlFragment.newInstance());
        fragments.add(DetailWeatherFragment.newInstance());
        fragments.add(DetailHistoryFragment.newInstance());
        fragments.add(DetailPlaybackFragment.newInstance());

//        预加载fragment，防止快速点击时会崩溃
        viewPager.setOffscreenPageLimit(fragments.size());

        //        建立activity和fragment的联系
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments, mTitles));
        slidingTabLayout.setViewPager(viewPager);
    }

    public void EkPlayerInit() {
        viewById = findViewById(R.id.mSv);
        initEk();
        EZAddSvCallBack();
        EZreadyStart();
        EZStart();
    }

    public void initEk() {
        EZOpenSDK.showSDKLog(true);
        EZOpenSDK.enableP2P(false);
        EZOpenSDK.initLib(getApplication(), "21494de78df641d180f9c19be52ee0e9");
        EZOpenSDK.getInstance().setAccessToken("at.7l4uc29h9azb2w8k8f9bg99d88jid3n9-86stq5w3i3-17057sn-y0gxcjbpk");

    }

    public void EZStart() {
        player.startRealPlay();
    }

    public void EZreadyStart() {
        player  = EZOpenSDK.getInstance().createPlayer(deviceSerial,1);
        player.setSurfaceHold(viewById.getHolder());player.startRealPlay();
    }

    private void EZAddSvCallBack(){
        viewById.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if (player != null) {
                    player.setSurfaceHold(holder);
                }
            }
            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                if (player != null) {
                    player.setSurfaceHold(null);
                }
            }
        });
    }


}