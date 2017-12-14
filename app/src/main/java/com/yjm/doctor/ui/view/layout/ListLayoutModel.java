package com.yjm.doctor.ui.view.layout;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by zs on 2017/12/14.
 */

public class ListLayoutModel {

    private int icon;
    private int title;
    private int titleColor;
    private String msg;
    private int msgColor;
    private String logo;
    private int imgOperaption;

    public ListLayoutModel() {
    }

    //个人中心、我的账户
    public ListLayoutModel(int icon, int title, int imgOperaption) {
        this.icon = icon;
        this.title = title;
        this.imgOperaption = imgOperaption;
    }

    //业务设置
    public ListLayoutModel(int title, String msg, int msgColor, int imgOperaption) {
        this.title = title;
        this.msg = msg;
        this.msgColor = msgColor;
        this.imgOperaption = imgOperaption;
    }

    public ListLayoutModel(int icon, int title, int titleColor, String msg, int msgColor, String logo, int imgOperaption) {
        this.icon = icon;
        this.title = title;
        this.titleColor = titleColor;
        this.msg = msg;
        this.msgColor = msgColor;
        this.logo = logo;
        this.imgOperaption = imgOperaption;
    }

    @Override
    public String toString() {
        return "ListLayoutModel{" +
                "icon=" + icon +
                ", title=" + title +
                ", titleColor=" + titleColor +
                ", msg='" + msg + '\'' +
                ", msgColor=" + msgColor +
                ", logo='" + logo + '\'' +
                ", imgOperaption=" + imgOperaption +
                '}';
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getMsgColor() {
        return msgColor;
    }

    public void setMsgColor(int msgColor) {
        this.msgColor = msgColor;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getImgOperaption() {
        return imgOperaption;
    }

    public void setImgOperaption(int imgOperaption) {
        this.imgOperaption = imgOperaption;
    }
}
