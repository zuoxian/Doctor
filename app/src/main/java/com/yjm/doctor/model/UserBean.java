package com.yjm.doctor.model;

/**
 * Created by zx on 2017/12/8.
 */

public class UserBean extends ObjectMessage {

    private User obj;

    public User getObj() {
        return obj;
    }

    public void setObj(User obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "obj=" + obj +
                '}';
    }
}
