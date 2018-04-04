package com.yjm.doctor.model;

import java.util.List;

/**
 * Created by cnlive-lsf-doc on 2017/12/24.
 */

public class DataTypeBean extends ObjectMessage {

    private List<DataType> obj;

    public List<DataType> getObj() {
        return obj;
    }

    public void setObj(List<DataType> obj) {
        this.obj = obj;
    }
}
