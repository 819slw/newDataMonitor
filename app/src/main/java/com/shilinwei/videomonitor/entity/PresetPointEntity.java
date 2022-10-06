package com.shilinwei.videomonitor.entity;

import java.io.Serializable;
import java.util.List;

public class PresetPointEntity implements Serializable {

    private int code;
    private List<DataDTO> data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataDTO {
        private int preset_index;
        private String preset_name;

        public int getPreset_index() {
            return preset_index;
        }

        public void setPreset_index(int preset_index) {
            this.preset_index = preset_index;
        }

        public String getPreset_name() {
            return preset_name;
        }

        public void setPreset_name(String preset_name) {
            this.preset_name = preset_name;
        }
    }
}
