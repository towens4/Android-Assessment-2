package com.example.assessment2.Utilities.ArrayActions;



import com.example.assessment2.Models.Contact;

import java.util.ArrayList;
import java.util.List;

public class Convert
{
    public static List<Contact> toList(List<Contact>[] hashList)
    {
        List<Contact> list = new ArrayList<>();
        for(int i = 0; i < hashList.length; i ++)
        {
            for(int j = 0; j < hashList[i].size(); j++)
            {
                list.add(hashList[i].get(j));
            }
        }

        return list;
    }
}
