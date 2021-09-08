package com.official.pb.satya;

public class Addons {
    String name;
    int price;

    public Addons(){

    }

    public Addons(String name,int price) {
        this.name = name;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
