package com.official.pb.satya.Genre_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.official.pb.satya.R;
import com.official.pb.satya.product_detail;

import java.util.ArrayList;
import java.util.List;


public class Genre_detail extends Fragment {

    String name;
    String url;
    RecyclerView recyclerView;
    TextView genretitle;
    List<product_detail> post;


    DatabaseReference mDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_genre_detail, container, false);


        recyclerView=v.findViewById(R.id.similar_items_recycler_view);
        Bundle bundle=this.getArguments();

        name =bundle.getString("name");
        url = bundle.getString("url");

        genretitle=v.findViewById(R.id.genre_title);



        genretitle.setText(name);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        post=new ArrayList<>();




        Query q = FirebaseDatabase.getInstance().getReference("search_list")
                .orderByChild("sub_category")
                .equalTo(name);
        q.addListenerForSingleValueEvent(valueEventListener);


        return v;

    }
    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            post.clear();
            //iterating through all the values in database
            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                product_detail upload=dataSnapshot1.getValue(product_detail.class);
                post.add(0,upload);}
            genre_detail_items_recycler_view_adapter adapter=new genre_detail_items_recycler_view_adapter(getActivity(),post);
            recyclerView.setAdapter(adapter);

        }







        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


}
