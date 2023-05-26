package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Huodong {
    /*活动id*/
    private int huodongId;
    public int getHuodongId() {
        return huodongId;
    }
    public void setHuodongId(int huodongId) {
        this.huodongId = huodongId;
    }

    /*活动名称*/
    private String huodongName;
    public String getHuodongName() {
        return huodongName;
    }
    public void setHuodongName(String huodongName) {
        this.huodongName = huodongName;
    }

    /*活动内容*/
    private String huodongDesc;
    public String getHuodongDesc() {
        return huodongDesc;
    }
    public void setHuodongDesc(String huodongDesc) {
        this.huodongDesc = huodongDesc;
    }

    /*活动时间*/
    private Timestamp huodongTime;
    public Timestamp getHuodongTime() {
        return huodongTime;
    }
    public void setHuodongTime(Timestamp huodongTime) {
        this.huodongTime = huodongTime;
    }

    /*活动社团*/
    private Shetuan shetuanObj;
    public Shetuan getShetuanObj() {
        return shetuanObj;
    }
    public void setShetuanObj(Shetuan shetuanObj) {
        this.shetuanObj = shetuanObj;
    }

    /*活动备注*/
    private String huodongMemo;
    public String getHuodongMemo() {
        return huodongMemo;
    }
    public void setHuodongMemo(String huodongMemo) {
        this.huodongMemo = huodongMemo;
    }

}