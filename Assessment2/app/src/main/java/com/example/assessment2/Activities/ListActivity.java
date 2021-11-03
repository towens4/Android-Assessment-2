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
import com.example.assessment2.R;
import com.example.assessment2.Utilities.ContactListAdapter;
import com.example.assessment2.Utilities.ContactListClickInterface;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ListActivity extends AppCompatActivity
{


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
                    Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                    startActivity(intent);
                }
            });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initItems()
    {
        contactList = new ArrayList<>();
        LocalDate date = LocalDate.parse("2000-05-05", DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH));
        contactList.add(new Contact("Alex Smith", "01 8283 2831", Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        contactList.add(new Contact("Alex Wilson", "01 8283 2831", Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        contactList.add(new Contact("Max Smith", "01 8283 2831", Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        contactList.add(new Contact("Johny Coleson", "01 8283 2831", Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        contactList.add(new Contact("Tim Smith", "01 8283 2831", Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        contactList.add(new Contact("Banjo You", "01 8283 2831", Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
    }


}
