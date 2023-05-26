package com.mobileclient.domain;

import java.io.Serializable;

public class Shenqing implements Serializable {
    /*申请id*/
    private int shenqingId;
    public int getShenqingId() {
        return shenqingId;
    }
    public void setShenqingId(int shenqingId) {
        this.shenqingId = shenqingId;
    }

    /*申请的社团*/
    private String shentuanObj;
    public String getShentuanObj() {
        return shentuanObj;
    }
    public void setShentuanObj(String shentuanObj) {
        this.shentuanObj = shentuanObj;
    }

    /*姓名*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*学号*/
    private String xuehao;
    public String getXuehao() {
        return xuehao;
    }
    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }

    /*主要事迹*/
    private String zysj;
    public String getZysj() {
        return zysj;
    }
    public void setZysj(String zysj) {
        this.zysj = zysj;
    }

    /*入会原因*/
    private String rhyy;
    public String getRhyy() {
        return rhyy;
    }
    public void setRhyy(String rhyy) {
        this.rhyy = rhyy;
    }

    /*申请人*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*申请时间*/
    private String sqTime;
    public String getSqTime() {
        return sqTime;
    }
    public void setSqTime(String sqTime) {
        this.sqTime = sqTime;
    }

    /*审核状态*/
    private String shenHeState;
    public String getShenHeState() {
        return shenHeState;
    }
    public void setShenHeState(String shenHeState) {
        this.shenHeState = shenHeState;
    }

    /*审核结果*/
    private String shenHeResult;
    public String getShenHeResult() {
        return shenHeResult;
    }
    public void setShenHeResult(String shenHeResult) {
        this.shenHeResult = shenHeResult;
    }

}