package com.example.assessment2.Utilities;

import com.example.assessment2.Activities.EditActivity;
//import  com.example.assessment2.Models.Contact;
import com.example.assessment2.Models.Contact;
import com.example.assessment2.Models.ContactSingleton;
import com.example.assessment2.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>
{
    public ArrayList<Contact> dataSet;
    private Context c;

    ContactSingleton singleton;

    public ContactListAdapter(ArrayList<Contact> dataSet, Context c)
    {
        this.dataSet = dataSet;
        this.c = c;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_item_view, parent, false);
        ContactViewHolder cvh = new ContactViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtFirstname.setText(dataSet.get(position).getFirstName());
        holder.txtPhoneNum.setText(dataSet.get(position).getPhoneNumber());
        holder.txtDate.setText(dataSet.get(position).getDob().toString());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
               try{
                   String firstName = dataSet.get(position).getFirstName();
                   String lastName = dataSet.get(position).getLastName();
                   String phonenumber = dataSet.get(position).getPhoneNumber();
                   String dob = dataSet.get(position).getDob().toString();

                   //LocalDate date = LocalDate.parse(dob);
                   Intent intent = new Intent(c, EditActivity.class);
                   Bundle extras = new Bundle();
                   extras.putString("firstname", firstName);
                   extras.putString("lastname", lastName);
                   extras.putString("phoneNumber", phonenumber);
                   extras.putString("dob", dob);
                   intent.putExtras(extras);
                   //intent.putExtra("contactObject",  new Contact(firstName, lastName, phonenumber, Date.valueOf(dob)));

                   c.startActivity(intent);
               }
               catch(Exception e)
               {
                   e.printStackTrace();
                   e.getMessage();
               }
            }
        });

        final float dx = holder.viewPane.getTranslationX();
        System.out.println(dx);
        holder.viewPane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = dataSet.get(position).getFirstName();
                String lastName = dataSet.get(position).getLastName();
                String phonenumber = dataSet.get(position).getPhoneNumber();
                String dob = dataSet.get(position).getDob().toString();

                singleton = ContactSingleton.getInstance();
                singleton.setPos(holder.getAdapterPosition());
                singleton.setFirstName(firstName);
                singleton.setLastName(lastName);
                singleton.setPhoneNumber(phonenumber);
                singleton.setContactCreationDate(dob);
                AnimationHandler.moveCard(dx, -150, holder.viewPane, holder.btnEdit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder
    {
        private TextView txtFirstname;
        private TextView txtLastname;
        private TextView txtPhoneNum;
        private TextView txtDate;
        private Button btnEdit;
        private CardView viewPane;

        //private final Context context;

        public Contact contactData;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFirstname = itemView.findViewById(R.id.txt_piv_firstname);
            txtLastname = itemView.findViewById(R.id.txt_piv_lastname);
            txtPhoneNum = itemView.findViewById(R.id.txt_piv_phonenum);
            txtDate = itemView.findViewById(R.id.txt_piv_date);

            viewPane = itemView.findViewById(R.id.view_card_contact);
            btnEdit = itemView.findViewById(R.id.btn_edit);

            //context = itemView.getContext();

            /*btnEdit.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    Intent intent  = new Intent(itemView.getContext(), EditActivity.class);
                    LocalDate date = LocalDate.parse(txtDate.toString(), DateTimeFormatter.ofPattern("d, mmmm, yyyy", Locale.ENGLISH));
                    intent.putExtra("contactObject", (Parcelable) new Contact(txtName.toString(), txtPhoneNum.toString(), Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
                    context.startActivity(intent);
                }
            });*/

        }
    }
}
