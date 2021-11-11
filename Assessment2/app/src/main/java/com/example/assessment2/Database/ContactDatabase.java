package com.example.assessment2.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.assessment2.Models.Contact;
import com.example.assessment2.Utilities.Convertors;


@Database(
        entities = {Contact.class},
        version = 1,
        exportSchema = false
)

@TypeConverters({Convertors.class})
public abstract class ContactDatabase extends RoomDatabase
{
    public abstract ContactDao contactDao();

    private static ContactDatabase contactDatabase;

    public static ContactDatabase getDBInstance(final Context context)
    {
        if(contactDatabase == null)
        {
            contactDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class, "contact_room.db").allowMainThreadQueries().build();
        }

        return contactDatabase;
    }
}
