package com.example.assessment2.Models;

import java.util.Date;

public class Contact
{
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date contactCreationDate;

    public Contact(String name, String lastName, String phoneNumber, Date contactCreationDate) {
        this.firstName = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.contactCreationDate = contactCreationDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
