package com.example.assessment2.Utilities.HashProcess;



import com.example.assessment2.Models.Contact;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Hash
{
    public static void BuildHashTable(List<Contact> list, List<Contact>[] hashTable)
    {
        if(list == null)
        {
            return;
        }
        for(int i = 0; i < list.size(); i++)
        {
            Contact c = list.get(i);
            int hashTableIndex = HashItem.hash(c.getFirstName());
            hashTable[hashTableIndex].add(c);
        }
    }


}
