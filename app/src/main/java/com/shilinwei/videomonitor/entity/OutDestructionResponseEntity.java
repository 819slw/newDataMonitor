package com.shilinwei.videomonitor.entity;

import java.io.Serializable;
import java.util.List;

public class OutDestructionResponseEntity implements Serializable {

    private int code;
    private DataDTO data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataDTO {
        private List<OutDestructionEntity> list;

        public List<OutDestructionEntity> getList() {
            return list;
        }

        public void setList(List<OutDestructionEntity> list) {
            this.list = list;
        }
    }
}
