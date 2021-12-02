package com.example.assessment2.Utilities;

import com.example.assessment2.Activities.EditActivity;
//import  com.example.assessment2.Models.Contact;
import com.example.assessment2.Models.Contact;
import com.example.assessment2.Models.ContactSingleton;
import com.example.assessment2.R;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>
{

    public List<Contact> dataSet;
    private List<Contact> dataSetFull;
    private Context c;
    private ContactClickInterface clickInterface;

    RecyclerView _RecyclerView;

    ContactSingleton singleton;

    public ContactListAdapter(List<Contact> dataSet, Context c, ContactClickInterface clickInterface)
    {
        this.dataSet = dataSet;
        this.dataSetFull = new ArrayList<>(dataSet);
        this.clickInterface = clickInterface;
        this.c = c;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        _RecyclerView = recyclerView;
    }

    public void filteredList(List<Contact> filterList)
    {
        dataSet = filterList;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_item_view, parent, false);
        ContactViewHolder cvh = new ContactViewHolder(v);
        return cvh;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtFirstname.setText(dataSet.get(position).getFirstName());
        holder.txtLastname.setText(dataSet.get(position).getLastName());
        holder.txtPhoneNum.setText(dataSet.get(position).getPhoneNumber());
        holder.txtDate.setText(dataSet.get(position).getDob().toString());

        clickInterface.getPosition(holder.getAdapterPosition());
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
                Contact contact = dataSet.get(position);

                singleton = ContactSingleton.getInstance();
                singleton.setPos(holder.getAdapterPosition());
                singleton.setFirstName(firstName);
                singleton.setLastName(lastName);
                singleton.setPhoneNumber(phonenumber);
                singleton.setContactCreationDate(dob);
                singleton.setID(dataSet.get(position).getId());
                singleton.setContact(contact);
                AnimationHandler.moveCard(dx, -150, holder.viewPane, holder.btnEdit);
            }
        });

        holder.viewPane.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

                ClipData dragData = new ClipData(
                        (CharSequence) view.getTag(),
                        new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN},
                        item
                );

                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(dragData, shadowBuilder, view, 0);
                ContactSingleton.getInstance().setPos(holder.getAdapterPosition());
                ContactSingleton.getInstance().setContact(dataSet.get(position));
                return false;
            }
        });

       /* holder.viewPane.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, @SuppressLint("ClickableViewAccessibility") MotionEvent motionEvent)
            {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

                        ClipData dragData = new ClipData(
                                (CharSequence) view.getTag(),
                          new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN},
                          item
                        );

                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                        view.startDragAndDrop(dragData, shadowBuilder, view, 0);
                        ContactSingleton.getInstance().setPos(holder.getAdapterPosition());
                        ContactSingleton.getInstance().setContact(dataSet.get(position));


                    return true;
                }
                else {


                    return false;
                }
            }
        });*/
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



        public Contact contactData;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFirstname = itemView.findViewById(R.id.txt_piv_firstname);
            txtLastname = itemView.findViewById(R.id.txt_piv_lastname);
            txtPhoneNum = itemView.findViewById(R.id.txt_piv_phonenum);
            txtDate = itemView.findViewById(R.id.txt_piv_date);

            viewPane = itemView.findViewById(R.id.view_card_contact);
            btnEdit = itemView.findViewById(R.id.btn_edit);


        }
    }
}
