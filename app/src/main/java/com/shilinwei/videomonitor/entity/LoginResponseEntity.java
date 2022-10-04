package com.shilinwei.videomonitor.entity;

import java.io.Serializable;

public class LoginResponseEntity implements Serializable {

    private int code;
    private LoginEntity data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LoginEntity getData() {
        return data;
    }

    public void setData(LoginEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class LoginEntity {
        private String depart_id;
        private String role_id;
        private String nick_name;
        private String access_token;
        private String auth_token;

        public String getDepart_id() {
            return depart_id;
        }

        public void setDepart_id(String depart_id) {
            this.depart_id = depart_id;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getAuth_token() {
            return auth_token;
        }

        public void setAuth_token(String auth_token) {
            this.auth_token = auth_token;
        }
    }
}
