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
import java.util.HashMap;
import java.util.List;

public class DetailHistoryFragment extends BaseFragment {

    private String deviceSerial;
    //        告警部分

    private LinearLayout ll_startTime;
    private LinearLayout ll_endTime;

    private TextView tv_startTime;
    private TextView tv_endTime;

    private Long startTime;
    private Long endTime;

    private Button bt_startPlayer;
    private LinearLayout ll_box;

    private ArrayList<PreviewFile> logPreviewFiles;
//    private int logIndex;


    //        定时部分

    private LinearLayout ll_startTime1;
    private LinearLayout ll_endTime1;

    private TextView tv_startTime1;
    private TextView tv_endTime1;

    private Long startTime1;
    private Long endTime1;

    private Button bt_startPlayer1;
    private LinearLayout ll_box1;
    private ArrayList<PreviewFile> alarmPreviewFiles;
//    private int alarmIndex;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_history, container, false);
//        告警部分

        ll_startTime = v.findViewById(R.id.ll_startTime);
        ll_endTime = v.findViewById(R.id.ll_endTime);

        tv_startTime = v.findViewById(R.id.tv_startTime);
        tv_endTime = v.findViewById(R.id.tv_endTime);
        bt_startPlayer = v.findViewById(R.id.bt_startPlayer);
        ll_box = v.findViewById(R.id.ll_box);



        ll_startTime1 = v.findViewById(R.id.ll_startTime1);
        ll_endTime1 = v.findViewById(R.id.ll_endTime1);

        tv_startTime1 = v.findViewById(R.id.tv_startTime1);
        tv_endTime1 = v.findViewById(R.id.tv_endTime1);
        bt_startPlayer1 = v.findViewById(R.id.bt_startPlayer1);
        ll_box1 = v.findViewById(R.id.ll_box1);


        //        定时部分

        bt_startPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = tv_startTime1.getText().toString();
                String e = tv_endTime1.getText().toString();

                if (s.equals("请选择开始时间")) {
                    showToastSync("请选择开始事件");
                    return;
                }

                if (e.equals("请选择结束时间")) {
                    showToastSync("请选择结束时间");
                    return;
                }

                if (startTime1 >= endTime1) {
                    showToastSync("开始时间必须大于结束时间");
                    return;
                }
                // 调用接口
                getLogList1();
            }
        });

        ll_startTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimerDialog(3);
            }
        });

        ll_endTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimerDialog(4);
            }
        });


        //        告警部分

        bt_startPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = tv_startTime.getText().toString();
                String e = tv_endTime.getText().toString();

                if (s.equals("请选择开始时间")) {
                    showToastSync("请选择开始事件");
                    return;
                }

                if (e.equals("请选择结束时间")) {
                    showToastSync("请选择结束时间");
                    return;
                }

                if (startTime >= endTime) {
                    showToastSync("开始时间必须大于结束时间");
                    return;
                }
                // 调用接口
                getLogList();
            }
        });

        ll_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimerDialog(1);
            }
        });

        ll_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimerDialog(2);
            }
        });
        return v;
    }

    public void openTimerDialog(int type) {
        MDatePicker.create(getActivity())
                //附加设置(非必须,有默认值)
                .setCanceledTouchOutside(true)
                .setGravity(Gravity.BOTTOM)
                .setSupportTime(true)
                .setTwelveHour(true)
                //结果回调(必须)
                .setOnDateResultListener(new MDatePicker.OnDateResultListener() {
                    @Override
                    public void onDateResult(long date) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        if (type == 1) {
                            startTime = date;
                            tv_startTime.setText(formatter.format(date));
                        } else if(type == 2) {
                            endTime = date;
                            tv_endTime.setText(formatter.format(date));
                        }else if (type == 3) {
                            startTime1 = date;
                            tv_startTime1.setText(formatter.format(date));
                        } else if(type == 4) {
                            endTime1 = date;
                            tv_endTime1.setText(formatter.format(date));
                        }
                    }
                })
                .build()
                .show();
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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