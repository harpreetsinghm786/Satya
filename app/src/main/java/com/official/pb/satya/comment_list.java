package com.official.pb.satya;

import java.util.Date;

public class comment_list {
    String commentername;
    String comment;
    Date date;
    public comment_list(){

    }
    public comment_list(String commentername, String comment,Date date) {
        this.commentername = commentername;
        this.comment = comment;
        this.date=date;
    }

    public String getCommentername() {
        return commentername;
    }

    public String getComment() {
        return comment;
    }

    public Date getdate() {
        return date;
    }
}
