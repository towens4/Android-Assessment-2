package com.example.assessment2.Utilities.ArrayActions;



import com.example.assessment2.Models.Contact;

import java.util.ArrayList;

public class Convert
{
    public ArrayList<Contact> toList(ArrayList<Contact>[] hashList)
    {
        ArrayList<Contact> list = new ArrayList<>();
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
