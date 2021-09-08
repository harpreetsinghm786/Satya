package com.official.pb.satya;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Addon_recycler_view_adapter extends RecyclerView.Adapter<Addon_recycler_view_adapter.ViewHolder> {

    private Context context;
    private List<Addons> uploads;
    List<Boolean> bool;
    RecyclerView acutebill;
    List<Addons> addonsList=new ArrayList<>();
    TextView subtotal;
    int sum;

    public Addon_recycler_view_adapter(Context context, List<Addons> uploads,RecyclerView acutebill,TextView subtotal,int sum) {
        this.uploads = uploads;
        this.context = context;
        this.acutebill=acutebill;
        this.subtotal=subtotal;
        this.sum=sum;


    }

    @Override
    public Addon_recycler_view_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addon_item, parent, false);


        //bill_adapter=new bil_list_adapter(getContext(),billaddons);
        //comment_recyclerview.setAdapter(bill_adapter);
        //bill_adapter.notifyDataSetChanged();
        //  acutebill =((MainActivity)context).findViewById(R.id.acutebill);
        Addon_recycler_view_adapter.ViewHolder viewHolder = new Addon_recycler_view_adapter.ViewHolder(v);
        bool=new ArrayList<>();
        for (int i=0;i<uploads.size();i++){
            bool.add(i,false);
        }


        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final Addon_recycler_view_adapter.ViewHolder holder, final int position) {
        final Addons list = uploads.get(position);

        holder.name.setText(list.getName());
        holder.price.setText("₹ "+list.getPrice()+"");
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bool.set(position,!bool.get(position));

                if (bool.get(position) == true ) {

                    addonsList.add(list);
                    sum=sum+list.getPrice();
                    subtotal.setText(String.valueOf(sum));



                    holder.cardView.setBackground(context.getResources().getDrawable(R.drawable.click_black));
                    holder.name.setTextColor(context.getResources().getColor(R.color.white));

                }
                if (bool.get(position) == false) {



                    for(int i=0;i<addonsList.size();i++){
                        if(addonsList.get(i).getName()==list.getName()){

                            sum=sum-addonsList.get(i).getPrice();
                            subtotal.setText(String.valueOf(sum));
                            addonsList.remove(i);
                        }
                    }



                    holder.cardView.setBackground(context.getResources().getDrawable(R.drawable.ring_black));
                    holder.name.setTextColor(context.getResources().getColor(R.color.color1));
                }

                Log.d("onk", "onBindViewHolder: "+addonsList.size());


                bil_list_adapter adapter=new bil_list_adapter(context,addonsList);
                acutebill.setAdapter(adapter);
                adapter.notifyDataSetChanged();




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
        TextView price;
        LinearLayout cardView;


        public ViewHolder(final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.text);
            price=itemView.findViewById(R.id.itprice);
            cardView=itemView.findViewById(R.id.card_board);
            this.item=itemView;

        }



    }





}



