package com.shilinwei.videomonitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.activity.DeviceDetailActivity;
import com.shilinwei.videomonitor.entity.LogListEntity;

import java.util.List;

public class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<LogListEntity> datas;

    public RecordListAdapter (Context context, List<LogListEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_record_layout, parent, false);
        RecordListAdapter.ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        int index = position;
        LogListEntity logListEntity = datas.get(index);
        vh.tv_name.setText("设备名称: " + logListEntity.getDeviceName());
        vh.tv_date.setText("日期: " + logListEntity.getCreate_at());
        vh.tv_status.setText("推送状态: " + logListEntity.getType_name());
        Glide.with(mContext).load(logListEntity.getDetail().get(0).getPic_url()).into(vh.iv_pic);
        vh.bt_preview.setOnClickListener(new View.OnClickListener() {
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



    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_pic;
        private TextView tv_name;
        private TextView tv_date;
        private TextView tv_status;
        private Button bt_preview;

        public ViewHolder(@NonNull View view) {
            super(view);
            iv_pic = view.findViewById(R.id.iv_pic);
            tv_name = view.findViewById(R.id.tv_name);
            tv_date = view.findViewById(R.id.tv_date);
            tv_status = view.findViewById(R.id.tv_status);
            bt_preview = view.findViewById(R.id.bt_preview);
        }
    }

}
