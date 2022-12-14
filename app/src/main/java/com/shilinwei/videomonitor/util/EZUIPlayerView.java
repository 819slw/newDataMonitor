package com.shilinwei.videomonitor.util;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;
/**
 * Description:
 * Created by dingwei3
 *
 * @date : 2017/2/7
 */
public class EZUIPlayerView extends RelativeLayout {
    private static final String TAG = "EzUIPlayer";
    private Context mContext;
    private SurfaceView mSurfaceView;
    private RelativeLayout mSurfaceFrame;
    private int mHeight = 0;
    private int mWidth = 0;
    private int mVideoWidth = 0;
    private int mVideoHeight = 0;
    private ProgressBar mProgressBar;
    private TextView mTipTextView;


    private AtomicBoolean isSurfaceInit = new AtomicBoolean(false);
    private boolean isPreparePlay = false;
    private boolean isOpenSound = true;

    private SurfaceHolder.Callback mCallback;


    public EZUIPlayerView(Context context) {
        super(context);
        mContext = context;
        initSurfaceView();
    }


    public EZUIPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initSurfaceView();
    }

    public EZUIPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initSurfaceView();
    }

    private void initSurfaceView(){
        if (mSurfaceView == null) {
            mSurfaceView = new SurfaceView(mContext);
            LayoutParams lp = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            mSurfaceView.setLayoutParams(lp);
            addView(mSurfaceView);
        }
    }


    public SurfaceView getSurfaceView() {
        return mSurfaceView;
    }

    public void showTipText(int resStringId) {
        showTipText(mContext.getString(resStringId));
    }

    public void showTipText(String tipstring) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        if (mTipTextView == null) {
            mTipTextView = new TextView(mContext);
            mTipTextView.setText(tipstring);
            mTipTextView.setTextColor(Color.rgb(255, 255, 255));
            LayoutParams lp = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);//???????????????????????????
            mTipTextView.setLayoutParams(lp);
            addView(mTipTextView);
        }
        mTipTextView.setVisibility(View.VISIBLE);
    }

//    public void showLoading() {
//        if (mTipTextView != null) {
//            mTipTextView.setVisibility(View.GONE);
//        }
//        if (mProgressBar == null) {
//            mProgressBar = new ProgressBar(mContext);
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
//            mProgressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.footer_progress));
//            mProgressBar.setLayoutParams(lp);
//            addView(mProgressBar);
//        }
//        mProgressBar.setVisibility(View.VISIBLE);
//    }

    public void dismissomLoading() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public void setSurfaceHolderCallback(SurfaceHolder.Callback callback){
        if (callback != null) {
            this.mCallback = callback;
            if (mSurfaceView != null) {
                mSurfaceView.getHolder().addCallback(callback);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mHeight = (int) (mWidth * 0.562);
        }
        changeSurfaceSize(mSurfaceView, 0, 0);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.getMode(widthMeasureSpec));
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void setVideoSizeChange(int videoWidth, int videoHeight) {
        mVideoWidth = videoWidth;
        mVideoHeight = videoHeight;
        changeSurfaceSize(mSurfaceView, mVideoWidth, mVideoHeight);
    }

    /**
     * ??????????????????????????????
     * ???width??????0???height??????0????????????????????????height???width???????????????????????????????????????????????????
     *
     * @param width  ???????????????
     * @param height ???????????????
     */
    public void setSurfaceSize(int width, int height) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(width, height);
        } else {
            lp.width = width;
            lp.height = height;
        }
        if (width == 0) {
            lp.width = (int) (height * 1.1778);
        }
        if (height == 0) {
            lp.height = (int) (width * 0.562);
        }
        setLayoutParams(lp);
        changeSurfaceSize(mSurfaceView, mVideoWidth, mVideoHeight);
    }

    private Point getSurfaceSize(SurfaceView surface, int videoWidth, int videoHeight) {
        int sw;
        int sh;
        Point pt = null;
        if (surface == null)
            return pt;
        if (videoWidth == 0 || videoHeight == 0) {
            // return;
            videoWidth = 16;
            videoHeight = 9;
        }
        // get screen size
//        sw = activity.getWindow().getDecorView().getWidth();
//        sh = activity.getWindow().getDecorView().getHeight();
        sw = mWidth;
        sh = mHeight;

        double dw = sw, dh = sh;
//        boolean isPortrait = activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        boolean isPortrait = false;
        if (sw > sh && isPortrait || sw < sh && !isPortrait) {
            dw = sh;
            dh = sw;
        }
        // sanity check
        if (dw * dh == 0 || videoWidth * videoHeight == 0) {
            return pt;
        }
        // compute the aspect ratio
        double ar, vw;
        vw = videoWidth;
        ar = (double) videoWidth / (double) videoHeight;
        // compute the display aspect ratio
        double dar = dw / dh;
        if (dar < ar)
            dh = dw / ar;
        else
            dw = dh * ar;
        int w = (int) Math.ceil(dw * videoWidth / videoWidth);
        int h = (int) Math.ceil(dh * videoHeight / videoHeight);
        pt = new Point(w, h);
        return pt;
    }

    private void changeSurfaceSize(SurfaceView surface, int videoWidth, int videoHeight) {
        if (surface == null)
            return;
        SurfaceHolder holder = surface.getHolder();

        // force surface buffer size
        Point size = getSurfaceSize(surface, videoWidth, videoHeight);
        if (size == null) {
            return;
        }
        holder.setFixedSize(videoWidth, videoHeight);
        // set display size
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) surface.getLayoutParams();
        int oldH = lp.height;
        lp.width = size.x;
        lp.height = size.y;
        surface.setLayoutParams(lp);
    }
}



