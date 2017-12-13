package com.yjm.doctor.model;

//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zx on 2017/12/10.
 */

@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 2146657448481966306L;
    private int age;
    private float balance;
    private long birthday;
    private int groupId;
    private String phone;
    private int point;
    private String realName;
    private int sex;
    @Id(autoincrement = false)
    private int userId;

    @Generated(hash = 1900924186)
    public Customer(int age, float balance, long birthday, int groupId,
            String phone, int point, String realName, int sex, int userId) {
        this.age = age;
        this.balance = balance;
        this.birthday = birthday;
        this.groupId = groupId;
        this.phone = phone;
        this.point = point;
        this.realName = realName;
        this.sex = sex;
        this.userId = userId;
    }

    @Generated(hash = 60841032)
    public Customer() {
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
