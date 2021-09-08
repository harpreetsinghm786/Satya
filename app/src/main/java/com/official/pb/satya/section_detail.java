package com.official.pb.satya;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class section_detail extends Fragment {


    TextView name;
    DatabaseReference databaseReference;
    List<product_detail> list;
    RecyclerView recyclerView;
    main_product_line_items_recycler_view_adapter main_product_line_items_recycler_view_adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_section_detail, container, false);
        recyclerView=v.findViewById(R.id.sectionpool);
        GridLayoutManager GridLayoutManager = new GridLayoutManager(getContext(),3,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(GridLayoutManager);
        Bundle bundle=getArguments();
        name=v.findViewById(R.id.name_of_section);
        list=new ArrayList<>();

        name.setText(bundle.get("name").toString());
        databaseReference= FirebaseDatabase.getInstance().getReference("Sections").child(name.getText().toString()).child("list");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    product_detail product_detail=dataSnapshot1.getValue(product_detail.class);
                    list.add(product_detail);
                }
                main_product_line_items_recycler_view_adapter =new main_product_line_items_recycler_view_adapter(getContext(),list);
                recyclerView.setAdapter(main_product_line_items_recycler_view_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return  v;
    }

}
