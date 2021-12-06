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
import com.example.assessment2.Models.APIContact;
import com.example.assessment2.Models.Contact;
import com.example.assessment2.Models.ContactSingleton;
import com.example.assessment2.R;

import java.sql.Date;
import java.util.List;

public class EditActivity extends AppCompatActivity
{
    private static final String TAG = "EditActivity";
    ContactDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_view);


        ContactSingleton contactSingleton = ContactSingleton.getInstance();

        getIncomingIntent();
        db = ContactDatabase.getDBInstance(EditActivity.this);
        EditText firstName = findViewById(R.id.edit_editview_firstname);
        EditText lastName = findViewById(R.id.edit_editview_lastname);
        EditText phoneNumber = findViewById(R.id.edit_editview_phonenumber);
        EditText dob = findViewById(R.id.edit_editview_date);

        Button btnEdit = findViewById(R.id.btn_editview_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sets initial contact from what was clicked in the ListActivity
                Contact contact = contactSingleton.getContact();

                //Assigns the new values to existing object
                contact.setFirstName(firstName.getText().toString());
                contact.setLastName(lastName.getText().toString());
                contact.setPhoneNumber(phoneNumber.getText().toString());
                contact.setDob(Date.valueOf(dob.getText().toString()));

                //edits existing contact
                db.contactDao().updateContacts(contact);

                Toast.makeText(EditActivity.this, "Contact Edited", Toast.LENGTH_SHORT).show();


                //redirects to ListActivity once the operation has been complete
                Intent intent = new Intent(EditActivity.this, ListActivity.class);
                startActivity(intent);
            }

        });
    }

    //passes in the bundle from ContactAdapter for the setting of the fields
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

        EditText txtName = findViewById(R.id.edit_editview_firstname);
        txtName.setText(contactObject.getString("firstname"));
        EditText lastName = findViewById(R.id.edit_editview_lastname);
        lastName.setText(contactObject.getString("lastname"));
        EditText txtPhonenum = findViewById(R.id.edit_editview_phonenumber);
        txtPhonenum.setText(contactObject.getString("phoneNumber"));
        EditText txtDate = findViewById(R.id.edit_editview_date);
        txtDate.setText(contactObject.getString("dob"));
    }
}
