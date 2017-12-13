package com.yjm.doctor.model;

import java.util.List;

/**
 * Created by zx on 2017/12/10.
 */

public class BannerBean extends Message{

    private List<Banner> obj;

    public List<Banner> getObj() {
        return obj;
    }

    public void setObj(List<Banner> obj) {
        this.obj = obj;
    }
}
