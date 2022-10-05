package com.shilinwei.videomonitor.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.shilinwei.videomonitor.MainActivity;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.adapter.OutDestructionAdapter;
import com.shilinwei.videomonitor.api.Api;
import com.shilinwei.videomonitor.api.ApiConfig;
import com.shilinwei.videomonitor.api.TtitCallback;
import com.shilinwei.videomonitor.entity.LoginResponseEntity;
import com.shilinwei.videomonitor.entity.OutDestructionEntity;
import com.shilinwei.videomonitor.entity.OutDestructionResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutDestructionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutDestructionFragment extends BaseFragment {

    RecyclerView recyclerView = null;
    public Context mContext;

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

        getOutDestructionList();
    }

    private void getOutDestructionList() {
        HashMap<String, String> params = new HashMap<>();
        String userInfo = findByKey("userInfo");
        LoginResponseEntity loginResponseEntity = new Gson().fromJson(userInfo, LoginResponseEntity.class);
        String depart_id = loginResponseEntity.getData().getDepart_id();
        params.put("depart_id", depart_id);

        Api.config(ApiConfig.DeviceList, params).postRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                OutDestructionResponseEntity list = new Gson().fromJson(res, OutDestructionResponseEntity.class);
                if(list != null) {
                    List<OutDestructionEntity> list1 = list.getData().getList();
                    OutDestructionAdapter outDestructionAdapter = new OutDestructionAdapter(getActivity(), list1);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(outDestructionAdapter);
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