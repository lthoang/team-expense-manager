package com.trhoanglee.expense.web.dto;

import java.util.Date;

public class FundInfo {
    private String id;
    private String comment;
    private Date date;

    public FundInfo() {

    }

    public FundInfo(String id, String comment, Date date) {
        this.id = id;
        this.comment = comment;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
