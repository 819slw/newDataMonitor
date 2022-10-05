package com.shilinwei.videomonitor.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.activity.LoginActivity;
import com.shilinwei.videomonitor.entity.LoginResponseEntity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends BaseFragment {
    public MyFragment() {
        // Required empty public constructor
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my, container, false);
        TextView name = v.findViewById(R.id.tv_name);
        TextView account = v.findViewById(R.id.tv_account);

        String userInfo = findByKey("userInfo");
        String accountVal = findByKey("account");
        LoginResponseEntity loginResponseEntity = new Gson().fromJson(userInfo, LoginResponseEntity.class);
        name.setText(loginResponseEntity.getData().getNick_name());
        account.setText(accountVal);
        return v;
    }

    public void switchAccount() {
        removeByKey("token");
        removeByKey("userInfo");
        removeByKey("account");
        navigateTo(LoginActivity.class);
    }


}