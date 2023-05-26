package com.mobileclient.domain;

import java.io.Serializable;

public class Shetuan implements Serializable {
    /*负责人账号*/
    private String stUserName;
    public String getStUserName() {
        return stUserName;
    }
    public void setStUserName(String stUserName) {
        this.stUserName = stUserName;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*社团名称*/
    private String shetuanName;
    public String getShetuanName() {
        return shetuanName;
    }
    public void setShetuanName(String shetuanName) {
        this.shetuanName = shetuanName;
    }

    /*社团logo*/
    private String shetuanPhoto;
    public String getShetuanPhoto() {
        return shetuanPhoto;
    }
    public void setShetuanPhoto(String shetuanPhoto) {
        this.shetuanPhoto = shetuanPhoto;
    }

    /*社团简介*/
    private String shetuanDesc;
    public String getShetuanDesc() {
        return shetuanDesc;
    }
    public void setShetuanDesc(String shetuanDesc) {
        this.shetuanDesc = shetuanDesc;
    }

    /*成立日期*/
    private java.sql.Timestamp bornDate;
    public java.sql.Timestamp getBornDate() {
        return bornDate;
    }
    public void setBornDate(java.sql.Timestamp bornDate) {
        this.bornDate = bornDate;
    }

    /*负责人*/
    private String fuzeren;
    public String getFuzeren() {
        return fuzeren;
    }
    public void setFuzeren(String fuzeren) {
        this.fuzeren = fuzeren;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*社团备注*/
    private String shetuanMemo;
    public String getShetuanMemo() {
        return shetuanMemo;
    }
    public void setShetuanMemo(String shetuanMemo) {
        this.shetuanMemo = shetuanMemo;
    }

}