package com.example.assessment2.Utilities;

import com.example.assessment2.Activities.EditActivity;
import  com.example.assessment2.Models.Contact;
import com.example.assessment2.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>
{
    public ArrayList<Contact> dataSet;
    private Context c;



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
        holder.txtDate.setText(dataSet.get(position).getContactCreationDate().toString());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
               try{
                   String firstName = dataSet.get(position).getFirstName();
                   String lastName = dataSet.get(position).getLastName();
                   String phonenumber = dataSet.get(position).getPhoneNumber();
                   String dob = dataSet.get(position).getContactCreationDate().toString();

                   LocalDate date = LocalDate.parse(dob.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH));
                   Intent intent = new Intent(c, EditActivity.class);
                   intent.putExtra("contactObject", (Parcelable) new Contact(firstName, lastName, phonenumber, Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
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