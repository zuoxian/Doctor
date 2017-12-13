package com.yjm.doctor.model;

import java.io.Serializable;

/**
 * Created by zx on 2017/12/10.
 */

public class Banner implements Serializable {

    private static final long serialVersionUID = 8030722115010682169L;
    private long createTime;
    private int id;
    private String link;
    private int num;
    private int pic;
    private String picUrl;
    private String source;
    private String status;
    private String title;
    private String updateTime;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public String toString() {
        return "BannerBean{" +
                "createTime=" + createTime +
                ", id=" + id +
                ", link='" + link + '\'' +
                ", num=" + num +
                ", pic=" + pic +
                ", picUrl='" + picUrl + '\'' +
                ", source='" + source + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
