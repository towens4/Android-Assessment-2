package com.example.assessment2.Utilities;

import com.example.assessment2.Models.Contact;

import java.util.List;

public class Sort
{
    public static Contact binarySearch(String value, List<Contact> list)
    {
        int index = 0;

        int end = list.size();

        while (index <= end)
        {
            int mid = index + (end - index) / 2;

            if(value.equals(list.get(mid).getLastName()))
            {
                return list.get(mid);

            }
            else if(value.compareTo(list.get(mid).getLastName()) > 0)
            {
                end = mid - 1;
            }
            else {
                end = mid + 1;
            }
        }

        return null;
    }
}
