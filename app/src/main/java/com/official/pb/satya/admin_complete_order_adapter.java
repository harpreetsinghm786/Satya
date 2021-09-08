package com.official.pb.satya;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class admin_complete_order_adapter extends RecyclerView.Adapter<admin_complete_order_adapter.ViewHolder> {

    private Context context;
    private List<cartorder> uploads;
    String formattedDate,copyformattedDate,dod;

    public admin_complete_order_adapter(Context context, List<cartorder> uploads) {
        this.uploads = uploads;
        this.context = context;

    }

    @Override
    public admin_complete_order_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_order_item_layout, parent, false);
        admin_complete_order_adapter.ViewHolder viewHolder = new admin_complete_order_adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final admin_complete_order_adapter.ViewHolder holder, final int position) {
        final cartorder list = uploads.get(position);
        holder.orderid.setText(list.getOrderid());
        holder.itemcount.setText(list.getList().size()+"");
        //-------------------Format Date------------------------
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formattedDate = df.format(list.getOrderDate());

        //------------------------------------------
        holder.orderdate.setText("Date of Order : "+formattedDate);
        holder.amount.setText(String.valueOf(Integer.valueOf(list.getGrandtotal())+list.getDeliverycharges()));


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                complete_order_details complete_order_details=new complete_order_details();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                dod = df.format(list.getDate_of_delivery());


                Bundle bundle=new Bundle();
                bundle.putString("orderid",list.getOrderid());
                bundle.putString("dod",dod);

                complete_order_details.setArguments(bundle);
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().add(R.id.container,complete_order_details).commit();



            }
        });

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }


    public void filterlist(List<cartorder> filterlist) {
        uploads = filterlist;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View item;
        TextView orderid,orderdate,amount,itemcount;

        public ViewHolder(final View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.orderid);
            orderdate = itemView.findViewById(R.id.order_date);
            amount = itemView.findViewById(R.id.order_amount);
            itemcount=itemView.findViewById(R.id.itemcount);


            this.item = itemView;

        }


    }


}






