package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Shetuan {
    /*�������˺�*/
    private String stUserName;
    public String getStUserName() {
        return stUserName;
    }
    public void setStUserName(String stUserName) {
        this.stUserName = stUserName;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*��������*/
    private String shetuanName;
    public String getShetuanName() {
        return shetuanName;
    }
    public void setShetuanName(String shetuanName) {
        this.shetuanName = shetuanName;
    }

    /*����logo*/
    private String shetuanPhoto;
    public String getShetuanPhoto() {
        return shetuanPhoto;
    }
    public void setShetuanPhoto(String shetuanPhoto) {
        this.shetuanPhoto = shetuanPhoto;
    }

    /*���ż��*/
    private String shetuanDesc;
    public String getShetuanDesc() {
        return shetuanDesc;
    }
    public void setShetuanDesc(String shetuanDesc) {
        this.shetuanDesc = shetuanDesc;
    }

    /*��������*/
    private Timestamp bornDate;
    public Timestamp getBornDate() {
        return bornDate;
    }
    public void setBornDate(Timestamp bornDate) {
        this.bornDate = bornDate;
    }

    /*������*/
    private String fuzeren;
    public String getFuzeren() {
        return fuzeren;
    }
    public void setFuzeren(String fuzeren) {
        this.fuzeren = fuzeren;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*���ű�ע*/
    private String shetuanMemo;
    public String getShetuanMemo() {
        return shetuanMemo;
    }
    public void setShetuanMemo(String shetuanMemo) {
        this.shetuanMemo = shetuanMemo;
    }

}