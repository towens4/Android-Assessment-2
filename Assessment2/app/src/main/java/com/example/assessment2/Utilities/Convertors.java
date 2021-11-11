package com.example.assessment2.Utilities;

import androidx.room.TypeConverter;

import java.sql.Date;

public class Convertors
{
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
