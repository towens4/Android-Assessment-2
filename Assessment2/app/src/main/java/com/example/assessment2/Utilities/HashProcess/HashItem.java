package com.example.assessment2.Utilities.HashProcess;

import java.nio.charset.Charset;

public class HashItem
{
    public static int hash(String s)
    {


        char c = s.toUpperCase().charAt(0);
        int asciiValue = (int) c;

        if(asciiValue >= 65 && asciiValue <= 90)
        {
            asciiValue = asciiValue - 64;
        } else{
            asciiValue =0;
        }


        return asciiValue;

    }
}
