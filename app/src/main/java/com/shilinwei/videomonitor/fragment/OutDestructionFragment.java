package com.shilinwei.videomonitor.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.adapter.OutDestructionAdapter;
import com.shilinwei.videomonitor.entity.OutDestructionEntity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutDestructionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutDestructionFragment extends Fragment {

    public OutDestructionFragment() {
        // Required empty public constructor
    }




    public static OutDestructionFragment newInstance() {
        OutDestructionFragment fragment = new OutDestructionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_out_destruction, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);

//        因为List列表是线性布局，所以先new一个线性布局类
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        设置布局
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        给recyclerView设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);

        List<OutDestructionEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OutDestructionEntity outDestructionEntity = new OutDestructionEntity();
            if(i == 0) {
                outDestructionEntity.setDeviceName("220kVkongtaierxian168小号侧");
                outDestructionEntity.setPoster("https://www.xzxdlkj.com:8890/uploads/poster/J00205213.jpg");
            }else if(i == 1) {
                outDestructionEntity.setDeviceName("220kVkongtaierxian255小号侧");
                outDestructionEntity.setPoster("https://www.xzxdlkj.com:8890/uploads/poster/J00205193.jpg");
            }else if(i == 2) {
                outDestructionEntity.setDeviceName("220kVkongtaierxian255小号侧");
                outDestructionEntity.setPoster("https://www.xzxdlkj.com:8890/uploads/poster/J00205199.jpg");
            }else if(i == 3) {
                outDestructionEntity.setDeviceName("220kVkongtaierxian255小号侧");
                outDestructionEntity.setPoster("https://www.xzxdlkj.com:8890/uploads/poster/J00205182.jpg");
            }else {
                outDestructionEntity.setDeviceName("220kVkongtaierxian255小号侧");
                outDestructionEntity.setPoster("https://www.xzxdlkj.com:8890/uploads/poster/J00205193.jpg");
            }

            outDestructionEntity.setStatus(1);
            list.add(outDestructionEntity);
        }

        OutDestructionAdapter outDestructionAdapter = new OutDestructionAdapter(getActivity(), list);
        recyclerView.setAdapter(outDestructionAdapter);
        return v;
    }
}