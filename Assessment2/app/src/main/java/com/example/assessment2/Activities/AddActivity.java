package com.example.assessment2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class AddActivity extends AppCompatActivity implements ContactAPIService.ResultsHandler
{
    private String TAG = this.getClass().getSimpleName();
    ContactDatabase db;
    Button btnAdd;
    EditText addFirstname, addLastname, addDob, addPhonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_view);
        btnAdd = findViewById(R.id.btn_addview_add);
        addFirstname = findViewById(R.id.add_addview_firstname);
        addLastname = findViewById(R.id.add_addview_lastname);
        addPhonenumber = findViewById(R.id.add_addview_phonenumber);
        addDob = findViewById(R.id.add_addview_date);
        db = ContactDatabase.getDBInstance(AddActivity.this);

        ContactAPIService api = ContactAPIService.getInstance();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Contact c = new Contact(addFirstname.getText().toString(), addLastname.getText().toString(),
                        addPhonenumber.getText().toString(), Date.valueOf(addDob.getText().toString()));

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.0.2/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RemoteDB service = retrofit.create(RemoteDB.class);

                Call<Contact> contactAdd = service.ContactCreate(c);

                contactAdd.enqueue(new Callback<Contact>() {
                    @Override
                    public void onResponse(Call<Contact> call, Response<Contact> response) {
                        Contact con = response.body();
                        Log.d(TAG, "Successful POST " + con);
                    }

                    @Override
                    public void onFailure(Call<Contact> call, Throwable t) {
                        Log.d(TAG, "Retrofit Exception -> " + ((t != null && t.getMessage() != null) ? t.getMessage() : "---"));
                    }
                });

                db.contactDao().insertContacts(c);

                api.ContactCreate(c, AddActivity.this);

                Toast.makeText(AddActivity.this, "Contact Added!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void CreateOnResponseHandler(Contact contact) {
        Log.d(TAG, "Contact has been added to API service");
    }

    @Override
    public void SingleReadOnResponseHandler(Contact contact) {

    }

    @Override
    public void ReadAllOnResponseHandler(List<APIContact> contactList) {

    }

    @Override
    public void EditOnResponseHandler() {

    }

    @Override
    public void DeleteOnResponseHandler(Contact contact) {

    }

    @Override
    public void OnFailureHandler(Throwable t) {
        Log.d(TAG, "Retrofit Exception -> " + ((t != null && t.getMessage() != null) ? t.getMessage() : "---"));
    }
}
