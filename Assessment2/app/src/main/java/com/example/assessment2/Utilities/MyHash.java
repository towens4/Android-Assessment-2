package com.example.assessment2.Utilities;

import com.example.assessment2.Models.Contact;
import com.example.assessment2.Utilities.ArrayActions.Offset;
import com.example.assessment2.Utilities.HashProcess.HashItem;

import java.util.ArrayList;
import java.util.List;


public class MyHash
{
    public Offset offset;
    private List<Contact>[] hashTable = new ArrayList[27];

    public MyHash()
    {
        for(int i = 0; i < this.hashTable.length; ++i) {
            this.hashTable[i] = new ArrayList();
        }
    }

    public List<Contact>[] getHashTable() {
        return hashTable;
    }

    public void BuildHashTable(List<Contact> list, List<Contact>[] hashTable)
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

    public int calcOffsetByKey(int key)
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
