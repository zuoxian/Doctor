package com.yjm.doctor.model;

import java.io.Serializable;

/**
 * Created by cnlive-lsf-doc on 2018/1/11.
 */

public class SMessage implements Serializable {
    private static final long serialVersionUID = 9008470050133582941L;

    private int consumerType;
    private String content;
    private Long createTime;
    private int id;
    private boolean isPushed;
    private boolean isRead;
    private boolean isdeleted;
    private String mtype;
    private String mtypeZh;
    private String pushContent;
    private String status;
    private String statusZh;
    private String title;
    private Long updateTime;
    private int userId;

    public int getConsumerType() {
        return consumerType;
    }

    public void setConsumerType(int consumerType) {
        this.consumerType = consumerType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPushed() {
        return isPushed;
    }

    public void setPushed(boolean pushed) {
        isPushed = pushed;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getMtypeZh() {
        return mtypeZh;
    }

    public void setMtypeZh(String mtypeZh) {
        this.mtypeZh = mtypeZh;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusZh() {
        return statusZh;
    }

    public void setStatusZh(String statusZh) {
        this.statusZh = statusZh;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
