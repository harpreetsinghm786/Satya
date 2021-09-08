package com.official.pb.satya;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class admin_all_order_track_adapter extends RecyclerView.Adapter<admin_all_order_track_adapter.ViewHolder> {

    private Context context;
    private List<cartorder> uploads;
    String formattedDate,copyformattedDate;




    public admin_all_order_track_adapter(Context context, List<cartorder> uploads) {
        this.uploads = uploads;
        this.context = context;

    }

    @Override
    public admin_all_order_track_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_order_item_layout, parent, false);
        admin_all_order_track_adapter.ViewHolder viewHolder = new admin_all_order_track_adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final admin_all_order_track_adapter.ViewHolder holder, final int position) {
        final cartorder list = uploads.get(position);
        holder.orderid.setText(list.getOrderid());
        holder.itemcount.setText(list.getList().size()+"");
        //-------------------Format Date------------------------
        Log.d("tagori", "onBindViewHolder: "+list.getOrderDate());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formattedDate = df.format(list.getOrderDate());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(formattedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 0);
        sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date resultdate = new Date(c.getTimeInMillis());
        formattedDate = sdf.format(resultdate);

        if(list.deliverymode.equals("fast")) {
            c.add(Calendar.DATE, 3);}else{
            c.add(Calendar.DATE, 6);
        }
        sdf = new SimpleDateFormat("dd-MMM-yyyy");
        resultdate = new Date(c.getTimeInMillis());
        copyformattedDate = sdf.format(resultdate);

        //------------------------------------------
        holder.orderdate.setText("Date of Order : "+formattedDate);
        holder.amount.setText(String.valueOf(Integer.valueOf(list.getGrandtotal())+list.getDeliverycharges()));


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,Admin.class);
                i.putExtra("orderid",list.getOrderid());
                i.putExtra("deliveredby",copyformattedDate);
                context.startActivity(i);
                ((Activity)context).finish();

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
        TextView orderid,orderdate,amount,itemcount,item_text;

        public ViewHolder(final View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.orderid);
            orderdate = itemView.findViewById(R.id.order_date);
            amount = itemView.findViewById(R.id.order_amount);
            itemcount=itemView.findViewById(R.id.itemcount);
            item_text=itemView.findViewById(R.id.item_text);


            this.item = itemView;

        }


    }


}




