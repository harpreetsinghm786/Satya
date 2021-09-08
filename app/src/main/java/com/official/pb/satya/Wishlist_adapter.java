package com.official.pb.satya;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Wishlist_adapter extends RecyclerView.Adapter<Wishlist_adapter.ViewHolder> {

    private Context context;
    private List<Wishlist_list> uploads;


    public Wishlist_adapter(Context context, List<Wishlist_list> uploads) {
        this.uploads = uploads;
        this.context = context;

    }

    @Override
    public Wishlist_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist_item, parent, false);
        Wishlist_adapter.ViewHolder viewHolder = new Wishlist_adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final Wishlist_adapter.ViewHolder holder, final int position) {
        final Wishlist_list list = uploads.get(position);
        holder.name.setText(list.getName());
        holder.price.setText("₹ " + String.valueOf(list.getPrice()));
        Picasso.with(context).load(list.getUrl()).into(holder.imageView);
        holder.rating.setText(String.valueOf(list.getRating()) + "/5");

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putString("postid", list.getPostid());

                Item_detail_and_customisation frag = new Item_detail_and_customisation();
                frag.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.myframelayout, frag).commit();

            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.wishlist_pop_up);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                LinearLayout drawer = dialog.findViewById(R.id.drawer);
                TextView name, price, rating;
                RoundedImageView imageView;
                name = dialog.findViewById(R.id.itemname);
                price = dialog.findViewById(R.id.itemprice);
                rating = dialog.findViewById(R.id.rating);
                imageView = dialog.findViewById(R.id.itemimage);
                Picasso.with(context).load(list.getUrl()).into(imageView);
                name.setText(list.getName());
                price.setText("₹ " + String.valueOf(list.getPrice()));
                rating.setText(String.valueOf(list.getRating()) + "/5");
                Animation transintion = AnimationUtils.loadAnimation(context, R.anim.transition_upward);
                drawer.setAnimation(transintion);
                LinearLayout remove = dialog.findViewById(R.id.remove_from_wishlist);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("wishlist");
                        databaseReference.child(list.getPostid()).removeValue();
                        Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
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
        RoundedImageView imageView;
        TextView price;
        TextView rating;


        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemname);
            imageView = itemView.findViewById(R.id.itemimage);
            price = itemView.findViewById(R.id.itemprice);
            rating = itemView.findViewById(R.id.rating);
            this.item = itemView;

        }


    }


}




