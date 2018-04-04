package com.yjm.doctor.model;

/**
 * Created by zx on 2017/12/22.
 */

public class PatientBean extends ObjectMessage {

    private Patients obj;

    public Patients getObj() {
        return obj;
    }

    public void setObj(Patients obj) {
        this.obj = obj;
    }
}
