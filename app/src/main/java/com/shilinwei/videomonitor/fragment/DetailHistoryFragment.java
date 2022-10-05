package com.shilinwei.videomonitor.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shilinwei.videomonitor.R;

public class DetailHistoryFragment extends Fragment {

    public DetailHistoryFragment() {
        // Required empty public constructor
    }

    public static DetailHistoryFragment newInstance() {
        DetailHistoryFragment fragment = new DetailHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_history, container, false);
    }
}