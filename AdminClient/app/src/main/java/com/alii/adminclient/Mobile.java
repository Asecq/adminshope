package com.alii.adminclient;

public class Mobile {
    String[] Names;
    String[] Counts;
    String[] Prices;
    String[] Ones;
    String[] Units;
    String[] Ptotal;
    String time , code , note;


    public Mobile() {
    }

    public Mobile(String[] names, String[] counts, String[] prices, String[] ones, String[] units,  String[] ptotal, String time, String code, String note) {
        Names = names;
        Counts = counts;
        Prices = prices;
        Ones = ones;
        Units = units;
        Ptotal = ptotal;
        this.time = time;
        this.code = code;
        this.note = note;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String[] getPtotal() {
        return Ptotal;
    }

    public void setPtotal(String[] ptotal) {
        Ptotal = ptotal;
    }

    public String[] getPrices() {
        return Prices;
    }

    public void setPrices(String[] prices) {
        Prices = prices;
    }

    public String[] getOnes() {
        return Ones;
    }

    public void setOnes(String[] ones) {
        Ones = ones;
    }

    public String[] getUnits() {
        return Units;
    }

    public void setUnits(String[] units) {
        Units = units;
    }


    public String[] getNames() {
        return Names;
    }

    public void setNames(String[] names) {
        Names = names;
    }

    public String[] getCounts() {
        return Counts;
    }

    public void setCounts(String[] counts) {
        Counts = counts;
    }
}
