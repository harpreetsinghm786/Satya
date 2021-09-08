package com.official.pb.satya;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class cancelled_order extends Fragment {
    RecyclerView recyclerView;
    List<cartorder> all_orders;
    DatabaseReference databaseReference,databaseReference2;
    cancel_order_adapter cancel_order_adapter;
    FirebaseAuth auth;
    List<String>keys;
    ProgressBar progressBar;
    LinearLayout nodata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_cancelled_order, container, false);
        recyclerView=v.findViewById(R.id.cancelled_orders);
        progressBar=v.findViewById(R.id.cancel_progress);
        all_orders=new ArrayList<>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        nodata=v.findViewById(R.id.nodata);
        databaseReference= FirebaseDatabase.getInstance().getReference("Admin").child("Orders");
        auth= FirebaseAuth.getInstance();
        databaseReference2= FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("Confirmed_orders");
        keys=new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        additems();

        return v;
    }
    private void additems(){


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keys.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String key=dataSnapshot1.getValue(String.class);
                    keys.add(key);
                }

                Log.d("keysize", "onDataChange: "+keys.size());
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        all_orders.clear();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                            cartorder cartorderitem=dataSnapshot1.getValue(cartorder.class);

                            for(int i=0;i<keys.size();i++){
                                if( cartorderitem.getOrderid().equals(keys.get(i))&&cartorderitem.getProg_status()==10)
                                    all_orders.add(cartorderitem);
                            }

                        }

                        if(all_orders.size()==0){
                            progressBar.setVisibility(View.GONE);
                            nodata.setVisibility(View.VISIBLE);
                        }else{
                            Log.d("sizeoflist", "onDataChange: "+all_orders.size());
                            cancel_order_adapter=new cancel_order_adapter(getContext(),all_orders);
                            recyclerView.setAdapter(cancel_order_adapter);
                            progressBar.setVisibility(View.GONE);
                            nodata.setVisibility(View.GONE);}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
