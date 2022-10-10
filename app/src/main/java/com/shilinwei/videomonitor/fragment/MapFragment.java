package com.shilinwei.videomonitor.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import com.google.gson.Gson;
import com.shilinwei.videomonitor.R;
import com.shilinwei.videomonitor.activity.DeviceDetailActivity;
import com.shilinwei.videomonitor.api.Api;
import com.shilinwei.videomonitor.api.ApiConfig;
import com.shilinwei.videomonitor.api.TtitCallback;
import com.shilinwei.videomonitor.entity.LoginResponseEntity;
import com.shilinwei.videomonitor.entity.OutDestructionEntity;
import com.shilinwei.videomonitor.entity.OutDestructionResponseEntity;

import java.util.HashMap;
import java.util.List;

public class MapFragment extends BaseFragment {

//    地图配置的文章： https://blog.csdn.net/weixin_44226181/article/details/126278237

    MapView mMapView = null;
    List<OutDestructionEntity> deviceList;
    AMap aMap;

    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    public double latitude;
    public double longitude;

    public int times = 0;


    public MapFragment() {
    }



    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    public void positonLocation() {
        try {
            mlocationClient = new AMapLocationClient(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                System.out.println("进入回调");
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
//                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        //                        aMapLocation.getAccuracy();//获取精度信息
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date date = new Date(aMapLocation.getTime());
                        //                        df.format(date);//定位时间
                        latitude = aMapLocation.getLatitude();//获取纬度
                        longitude = aMapLocation.getLongitude();//获取经度
                        LatLng latLng1 = new LatLng(latitude ,longitude);
                        if(times == 0) {

                            Marker marker = aMap.addMarker(new MarkerOptions().position(latLng1).title("我在这"));
                            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.dingweiown));
                            aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitude, longitude)));
                            times = 1;
                        }
                    } else {
                        System.out.println("获取失败2");
                        System.out.println(aMapLocation.getErrorInfo());
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode());
                    }
                }else {
                    System.out.println("获取失败1");
                }
            }
        });
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        MapsInitializer.updatePrivacyAgree(getActivity(), true);
        MapsInitializer.updatePrivacyShow(getActivity(),true,true);

        //获取地图控件引用
        mMapView = v.findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        initDevicceList();
        positonLocation();
        return v;
    }

    public void initDevicceList() {
        HashMap<String, String> params = new HashMap<>();
        String userInfo = findByKey("userInfo");
        LoginResponseEntity loginResponseEntity = new Gson().fromJson(userInfo, LoginResponseEntity.class);
        String depart_id = loginResponseEntity.getData().getDepart_id();
        params.put("depart_id", depart_id);

        Api.config(ApiConfig.DeviceList, params).postRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                OutDestructionResponseEntity list = new Gson().fromJson(res, OutDestructionResponseEntity.class);
                if(list != null) {
                    deviceList = list.getData().getList();

                    AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
                        // marker 对象被点击时回调的接口
                        // 返回 true 则表示接口已响应事件，否则返回false
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            System.out.println("marker----");
                            System.out.println(marker);
                            return false;
                        }
                    };

                    AMap.OnInfoWindowClickListener infoWindowClickListener = new AMap.OnInfoWindowClickListener() {

                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            OutDestructionEntity item = (OutDestructionEntity) marker.getObject();
                            String deviceSerial = item.getDeviceSerial();
                            String lat = item.getLat();
                            String lng = item.getLng();
                            Intent intent = new Intent(getActivity(), DeviceDetailActivity.class);
                            intent.putExtra("deviceSerial", deviceSerial);
                            intent.putExtra("lat", lat);
                            intent.putExtra("lng", lng);
                            getActivity().startActivity(intent);
                        }
                    };

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < deviceList.size(); i++) {
                                String lat = deviceList.get(i).getLat();
                                String lng = deviceList.get(i).getLng();
                                String deviceName = deviceList.get(i).getDeviceName();
                                if(lng == null || "".equals(lng)) {
                                    continue;
                                }
                                LatLng latLng = new LatLng(Double.parseDouble(lat) ,Double.parseDouble(lng));
                                final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(deviceName).snippet("经纬度:" + lat +","+ lng));
                                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.dingwei));
                                marker.setObject(deviceList.get(i));
                                aMap.setOnInfoWindowClickListener(infoWindowClickListener);
                                aMap.setOnMarkerClickListener(markerClickListener);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("失败");
                System.out.println(e);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}