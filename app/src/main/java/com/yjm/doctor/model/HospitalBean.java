package com.yjm.doctor.model;

import java.util.List;

/**
 * Created by zx on 2017/12/26.
 */

public class HospitalBean extends ObjectMessage {

    private List<Hospital> obj;

    public List<Hospital> getObj() {
        return obj;
    }

    public void setObj(List<Hospital> obj) {
        this.obj = obj;
    }
}
