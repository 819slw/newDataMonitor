package com.shilinwei.videomonitor.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shilinwei.videomonitor.R;
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

    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
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

        OutDestructionEntity outDestructionEntity = datas.get(position);
        vh.name.setText(outDestructionEntity.getDeviceName());
//        vh.poster.setImageBitmap(getHttpBitmap(outDestructionEntity.getPoster()));
        vh.isOnline.setText(outDestructionEntity.getStatus() == 1 ? "在线" : "离线" );
        vh.isOnline.setTextColor(Color.parseColor(outDestructionEntity.getStatus() == 1 ? "#4CAF50" : "#F44336"));
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

        public ViewHolder(@NonNull View view) {
            super(view);
            poster = view.findViewById(R.id.iv_video_poster);
            name = view.findViewById(R.id.tv_devicename);
            circle = view.findViewById(R.id.tv_circle);
            isOnline = view.findViewById(R.id.tv_isonline);
        }

    }
}
