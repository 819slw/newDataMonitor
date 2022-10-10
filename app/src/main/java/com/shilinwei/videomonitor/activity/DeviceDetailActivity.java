package com.shilinwei.videomonitor.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.adapter.MyPagerAdapter;
import com.shilinwei.videomonitor.api.Api;
import com.shilinwei.videomonitor.api.ApiConfig;
import com.shilinwei.videomonitor.entity.LoginResponseEntity;
import com.shilinwei.videomonitor.fragment.DetailControlFragment;
import com.shilinwei.videomonitor.fragment.DetailHistoryFragment;
import com.shilinwei.videomonitor.fragment.DetailPlaybackFragment;
import com.shilinwei.videomonitor.fragment.DetailWeatherFragment;

import com.shilinwei.videomonitor.util.EZUIPlayerView;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;

import java.util.ArrayList;

public class DeviceDetailActivity extends BaseActivity {

    private EZUIPlayerView viewById;
    private EZPlayer player;
    private String deviceSerial;

    private RelativeLayout play_layout;
    private MyOrientationDetector mOrientationDetector;


    private String lat;
    private String lng;

    private Boolean isLoad = false;


    private String[] mTitles = {"云控", "天气", "历史", "回放"};
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public class MyOrientationDetector extends OrientationEventListener {

        private WindowManager mWindowManager;
        private int mLastOrientation = 0;

        public MyOrientationDetector(Context context) {
            super(context);
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }

        public boolean isWideScrren() {
            Display display = mWindowManager.getDefaultDisplay();
            Point pt = new Point();
            display.getSize(pt);
            System.out.println("------");
            System.out.println(pt.x);
            System.out.println(pt.y);
            System.out.println("------");
            return pt.x > pt.y;
        }

        @Override
        public void onOrientationChanged(int orientation) {
            int value = getCurentOrientationEx(orientation);
            if (value != mLastOrientation) {
                mLastOrientation = value;
                int current = getRequestedOrientation();
                if (current == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        || current == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }
        }

        private int getCurentOrientationEx(int orientation) {
            int value = 0;
            if (orientation >= 315 || orientation < 45) {
                // 0度
                value = 0;
                return value;
            }
            if (orientation >= 45 && orientation < 135) {
                // 90度
                value = 90;
                return value;
            }
            if (orientation >= 135 && orientation < 225) {
                // 180度
                value = 180;
                return value;
            }
            if (orientation >= 225 && orientation < 315) {
                // 270度
                value = 270;
                return value;
            }
            return value;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String isLoadFullScreen = findByKey("isLoadFullScreen");
        if(!isLoadFullScreen.equals("") && isLoadFullScreen.equals("1")) {
            setStatusBarTranslucent(DeviceDetailActivity.this);
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_detail);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        deviceSerial =extras.get("deviceSerial").toString();
        lat =extras.get("lat").toString();
        lng =extras.get("lng").toString();
        EkPlayerInit();
        initTabs();
        mOrientationDetector = new MyOrientationDetector(this);


        play_layout = findViewById(R.id.play_layout);
        play_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isWideScrren = mOrientationDetector.isWideScrren();
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                System.out.println(isWideScrren);
                if(isWideScrren) {
                    setLocalstorage("isLoadFullScreen", "0");
                    viewById.setSurfaceSize(dm.widthPixels, 0);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }else {
                    setLocalstorage("isLoadFullScreen", "1");
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    viewById.setSurfaceSize(dm.widthPixels, dm.heightPixels);
                }
            }
        });
    }

    public static void setStatusBarTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }



    public void initTabs() {
        viewPager = findViewById(R.id.viewpager);
        slidingTabLayout = findViewById(R.id.slidingTabLayout);

//        添加fragment
        fragments.add(DetailControlFragment.newInstance(deviceSerial));
        fragments.add(DetailWeatherFragment.newInstance(lng, lat));
        fragments.add(DetailHistoryFragment.newInstance(deviceSerial));
        fragments.add(DetailPlaybackFragment.newInstance(deviceSerial));

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
        String userInfo = findByKey("userInfo");

        LoginResponseEntity loginResponseEntity = new Gson().fromJson(userInfo, LoginResponseEntity.class);
        String access_token = loginResponseEntity.getData().getAccess_token();
        EZOpenSDK.getInstance().setAccessToken(access_token);
    }

    public void EZStart() {
        player.startRealPlay();
    }

    public void EZreadyStart() {
        player  = EZOpenSDK.getInstance().createPlayer(deviceSerial,1);
        player.setSurfaceHold(viewById.getSurfaceView().getHolder());
    }

    private void EZAddSvCallBack(){
        viewById.getSurfaceView().getHolder().addCallback(new SurfaceHolder.Callback() {
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