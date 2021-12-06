package com.example.assessment2.Activities;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assessment2.Database.ContactAPIService;
import com.example.assessment2.Database.ContactDatabase;
import com.example.assessment2.Models.Contact;
import com.example.assessment2.Models.ContactSingleton;
import com.example.assessment2.R;
import com.example.assessment2.Utilities.ArrayActions.Convert;
import com.example.assessment2.Utilities.ContactListAdapter;
import com.example.assessment2.Utilities.MyHash;
import com.example.assessment2.Utilities.Sort;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements ContactAPIService.ResultsHandler
{
    private MyHash hash;
    private String TAG = this.getClass().getSimpleName();
    ContactDatabase db;
    ContactSingleton singleton = ContactSingleton.getInstance();
    //private static List<Contact> contactList;
    ContactListAdapter contactListAdapter;
    RecyclerView contactListview;

    List<Contact> initialList;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonebook_list);
        db = ContactDatabase.getDBInstance(ListActivity.this);

        //initialises the list with the database list of contacts
         initialList = db.contactDao().getAllContacts();
         contactListview = findViewById(R.id.recycleListView);
        hash = new MyHash();

        //creates hash table using the initial list given by the database
        hash.BuildHashTable(initialList, hash.getHashTable());

        //loads contact list from hashList into adapter
        contactListAdapter = new ContactListAdapter(Convert.toList(hash.getHashTable()), ListActivity.this);

        //sets the adapter of the recycler view.
        contactListview.setAdapter(contactListAdapter);
        contactListview.setLayoutManager(new LinearLayoutManager(this));

        //sets the buttons of the side bars
        setAllNavBtnCLickListener();


        //initialises the buttons and search view
        Button btnAdd = findViewById(R.id.btn_phoneitemlist_add);
        Button btnDetail = findViewById(R.id.btn_phoneitemlist_detail);
        Button btnDelete = findViewById(R.id.btn_phoneitemlist_delete);
        Button btnSort = findViewById(R.id.btn_phoneitemlist_sort);
        SearchView srcView = (SearchView) findViewById(R.id.search_phoneitemlist);


        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sorts the list of the initial list by last name and resets the adapter
                contactListAdapter = new ContactListAdapter(sort(initialList), ListActivity.this);
                contactListview.setAdapter(contactListAdapter);
                contactListview.setLayoutManager(new LinearLayoutManager(ListActivity.this));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirects to AddActivity
                Intent intent = new Intent(ListActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    singleton = ContactSingleton.getInstance();
                    Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                    Bundle extras = new Bundle();

                    //assigns the string values to the values of the properties of the contact singleton
                    String firstName = singleton.getFirstName();
                    String lastName = singleton.getLastName();
                    String phonenumber = singleton.getPhoneNumber();
                    String dob = singleton.getContactCreationDate();

                    //Assigns the String values to the bundle
                    extras.putString("firstname", firstName);
                    extras.putString("lastname", lastName);
                    extras.putString("phoneNumber", phonenumber);
                    extras.putString("dob", dob);
                    intent.putExtras(extras);
                    //redirects to the detail page
                    startActivity(intent);
                }
                catch(Exception e)
                {
                    e.getStackTrace();
                }
            }
        });


        srcView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {



                //searches for given object by the value provided by the search view
                Contact result = Sort.binarySearch(s, initialList);

                //creates new list to insert a single value of the result which will filter the
                //adapterList by only presenting the found contact
                List<Contact> list = new ArrayList<>();
                list.add(result);

                contactListAdapter.filteredList(list);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });



        contactListAdapter.onAttachedToRecyclerView(contactListview);

        //Listens for the drag provided by the dragged card
      btnDelete.setOnDragListener(new View.OnDragListener() {

          @Override
          public boolean onDrag(View view, DragEvent dragEvent) {
              final View draggedView = (View) dragEvent.getLocalState();
              switch (dragEvent.getAction())
              {
                  case DragEvent.ACTION_DRAG_STARTED:
                      Log.d(TAG, "onDrag: started");

                      return true;
                  case DragEvent.ACTION_DRAG_ENTERED:
                      Log.d(TAG, "onDrag: entered");

                      return  true;
                  case DragEvent.ACTION_DRAG_EXITED:
                      Log.d(TAG, "onDrag: Exited");

                      return true;
                  case DragEvent.ACTION_DROP:
                      Log.d(TAG, "onDrag: Dropped");

                        //removes the object at the given position when the contact has been dropped on it
                          contactListAdapter.dataSet.remove(singleton.getPos());
                          db.contactDao().deleteStudents(singleton.getContact());
                          contactListAdapter.notifyDataSetChanged();

                          view.setTag("dropped");



                          ViewGroup owner = (ViewGroup) draggedView.getParent();
                          owner.removeView(draggedView);
                          Log.d(TAG, "onDrag: Dropped Success");
                          return true;




                  case DragEvent.ACTION_DRAG_ENDED:

                      if (dragEvent.getResult()) {
                          Log.d(TAG, "onDrag: Successful");


                      } else {
                          draggedView.post(new Runnable() {
                              @Override
                              public void run() {
                                  draggedView.setVisibility(View.VISIBLE);
                              }
                          });

                          Log.d(TAG, "onDrag: Unsuccessful");
                      }
                  default:
                      break;
              }
              return false;
          }
      });



    }









    @Override
    public void CreateOnResponseHandler(Contact contact) {

    }

    //sorts list of contacts by lastname in a descending order
    private List<Contact> sort(List<Contact> list)
    {
        for(int i = 0; i < list.size() - 1; i++)
        {
            for (int j = 0; j < list.size()- 1; j++)
            {
                if(list.get(j).getLastName().compareTo(list.get(j+1).getLastName()) > 0 )
                {
                    Contact temp = list.get(j);
                    list.set(j, list.get(j+1));
                    list.set(j+1, temp);
                }
            }
        }

        return list;
    }


    @Override
    public void OnFailureHandler(Throwable t) {
        Log.d(TAG, "Retrofit Exception -> " + ((t != null && t.getMessage() != null) ? t.getMessage() : "---"));
    }

    private void navBtnClick(int key)
    {
        //if the key provided is greater than 26 or less than 0 return null else get offset
        if(key < 0 || key > 26)
        {
            return;
        }

        int offset = hash.calcOffsetByKey(key);
        ((LinearLayoutManager) contactListview.getLayoutManager()).scrollToPositionWithOffset(offset, 0);
    }

    //Listens for whatever button is pressed determined by the key
    private  void setAllNavBtnCLickListener()
    {
        findViewById(R.id.btn_main_nav_ee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(0);
            }
        });
        findViewById(R.id.btn_main_nav_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(1);
            }
        });
        findViewById(R.id.btn_main_nav_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(2);
            }
        });
        findViewById(R.id.btn_main_nav_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(3);
            }
        });
        findViewById(R.id.btn_main_nav_d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(4);
            }
        });
        findViewById(R.id.btn_main_nav_e).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(5);
            }
        });
        findViewById(R.id.btn_main_nav_f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(6);
            }
        });
        findViewById(R.id.btn_main_nav_g).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(7);
            }
        });
        findViewById(R.id.btn_main_nav_h).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(8);
            }
        });
        findViewById(R.id.btn_main_nav_i).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(9);
            }
        });
        findViewById(R.id.btn_main_nav_j).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(10);
            }
        });
        findViewById(R.id.btn_main_nav_k).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(11);
            }
        });
        findViewById(R.id.btn_main_nav_l).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(12);
            }
        });
        findViewById(R.id.btn_main_nav_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(13);
            }
        });
        findViewById(R.id.btn_main_nav_n).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(14);
            }
        });
        findViewById(R.id.btn_main_nav_o).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(15);
            }
        });
        findViewById(R.id.btn_main_nav_p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(16);
            }
        });
        findViewById(R.id.btn_main_nav_q).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(17);
            }
        });
        findViewById(R.id.btn_main_nav_r).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(18);
            }
        });
        findViewById(R.id.btn_main_nav_s).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(19);
            }
        });
        findViewById(R.id.btn_main_nav_t).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(20);
            }
        });
        findViewById(R.id.btn_main_nav_u).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(21);
            }
        });
        findViewById(R.id.btn_main_nav_v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(22);
            }
        });
        findViewById(R.id.btn_main_nav_w).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(23);
            }
        });
        findViewById(R.id.btn_main_nav_x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(24);
            }
        });
        findViewById(R.id.btn_main_nav_y).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(25);
            }
        });
        findViewById(R.id.btn_main_nav_z).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.navBtnClick(26);
            }
        });

    }





}
