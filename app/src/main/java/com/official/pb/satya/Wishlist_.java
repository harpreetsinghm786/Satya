package com.official.pb.satya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Wishlist_ extends AppCompatActivity {

    RecyclerView recyclerView;
    Wishlist_adapter wishlist_adapter;
    List<Wishlist_list> list;
    DatabaseReference reference;
    LinearLayout emptywishlist;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_);

        recyclerView=findViewById(R.id.my_recycler_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.color1));
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(Wishlist_.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        emptywishlist=findViewById(R.id.emptywishlist);
        list=new ArrayList<>();
        auth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("wishlist");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Wishlist_list wishlist_list=dataSnapshot1.getValue(Wishlist_list.class);

                    list.add(wishlist_list);
                }
                if(list.size()!=0){
                    recyclerView.setVisibility(View.VISIBLE);
                    wishlist_adapter=new Wishlist_adapter(Wishlist_.this,list);
                    recyclerView.setAdapter(wishlist_adapter);}else{
                    emptywishlist.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Fragment frameLayout=getSupportFragmentManager().findFragmentById(R.id.myframelayout);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(frameLayout!=null){
            fragmentTransaction.remove(frameLayout);
            fragmentTransaction.commit();
        }else{
            super.onBackPressed();}
    }
}
