package com.shilinwei.videomonitor.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shilinwei.videomonitor.R;


public class BirdDriveConfigFragment extends Fragment {

    public BirdDriveConfigFragment() {
    }

    public static BirdDriveConfigFragment newInstance(String deviceSerial) {
        BirdDriveConfigFragment fragment = new BirdDriveConfigFragment();
        Bundle args = new Bundle();
        args.putString("deviceSerial", deviceSerial);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bird_drive_config, container, false);
    }
}