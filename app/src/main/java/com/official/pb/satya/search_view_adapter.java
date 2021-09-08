package com.official.pb.satya;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class search_view_adapter extends RecyclerView.Adapter<search_view_adapter.ViewHolder> {

    private Context context;
    private List<search_list> uploads;
    String postid,a ;

    EditText text;

    Fragment fragment;

    public search_view_adapter(Context context, List<search_list> uploads, EditText text) {
        this.uploads = uploads;
        this.context = context;
        this.text = text;


    }

    @Override
    public search_view_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        search_view_adapter.ViewHolder viewHolder = new search_view_adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final search_view_adapter.ViewHolder holder, final int position) {
        final search_list list = uploads.get(position);
        holder.name.setText(list.getUname());






        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                history(list.getUname());
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(holder.item.getWindowToken(), 0);

                text.setText(list.getUname());
                //text.setSelection(text.getText().length());
                text.clearFocus();
                Bundle bundle = new Bundle();

                bundle.putString("key", text.getText().toString());

                search_results frag = new search_results();
                frag.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag).commit();
//                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(holder.item.getWindowToken(), 0);

            }
        });
        fragment= ((FragmentActivity)context).getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        a=text.getText().toString();

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(a.isEmpty()){
//
//                }else{
//                    if(fragment==null){
//                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                        linearLayout.setVisibility(View.VISIBLE);
//                        text.setFocusable(false);
//
//                    }else{
//                        Intent i=new Intent(context,search.class);
//                        context.startActivity(i);
//                        ((Activity)context).finish();
//                    }
//                }
//
//
//            }
//
//        });





        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,search.class);
                context.startActivity(i);
                ((Activity)context).finish();
                text.clearFocus();
            }
        });


    }

    @Override
    public int getItemCount() {
        return uploads.size();


    }


    public void filterlist(List<search_list> filterlist) {
        uploads = filterlist;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView name;


        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.suggestions);

            this.item = itemView;

        }


    }

    private void history(final String a) {

        final DatabaseReference reference,reference2;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("History");
        reference2 = FirebaseDatabase.getInstance().getReference("search_list");

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    product_detail product_detail=dataSnapshot1.getValue(product_detail.class);
                    if(product_detail.getName().equals(a)){
                        postid=product_detail.getPostid();}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                History_list history_list = new History_list(a,postid);
                if (dataSnapshot.getChildrenCount() < 5) {

                    reference.child(String.valueOf(dataSnapshot.getChildrenCount())).setValue(history_list);

                } else {
                    for (int i = 4; i > 0; i--) {

                        reference.child(String.valueOf(i)).setValue(dataSnapshot.child(String.valueOf(i - 1)).getValue(history_list.getClass()));

                    }
                    reference.child(String.valueOf(0)).setValue(history_list);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}



