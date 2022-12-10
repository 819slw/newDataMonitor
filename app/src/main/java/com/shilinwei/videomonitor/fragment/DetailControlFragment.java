package com.shilinwei.videomonitor.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.activity.HomeActivity;
import com.shilinwei.videomonitor.api.Api;
import com.shilinwei.videomonitor.api.ApiConfig;
import com.shilinwei.videomonitor.api.TtitCallback;
import com.shilinwei.videomonitor.entity.LoginResponseEntity;
import com.shilinwei.videomonitor.entity.PresetPointEntity;
import com.shilinwei.videomonitor.util.MD5Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailControlFragment extends BaseFragment {

    private Spinner spinner;
    private String deviceSerial;
    private ArrayAdapter<String> adapter;
    private int positonSelect = 0;
    private PresetPointEntity presetPointEntity;
    private EditText editText;
    private Button usePresetBtn;
    private Button delPresetBtn;
    private Button addPresetBtn;
    private RelativeLayout addViewTl8;
    private RelativeLayout yugua1;
    private RelativeLayout zuoshang4;
    private RelativeLayout shang0;
    private RelativeLayout youshang6;
    private RelativeLayout suoxiao9;
    private RelativeLayout zuoer2;
    private RelativeLayout mic9;
    private RelativeLayout you3;
    private RelativeLayout zuoxia5;
    private RelativeLayout xia1;
    private RelativeLayout youxia7;

    public DetailControlFragment() {
        // Required empty public constructor
    }

    public static DetailControlFragment newInstance(String deviceSerial) {
        DetailControlFragment fragment = new DetailControlFragment();
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

    public void usePreset() {
        int preset_index = presetPointEntity.getData().get(positonSelect).getPreset_index();
        HashMap<String, String> params = new HashMap<>();
        params.put("deviceSerial", deviceSerial);
        params.put("preset_index", Integer.toString(preset_index));
        useRequest(ApiConfig.UsePreset, params);
    }

    public void usePreset1(String index) {
        HashMap<String, String> params = new HashMap<>();
        params.put("deviceSerial", deviceSerial);
        params.put("preset_index", index);
        useRequest(ApiConfig.UsePreset, params);
    }

    public void initEvent() {
        usePresetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usePreset();
            }
        });

        delPresetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delPreset();
            }
        });

        addPresetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPreset();
            }
        });

        yugua1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usePreset1("1");
            }
        });

        addViewTl8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        moveEndPreset("8");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("按下");
                        moveStartPreset("8");
                        break;
                    default:break;
                }
                return true;
            }
        });


        zuoshang4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        moveEndPreset("4");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("按下");
                        moveStartPreset("4");
                        break;
                    default:break;
                }
                return true;
            }
        });

        shang0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        moveEndPreset("0");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("按下");
                        moveStartPreset("0");
                        break;
                    default:break;
                }
                return true;
            }
        });

        youshang6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        moveEndPreset("6");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("按下");
                        moveStartPreset("6");
                        break;
                    default:break;
                }
                return true;
            }
        });

        suoxiao9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        moveEndPreset("9");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("按下");
                        moveStartPreset("9");
                        break;
                    default:break;
                }
                return true;
            }
        });

        zuoer2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        moveEndPreset("2");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("按下");
                        moveStartPreset("2");
                        break;
                    default:break;
                }
                return true;
            }
        });

        you3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        moveEndPreset("3");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("按下");
                        moveStartPreset("3");
                        break;
                    default:break;
                }
                return true;
            }
        });

        zuoxia5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        moveEndPreset("5");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("按下");
                        moveStartPreset("5");
                        break;
                    default:break;
                }
                return true;
            }
        });

        xia1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        moveEndPreset("1");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("按下");
                        moveStartPreset("1");
                        break;
                    default:break;
                }
                return true;
            }
        });

        youxia7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        moveEndPreset("7");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("按下");
                        moveStartPreset("7");
                        break;
                    default:break;
                }
                return true;
            }
        });

        mic9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        moveEndPreset("9");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("按下");
                        moveStartPreset("9");
                        break;
                    default:break;
                }
                return true;
            }
        });

    }

    public void moveStartPreset(String index) {
        HashMap<String, String> params = new HashMap<>();
        params.put("deviceSerial", deviceSerial);
        params.put("direction", index);
        useRequest(ApiConfig.MoveStartPreset, params);
    }

    public void moveEndPreset(String index) {
        HashMap<String, String> params = new HashMap<>();
        params.put("deviceSerial", deviceSerial);
        params.put("direction", index);
        useRequest(ApiConfig.MoveEndPreset, params);
    }

    public void addPreset() {
        String preset_name = editText.getText().toString().trim();
        if(preset_name.length() > 0) {
            HashMap<String, String> params = new HashMap<>();
            params.put("deviceSerial", deviceSerial);
            params.put("preset_name", preset_name);
            useRequest(ApiConfig.AddPreset, params);
        }else {
            showToastSync("预置点名称不能为空");
        }
    }

    public void delPreset() {
        int preset_index = presetPointEntity.getData().get(positonSelect).getPreset_index();
        HashMap<String, String> params = new HashMap<>();
        params.put("deviceSerial", deviceSerial);
        params.put("preset_index", Integer.toString(preset_index));
        useRequest(ApiConfig.DelPreset, params);
    }

    public void useRequest(String url, HashMap params) {
        Api.config(url, params).postRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                if(url == ApiConfig.AddPreset || url == ApiConfig.DelPreset) {
                    initSpinner();
                }
                showToastSync("操作成功");
            }

            @Override
            public void onFailure(Exception e) {
                showToastSync("操作失败，请重试");
            }
        });
    }

    public void initSpinner() {
        HashMap<String, String> params = new HashMap<>();
        params.put("deviceSerial", deviceSerial);
        Api.config(ApiConfig.PresetList, params).postRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                presetPointEntity = new Gson().fromJson(res, PresetPointEntity.class);
                System.out.println(presetPointEntity);
                if(presetPointEntity == null) {
                   return;
                }
                List<String> presetArr = new ArrayList<>();

                for (PresetPointEntity.DataDTO datum : presetPointEntity.getData()) {
                    presetArr.add(datum.getPreset_name());
                }
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, presetArr);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                showToastSync("登录失败，请重试");
            }
        });

    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            positonSelect = arg2;
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_control, container, false);
        spinner = v.findViewById(R.id.sp_select);
        editText = v.findViewById(R.id.et_search);
        usePresetBtn = v.findViewById(R.id.bt_usePreset);
        delPresetBtn = v.findViewById(R.id.bt_delPreset);
        addPresetBtn = v.findViewById(R.id.bt_addPreset);
        yugua1 = v.findViewById(R.id.rl_yugua1);
        addViewTl8 = v.findViewById(R.id.rl_addView8);
        zuoshang4 = v.findViewById(R.id.rl_zuoshang4);
        shang0 = v.findViewById(R.id.rl_shang0);

        youshang6 = v.findViewById(R.id.rl_youshang6);
        suoxiao9 = v.findViewById(R.id.rl_suoxiao9);
        zuoer2 = v.findViewById(R.id.rl_zuoer2);
        mic9 = v.findViewById(R.id.rl_mic9);
        you3 = v.findViewById(R.id.rl_you3);
        zuoxia5 = v.findViewById(R.id.rl_zuoxia5);
        xia1 = v.findViewById(R.id.rl_xia1);
        youxia7 = v.findViewById(R.id.rl_youxia7);

        initSpinner();
        initEvent();
        return v;
    }
}