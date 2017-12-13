package com.yjm.doctor.model;

import java.io.Serializable;

/**
 * Created by zx on 2017/12/7.
 */

public class Message implements Serializable {

    private String msg;

    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msg='" + msg + '\'' +
                ", success=" + success +
                '}';
    }
}
