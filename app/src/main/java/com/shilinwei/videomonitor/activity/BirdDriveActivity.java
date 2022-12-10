package com.shilinwei.videomonitor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.adapter.MyPagerAdapter;
import com.shilinwei.videomonitor.fragment.BirdDriveConfigFragment;
import com.shilinwei.videomonitor.fragment.BirdDriveControlFragment;
import com.shilinwei.videomonitor.fragment.DetailPlaybackFragment;
import com.shilinwei.videomonitor.fragment.DetailWeatherFragment;

import java.util.ArrayList;

public class BirdDriveActivity extends AppCompatActivity {
    private ImageView birdDriveImg;
    private SlidingTabLayout slidingTabLayout;
    private String[] mTitles = {"控制","天气","配置","历史"};
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String deviceSerial;
    private String deviceName;

    private String lng;
    private String lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bird_drive);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        deviceSerial = extras.get("deviceSerial").toString();
        deviceName = extras.get("deviceName").toString();
        lat = extras.get("lat").toString();
        lng = extras.get("lng").toString();
        initTabs();
    }

    private void initTabs(){
        viewPager = findViewById(R.id.viewpager);
        slidingTabLayout = findViewById(R.id.slidingTabLayout);

//        添加fragment
        fragments.add(BirdDriveControlFragment.newInstance(deviceSerial));
        fragments.add(DetailWeatherFragment.newInstance(lng,lat));
        fragments.add(BirdDriveConfigFragment.newInstance(deviceSerial));
        fragments.add(DetailPlaybackFragment.newInstance(deviceSerial));

        viewPager.setOffscreenPageLimit(fragments.size());

        viewPager.setAdapter( new MyPagerAdapter(getSupportFragmentManager(),fragments,mTitles));
        slidingTabLayout.setViewPager(viewPager);
    }
}