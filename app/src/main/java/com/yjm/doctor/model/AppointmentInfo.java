package com.yjm.doctor.model;

//import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by zx on 2017/12/10.
 */

public class AppointmentInfo implements Serializable {

    private static final long serialVersionUID = -3440866088432378367L;
    private int amount;
    private String appointAddress;
    private String appointMessage;
    private String appointName;
    private String appointStatus;
    private String appointTime;

    public String getAppointAddress() {
        return appointAddress;
    }

    public void setAppointAddress(String appointAddress) {
        this.appointAddress = appointAddress;
    }

    private String appointmentNo;
    private String confirmTime;
    private int createBy;
    private long createTime;

    private int doctorId;
    private int id;
    private boolean isCommented;
    private String linkName;
    private String linkWay;
    private String pics;
    private String secondTime;
    private String sourse;
    private String status;
    private int time;
    private String timeZh;
    private long updateTime;
    private User user;

    private int userId;


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAppointMessage() {
        return appointMessage;
    }

    public void setAppointMessage(String appointMessage) {
        this.appointMessage = appointMessage;
    }

    public String getAppointName() {
        return appointName;
    }

    public void setAppointName(String appointName) {
        this.appointName = appointName;
    }

    public String getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(String appointStatus) {
        this.appointStatus = appointStatus;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public String getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(String appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCommented() {
        return isCommented;
    }

    public void setCommented(boolean commented) {
        isCommented = commented;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkWay() {
        return linkWay;
    }

    public void setLinkWay(String linkWay) {
        this.linkWay = linkWay;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getSecondTime() {
        return secondTime;
    }

    public void setSecondTime(String secondTime) {
        this.secondTime = secondTime;
    }

    public String getSourse() {
        return sourse;
    }

    public void setSourse(String sourse) {
        this.sourse = sourse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTimeZh() {
        return timeZh;
    }

    public void setTimeZh(String timeZh) {
        this.timeZh = timeZh;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AppointmentInfo{" +
                "amount=" + amount +
                ", appointAddress='" + appointAddress + '\'' +
                ", appointMessage='" + appointMessage + '\'' +
                ", appointName='" + appointName + '\'' +
                ", appointStatus='" + appointStatus + '\'' +
                ", appointTime='" + appointTime + '\'' +
                ", appointmentNo='" + appointmentNo + '\'' +
                ", confirmTime='" + confirmTime + '\'' +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", doctorId=" + doctorId +
                ", id=" + id +
                ", isCommented=" + isCommented +
                ", linkName='" + linkName + '\'' +
                ", linkWay='" + linkWay + '\'' +
                ", pics='" + pics + '\'' +
                ", secondTime='" + secondTime + '\'' +
                ", sourse='" + sourse + '\'' +
                ", status='" + status + '\'' +
                ", time=" + time +
                ", timeZh='" + timeZh + '\'' +
                ", updateTime=" + updateTime +
                ", user=" + user +
                ", userId=" + userId +
                '}';
    }
}
