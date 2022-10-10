package com.shilinwei.videomonitor.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import com.google.gson.Gson;
import com.himangi.imagepreview.ImagePreviewActivity;
import com.himangi.imagepreview.PreviewFile;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.activity.DeviceDetailActivity;
import com.shilinwei.videomonitor.api.Api;
import com.shilinwei.videomonitor.api.ApiConfig;
import com.shilinwei.videomonitor.api.TtitCallback;
import com.shilinwei.videomonitor.entity.LogListEntity;
import com.shilinwei.videomonitor.entity.LoginResponseEntity;
import com.shilinwei.videomonitor.entity.OutDestructionEntity;
import com.shilinwei.videomonitor.entity.OutDestructionResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
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


    public void previewImage(ArrayList<PreviewFile> previewFiles, int index) {
        Intent intent = new Intent(mContext, ImagePreviewActivity.class);
        intent.putExtra(ImagePreviewActivity.IMAGE_LIST, previewFiles);
        intent.putExtra(ImagePreviewActivity.CURRENT_ITEM, index);
        mContext.startActivity(intent);
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

        vh.iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogListEntity logListEntity = datas.get(index);
                ArrayList<PreviewFile> logPreviewFiles = new ArrayList<>();
                for (int i = 0; i < logListEntity.getDetail().size(); i++) {
                    LogListEntity.DetailDTO detailDTO = logListEntity.getDetail().get(i);
                    if(detailDTO != null) {
                        String pic_url = detailDTO.getPic_url();
                        if(!pic_url.equals("")) {
                            PreviewFile previewFile = new PreviewFile(pic_url, logListEntity.getDeviceName());
                            logPreviewFiles.add(previewFile);
                        }
                    }
                }
                previewImage(logPreviewFiles, 0);
            }
        });

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

        vh.bt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Verbose","点击");
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("确定要删除吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String id = datas.get(index).getId();
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id", id);
                        Api.config(ApiConfig.deltetByIdDevice, params).postRequest(mContext,new TtitCallback() {
                            @Override
                            public void onSuccess(String res) {
                            }

                            @Override
                            public void onFailure(Exception e) {
                            }
                        });
                        datas.remove(index);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(0, datas.size());
                    }
                });

                builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e("sa","取消");
                    }
                });

                builder.show();
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
        private Button bt_del;

        public ViewHolder(@NonNull View view) {
            super(view);
            iv_pic = view.findViewById(R.id.iv_pic);
            tv_name = view.findViewById(R.id.tv_name);
            tv_date = view.findViewById(R.id.tv_date);
            tv_status = view.findViewById(R.id.tv_status);
            bt_preview = view.findViewById(R.id.bt_preview);
            bt_del = view.findViewById(R.id.bt_del);
        }
    }

}
