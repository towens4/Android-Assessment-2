package com.example.assessment2.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assessment2.Models.Contact;

import java.util.List;

@Dao
public interface ContactDao
{
    //Query to insert contacts into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertContacts(Contact contacts);

    //query to update contact in database
    @Update
    public void updateContacts(Contact contacts);

    //Query to delete contact from database
    @Delete
    public void deleteStudents(Contact contacts);

    //Query to delete all contacts from DB
    @Query("DELETE FROM contacts")
    public void ClearTable();

    //Query to get all contacts from database
    @Query("Select * FROM contacts")
    public List<Contact> getAllContacts();


}
