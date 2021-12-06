package com.example.assessment2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assessment2.Database.ContactAPIService;
import com.example.assessment2.Database.ContactDatabase;
import com.example.assessment2.Database.RemoteDB;
import com.example.assessment2.Models.APIContact;
import com.example.assessment2.Models.Contact;
import com.example.assessment2.R;

import java.sql.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ContactAPIService.ResultsHandler {
private String isDarkThemeKey = "dark_theme";
private int activeThemeResId;
private GestureDetector gestureDetector;
public enum RequestCode
{
    VIEW_DETAIL_REQUEST_CODE,
}

private String TAG = this.getClass().getSimpleName();
ContactDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = ContactDatabase.getDBInstance(MainActivity.this);
        db.contactDao().ClearTable();
        reinitializeDatabase();
        Button but = findViewById(R.id.btn_main_view);
        Button camera = findViewById(R.id.btn_main_camera);



        ContactAPIService.getInstance().ReadAllContacts(this);



        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //directs to the ListActivity class
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }

        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opens the phones camera
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "ON start is called ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CreateOnResponseHandler(Contact contact) {
        Log.d(TAG, contact.toString());
    }


    @Override
    public void OnFailureHandler(Throwable t) {
        Log.d(TAG, "Retrofit Exception -> " + ((t != null && t.getMessage() != null) ? t.getMessage() : "---"));
    }

    //Loads in default data on startup to the database
    private void reinitializeDatabase()
    {
        db.contactDao().ClearTable();

        db.contactDao().insertContacts(new Contact("Alex","Smith", "01 8283 2831", Date.valueOf("2012-09-09")));
        db.contactDao().insertContacts(new Contact("Alex", "Wilson", "01 8283 2831", Date.valueOf("2020-09-01")));
        db.contactDao().insertContacts(new Contact("Max", "Smith", "01 8283 2831", Date.valueOf("2019-09-09")));
        db.contactDao().insertContacts(new Contact("Johny", "Coleson", "01 8283 2831", Date.valueOf("2013-09-12")));
        db.contactDao().insertContacts(new Contact("Tim", "Smith", "01 8283 2831", Date.valueOf("2017-09-03")));
        db.contactDao().insertContacts(new Contact("Banjo", "You", "01 8283 2831", Date.valueOf("2016-09-04")));
        db.contactDao().insertContacts(new Contact("Johny", "Coleson", "01 8283 2831", Date.valueOf("2013-09-12")));
        db.contactDao().insertContacts(new Contact("Melson", "Rudeger", "01 8283 2831", Date.valueOf("2013-09-12")));
        db.contactDao().insertContacts(new Contact("Kelly", "Flanagan", "01 8283 2831", Date.valueOf("2013-09-12")));
        db.contactDao().insertContacts(new Contact("Lorena", "Petersen", "01 8283 2831", Date.valueOf("2013-09-12")));
        db.contactDao().insertContacts(new Contact("Eliza", "Planse", "01 8283 2831", Date.valueOf("2013-09-12")));
        db.contactDao().insertContacts(new Contact("Carly", "Pierce", "01 8283 2831", Date.valueOf("2013-09-12")));
        db.contactDao().insertContacts(new Contact("Damien", "Thomand", "01 8283 2831", Date.valueOf("2013-09-12")));
    }

    private void loadAndSetTheme()
    {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        boolean isDark = sharedPreferences.getBoolean(isDarkThemeKey, false);
        activeThemeResId = isDark ? R.style.DarkTheme : R.style.AppTheme;
        setTheme(activeThemeResId);
    }

    private void switchAndSaveThemeSetting() {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(activeThemeResId == R.style.DarkTheme)
        {
            editor.putBoolean(isDarkThemeKey, true);
        }
        else{
            editor.putBoolean(isDarkThemeKey, true);
        }
        editor.commit();
    }
}