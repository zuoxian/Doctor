package com.yjm.doctor.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by zx on 2017/12/26.
 */

public class DepartMent implements Serializable ,Comparable<DepartMent>{
    private static final long serialVersionUID = 3367019393265252882L;

    private Long createTime;
    private int hospitalId;
    private String icon;
    private int id;
    private String name;
    private String status;
    private Long updateTime;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int compareTo(@NonNull DepartMent o) {
        int i = this.getId() - o.getId();
        return i;
    }
}
