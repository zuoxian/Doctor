package com.yjm.doctor.model;

import java.util.List;

/**
 * Created by zx on 2017/12/10.
 */

public class MainContentItem<T> {

    private String type;
    private List<T>  list;

    public MainContentItem(String type, List<T> list) {
        this.type = type;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
