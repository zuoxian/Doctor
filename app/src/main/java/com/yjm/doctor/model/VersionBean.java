package com.yjm.doctor.model;

/**
 * Created by zx on 2018/2/2.
 */

public class VersionBean extends ObjectMessage{

    private VersionInfo obj;

    public VersionInfo getObj() {
        return obj;
    }

    public void setObj(VersionInfo obj) {
        this.obj = obj;
    }
}
