package com.yjm.doctor.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by zx on 2017/12/8.
 */

public class Level implements Serializable,Comparable<Level> {

    private long createTime;
    private int id;
    private String status;
    private long updateTime;
    private String name;

    public Level(long createTime, int id, String status, long updateTime, String name) {
        this.createTime = createTime;
        this.id = id;
        this.status = status;
        this.updateTime = updateTime;
        this.name = name;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Level{" +
                "createTime=" + createTime +
                ", id=" + id +
                ", status='" + status + '\'' +
                ", updateTime=" + updateTime +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull Level o) {
        int i = this.getId() - o.getId();
        return i;
    }
}
