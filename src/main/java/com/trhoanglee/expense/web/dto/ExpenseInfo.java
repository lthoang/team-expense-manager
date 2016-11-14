package com.trhoanglee.expense.web.dto;

import java.util.Date;

public class ExpenseInfo {
    private String id;
    private String description;
    private Long total;
    private Date date;

    public ExpenseInfo() {

    }

    public ExpenseInfo(String id, String description, Long total, Date date) {
        this.id = id;
        this.description = description;
        this.total = total;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
