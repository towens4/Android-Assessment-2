package com.example.assessment2.Models;

import java.util.Date;

public class Contact
{
    private String name;
    private String phoneNumber;
    private Date contactCreationDate;

    public Contact(String name, String phoneNumber, Date contactCreationDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.contactCreationDate = contactCreationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getContactCreationDate() {
        return contactCreationDate;
    }

    public void setContactCreationDate(Date contactCreationDate) {
        this.contactCreationDate = contactCreationDate;
    }
}
