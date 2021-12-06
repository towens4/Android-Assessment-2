package com.example.assessment2.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assessment2.Models.Contact;
import com.example.assessment2.R;

public class DetailActivity extends AppCompatActivity
{
    private static final String TAG = "EditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);
        getIncomingIntent();
    }

    //passes in the bundle from ListActivity for the setting of the fields
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        Bundle bundle = getIntent().getExtras();

        Log.d(TAG, "getIncomingIntent: found intent extras");

        Contact contactObject = getIntent().getParcelableExtra("contactObject");
        setContact(bundle);

    }

    //sets the displayed contents of the contact
    private void setContact(Bundle contactObject)
    {
        Log.d(TAG, "setContact: setting contact to edit text widgets");

        EditText txtName = findViewById(R.id.detail_detailview_firstname);
        txtName.setText(contactObject.getString("firstname"));
        EditText lastName = findViewById(R.id.detail_detailview_lastname);
        lastName.setText(contactObject.getString("lastname"));
        EditText txtPhonenum = findViewById(R.id.detail_detailview_phonenumber);
        txtPhonenum.setText(contactObject.getString("phoneNumber"));
        EditText txtDate = findViewById(R.id.detail_detailview_date);
        txtDate.setText(contactObject.getString("dob"));
    }
}
