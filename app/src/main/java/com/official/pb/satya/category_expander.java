package com.official.pb.satya;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.official.pb.satya.Genre_details.genre_detail_items_recycler_view_adapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class category_expander extends Fragment {

    RoundedImageView banner;
    RecyclerView recyclerView;
    genre_detail_items_recycler_view_adapter adapter;
    DatabaseReference databaseReference;
    List<Banner_list> list;
    String key;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_category_expander, container, false);
        banner=v.findViewById(R.id.banner);

        recyclerView=v.findViewById(R.id.category_list);
        databaseReference= FirebaseDatabase.getInstance().getReference("Banners");
        list=new ArrayList<>();

        Bundle bundle=getArguments();
        key=bundle.getString("key");


        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                  Banner_list banneritem=dataSnapshot.child(key).getValue(Banner_list.class);
                  list.add(banneritem);

                adapter=new genre_detail_items_recycler_view_adapter(getContext(),list.get(0).getList());
                recyclerView.setAdapter(adapter);

                Picasso.with(getContext()).load(dataSnapshot.child(key).child("banner_url").getValue(String.class)).into(banner);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        return v;
    }
}
