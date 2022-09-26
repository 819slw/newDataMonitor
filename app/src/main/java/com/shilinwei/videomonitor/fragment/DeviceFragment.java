package com.shilinwei.videomonitor.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.adapter.MyPagerAdapter;
import com.shilinwei.videomonitor.entity.TabEntity;

import java.util.ArrayList;


public class DeviceFragment extends Fragment {

    private String[] mTitles = {"防外破监测", "视频监测", "图像监测", "布控球"};
    //    存放tabs的集合
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    public DeviceFragment() {
    }

    public static DeviceFragment newInstance() {
        DeviceFragment fragment = new DeviceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_device, container, false);
        viewPager = v.findViewById(R.id.deviceViewPager);
        slidingTabLayout = v.findViewById(R.id.slidingTabLayout);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragments.add(OutDestructionFragment.newInstance());
        fragments.add(VideoFragment.newInstance());
        fragments.add(ImageFragment.newInstance());
        fragments.add(BallFragment.newInstance());

        //        预加载fragment，防止快速点击时会崩溃
        viewPager.setOffscreenPageLimit(fragments.size());

        //        建立activity和fragment的联系
        viewPager.setAdapter(new MyPagerAdapter(getFragmentManager(), fragments, mTitles));
        slidingTabLayout.setViewPager(viewPager);
    }
}