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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertContacts(Contact contacts);

    @Update
    public void updateContacts(Contact contacts);

    @Delete
    public void deleteStudents(Contact contacts);

    @Query("DELETE FROM contacts")
    public void ClearTable();

    @Query("Select * FROM contacts")
    public List<Contact> getAllContacts();
}
