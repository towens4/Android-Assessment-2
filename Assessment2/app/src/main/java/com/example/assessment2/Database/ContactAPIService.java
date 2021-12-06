package com.example.assessment2.Database;

import android.util.Log;

import com.example.assessment2.Models.APIContact;
import com.example.assessment2.Models.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactAPIService
{
    private String TAG = this.getClass().getSimpleName();
    private static ContactAPIService remoteDBSingletonInstance = null;
    private static RemoteDB service;

    private ContactAPIService()
    {
        //creates retrofit object using url and GsonConvertor factory
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Assigns the retrofit object to the service interface
        service = retrofit.create(RemoteDB.class);
    }

    //gets or creates instance of ContactAPIService
    public static ContactAPIService getInstance()
    {
        if(remoteDBSingletonInstance == null)
        {
            remoteDBSingletonInstance = new ContactAPIService();
        }
        return remoteDBSingletonInstance;
    }



    public void ContactCreate(Contact contact, final ResultsHandler handler)
    {
        Log.d(TAG,"Contact = " + contact);
        Call<Contact> createContact = service.ContactCreate(contact);
        createContact.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                Contact contact1 = response.body();
                handler.CreateOnResponseHandler(contact1);
                return;
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                handler.OnFailureHandler(t);
                return;
            }
        });
        return;
    }

    //Reads all contacts in the server-side database
    public void ReadAllContacts(final ResultsHandler handler)
    {
        Call<List<APIContact>> contactReadAll = service.GetAllContacts();
        contactReadAll.enqueue(new Callback<List<APIContact>>() {



            @Override
            public void onResponse(Call<List<APIContact>> call, Response<List<APIContact>> response) {

                Log.d(TAG, "Response Contents" + response.body().toString());
                List<APIContact> contactList = response.body();
                //handler.ReadAllOnResponseHandler(contactList);
                return;
            }

            @Override
            public void onFailure(Call<List<APIContact>> call, Throwable t) {
                handler.OnFailureHandler(t);
                return;
            }
        });
        return;
    }

    public interface ResultsHandler
    {
        void CreateOnResponseHandler(Contact contact);
        void OnFailureHandler(Throwable t);
    }
}
