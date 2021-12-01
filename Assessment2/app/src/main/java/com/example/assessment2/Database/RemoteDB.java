package com.example.assessment2.Database;

import androidx.room.Delete;

import com.example.assessment2.Models.APIContact;
import com.example.assessment2.Models.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface RemoteDB
{
    @POST("Contacts/")
    Call<Contact> ContactCreate(@Body Contact contact);

    @GET("Contacts")
    Call<List<APIContact>> GetAllContacts();
/*
    @GET("Contacts")
    Call<Contact> Contact(@Path("id") int id);

    @PUT("Contacts/{id}")
    Call<Void> UpdateContact(@Path("id") int id,@Body Contact contact);

    @DELETE("Contacts/{id}")
    Call<Contact> ContactDelete(@Path("id") int id);*/
}
