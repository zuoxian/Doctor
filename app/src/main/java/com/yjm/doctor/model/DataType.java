package com.yjm.doctor.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by zx on 2017/12/24.
 */

public class DataType implements Serializable{
    private static final long serialVersionUID = 9179354821680308137L;

    private String basetypeCode;
    private String codeName;
    private String description;
    private String id;
    private String name;
    private String pid;
    private String seq;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBasetypeCode() {
        return basetypeCode;
    }

    public void setBasetypeCode(String basetypeCode) {
        this.basetypeCode = basetypeCode;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

}
