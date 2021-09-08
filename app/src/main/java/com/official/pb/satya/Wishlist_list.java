package com.official.pb.satya;

public class Wishlist_list {
    String name,url;
    int price;
    float rating;
    String postid;

    public Wishlist_list(){

    }

    public Wishlist_list(String name, int price,float rating, String url,String postid) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.rating=rating;
        this.postid=postid;

    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }

    public String getPostid() {
        return postid;
    }
}
