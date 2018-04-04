package com.yjm.doctor.model;

//import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import org.greenrobot.greendao.DaoException;
import com.yjm.doctor.dao.DaoSession;
import com.yjm.doctor.dao.MemberDoctorDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.yjm.doctor.dao.CustomerDao;
import com.yjm.doctor.dao.UserDao;

/**
 * Created by zx on 2017/12/7.
 */
@Entity
public class User implements Serializable {

    @Id(autoincrement = false)
    private int id;

    private static final long serialVersionUID = -8951807283047736469L;


    private int amount;
    private String email;

    private String headImage;

    private String hxPassword;

    private boolean hxStatus;

    private int groupid;

    private int isAdmin;

    private int lastLoginIp;

    private Long lastLoginTime;

    private int login;

    private String mobile;

    private int modelid;

    private String password;

    private String pic;

    private String picUrl;
    private int regIp;

    private long regTime;

    private int score;

    private int status;

    private String tokenId;

    private int updateTime;

    private String username;

    @Transient
    private Customer customer;

    @Transient
    private MemberDoctor memberDoctor;

    @Transient
    private String pwd;

    @Transient
    private Patient patient;


    @Generated(hash = 43509266)
    public User(int id, int amount, String email, String headImage,
            String hxPassword, boolean hxStatus, int groupid, int isAdmin,
            int lastLoginIp, Long lastLoginTime, int login, String mobile,
            int modelid, String password, String pic, String picUrl, int regIp,
            long regTime, int score, int status, String tokenId, int updateTime,
            String username) {
        this.id = id;
        this.amount = amount;
        this.email = email;
        this.headImage = headImage;
        this.hxPassword = hxPassword;
        this.hxStatus = hxStatus;
        this.groupid = groupid;
        this.isAdmin = isAdmin;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginTime = lastLoginTime;
        this.login = login;
        this.mobile = mobile;
        this.modelid = modelid;
        this.password = password;
        this.pic = pic;
        this.picUrl = picUrl;
        this.regIp = regIp;
        this.regTime = regTime;
        this.score = score;
        this.status = status;
        this.tokenId = tokenId;
        this.updateTime = updateTime;
        this.username = username;
    }

    @Generated(hash = 586692638)
    public User() {
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }



    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public MemberDoctor getMemberDoctor() {
        return memberDoctor;
    }

    public void setMemberDoctor(MemberDoctor memberDoctor) {
        this.memberDoctor = memberDoctor;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", amount=" + amount +
                ", email='" + email + '\'' +
                ", groupid=" + groupid +
                ", hxPassword='" + hxPassword + '\'' +
                ", hxStatus=" + hxStatus +
                ", isAdmin=" + isAdmin +
                ", lastLoginIp=" + lastLoginIp +
                ", lastLoginTime=" + lastLoginTime +
                ", login=" + login +
                ", mobile='" + mobile + '\'' +
                ", modelid=" + modelid +
                ", password='" + password + '\'' +
                ", pic='" + pic + '\'' +
                ", regIp=" + regIp +
                ", regTime=" + regTime +
                ", score=" + score +
                ", status=" + status +
                ", tokenId='" + tokenId + '\'' +
                ", updateTime=" + updateTime +
                ", username='" + username + '\'' +
                ", customer=" + customer +
                ", memberDoctor=" + memberDoctor +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadImage() {
        return this.headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getHxPassword() {
        return this.hxPassword;
    }

    public void setHxPassword(String hxPassword) {
        this.hxPassword = hxPassword;
    }

    public boolean getHxStatus() {
        return this.hxStatus;
    }

    public void setHxStatus(boolean hxStatus) {
        this.hxStatus = hxStatus;
    }

    public int getGroupid() {
        return this.groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public int getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getLastLoginIp() {
        return this.lastLoginIp;
    }

    public void setLastLoginIp(int lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Long getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLogin() {
        return this.login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getModelid() {
        return this.modelid;
    }

    public void setModelid(int modelid) {
        this.modelid = modelid;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getRegIp() {
        return this.regIp;
    }

    public void setRegIp(int regIp) {
        this.regIp = regIp;
    }

    public long getRegTime() {
        return this.regTime;
    }

    public void setRegTime(long regTime) {
        this.regTime = regTime;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public int getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
}
