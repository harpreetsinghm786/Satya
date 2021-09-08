package com.official.pb.satya;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class wallet_transaction_adapter extends RecyclerView.Adapter<wallet_transaction_adapter.ViewHolder> {

    private Context context;
    private List<Transactions> uploads;
    String formattedDate,copyformattedDate;


    public wallet_transaction_adapter(Context context, List<Transactions> uploads) {
        this.uploads = uploads;
        this.context = context;

    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }

    @Override
    public wallet_transaction_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);
        wallet_transaction_adapter.ViewHolder viewHolder = new wallet_transaction_adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Transactions list = uploads.get(position);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formattedDate = df.format(list.getDate());
        SimpleDateFormat dff=new SimpleDateFormat("hh:mm:ss a",Locale.getDefault());

        holder.orderid.setText(list.getTransactionid()+"");
        holder.reason.setText(list.getCategory()+"");
        holder.amount.setText(list.getAmount()+"");
        holder.date.setText(formattedDate);
        holder.time.setText(dff.format(list.getDate().getTime()));

        if(list.getCategory().equals("Order Placed")){
            holder.reason.setTextColor(context.getResources().getColor(R.color.green));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.piccontainer.getBackground().setTint(context.getResources().getColor(android.R.color.holo_red_dark));//setBackgroundColor(context.getResources().getColor(R.color.green));
            }
            holder.pic.setImageResource(R.drawable.ic_arrow_downward_black_24dp);

        }else if(list.getCategory().equals("Order Cancelled")){
            holder.reason.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.piccontainer.getBackground().setTint(context.getResources().getColor(R.color.green));
            }
            holder.pic.setImageResource(R.drawable.ic_arrow_upward_black_24dp);

        }else if(list.getCategory().equals("Wallet Recharge")){
            holder.reason.setTextColor(context.getResources().getColor(R.color.darkyellow));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.piccontainer.getBackground().setTint(context.getResources().getColor(R.color.green));
            }
            holder.pic.setImageResource(R.drawable.ic_arrow_upward_black_24dp);

        }


    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView orderid,date,amount,reason,time;
        LinearLayout piccontainer;
        RoundedImageView pic;





        public ViewHolder(final View itemView) {
            super(itemView);
            orderid=itemView.findViewById(R.id.orderid);
            amount=itemView.findViewById(R.id.order_amount);
            reason=itemView.findViewById(R.id.reason);
            date=itemView.findViewById(R.id.order_date);
            time=itemView.findViewById(R.id.order_time);
            pic=itemView.findViewById(R.id.pic);
            piccontainer=itemView.findViewById(R.id.pic_container);
            this.item = itemView;

        }


    }

}
