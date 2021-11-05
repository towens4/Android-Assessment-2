package com.example.assessment2.Models;

import java.sql.Date;

public class ContactSingleton
{
    private static ContactSingleton instance = null;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String contactCreationDate;


    private int pos;
    public static ContactSingleton getInstance()
    {
        if(instance == null)
        {
            instance = new ContactSingleton();
        }

        return instance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactCreationDate() {
        return contactCreationDate;
    }

    public void setContactCreationDate(String contactCreationDate) {
        this.contactCreationDate = contactCreationDate;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
