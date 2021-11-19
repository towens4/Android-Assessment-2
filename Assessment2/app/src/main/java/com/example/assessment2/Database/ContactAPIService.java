package com.example.assessment2.Database;

import android.util.Log;

import com.example.assessment2.Models.APIContact;
import com.example.assessment2.Models.Contact;
import com.example.assessment2.Utilities.UnsafeOkHttpClient;

import java.util.List;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
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

    //OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    private ContactAPIService()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.2:443/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteDB.class);
    }

    public static ContactAPIService getInstance()
    {
        if(remoteDBSingletonInstance == null)
        {
            remoteDBSingletonInstance = new ContactAPIService();
        }
        return remoteDBSingletonInstance;
    }

    public void ReadOneContact(int id, final ResultsHandler handler)
    {
        Call<Contact> contactReadOne = service.Contact(id);
        contactReadOne.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                Contact contact = response.body();
                handler.SingleReadOnResponseHandler(contact);
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                handler.OnFailureHandler(t);
                return;
            }
        });

        return;
    }

    public void ContactCreate(Contact contact, final ResultsHandler handler)
    {
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

    public void ReadAllContacts(final ResultsHandler handler)
    {
        Call<List<APIContact>> contactReadAll = service.GetAllContacts();
        contactReadAll.enqueue(new Callback<List<APIContact>>() {



            @Override
            public void onResponse(Call<List<APIContact>> call, Response<List<APIContact>> response) {

                Log.d(TAG, "Response Contents" + response.body().toString());
                List<APIContact> contactList = response.body();
                handler.ReadAllOnResponseHandler(contactList);
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

    public void EditContact(int id, Contact contact, final ResultsHandler handler)
    {
        Call<Void> editContact = service.UpdateContact(id, contact);
        editContact.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                handler.EditOnResponseHandler();
                return;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                handler.OnFailureHandler(t);
                return;
            }
        });
        return;
    }

    public void DeleteContact(int id, final ResultsHandler handler)
    {
        Call<Contact> contactDelete = service.ContactDelete(id);
        contactDelete.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                Contact contact = response.body();
                handler.DeleteOnResponseHandler(contact);
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


    public interface ResultsHandler
    {
        void CreateOnResponseHandler(Contact contact);
        void SingleReadOnResponseHandler(Contact contact);
        void ReadAllOnResponseHandler(List<APIContact> contactList);
        void EditOnResponseHandler();
        void DeleteOnResponseHandler(Contact contact);
        void OnFailureHandler(Throwable t);
    }
}
