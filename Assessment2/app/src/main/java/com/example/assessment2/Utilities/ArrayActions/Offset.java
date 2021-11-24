package com.example.assessment2.Utilities.ArrayActions;



import com.example.assessment2.Models.Contact;

import java.util.ArrayList;
import java.util.List;

public class Offset {
    public static int calcOffsetByKey(int key, List<Contact>[] hashTable)
    {
        int offset= 0;
        if(key < 0 || key >= hashTable.length)
        {
            offset = 0;
        }
        else
        {
            for(int i = 0; i < key; i++)
            {
                offset = offset + hashTable[i].size();
            }
        }

        return offset;


    }
}
