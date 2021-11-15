package com.example.assessment2.Database;

import com.example.assessment2.Models.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactAPIService
{
    private static ContactAPIService remoteDBSingletonInstance = null;
    private static RemoteDB service;

    private ContactAPIService()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.2:5000/swagger/")
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
                handler.OnFailureHandler();
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
                handler.CreateOnResponseHandler(contact);
                return;
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                handler.OnFailureHandler();
                return;
            }
        });
        return;
    }

    public void ReadAllContacts(final ResultsHandler handler)
    {
        Call<List<Contact>> contactReadAll = service.GetAllContacts();
        contactReadAll.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contactList = response.body();
                handler.ReadAllOnResponseHandler(contactList);
                return;
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                handler.OnFailureHandler();
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
                handler.OnFailureHandler();
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
                handler.OnFailureHandler();
                return;
            }
        });
        return;
    }


    public interface ResultsHandler
    {
        void CreateOnResponseHandler(Contact contact);
        void SingleReadOnResponseHandler(Contact contact);
        void ReadAllOnResponseHandler(List<Contact> contactList);
        void EditOnResponseHandler();
        void DeleteOnResponseHandler(Contact contact);
        void OnFailureHandler();
    }
}
