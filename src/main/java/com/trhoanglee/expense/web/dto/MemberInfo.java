package com.trhoanglee.expense.web.dto;

import java.util.Date;

import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

import com.trhoanglee.expense.domain.Name;

public class MemberInfo {
    private String id;

    @Valid
    @Embedded
    private Name name;

    @Email
    private String email;

    private String mobile;

    private Date dob;

    public MemberInfo() {
        
    }
    
    public MemberInfo(String id, Name name, String email, String mobile, Date dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
