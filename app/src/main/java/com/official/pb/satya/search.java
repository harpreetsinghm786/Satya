package com.official.pb.satya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity {
    public EditText searchView;
    List<search_list> list;
    List<History_list> list2;
    RecyclerView recyclerView,recyclerView2;
    search_view_adapter adapter;
    History_adapter adapter2;
    FirebaseAuth auth;
    ImageView search;


    DatabaseReference reference,reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchbar);
        search=findViewById(R.id.searchit);
        auth=FirebaseAuth.getInstance();
        LinearLayoutManager layoutManager=new LinearLayoutManager(search.this,RecyclerView.VERTICAL,false);
        LinearLayoutManager layoutManager2=new LinearLayoutManager(search.this,RecyclerView.VERTICAL,false);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView2=findViewById(R.id.recyclerviewHistory);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.blackover));
        }
        reference = FirebaseDatabase.getInstance().getReference("search_list");
        reference2= FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("History");
        list = new ArrayList<>();
        list2=new ArrayList<>();
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    History_list detail = dataSnapshot1.getValue(History_list.class);
                    list2.add(0, detail);


                }
                adapter2=new History_adapter(search.this,list2,searchView);
                recyclerView2.setAdapter(adapter2);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    product_detail detail=dataSnapshot1.getValue(product_detail.class);
                    search_list search_list=new search_list(detail.getName(),dataSnapshot1.getKey(),detail.getItem_components(),detail.getGenre());
                    list.add(0,search_list);
                }
                adapter=new search_view_adapter(search.this,list,searchView);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE) {
                    //your functionality

                    if(!TextUtils.isEmpty(searchView.getText().toString())){
                    //text.setSelection(text.getText().length());
                    searchView.clearFocus();
                    Bundle bundle = new Bundle();

                    bundle.putString("key", searchView.getText().toString());

                    search_results frag = new search_results();
                    frag.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag).commit();


                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    return true;
                }
                }
                return false;
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //text.setSelection(text.getText().length());
                if (!TextUtils.isEmpty(searchView.getText().toString())) {
                    searchView.clearFocus();
                    Bundle bundle = new Bundle();

                    bundle.putString("key", searchView.getText().toString());

                    search_results frag = new search_results();
                    frag.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag).commit();


                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                }
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchView.requestFocus();

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter=new search_view_adapter(search.this,list,searchView);

                filter(s.toString());

            }
        });
    }

    private  void filter(String text){
        String[] a=text.split(" ");
        List<search_list> filterlist=new ArrayList<>();
        for(search_list item:list){
           for(int i=0;i<a.length;i++){
                if(item.getUname().toLowerCase().contains(a[i])){
                filterlist.add(item);
            }
           }
        }
        adapter.filterlist(filterlist);
        if(text.equals("")){
            recyclerView.setVisibility(View.GONE);
            recyclerView2.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);

        }
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       if (fragment != null) {
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();

        } else {
            super.onBackPressed();
        }

    }
}
