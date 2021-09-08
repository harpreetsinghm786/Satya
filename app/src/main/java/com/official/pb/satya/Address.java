package com.official.pb.satya;

public class Address {
    String housenumber, streetnumber, colony, city, nearby, key;
    boolean bool;
    static Address address;

    public Address(){

    }
    public Address(String housenumber, String streetnumber, String colony, String city, String nearby, String key,boolean bool) {
        this.housenumber = housenumber;
        this.streetnumber = streetnumber;
        this.colony = colony;
        this.city = city;
        this.key = key;
        this.nearby = nearby;
        this.bool=bool;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public String getColony() {
        return colony;
    }

    public String getCity() {
        return city;
    }

    public String getNearby() {
        return nearby;
    }

    public String getKey() {
        return key;
    }

    public boolean isBool() {
        return bool;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setNearby(String nearby) {
        this.nearby = nearby;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }


}
