package com.yjm.doctor.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by zx on 2017/12/26.
 */

public class Hospital  implements Serializable,Comparable<Hospital>{

    private static final long serialVersionUID = -1722041632676819067L;

    private int city;
    private int county;
    private Long createTime;
    private String hospitalLevel;
    private String hospitalName;
    private int id;
    private String introduce;
    private Long province;
    private String status;
    private Long updateTime;

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getCounty() {
        return county;
    }

    public void setCounty(int county) {
        this.county = county;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Long getProvince() {
        return province;
    }

    public void setProvince(Long province) {
        this.province = province;
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
    public int compareTo(@NonNull Hospital o) {
        int i = this.getId() - o.getId();
        return i;
    }
}
