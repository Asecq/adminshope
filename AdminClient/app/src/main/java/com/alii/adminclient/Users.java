package com.alii.adminclient;

public class Users {

    String username , phone , pass , online;

    public Users() {
    }

    public Users(String username, String phone, String pass , String online) {
        this.username = username;
        this.phone = phone;
        this.pass = pass;
        this.online = online;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
