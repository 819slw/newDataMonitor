package com.shilinwei.videomonitor.entity;

import java.io.Serializable;
import java.util.List;

public class LogListEntity implements Serializable {
    private String id;
    private String deviceSerial;
    private String deviceName;
    private List<DetailDTO> detail;
    private int push_type;
    private String type_name;
    private String create_at;
    private String lng;
    private String lat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public List<DetailDTO> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailDTO> detail) {
        this.detail = detail;
    }

    public int getPush_type() {
        return push_type;
    }

    public void setPush_type(int push_type) {
        this.push_type = push_type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public static class DetailDTO {
        private String pic_url;
        private String preset_name;
        private int preset_index;
        private String picture_name;
        private boolean is_alarm;

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getPreset_name() {
            return preset_name;
        }

        public void setPreset_name(String preset_name) {
            this.preset_name = preset_name;
        }

        public int getPreset_index() {
            return preset_index;
        }

        public void setPreset_index(int preset_index) {
            this.preset_index = preset_index;
        }

        public String getPicture_name() {
            return picture_name;
        }

        public void setPicture_name(String picture_name) {
            this.picture_name = picture_name;
        }

        public boolean isIs_alarm() {
            return is_alarm;
        }

        public void setIs_alarm(boolean is_alarm) {
            this.is_alarm = is_alarm;
        }
    }
}
