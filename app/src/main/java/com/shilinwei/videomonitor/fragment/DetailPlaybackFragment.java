package com.shilinwei.videomonitor.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manu.mdatepicker.MDatePicker;
import com.shilinwei.videomonitor.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.callback.CallbackHandler;

public class DetailPlaybackFragment extends BaseFragment {

    private LinearLayout ll_startTime;
    private LinearLayout ll_endTime;

    private TextView tv_startTime;
    private TextView tv_endTime;
    private Button bt_startPlayer;

    private Long startTime;
    private Long endTime;

    public DetailPlaybackFragment() {
        // Required empty public constructor
    }
    public static DetailPlaybackFragment newInstance() {
        DetailPlaybackFragment fragment = new DetailPlaybackFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_playback, container, false);
        ll_startTime = v.findViewById(R.id.ll_startTime);
        ll_endTime = v.findViewById(R.id.ll_endTime);

        tv_startTime = v.findViewById(R.id.tv_startTime);
        tv_endTime = v.findViewById(R.id.tv_endTime);

        bt_startPlayer = v.findViewById(R.id.bt_startPlayer);


        bt_startPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = tv_startTime.getText().toString();
                String e = tv_endTime.getText().toString();

                if(s.equals("请选择开始时间")) {
                    showToastSync("请选择开始事件");
                    return;
                }

                if(e.equals("请选择结束时间")) {
                    showToastSync("请选择结束时间");
                    return;
                }

                if(startTime >= endTime) {
                    showToastSync("开始时间必须大于结束时间");
                    return;
                }



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
                        if(type == 1) {
                            startTime = date;
                            tv_startTime.setText(formatter.format(date));
                        }else {
                            endTime = date;
                            tv_endTime.setText(formatter.format(date));
                        }
                    }
                })
                .build()
                .show();
    }

}