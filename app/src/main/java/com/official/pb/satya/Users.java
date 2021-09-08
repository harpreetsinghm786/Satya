package com.official.pb.satya;

public class Users {

    String name;
    String phonenumber;
    String url;

    public Users(String name, String phonenumber, String url) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getUrl() {
        return url;
    }
}
