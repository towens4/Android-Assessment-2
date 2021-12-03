package com.example.assessment2.Activities;

import android.content.ClipData;
import android.content.ClipDescription;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assessment2.Database.ContactAPIService;
import com.example.assessment2.Database.ContactDatabase;
import com.example.assessment2.Models.APIContact;
import com.example.assessment2.Models.Contact;
import com.example.assessment2.Models.ContactSingleton;
import com.example.assessment2.R;
import com.example.assessment2.Utilities.ArrayActions.Convert;
import com.example.assessment2.Utilities.ArrayActions.Offset;
import com.example.assessment2.Utilities.Box;
import com.example.assessment2.Utilities.ContactClickInterface;
import com.example.assessment2.Utilities.ContactListAdapter;
import com.example.assessment2.Utilities.ContactListClickInterface;
import com.example.assessment2.Utilities.HashProcess.Hash;
import com.example.assessment2.Utilities.MyDragShadowBuilder;
import com.example.assessment2.Utilities.MyHash;
import com.example.assessment2.Utilities.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListActivity extends AppCompatActivity implements ContactAPIService.ResultsHandler, ItemTouchHelper.ViewDropHandler, ContactClickInterface
{
    private MyHash hash;
    private String TAG = this.getClass().getSimpleName();
    ContactDatabase db;
    ContactSingleton singleton = ContactSingleton.getInstance();
    //private static List<Contact> contactList;
    ContactListAdapter contactListAdapter;
    RecyclerView contactListview;
    ContactClickInterface clickInterface;
    List<Contact> initialList;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonebook_list);
        db = ContactDatabase.getDBInstance(ListActivity.this);

         initialList = db.contactDao().getAllContacts();
         contactListview = findViewById(R.id.recycleListView);
        hash = new MyHash();

        hash.BuildHashTable(initialList, hash.getHashTable());

        //loads contact list from database into adapter
        contactListAdapter = new ContactListAdapter(Convert.toList(hash.getHashTable()), ListActivity.this, this);

        contactListview.setAdapter(contactListAdapter);
        contactListview.setLayoutManager(new LinearLayoutManager(this));

        setAllNavBtnCLickListener();


            Button btnAdd = findViewById(R.id.btn_phoneitemlist_add);
            Button btnDetail = findViewById(R.id.btn_phoneitemlist_detail);
            Button btnDelete = findViewById(R.id.btn_phoneitemlist_delete);
            Button btnSort = findViewById(R.id.btn_phoneitemlist_sort);
            SearchView srcView = (SearchView) findViewById(R.id.search_phoneitemlist);

            btnSort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    contactListAdapter = new ContactListAdapter(sort(initialList), ListActivity.this, clickInterface);
                    contactListview.setAdapter(contactListAdapter);
                    contactListview.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                }
            });

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                        String firstName = singleton.getFirstName();

                        String lastName = singleton.getLastName();
                        String phonenumber = singleton.getPhoneNumber();
                        String dob = singleton.getContactCreationDate();

                        extras.putString("firstname", firstName);
                        extras.putString("lastname", lastName);
                        extras.putString("phoneNumber", phonenumber);
                        extras.putString("dob", dob);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                    catch(Exception e)
                    {
                        e.getStackTrace();
                    }
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //RecyclerView.ViewHolder holder = contactListview.findViewHolderForAdapterPosition(getPosition());

                    ContactDatabase.getDBInstance(ListActivity.this).contactDao().deleteStudents(singleton.getContact());
                    contactListAdapter.dataSet.get(singleton.getPos());
                }
            });

            srcView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {




                        Contact result = Sort.binarySearch(s, initialList);


                        List<Contact> list = new ArrayList<>();
                        list.add(result);
                        contactListAdapter.filteredList(list);
                        /*contactListAdapter = new ContactListAdapter(list, ListActivity.this);
                         contactListview.setAdapter(contactListAdapter);
                         contactListview.setLayoutManager(new LinearLayoutManager(ListActivity.this));*/





                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });

            //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            //itemTouchHelper.attachToRecyclerView(contactListview);

            contactListAdapter.onAttachedToRecyclerView(contactListview);

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

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Button btnDelete = findViewById(R.id.btn_phoneitemlist_delete);




            //Collections.swap(Convert.toList(hash.getHashTable()), fromPosition, toPosition);

            //prepareForDrop(viewHolder.itemView, btnDelete, 0, 0);

            //recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            ContactDatabase.getDBInstance(ListActivity.this).contactDao().deleteStudents(contactListAdapter.dataSet.get(viewHolder.getAdapterPosition()));
            recyclerView.getAdapter().notifyDataSetChanged();

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }



    };




    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initItems()
    {
        //contactList = db.contactDao().getAllContacts();
        //String str = "2000-05-05";

        /*.add(new Contact("Alex","Smith", "01 8283 2831", Date.valueOf(str)));
        contactList.add(new Contact("Alex", "Wilson", "01 8283 2831", Date.valueOf(str)));
        contactList.add(new Contact("Max", "Smith", "01 8283 2831", Date.valueOf(str)));
        contactList.add(new Contact("Johny", "Coleson", "01 8283 2831", Date.valueOf(str)));
        contactList.add(new Contact("Tim", "Smith", "01 8283 2831", Date.valueOf(str)));
        contactList.add(new Contact("Banjo", "You", "01 8283 2831", Date.valueOf(str)));*/
    }


    @Override
    public void CreateOnResponseHandler(Contact contact) {

    }

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
        if(key < 0 || key > 26)
        {
            return;
        }

        int offset = hash.calcOffsetByKey(key);
        ((LinearLayoutManager) contactListview.getLayoutManager()).scrollToPositionWithOffset(offset, 0);
    }

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

    @Override
    public void prepareForDrop(@NonNull View view, @NonNull View target, int x, int y) {


        target.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int x = (int)event.getRawX();
                int y = (int)event.getRawY();
                if(event.getAction() == MotionEvent.ACTION_HOVER_EXIT){
                    if(isViewInBounds(target, x, y))
                        target.dispatchTouchEvent(event);
                    else if(isViewInBounds(target, x, y)){
                        Log.d(TAG, "onTouch target");
                        ContactDatabase.getDBInstance(ListActivity.this).contactDao().deleteStudents(singleton.getContact());
                    }
                }
                return false;
            }
        });

        view.offsetLeftAndRight(x);
        view.offsetTopAndBottom(y);

        db.contactDao().deleteStudents(singleton.getContact());
    }

    private boolean isViewInBounds(View view, int x, int y)
    {
        Rect outRect = new Rect();
        int[] location = new int[2];
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x,y);
    }


    @Override
    public int getPosition(int position) {
        return position;
    }
}
