package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Shenqing {
    /*����id*/
    private int shenqingId;
    public int getShenqingId() {
        return shenqingId;
    }
    public void setShenqingId(int shenqingId) {
        this.shenqingId = shenqingId;
    }

    /*���������*/
    private Shetuan shentuanObj;
    public Shetuan getShentuanObj() {
        return shentuanObj;
    }
    public void setShentuanObj(Shetuan shentuanObj) {
        this.shentuanObj = shentuanObj;
    }

    /*����*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*ѧ��*/
    private String xuehao;
    public String getXuehao() {
        return xuehao;
    }
    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }

    /*��Ҫ�¼�*/
    private String zysj;
    public String getZysj() {
        return zysj;
    }
    public void setZysj(String zysj) {
        this.zysj = zysj;
    }

    /*���ԭ��*/
    private String rhyy;
    public String getRhyy() {
        return rhyy;
    }
    public void setRhyy(String rhyy) {
        this.rhyy = rhyy;
    }

    /*������*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*����ʱ��*/
    private String sqTime;
    public String getSqTime() {
        return sqTime;
    }
    public void setSqTime(String sqTime) {
        this.sqTime = sqTime;
    }

    /*���״̬*/
    private String shenHeState;
    public String getShenHeState() {
        return shenHeState;
    }
    public void setShenHeState(String shenHeState) {
        this.shenHeState = shenHeState;
    }

    /*��˽��*/
    private String shenHeResult;
    public String getShenHeResult() {
        return shenHeResult;
    }
    public void setShenHeResult(String shenHeResult) {
        this.shenHeResult = shenHeResult;
    }

}