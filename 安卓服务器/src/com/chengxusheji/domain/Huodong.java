package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Huodong {
    /*�id*/
    private int huodongId;
    public int getHuodongId() {
        return huodongId;
    }
    public void setHuodongId(int huodongId) {
        this.huodongId = huodongId;
    }

    /*�����*/
    private String huodongName;
    public String getHuodongName() {
        return huodongName;
    }
    public void setHuodongName(String huodongName) {
        this.huodongName = huodongName;
    }

    /*�����*/
    private String huodongDesc;
    public String getHuodongDesc() {
        return huodongDesc;
    }
    public void setHuodongDesc(String huodongDesc) {
        this.huodongDesc = huodongDesc;
    }

    /*�ʱ��*/
    private Timestamp huodongTime;
    public Timestamp getHuodongTime() {
        return huodongTime;
    }
    public void setHuodongTime(Timestamp huodongTime) {
        this.huodongTime = huodongTime;
    }

    /*�����*/
    private Shetuan shetuanObj;
    public Shetuan getShetuanObj() {
        return shetuanObj;
    }
    public void setShetuanObj(Shetuan shetuanObj) {
        this.shetuanObj = shetuanObj;
    }

    /*���ע*/
    private String huodongMemo;
    public String getHuodongMemo() {
        return huodongMemo;
    }
    public void setHuodongMemo(String huodongMemo) {
        this.huodongMemo = huodongMemo;
    }

}