package com.shilinwei.videomonitor.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.adapter.OutDestructionAdapter;
import com.shilinwei.videomonitor.adapter.RecordListAdapter;
import com.shilinwei.videomonitor.api.Api;
import com.shilinwei.videomonitor.api.ApiConfig;
import com.shilinwei.videomonitor.api.TtitCallback;
import com.shilinwei.videomonitor.entity.LogListEntity;
import com.shilinwei.videomonitor.entity.LogListResponseEntity;
import com.shilinwei.videomonitor.entity.LoginResponseEntity;
import com.shilinwei.videomonitor.entity.OutDestructionEntity;
import com.shilinwei.videomonitor.entity.OutDestructionResponseEntity;

import java.util.HashMap;
import java.util.List;


public class RecordFragment extends BaseFragment {

    RecyclerView recyclerView = null;
    public Context mContext;

    public RecordFragment() {
    }

    public static RecordFragment newInstance() {
        RecordFragment fragment = new RecordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //        因为List列表是线性布局，所以先new一个线性布局类
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        设置布局
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //        给recyclerView设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);

        getRecordList();
    }

    public void getRecordList() {
        HashMap<String, String> params = new HashMap<>();
        String userInfo = findByKey("userInfo");
        LoginResponseEntity loginResponseEntity = new Gson().fromJson(userInfo, LoginResponseEntity.class);
        String depart_id = loginResponseEntity.getData().getDepart_id();
        params.put("depart_id", depart_id);
        params.put("page", "1");
        params.put("push_type", "1");
        params.put("pageSize", "30");


        Api.config(ApiConfig.GetLogList, params).postRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                LogListResponseEntity list = new Gson().fromJson(res, LogListResponseEntity.class);
                if(list != null) {
                    List<LogListEntity> list1 = list.getData().getList();
                    RecordListAdapter recordListAdapter = new RecordListAdapter(getActivity(), list1);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(recordListAdapter);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("失败");
                System.out.println(e);
            }
        });


    }

}