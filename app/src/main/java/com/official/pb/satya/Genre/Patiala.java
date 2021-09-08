package com.official.pb.satya.Genre;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.official.pb.satya.Genre.recycler_views.common_list;
import com.official.pb.satya.Genre.recycler_views.recycler_view_adapter_patiala;
import com.official.pb.satya.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Patiala extends Fragment {

    private RecyclerView recyclerView;

    private List<common_list> post;
    Context context;
    ProgressBar progressBar;
    recycler_view_adapter_patiala recycler_view_adpter;
    DatabaseReference mDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patiala, container, false);

        context=getContext();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        recyclerView=v.findViewById(R.id.recycler_view_patiala);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        post=new ArrayList<>();
        progressBar=v.findViewById(R.id.progress);
        mDatabase = FirebaseDatabase.getInstance().getReference("Genre").child("patiala");

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
                recycler_view_adpter = new recycler_view_adapter_patiala(getActivity(), post);
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
