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
    @Id(autoincrement = false)
    private Long id;
    private int age;
    private float balance;
    private long birthday;
    private String birthdayStr;
    private int groupId;
    private String phone;
    private int point;
    private String realName;
    private int sex;

    private int userId;



    @Generated(hash = 5824821)
    public Customer(Long id, int age, float balance, long birthday,
            String birthdayStr, int groupId, String phone, int point,
            String realName, int sex, int userId) {
        this.id = id;
        this.age = age;
        this.balance = balance;
        this.birthday = birthday;
        this.birthdayStr = birthdayStr;
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


    
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", age=" + age +
                ", balance=" + balance +
                ", birthday=" + birthday +
                ", groupId=" + groupId +
                ", phone='" + phone + '\'' +
                ", point=" + point +
                ", realName='" + realName + '\'' +
                ", sex=" + sex +
                ", userId=" + userId +
                '}';
    }



    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public int getAge() {
        return this.age;
    }



    public void setAge(int age) {
        this.age = age;
    }



    public float getBalance() {
        return this.balance;
    }



    public void setBalance(float balance) {
        this.balance = balance;
    }



    public long getBirthday() {
        return this.birthday;
    }



    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }



    public int getGroupId() {
        return this.groupId;
    }



    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }



    public String getPhone() {
        return this.phone;
    }



    public void setPhone(String phone) {
        this.phone = phone;
    }



    public int getPoint() {
        return this.point;
    }



    public void setPoint(int point) {
        this.point = point;
    }



    public String getRealName() {
        return this.realName;
    }



    public void setRealName(String realName) {
        this.realName = realName;
    }



    public int getSex() {
        return this.sex;
    }



    public void setSex(int sex) {
        this.sex = sex;
    }



    public int getUserId() {
        return this.userId;
    }



    public void setUserId(int userId) {
        this.userId = userId;
    }



    public String getBirthdayStr() {
        return this.birthdayStr;
    }



    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }
}
