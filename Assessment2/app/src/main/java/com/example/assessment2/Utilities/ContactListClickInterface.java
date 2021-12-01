package com.example.assessment2.Utilities;

import com.example.assessment2.Models.Contact;

public interface ContactListClickInterface
{
    Contact onItemClick(int position);
    Contact onLongItemClick(int position);
}
