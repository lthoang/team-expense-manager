package com.trhoanglee.expense.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Formula;

@Embeddable
public class Name {

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @Formula("CONCAT(FIRST_NAME, ' ', MIDDLE_NAME, ' ', LAST_NAME)")
    private String fullNameFML;
    
    public Name() {
        //this required by Hibernate default
    }
    
    public Name(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullNameFML() {
        return fullNameFML;
    }

    public void setFullNameFML(String fullNameFML) {
        this.fullNameFML = fullNameFML;
    }

    @Override
    public String toString() {
        return String.format("Name FML: %s %s %s", firstName, middleName, lastName);
    }

}
