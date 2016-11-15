package com.trhoanglee.expense.web.dto;

import java.util.Date;

public class TeamInfo {
    private String id;
    private String name;
    private String description;
    private Date createdDate;
    private MemberInfo manager;

    public TeamInfo() {

    }

    public TeamInfo(String id, String name, String description, Date createdDate, MemberInfo manager) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.manager = manager;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public MemberInfo getManager() {
        return manager;
    }

    public void setManager(MemberInfo manager) {
        this.manager = manager;
    }
}
