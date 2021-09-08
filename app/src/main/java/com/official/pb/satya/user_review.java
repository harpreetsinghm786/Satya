package com.official.pb.satya;

public class user_review {
    String name,review,url;

    public user_review(){

    }

    public user_review(String name, String review, String url) {
        this.name = name;
        this.review = review;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
