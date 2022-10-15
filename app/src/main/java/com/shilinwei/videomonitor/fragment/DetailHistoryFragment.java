package com.shilinwei.videomonitor.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.himangi.imagepreview.ImagePreviewActivity;
import com.himangi.imagepreview.PreviewFile;
import com.manu.mdatepicker.MDatePicker;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.adapter.RecordListAdapter;
import com.shilinwei.videomonitor.api.Api;
import com.shilinwei.videomonitor.api.ApiConfig;
import com.shilinwei.videomonitor.api.TtitCallback;
import com.shilinwei.videomonitor.entity.LogListEntity;
import com.shilinwei.videomonitor.entity.LogListResponseEntity;
import com.shilinwei.videomonitor.entity.PresetPointEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DetailHistoryFragment extends BaseFragment {

    private String deviceSerial;
    //        告警部分

    private String tv_startTime;
    private String tv_endTime;

    private Long startTime;
    private Long endTime;

    private LinearLayout ll_box;

    private ArrayList<PreviewFile> logPreviewFiles;
    private TextView tv_nodata;

    private ImageView iv_alarm_time;
    private ImageView iv_log_time;


    //        定时部分


    private String tv_startTime1;
    private String tv_endTime1;

    private Long startTime1;
    private Long endTime1;

    private LinearLayout ll_box1;
    private ArrayList<PreviewFile> alarmPreviewFiles;

    private TextView tv_nodata1;

    public DetailHistoryFragment() {
    }

    public static DetailHistoryFragment newInstance(String deviceSerial) {
        DetailHistoryFragment fragment = new DetailHistoryFragment();
        Bundle args = new Bundle();
        args.putString("deviceSerial", deviceSerial);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deviceSerial = getArguments().getString("deviceSerial");
        }

        startTime = new Date().getTime() - 24 * 60 *60*1000;
        endTime = new Date().getTime();

        startTime1 = new Date().getTime() - 24 * 60 *60*1000;
        endTime1 = new Date().getTime();
        getLogList();
        getLogList1();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_history, container, false);
//        告警部分

        ll_box = v.findViewById(R.id.ll_box);
        tv_nodata = v.findViewById(R.id.tv_nodata);

        iv_alarm_time = v.findViewById(R.id.iv_alarm_time);
        iv_log_time = v.findViewById(R.id.iv_log_time);


        iv_alarm_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimerDialog(1);
            }
        });

        iv_log_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimerDialog(3);
            }
        });

        ll_box1 = v.findViewById(R.id.ll_box1);
        tv_nodata1 = v.findViewById(R.id.tv_nodata1);
        return v;
    }

    public void openTimerDialog(int type) {
        MDatePicker.create(getActivity())
                //附加设置(非必须,有默认值)
                .setCanceledTouchOutside(true)
                .setGravity(Gravity.BOTTOM)
                .setSupportTime(true)
                .setTwelveHour(true)
                .setTitle(type % 2 == 0 ? "结束时间" : "开始时间" )
                //结果回调(必须)
                .setOnDateResultListener(new MDatePicker.OnDateResultListener() {
                    @Override
                    public void onDateResult(long date) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        if (type == 1) {
                            startTime = date;
                            tv_startTime = formatter.format(date);
                            openTimerDialog(2);
                        } else if(type == 2) {
                            endTime = date;
                            tv_endTime = formatter.format(date);
                            beginSendAlarmSearch();
                        }else if (type == 3) {
                            startTime1 = date;
                            tv_startTime1 = formatter.format(date);
                            openTimerDialog(4);
                        } else if(type == 4) {
                            endTime1 = date;
                            tv_endTime1 = formatter.format(date);
                            beginSendLogSearch();
                        }
                    }
                })
                .build()
                .show();
    }

    public void beginSendLogSearch() {
        String s = tv_startTime1;
        String e = tv_endTime1;

        if (s.equals("请选择开始时间")) {
            showToast("请选择开始事件");
        }else if (e.equals("请选择结束时间")) {
            showToast("请选择结束时间");
        }else if (startTime1 >= endTime1) {
            showToast("开始时间必须大于结束时间");
        }else {
            // 调用接口
            getLogList1();
        }
    }

    public void beginSendAlarmSearch() {
        String s = tv_startTime;
        String e = tv_endTime;

        if (s.equals("请选择开始时间")) {
            showToast("请选择开始事件");
        }else if (e.equals("请选择结束时间")) {
            showToast("请选择结束时间");
        }else if (startTime >= endTime) {
            showToast("开始时间必须大于结束时间");
        }else {
            // 调用接口
            getLogList();
        }
    }

    public void getLogList () {
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        String format = f.format(startTime);
        String format1 = f.format(endTime);

        HashMap<String, String> params = new HashMap<>();
        params.put("deviceSerial", deviceSerial);
        params.put("start", format);
        params.put("end", format1);
        Api.config(ApiConfig.getAlarmByDeviceSerialWithTime, params).postRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                LogListResponseEntity list = new Gson().fromJson(res, LogListResponseEntity.class);
                if(list != null) {
                    List<LogListEntity> list1 = list.getData().getList();
                    if (list1 == null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_nodata.setTextColor(getResources().getColor(R.color.white));
                                ll_box.removeAllViews();
                            }});
//                        showToastSync("查询数据为空");
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ll_box.removeAllViews();

                            tv_nodata.setTextColor(getResources().getColor(R.color.purple_200));
                            int alarmIndex = -1;
                            alarmPreviewFiles = new ArrayList<>();
                            for (int i = 0; i < list1.size(); i++) {
                                String deviceName = list1.get(i).getDeviceName();
                                for (int j = 0; j < list1.get(i).getDetail().size(); j++) {
                                    String pic_url = list1.get(i).getDetail().get(j).getPic_url();
                                    ImageView image = new ImageView(getActivity());
                                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(400, 200);
                                    image.setLayoutParams(layoutParams);
                                    if(pic_url == null || "".equals(pic_url)) {
                                        continue;
                                    }
                                    Glide.with(getActivity()).load(pic_url).into(image);
                                    ll_box.addView(image);
                                    alarmPreviewFiles.add(new PreviewFile(pic_url,deviceName));
                                    alarmIndex++;
                                    int finalAlarmIndex = alarmIndex + 1;
                                    image.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            previewImage(alarmPreviewFiles, finalAlarmIndex);
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
                showToastSync("请求失败，请重试");
            }
        });
    }


    public void getLogList1() {
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        String format = f.format(startTime1);
        String format1 = f.format(endTime1);

        HashMap<String, String> params = new HashMap<>();
        params.put("deviceSerial", deviceSerial);
        params.put("start", format);
        params.put("end", format1);
        Api.config(ApiConfig.getLogByDeviceSerialWithTime, params).postRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                LogListResponseEntity list = new Gson().fromJson(res, LogListResponseEntity.class);
                if(list != null) {
                    List<LogListEntity> list1 = list.getData().getList();
                    if(list1 == null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_nodata1.setTextColor(getResources().getColor(R.color.white));
                                ll_box1.removeAllViews();
                            }});
//                        showToastSync("查询数据为空");
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ll_box1.removeAllViews();

                            tv_nodata1.setTextColor(getResources().getColor(R.color.purple_200));
                            int logIndex = -1;
                            logPreviewFiles = new ArrayList<>();
                            for (int i = 0; i < list1.size(); i++) {
                                String deviceName = list1.get(i).getDeviceName();
                                for (int j = 0; j < list1.get(i).getDetail().size(); j++) {
                                    ImageView image = new ImageView(getActivity());
                                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(400, 200);
                                image.setLayoutParams(layoutParams);
                                String pic_url = list1.get(i).getDetail().get(0).getPic_url();
                                if(pic_url == null || "".equals(pic_url)) {
                                    continue;
                                }
                                Glide.with(getActivity()).load(pic_url).into(image);
                                ll_box1.addView(image);

                                logPreviewFiles.add(new PreviewFile(pic_url,deviceName));
                                    logIndex++;
                                    int finalLogIndex = logIndex + 1;
                                image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        previewImage(logPreviewFiles, finalLogIndex);
                                    }
                                });
                                }
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
                showToastSync("请求失败，请重试");
            }
        });
    }

}