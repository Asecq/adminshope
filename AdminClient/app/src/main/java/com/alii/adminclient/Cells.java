package com.alii.adminclient;

public class Cells {
    String code , time , note , number ,phone , user , statics , saller , dep;

    public Cells() {
    }

    public Cells(String code, String time, String note, String number , String phone , String user , String statics , String saller , String dep) {
        this.code = code;
        this.time = time;
        this.note = note;
        this.number = number;
        this.phone = phone;
        this.user = user;
        this.statics = statics;
    }

    public String getSaller() {
        return saller;
    }

    public void setSaller(String saller) {
        this.saller = saller;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getStatics() {
        return statics;
    }

    public void setStatics(String statics) {
        this.statics = statics;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}