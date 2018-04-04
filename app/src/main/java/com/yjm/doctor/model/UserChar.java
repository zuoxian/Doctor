package com.yjm.doctor.model;

import java.util.List;

/**
 * Created by zx on 2018/1/28.
 */

public class UserChar extends ObjectMessage {

    private List<User> obj;

    public List<User> getObj() {
        return obj;
    }

    public void setObj(List<User> obj) {
        this.obj = obj;
    }
}
