package com.official.pb.satya.Genre;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.official.pb.satya.Genre.recycler_views.common_list;
import com.official.pb.satya.Genre.recycler_views.recycler_view_adapter_western;
import com.official.pb.satya.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Western extends Fragment {

    private RecyclerView recyclerView;

    private List<common_list> post;
    Context context;
    ProgressBar progressBar;
    recycler_view_adapter_western recycler_view_adpter;
    DatabaseReference mDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_weston, container, false);
        context=getContext();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        progressBar=v.findViewById(R.id.progress);
        recyclerView=v.findViewById(R.id.recycler_view_western);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        post=new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("Genre").child("western");

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog

                post.clear();
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    common_list upload = postSnapshot.getValue(common_list.class);
                    post.add(0,upload);
                }
                //creating adapter
                recycler_view_adpter = new recycler_view_adapter_western(getActivity(), post);
                recyclerView.setAdapter(recycler_view_adpter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        });

        return v;
    }

}
