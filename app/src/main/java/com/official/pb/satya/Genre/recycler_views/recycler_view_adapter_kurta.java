package com.official.pb.satya.Genre.recycler_views;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.official.pb.satya.Genre_details.Genre_detail;
import com.official.pb.satya.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class recycler_view_adapter_kurta extends RecyclerView.Adapter< recycler_view_adapter_kurta.ViewHolder> {

    private Context context;
    private List<common_list> uploads;

    public recycler_view_adapter_kurta(Context context, List<common_list> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genre_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final common_list list = uploads.get(position);
        holder.name.setText(list.getName());
        try {
            if(holder.imageView.getDrawable()==null)
                Picasso.with(context).load(list.getUrl()).fit().centerCrop().into(holder.imageView);
        }catch (Exception e){}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("name",list.getName());
                bundle.putString("url",list.getUrl());
                Genre_detail frag= new Genre_detail();
                frag.setArguments(bundle);
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,frag).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View item;
        TextView name;
        ImageView imageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.itemname);
            imageView=itemView.findViewById(R.id.image);
            this.item=itemView;

        }



    }



}
