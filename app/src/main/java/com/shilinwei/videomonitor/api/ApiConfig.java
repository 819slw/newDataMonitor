package com.shilinwei.videomonitor.api;

public class ApiConfig {
    public static final int PAGE_SIZE = 5;
    public static final String BASE_URl = "https://www.xzxdlkj.com:8890";
    public static final String BASE_URl1 = "https://devapi.qweather.com";
    public static final String LOGIN = "/api/v1/mobile/user/login"; //登录
    public static final String DeviceList = "/api/v1/mobile/device/list"; // 设备列表
    public static final String GetAccessToken = "/api/v1/back/token/get"; // 获取accessToken
    public static final String PresetList = "/api/v1/mobile/device/preset/list"; // 预设点列表
    public static final String UsePreset = "/api/v1/mobile/device/preset/move"; // 预设点列表
    public static final String DelPreset = "/api/v1/mobile/device/preset/del"; // 预设点列表
    public static final String AddPreset = "/api/v1/mobile/device/preset/add"; // 预设点列表
    public static final String MoveStartPreset = "/api/v1/mobile/device/ptz/start"; // 预设点列表
    public static final String MoveEndPreset = "/api/v1/mobile/device/ptz/stop"; // 预设点列表
    public static final String nowWeather = "/v7/weather/now"; // 天气
    public static final String threeDayWeather = "/v7/weather/3d"; // 天气
}
