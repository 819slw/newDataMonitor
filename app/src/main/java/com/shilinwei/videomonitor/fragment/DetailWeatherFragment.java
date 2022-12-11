package com.shilinwei.videomonitor.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.api.Api;
import com.shilinwei.videomonitor.api.ApiConfig;
import com.shilinwei.videomonitor.api.TtitCallback;
import com.shilinwei.videomonitor.entity.PresetPointEntity;
import com.shilinwei.videomonitor.entity.ThreeDayEntity;
import com.shilinwei.videomonitor.entity.WeatherEntity;

import java.util.HashMap;

public class DetailWeatherFragment extends BaseFragment {


    private String lat;
    private String lng;

    private TextView tvNowTemp;
    private TextView tvNowWord;
    private TextView tvNowFeng;
    private TextView tvNowKejian;
    private ImageView tvNowIcon;

    private TextView tvTodayIcon;
    private TextView tvTomorrowIcon;
    private TextView tvTomorrowrowIcon;

    private ImageView ivTodayIcon;
    private ImageView ivTomorrowIcon;
    private ImageView ivTomorrowrowIcon;



    public DetailWeatherFragment() {
        // Required empty public constructor
    }
    public static DetailWeatherFragment newInstance(String lng,String lat) {
        DetailWeatherFragment fragment = new DetailWeatherFragment();
        Bundle args = new Bundle();
        args.putString("lng", lng);
        args.putString("lat", lat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lng = getArguments().getString("lng");
            lat = getArguments().getString("lat");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_weather, container, false);
        tvNowTemp = v.findViewById(R.id.now_temp);
        tvNowWord = v.findViewById(R.id.tv_now_word);
        tvNowFeng = v.findViewById(R.id.tv_now_feng);
        tvNowKejian = v.findViewById(R.id.tv_now_kejian);
        tvNowIcon = v.findViewById(R.id.iv_now_icon);

        tvTodayIcon = v.findViewById(R.id.tv_today_word);
        tvTomorrowIcon = v.findViewById(R.id.tv_tomorrow_word);
        tvTomorrowrowIcon = v.findViewById(R.id.tv_tomorrowrow_word);


        ivTodayIcon = v.findViewById(R.id.iv_today_icon);
        ivTomorrowIcon = v.findViewById(R.id.iv_tomorrow_icon);
        ivTomorrowrowIcon = v.findViewById(R.id.iv_tomorrowrow_icon);
        getWeatherData();
        getThreeWeatherData();
        return v;
    }

    public void getThreeWeatherData() {

        HashMap<String, String> params = new HashMap<>();
        params.put("key", "326a337c44ef4918b1abb5772db1c192");
        params.put("location", lng +","+ lat);

        Api.config(ApiConfig.threeDayWeather, params).getRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                ThreeDayEntity threeDayEntity = new Gson().fromJson(res, ThreeDayEntity.class);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(threeDayEntity.getDaily() == null) {
                            return;
                        }
                        String todayMin = threeDayEntity.getDaily().get(0).getTempMin();
                        String todayMax = threeDayEntity.getDaily().get(0).getTempMax();
                        tvTodayIcon.setText(todayMin + "度/" + todayMax + "度");
                        setIcon(ivTodayIcon, threeDayEntity.getDaily().get(0).getTextDay());

                        String tomorrowMin = threeDayEntity.getDaily().get(1).getTempMin();
                        String tomorrowMax = threeDayEntity.getDaily().get(1).getTempMax();
                        tvTomorrowIcon.setText(tomorrowMin + "度/" + tomorrowMax + "度");
                        setIcon(ivTomorrowIcon, threeDayEntity.getDaily().get(1).getTextDay());

                        String tomorrowrowMin = threeDayEntity.getDaily().get(2).getTempMin();
                        String tomorrowrowMax = threeDayEntity.getDaily().get(2).getTempMax();
                        tvTomorrowrowIcon.setText(tomorrowrowMin + "度/" + tomorrowrowMax + "度");
                        setIcon(ivTomorrowrowIcon, threeDayEntity.getDaily().get(2).getTextDay());
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                showToastSync("数据获取失败，请重试");
            }
        });
    }

    public void setIcon(ImageView iv, String weather) {
        if(weather.equals("多云") || weather.equals("阴")) {
            iv.setImageResource(R.mipmap.yintian);
        }else if(weather.equals("晴")){
            iv.setImageResource(R.mipmap.qingtian);
        }else {
            iv.setImageResource(R.mipmap.tianqi_yutian);
        }
    }

    public void getWeatherData() {

        HashMap<String, String> params = new HashMap<>();
        params.put("key", "326a337c44ef4918b1abb5772db1c192");
        params.put("location", lng +","+ lat);

        Api.config(ApiConfig.nowWeather, params).getRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                WeatherEntity weatherEntity = new Gson().fromJson(res, WeatherEntity.class);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weatherEntity.getNow() == null) {
                            return;
                        }
                        tvNowTemp.setText(weatherEntity.getNow().getTemp() + "℃");
                        tvNowWord.setText(weatherEntity.getNow().getText());
                        tvNowFeng.setText(weatherEntity.getNow().getWindScale() + "级");
                        tvNowKejian.setText(weatherEntity.getNow().getVis() + "公里");
                        setIcon(tvNowIcon, weatherEntity.getNow().getText());
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                showToastSync("数据获取失败，请重试");
            }
        });
    }

}