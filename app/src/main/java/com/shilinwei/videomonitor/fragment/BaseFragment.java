package com.shilinwei.videomonitor.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import static android.content.Context.MODE_PRIVATE;

import com.himangi.imagepreview.ImagePreviewActivity;
import com.himangi.imagepreview.PreviewFile;

import java.util.ArrayList;

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

    public void showToastSync(String msg) {
        Looper.prepare();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public void previewImage(ArrayList<PreviewFile> previewFiles, int index) {
        Intent intent = new Intent(getActivity(), ImagePreviewActivity.class);
        intent.putExtra(ImagePreviewActivity.IMAGE_LIST, previewFiles);
        intent.putExtra(ImagePreviewActivity.CURRENT_ITEM, index);
        startActivity(intent);
    }

    public void navigateTo(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

}
