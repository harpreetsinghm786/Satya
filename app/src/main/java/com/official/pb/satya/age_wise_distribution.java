package com.official.pb.satya;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class age_wise_distribution extends Fragment {


    RoundedImageView banner;
    RecyclerView recyclerView;
    genre_detail_items_recycler_view_adapter adapter;
    DatabaseReference databaseReference;
    List<product_detail> list;
    String key;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_age_wise_distribution, container, false);

        recyclerView=v.findViewById(R.id.greoup_list);
        banner=v.findViewById(R.id.group_banner);
        databaseReference= FirebaseDatabase.getInstance().getReference("search_list");
        list=new ArrayList<>();

       Bundle bundle=getArguments();
       key=bundle.getString("key");
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    product_detail product_detail = dataSnapshot1.getValue(product_detail.class);
                   if(product_detail.getTags().toLowerCase().contains(key.toLowerCase()) && !product_detail.getTags().contains("mask")||product_detail.getName().toLowerCase().contains(key.toLowerCase())&&!product_detail.getTags().contains("mask")|| product_detail.getItem_components().toLowerCase().contains(key.toLowerCase())&&!product_detail.getTags().contains("mask")|| product_detail.getSub_category().toLowerCase().contains(key.toLowerCase())&&!product_detail.getTags().contains("mask")||product_detail.getGenre().toLowerCase().contains(key.toLowerCase())&&!product_detail.getTags().contains("mask")){
                    list.add(product_detail);
                }
                }
                adapter=new genre_detail_items_recycler_view_adapter(getContext(),list);
                recyclerView.setAdapter(adapter);


                if(key.toLowerCase().equals("womens")){
                    banner.setImageResource(R.mipmap.women_banner);

                }else   if(key.toLowerCase().equals("girls")){
                    banner.setImageResource(R.mipmap.girls_banner);
                }else   if(key.toLowerCase().equals("kids")){
                    banner.setImageResource(R.mipmap.kids_banner);
                }else   if(key.toLowerCase().equals("marriage")){
                    banner.setImageResource(R.mipmap.marriage_banner);
                }else   if(key.toLowerCase().equals("home,simple")){
                    banner.setImageResource(R.mipmap.simple_banner);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

}
