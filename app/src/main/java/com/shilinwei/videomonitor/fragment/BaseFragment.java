package com.shilinwei.videomonitor.fragment;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import static android.content.Context.MODE_PRIVATE;

public class BaseFragment extends Fragment {

    protected String findByKey(String key) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        return sp.getString(key, "");
    }

    protected void removeByKey(String key) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }


    public void navigateTo(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

}
