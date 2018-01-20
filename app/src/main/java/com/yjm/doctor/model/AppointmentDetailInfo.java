package com.yjm.doctor.model;

/**
 * Created by zx on 2018/1/17.
 */

public class AppointmentDetailInfo extends Message{

    private AppointmentInfo obj;


    public AppointmentInfo getObj() {
        return obj;
    }

    public void setObj(AppointmentInfo obj) {
        this.obj = obj;
    }
}
