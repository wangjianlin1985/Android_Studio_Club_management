package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ClassInfo {
    /*班级编号*/
    private String classNo;
    public String getClassNo() {
        return classNo;
    }
    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    /*班级名称*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*所在学院*/
    private String collegeName;
    public String getCollegeName() {
        return collegeName;
    }
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /*所属专业*/
    private String specialName;
    public String getSpecialName() {
        return specialName;
    }
    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    /*成立日期*/
    private Timestamp bornDate;
    public Timestamp getBornDate() {
        return bornDate;
    }
    public void setBornDate(Timestamp bornDate) {
        this.bornDate = bornDate;
    }

    /*班主任*/
    private String banzhuren;
    public String getBanzhuren() {
        return banzhuren;
    }
    public void setBanzhuren(String banzhuren) {
        this.banzhuren = banzhuren;
    }

}