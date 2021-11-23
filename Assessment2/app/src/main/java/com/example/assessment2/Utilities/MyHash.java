package com.example.assessment2.Utilities;

import com.example.assessment2.Models.Contact;
import com.example.assessment2.Utilities.ArrayActions.Offset;

import java.util.ArrayList;



public class MyHash
{
    public Offset offset;
    private ArrayList<Contact>[] hashTable = new ArrayList[27];

    public MyHash()
    {
        for(int i = 0; i < this.hashTable.length; ++i) {
            this.hashTable[i] = new ArrayList();
        }
    }

    public ArrayList<Contact>[] getHashTable() {
        return hashTable;
    }
}
