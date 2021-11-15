package com.example.assessment2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.assessment2.Database.ContactAPIService;
import com.example.assessment2.Database.ContactDatabase;
import com.example.assessment2.Models.Contact;
import com.example.assessment2.R;

import java.sql.Date;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ContactAPIService.ResultsHandler {
public enum RequestCode
{
    VIEW_DETAIL_REQUEST_CODE,
}
ContactDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = ContactDatabase.getDBInstance(MainActivity.this);
        reinitializeDatabase();
        Button but = findViewById(R.id.button);

        ContactAPIService apiService = ContactAPIService.getInstance();
        apiService.ReadAllContacts(this);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void CreateOnResponseHandler(Contact contact) {

    }

    @Override
    public void SingleReadOnResponseHandler(Contact contact) {

    }

    @Override
    public void ReadAllOnResponseHandler(List<Contact> contactList)
    {
        for(Contact obj : contactList)
        {
            db.contactDao().insertContacts(obj);
        }
    }

    @Override
    public void EditOnResponseHandler() {

    }

    @Override
    public void DeleteOnResponseHandler(Contact contact) {

    }

    @Override
    public void OnFailureHandler() {

    }

    private void reinitializeDatabase()
    {
        db.contactDao().ClearTable();

        db.contactDao().insertContacts(new Contact("Alex","Smith", "01 8283 2831", Date.valueOf("2012-09-09")));
        db.contactDao().insertContacts(new Contact("Alex", "Wilson", "01 8283 2831", Date.valueOf("2020-09-01")));
        db.contactDao().insertContacts(new Contact("Max", "Smith", "01 8283 2831", Date.valueOf("2019-09-09")));
        db.contactDao().insertContacts(new Contact("Johny", "Coleson", "01 8283 2831", Date.valueOf("2013-09-12")));
        db.contactDao().insertContacts(new Contact("Tim", "Smith", "01 8283 2831", Date.valueOf("2017-09-03")));
        db.contactDao().insertContacts(new Contact("Banjo", "You", "01 8283 2831", Date.valueOf("2016-09-04")));
    }
}