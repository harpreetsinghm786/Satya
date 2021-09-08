package com.official.pb.satya;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class main_product_line_items_recycler_view_adapter extends RecyclerView.Adapter<main_product_line_items_recycler_view_adapter.ViewHolder> {

    private Context context;
    private List<product_detail> uploads;

    public main_product_line_items_recycler_view_adapter(Context context, List<product_detail> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_time_line_prduct_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final product_detail list = uploads.get(position);
        holder.name.setText(list.getName());
        holder.price.setText("₹ "+list.getPrice());
        holder.rating.setText(""+list.getRating()+"/5");

        try {
            if(holder.imageView.getDrawable()==null)
                Picasso.with(context).load(list.getUrl_main()).fit().centerCrop().into(holder.imageView);
        }catch (Exception e){}
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("postid",list.getPostid());
                Item_detail_and_customisation frag= new Item_detail_and_customisation();
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
        TextView price;
        TextView rating;

        public ViewHolder(final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            imageView=itemView.findViewById(R.id.list_item_image);
            price=itemView.findViewById(R.id.price);
            rating=itemView.findViewById(R.id.rating);
            this.item=itemView;

        }



    }



}
