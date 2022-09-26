package com.shilinwei.videomonitor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.adapter.MyPagerAdapter;
import com.shilinwei.videomonitor.entity.TabEntity;
import com.shilinwei.videomonitor.fragment.DeviceFragment;
import com.shilinwei.videomonitor.fragment.MapFragment;
import com.shilinwei.videomonitor.fragment.MyFragment;
import com.shilinwei.videomonitor.fragment.RecordFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    private String[] mTitles = {"设备", "记录", "地图", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.device, R.mipmap.record,
            R.mipmap.map, R.mipmap.my};
    private int[] mIconSelectIds = {
            R.mipmap.select_device, R.mipmap.select_recode,
            R.mipmap.select_map, R.mipmap.select_my};

//    存放tabs的集合
    private ArrayList<Fragment> fragments = new ArrayList<>();
//    tabs
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ViewPager viewPager;
    private CommonTabLayout commonTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.commonTabLayout);

//        添加fragment
        fragments.add(DeviceFragment.newInstance());
        fragments.add(RecordFragment.newInstance());
        fragments.add(MapFragment.newInstance());
        fragments.add(MyFragment.newInstance());

//        添加tabs
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

//        建立activity和fragment的联系
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments, mTitles));

        commonTabLayout.setTabData(mTabEntities);


//        给tabs添加点击事件
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });


//        页面切换的回调
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}