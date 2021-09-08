package com.official.pb.satya;

import java.util.List;

public class cart_list {
    String name;
    int price;
    int oldprice;
    int quantity;
    String desc;
    String image;
    String notprice;
    String postid;
    String poff;
    List<Addons>addons;
    public cart_list(){

    }
    public cart_list(String name, int price, int quantity,String image,String postid,List<Addons>addons,int oldprice,String desc,String poff,String notprice) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image=image;
        this.postid=postid;
        this.notprice=notprice;
        this.desc=desc;
        this.addons=addons;
        this.poff=poff;
        this.oldprice=oldprice;


    }

    public String getNotprice() {
        return notprice;
    }

    public void setNotprice(String notprice) {
        this.notprice = notprice;
    }

    public String getPoff() {
        return poff;
    }

    public void setPoff(String poff) {
        this.poff = poff;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setOldprice(int oldprice) {
        this.oldprice = oldprice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public void setAddons(List<Addons> addons) {
        this.addons = addons;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImage() {

        return image;
    }

    public String getPostid() {
        return postid;
    }

    public List<Addons> getAddons() {
        return addons;
    }

    public int getOldprice() {
        return oldprice;
    }
}
