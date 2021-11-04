package com.example.assessment2.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assessment2.Models.Contact;
import com.example.assessment2.R;

public class EditActivity extends AppCompatActivity
{
    private static final String TAG = "EditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_view);

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        if(getIntent().hasExtra("contactObject"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras");

            Contact contactObject = getIntent().getParcelableExtra("contactObject");
            setContact(contactObject);
        }
    }

    private void setContact(Contact contactObject)
    {
        Log.d(TAG, "setContact: setting contact to edit text widgets");

        EditText txtName = findViewById(R.id.edit_editview_firstname);
        txtName.setText(contactObject.getFirstName());
        EditText lastName = findViewById(R.id.edit_editview_lastname);
        lastName.setText(contactObject.getLastName());
        EditText txtPhonenum = findViewById(R.id.edit_editview_phonenumber);
        txtPhonenum.setText(contactObject.getPhoneNumber());
        EditText txtDate = findViewById(R.id.edit_editview_date);
        txtDate.setText(contactObject.getContactCreationDate().toString());
    }
}
