package com.example.assessment2.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "contacts")
public class Contact
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contactID")
    private int id;
    @ColumnInfo(name = "firstname")
    private String firstName;
    @ColumnInfo(name = "lastname")
    private String lastName;
    @ColumnInfo(name = "phonenumber")
    private String phoneNumber;
    @ColumnInfo(name = "dob")
    private Date dob;



    public Contact() {
    }

    public Contact(String name, String lastName, String phoneNumber, Date contactCreationDate) {
        this.firstName = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dob = contactCreationDate;
        //setId(this.id);

    }

    @Ignore
    public Contact(int id, String name, String lastName, String phoneNumber, Date contactCreationDate) {
        this.firstName = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dob = contactCreationDate;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dob=" + dob +
                '}';
    }
}
