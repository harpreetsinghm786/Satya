package com.official.pb.satya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class addlocation_adapter extends RecyclerView.Adapter<addlocation_adapter.ViewHolder> {

    private Context context;
    private List<Address> uploads;
    Address list;
    List<String>keys;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("Locations");






    public addlocation_adapter(Context context, List<Address> uploads) {
        this.uploads = uploads;
        this.context = context;


    }

    @Override
    public addlocation_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_location_item, parent, false);

        addlocation_adapter.ViewHolder viewHolder = new addlocation_adapter.ViewHolder(v);
        keys=new ArrayList<>();

        FirebaseAuth auth=FirebaseAuth.getInstance();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("Locations");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keys.clear();
                int s=0;
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Address address=dataSnapshot1.getValue(Address.class);
                    keys.add(0,address.getKey());
                    if(address.getKey()!=null){
                        s=1;

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final addlocation_adapter.ViewHolder holder, final int position) {
        list = uploads.get(position);

        if(uploads.size()==1){
            databaseReference2.child(list.getKey()).child("bool").setValue(true);


        }

        holder.Houseno.setText("Hno. "+list.getHousenumber());
        holder.street.setText("Stno. "+list.getStreetnumber());
        holder.colony_city.setText(list.getColony()+","+list.getCity());
        holder.nearby.setText("Nearby "+list.getNearby());




        if (list.bool==true) {
            holder.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.addlocation_checked_item));
            holder.Houseno.setTextColor(context.getResources().getColor(R.color.white));
            holder.street.setTextColor(context.getResources().getColor(R.color.white));
            holder.colony_city.setTextColor(context.getResources().getColor(R.color.white));
            holder.nearby.setTextColor(context.getResources().getColor(R.color.white));

        }else{

            holder.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_border));
            holder.Houseno.setTextColor(context.getResources().getColor(R.color.color1));
            holder.street.setTextColor(context.getResources().getColor(R.color.color1));
            holder.colony_city.setTextColor(context.getResources().getColor(R.color.color1));
            holder.nearby.setTextColor(context.getResources().getColor(R.color.color1));
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int k = 0; k < uploads.size(); k++) {
                    databaseReference2.child(keys.get(k)).child("bool").setValue(false);

                }


                databaseReference2.child(keys.get(position)).child("bool").setValue(true);

            }


        });


    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View item;
        TextView Houseno,street,colony_city,nearby;
        LinearLayout linearLayout;



        public ViewHolder(final View itemView) {
            super(itemView);
            Houseno=itemView.findViewById(R.id.houseno);
            street=itemView.findViewById(R.id.street);
            linearLayout=itemView.findViewById(R.id.card);
            colony_city=itemView.findViewById(R.id.colony_city);
            nearby=itemView.findViewById(R.id.nearbytext);
            this.item=itemView;

        }



    }








}
