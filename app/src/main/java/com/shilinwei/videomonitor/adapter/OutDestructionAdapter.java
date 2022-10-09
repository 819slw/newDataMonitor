package com.shilinwei.videomonitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.activity.DeviceDetailActivity;
import com.shilinwei.videomonitor.activity.LoginActivity;
import com.shilinwei.videomonitor.entity.OutDestructionEntity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class OutDestructionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<OutDestructionEntity> datas;

    public OutDestructionAdapter(Context context, List<OutDestructionEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        在这里导入自己需要用到的布局文件
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_video_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        在这里进行数据绑定
        ViewHolder vh = (ViewHolder) holder;
        int index = position;
        OutDestructionEntity outDestructionEntity = datas.get(position);
        vh.name.setText(outDestructionEntity.getDeviceName());
        vh.isOnline.setText(outDestructionEntity.getStatus() == 1 ? "在线" : "离线" );
        vh.isOnline.setTextColor(Color.parseColor(outDestructionEntity.getStatus() == 1 ? "#4CAF50" : "#F44336"));
        Glide.with(mContext).load(outDestructionEntity.getPoster()).into(vh.poster);


        vh.iv_play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deviceSerial = datas.get(index).getDeviceSerial();
                String lat = datas.get(index).getLat();
                String lng = datas.get(index).getLng();
                Intent intent = new Intent(mContext, DeviceDetailActivity.class);
                intent.putExtra("deviceSerial", deviceSerial);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


// 在viewholder中获取元素
    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView poster;
        private TextView name;
        private TextView circle;
        private TextView isOnline;
        private RelativeLayout rlBox;
        private ImageView iv_play_btn;

        public ViewHolder(@NonNull View view) {
            super(view);
            poster = view.findViewById(R.id.iv_video_poster);
            name = view.findViewById(R.id.tv_devicename);
            circle = view.findViewById(R.id.tv_circle);
            isOnline = view.findViewById(R.id.tv_isonline);
            rlBox = view.findViewById(R.id.rl_box);
            iv_play_btn = view.findViewById(R.id.iv_play_btn);
        }

    }
}
