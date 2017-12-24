package com.yjm.doctor.model;

import java.io.Serializable;

/**
 * Created by zx on 2017/12/24.
 */

public class Account implements Serializable{


    private static final long serialVersionUID = -6126692025540737272L;

    private String alipay;
    private String bankAccount;
    private String bankCard;
    private String bankCode;
    private String bankCodeZh;
    private String bankIdNo;
    private String bankName;
    private String bankPhone;
    private int userId;

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankCodeZh() {
        return bankCodeZh;
    }

    public void setBankCodeZh(String bankCodeZh) {
        this.bankCodeZh = bankCodeZh;
    }

    public String getBankIdNo() {
        return bankIdNo;
    }

    public void setBankIdNo(String bankIdNo) {
        this.bankIdNo = bankIdNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankPhone() {
        return bankPhone;
    }

    public void setBankPhone(String bankPhone) {
        this.bankPhone = bankPhone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
