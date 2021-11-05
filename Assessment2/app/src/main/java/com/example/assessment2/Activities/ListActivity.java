package com.example.assessment2.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assessment2.Models.Contact;
import com.example.assessment2.Models.ContactSingleton;
import com.example.assessment2.R;
import com.example.assessment2.Utilities.ContactListAdapter;
import com.example.assessment2.Utilities.ContactListClickInterface;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Locale;

public class ListActivity extends AppCompatActivity
{

    ContactSingleton singleton;
    private static ArrayList<Contact> contactList;
    ContactListAdapter contactListAdapter;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonebook_list);

        initItems();

        RecyclerView contactListview = findViewById(R.id.recycleListView);
        contactListAdapter = new ContactListAdapter(contactList, ListActivity.this);

        contactListview.setAdapter(contactListAdapter);
        contactListview.setLayoutManager(new LinearLayoutManager(this));



            Button btnAdd = findViewById(R.id.btn_phoneitemlist_add);
            Button btnDetail = findViewById(R.id.btn_phoneitemlist_detail);
            Button btnDelete;

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListActivity.this, AddActivity.class);
                    startActivity(intent);
                }
            });

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {


                        singleton = ContactSingleton.getInstance();
                        Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                        Bundle extras = new Bundle();
                        String firstName = singleton.getFirstName();

                        String lastName = singleton.getLastName();
                        String phonenumber = singleton.getPhoneNumber();
                        String dob = singleton.getContactCreationDate();

                        extras.putString("firstname", firstName);
                        extras.putString("lastname", lastName);
                        extras.putString("phoneNumber", phonenumber);
                        extras.putString("dob", dob);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                    catch(Exception e)
                    {
                        e.getStackTrace();
                    }
                }
            });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initItems()
    {
        contactList = new ArrayList<>();
        String str = "2000-05-05";

        contactList.add(new Contact("Alex","Smith", "01 8283 2831", Date.valueOf(str)));
        contactList.add(new Contact("Alex", "Wilson", "01 8283 2831", Date.valueOf(str)));
        contactList.add(new Contact("Max", "Smith", "01 8283 2831", Date.valueOf(str)));
        contactList.add(new Contact("Johny", "Coleson", "01 8283 2831", Date.valueOf(str)));
        contactList.add(new Contact("Tim", "Smith", "01 8283 2831", Date.valueOf(str)));
        contactList.add(new Contact("Banjo", "You", "01 8283 2831", Date.valueOf(str)));
    }


}
