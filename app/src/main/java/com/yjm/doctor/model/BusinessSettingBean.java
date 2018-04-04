package com.yjm.doctor.model;

/**
 * Created by zs on 2017/12/14.
 */

public class BusinessSettingBean extends ObjectMessage {

    private ObjBean obj;

    public BusinessSettingBean() {
    }

    @Override
    public String toString() {
        return "BusinessSettingBean{" +
                "obj=" + obj +
                '}';
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }
}
