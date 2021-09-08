package com.official.pb.satya;


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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.official.pb.satya.Genre_details.genre_detail_items_recycler_view_adapter;

import java.util.ArrayList;
import java.util.List;

public class search_results extends Fragment {


    RecyclerView results;
    genre_detail_items_recycler_view_adapter adapter;
    DatabaseReference databaseReference;
    List<product_detail> list;
    String key;
    TextView numberof_results;
    List<user_review> reviews;
    RecyclerView user_reviews;
    user_review_adapter user_review_adapter;
    LinearLayout progressbar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_search_results, container, false);

        reviews=new ArrayList<>();


        user_reviews=v.findViewById(R.id.user_review_rv);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);

        user_reviews.setLayoutManager(linearLayoutManager2);


        reviews.add(new user_review("Harvinder Kaur","Satya is an awsome app where i found all the needy stuff that i can wear at home and for office as well. Apart from that the Services are so trust worthy. Order can be placed very simple method. I will give 5 out of 5 rating for this app.","https://firebasestorage.googleapis.com/v0/b/satya-8b1a7.appspot.com/o/my%20own%20pics%2Froobi.jpg?alt=media&token=1e510839-98f9-43ec-b93f-b3c122024f82"));
        reviews.add(new user_review("Inderjit Kaur","Industry needs such a great so that they can connect any of the needy citize, most of the e-commerce apps are there who target only on some specific area of population, but this app serves all the levels of society very pleasantly. ","https://firebasestorage.googleapis.com/v0/b/satya-8b1a7.appspot.com/o/my%20own%20pics%2F44110198_2021036884619789_3272512359381336064_n.jpg?alt=media&token=f2816492-9e26-4f1d-bcbf-d36694eb0a55"));
        reviews.add(new user_review("Sejal Aggrawal","My friend had recommend me to use this app and trust me i got an amazing and satisfactory results from this android Application , the staff is very lenient and helpfull as much as they can, I will give 5 out of five to this android application.  ","https://firebasestorage.googleapis.com/v0/b/satya-8b1a7.appspot.com/o/my%20own%20pics%2Fsejal.jpg?alt=media&token=064df9a6-d4c5-4027-bb85-7218b3b9fb06"));

        user_review_adapter=new user_review_adapter(getContext(),reviews);
        user_reviews.setAdapter(user_review_adapter);


        results=v.findViewById(R.id.search_results);
        numberof_results=v.findViewById(R.id.number_of_results);
        progressbar=v.findViewById(R.id.progress);
        progressbar.setVisibility(View.VISIBLE);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        results.setLayoutManager(gridLayoutManager);
        Bundle bundle=getArguments();
        key=bundle.getString("key");

        list=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference("search_list");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] a=key.toLowerCase().split(" ");
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    product_detail product_detail=dataSnapshot1.getValue(product_detail.class);
                    for(int i=0;i<a.length;i++) {
                        if (product_detail.getTags().toLowerCase().contains(a[i].toLowerCase()) || product_detail.getName().toLowerCase().contains(a[i].toLowerCase()) || product_detail.getItem_components().toLowerCase().contains(a[i].toLowerCase()) || product_detail.getSub_category().toLowerCase().contains(a[i].toLowerCase()) || product_detail.getGenre().toLowerCase().contains(a[i].toLowerCase())) {
                          if(!list.contains(product_detail)) {
                              list.add(product_detail);
                          }

                        }
                    }


                  numberof_results.setText(list.size()+" Search Results");

                }

                adapter=new genre_detail_items_recycler_view_adapter(getContext(),list);
                results.setAdapter(adapter);
                progressbar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return v;

    }

}
