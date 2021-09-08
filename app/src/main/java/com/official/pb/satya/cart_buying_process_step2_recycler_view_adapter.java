package com.official.pb.satya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class cart_buying_process_step2_recycler_view_adapter extends RecyclerView.Adapter<cart_buying_process_step2_recycler_view_adapter.ViewHolder> {

    private Context context;
    private List<cart_list> uploads;


    public cart_buying_process_step2_recycler_view_adapter(Context context, List<cart_list> uploads) {
        this.uploads = uploads;
        this.context = context;

    }

    @Override
    public cart_buying_process_step2_recycler_view_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_buying_process_step2_item, parent, false);
        cart_buying_process_step2_recycler_view_adapter.ViewHolder viewHolder = new cart_buying_process_step2_recycler_view_adapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final cart_buying_process_step2_recycler_view_adapter.ViewHolder holder, final int position) {
        final cart_list list = uploads.get(position);
        holder.name.setText(list.getName());

        holder.price.setText("₹ " + list.getOldprice());
        Picasso.with(context).load(list.getImage()).fit().into(holder.imageView);
        holder.main_name.setText(list.getName());
        holder.main_price.setText(list.getOldprice() + "");
        holder.quantity.setText("Quantity : " + list.getQuantity());

        if (list.getAddons() != null) {
            bil_list_adapter adapter = new bil_list_adapter(context, list.getAddons());
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.setAdapter(adapter);
            holder.subtotal.setText( list.getPrice()+"");

        } else {
            holder.subtotal.setText( list.getPrice()+"");
        }



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
        TextView quantity;
        RecyclerView recyclerView;
        TextView main_name, main_price, subtotal, deliverycharges;


        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemname);
            price = itemView.findViewById(R.id.itemprice);
            imageView=itemView.findViewById(R.id.itemimage);
            quantity = itemView.findViewById(R.id.itemquantity);
            recyclerView = itemView.findViewById(R.id.addons);
            main_name = itemView.findViewById(R.id.main_name);
            main_price = itemView.findViewById(R.id.normalprice);
            subtotal=itemView.findViewById(R.id.subtotal);
            deliverycharges=itemView.findViewById(R.id.deliverycharges);
            this.item = itemView;

        }


    }


}



