package com.example.assessment2.Utilities.ArrayActions;



import com.example.assessment2.Models.Contact;

import java.util.ArrayList;

public class Print
{
    public static void output(ArrayList<Contact>[] hashList)
    {
        int asciiValue = 65;
        for(int i =0; i < hashList.length; i++)
        {
            System.out.println((char) asciiValue);
            asciiValue++;
            for(int j = 0; j < hashList[i].size(); j++)
            {
                System.out.print(hashList[i].toString() + " ");
            }
        }
    }
}
