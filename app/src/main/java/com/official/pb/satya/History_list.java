package com.official.pb.satya;

public class History_list {
    String history;
    String postid;

    public History_list(){

    }
    public History_list(String history,String postid) {
        this.history = history;
        this.postid=postid;

    }

    public String getHistory() {
        return history;
    }

    public String getPostid() {
        return postid;
    }
}
