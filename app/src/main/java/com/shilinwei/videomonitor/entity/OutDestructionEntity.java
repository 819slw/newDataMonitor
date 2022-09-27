package com.shilinwei.videomonitor.entity;

import java.io.Serializable;

public class OutDestructionEntity implements Serializable {
    private String poster;
    private Integer status;
    private String deviceName;

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public String getPoster() {
        return poster;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    private String deviceSerial;
}
