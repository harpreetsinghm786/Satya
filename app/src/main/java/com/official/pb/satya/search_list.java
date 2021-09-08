package com.official.pb.satya;

public class search_list {
    String uname, postid;
    String des;
    String genre;

    public search_list(){

    }
    public search_list(String uname, String postid,String des,String genre) {
        this.uname = uname;
        this.postid = postid;
        this.des=des;
        this.genre=genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUname() {
        return uname;
    }

    public String getPostid() {
        return postid;
    }
}
