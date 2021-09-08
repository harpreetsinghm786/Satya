package com.official.pb.satya;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class user_review_adapter extends RecyclerView.Adapter<user_review_adapter.ViewHolder> {

    private Context context;
    private List<user_review> uploads;


    public user_review_adapter(Context context, List<user_review> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_review_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final user_review list = uploads.get(position);
        holder.name.setText(list.getName().toString());
        holder.review.setText(list.getReview());




        try {
            if(holder.imageView.getDrawable()==null)
                Picasso.with(context).load(list.getUrl()).fit().centerCrop().into(holder.imageView);
        }catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View item;
        TextView name;
        RoundedImageView imageView;
        TextView review;

        ImageView bar;

        public ViewHolder(final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_of_customer);
            imageView=itemView.findViewById(R.id.customer_url);
            review=itemView.findViewById(R.id.review);
            bar=itemView.findViewById(R.id.bar);

            this.item=itemView;

        }



    }



}
