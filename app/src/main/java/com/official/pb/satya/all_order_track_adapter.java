package com.official.pb.satya;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class all_order_track_adapter extends RecyclerView.Adapter<all_order_track_adapter.ViewHolder> {

    private Context context;
    private List<cartorder> uploads;
    String formattedDate,copyformattedDate,output;
    List<cart_list> cart_list;




    public all_order_track_adapter(Context context, List<cartorder> uploads) {
        this.uploads = uploads;
        this.context = context;

    }

    @Override
    public all_order_track_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_order_item_layout, parent, false);
        all_order_track_adapter.ViewHolder viewHolder = new all_order_track_adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final all_order_track_adapter.ViewHolder holder, final int position) {
        final cartorder list = uploads.get(position);
        holder.orderid.setText(list.getOrderid());
        holder.itemcount.setText(list.getList().size()+"");
        //-------------------Format Date------------------------

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formattedDate = df.format(list.getOrderDate());
        SimpleDateFormat dff=new SimpleDateFormat("hh:mm:ss a",Locale.getDefault());



        Calendar c = Calendar.getInstance();
        try {
            c.setTime(df.parse(formattedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(list.getDeliverymode().equals("normal")){
            c.add(Calendar.DATE, 7);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

        }else {
            c.add(Calendar.DATE, 4);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
        output = sdf1.format(c.getTime());


        //------------------------------------------
        holder.orderdate.setText("Date of Order : "+formattedDate+" "+ dff.format(list.getOrderDate().getTime()));
        holder.deliverydate.setText("Delivery date : "+output);
        holder.amount.setText("₹ "+(Integer.valueOf(list.getGrandtotal())+list.getDeliverycharges()));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);


        cart_list=new ArrayList<>();
        cart_list=list.getList();
        track_order_orderlist_adapter track_order_orderlist_adapter=new track_order_orderlist_adapter(context, cart_list);
        holder.recyclerView.setAdapter(track_order_orderlist_adapter);

        if(cart_list.size()>1){

            holder.item_text.setText("Items");
        }else {
            holder.item_text.setText("Item");
        }


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,track_your_order.class);
                i.putExtra("orderid",list.getOrderid());
                i.putExtra("deliveredby",holder.deliverydate.getText().toString().substring(16));
                context.startActivity(i);
                ((Activity)context).finish();

            }
        });






    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView orderid,orderdate,amount,itemcount,deliverydate,item_text;
        RecyclerView recyclerView;

        public ViewHolder(final View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.orderid);
            orderdate = itemView.findViewById(R.id.order_date);
            amount=itemView.findViewById(R.id.amount);
            itemcount=itemView.findViewById(R.id.itemcount);
            item_text=itemView.findViewById(R.id.item_text);
            recyclerView=itemView.findViewById(R.id.orderlist);
            deliverydate=itemView.findViewById(R.id.deliverydate);


            this.item = itemView;

        }


    }


}




