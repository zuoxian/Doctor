package com.yjm.doctor.model;

/**
 * Created by zx on 2017/12/23.
 */

public class UserPatientInfoBean extends ObjectMessage {

    private UserPatientInfos obj;

    public UserPatientInfos getObj() {
        return obj;
    }

    public void setObj(UserPatientInfos obj) {
        this.obj = obj;
    }
}
