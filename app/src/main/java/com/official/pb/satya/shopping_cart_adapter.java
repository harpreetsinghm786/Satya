package com.official.pb.satya;

import android.content.Context;
import android.graphics.Paint;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class shopping_cart_adapter extends RecyclerView.Adapter<shopping_cart_adapter.ViewHolder> {

    private Context context;
    private List<cart_list> uploads;
    int a=0;
    List<Boolean> bool;
    Animation a1;
    Animation a2;
    DatabaseReference databaseReference;
    FirebaseAuth auth;



    public shopping_cart_adapter(Context context, List<cart_list> uploads) {
        this.uploads = uploads;
        this.context = context;

    }

    @Override
    public shopping_cart_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        shopping_cart_adapter.ViewHolder viewHolder = new shopping_cart_adapter.ViewHolder(v);
        bool=new ArrayList<>();
         a1= AnimationUtils.loadAnimation(context,R.anim.transition_downward);
       a2=AnimationUtils.loadAnimation(context,R.anim.transition_upward);
        for (int i=0;i<uploads.size();i++){
            bool.add(i,false);
        }
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final shopping_cart_adapter.ViewHolder holder, final int position) {
        final cart_list list = uploads.get(position);
        holder.name.setText(list.getName());
        holder.price.setText("₹ "+String.valueOf(list.oldprice) );
        holder.main_name.setText(list.getName());
        holder.grandtotal.setText(""+list.getPrice());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);

        if(list.getAddons()!=null) {
              bil_list_adapter adapter = new bil_list_adapter(context, list.getAddons());
            Log.d("list size", "onBindViewHolder: "+list.getAddons().size());
            holder.recyclerView.setAdapter(adapter);
        }else{
          //  Log.d("list size", "onBindViewHolder: "+list.getAddons().size());


        }
        holder.main_price.setText("₹ "+String.valueOf(list.oldprice) );


        Picasso.with(context).load(list.getImage()).into(holder.imageView);
        if(list.getPrice()<499){
            holder.deliverycard.setVisibility(View.GONE);
        }
        holder.quantity.setText(String.valueOf(list.getQuantity()));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int curposition=uploads.indexOf(list);
                uploads.remove(curposition);
                notifyItemRemoved(curposition);

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("cart");
                databaseReference.child("items").child(String.valueOf(list.getPrice())).removeValue();
                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("cart");

                if(list.getAddons()!=null) {
                      databaseReference2.child(list.getPostid()).child(String.valueOf(list.addons.size())).child(String.valueOf(list.getPrice())).removeValue();
                }else{
                    databaseReference2.child(list.getPostid()).child(String.valueOf(0)).child(String.valueOf(list.getPrice())).removeValue();

                }

                Toast.makeText(context,"Item Deleted",Toast.LENGTH_SHORT).show();
            }
        });

        holder.showdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool.set(position,!bool.get(position));

                if (bool.get(position) == true ) {
                    holder.toggal.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    holder.details.setVisibility(View.VISIBLE);

                    }
                if (bool.get(position) == false ) {


                    holder.details.setVisibility(View.GONE);


                    holder.toggal.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }

            }
        });

        holder.addtowish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                auth=FirebaseAuth.getInstance();
                Wishlist_list wishlist_list=new Wishlist_list(list.getName(),list.getPrice(),Float.valueOf("5"),list.getImage(),list.getPostid());
                databaseReference=FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("wishlist");

                databaseReference.child(wishlist_list.postid).setValue(wishlist_list);



                int curposition=uploads.indexOf(list);
                uploads.remove(curposition);
                notifyItemRemoved(curposition);

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("cart");
                databaseReference.child("items").child(String.valueOf(list.getPrice())).removeValue();
                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("cart");

                if(list.getAddons()!=null) {
                    databaseReference2.child(list.getPostid()).child(String.valueOf(list.addons.size())).child(String.valueOf(list.getPrice())).removeValue();
                }else{
                    databaseReference2.child(list.getPostid()).child(String.valueOf(0)).child(String.valueOf(list.getPrice())).removeValue();

                }

                Toast.makeText(context,"Moved to Wishlist",Toast.LENGTH_SHORT).show();
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final DatabaseReference databaseReference2;
                if(list.getAddons()!=null) {
                    databaseReference2  = FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("cart").child(list.postid).child(String.valueOf(list.getAddons().size())).child(String.valueOf(list.getPrice())).child("quantity");
                }else{
                    databaseReference2  = FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("cart").child(list.postid).child(String.valueOf(0)).child(String.valueOf(list.getPrice())).child("quantity");

                }
                final DatabaseReference databaseReference3= FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("cart").child("items").child(String.valueOf(list.getPrice())).child("quantity");
                databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int b=dataSnapshot.getValue(Integer.class);
                        if(b<5){
                            b++;
                            databaseReference3.setValue(b);}
                        else {
                         }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                      a=dataSnapshot.getValue(Integer.class);
                        if(a<5){
                      a++;
                      databaseReference2.setValue(a);}
                        else {
                            Toast.makeText(context, "This is the maximum Limit of BuynowOrder for this dress", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        holder.notprice.setText("MRP:₹ " +list.getNotprice());
        holder.notprice.setPaintFlags(holder.notprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.desc.setText(list.getDesc());
        holder.poff.setText(list.getPoff()+"% off");

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference2;
                if(list.getAddons()!=null) {
                 databaseReference2  = FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("cart").child(list.postid).child(String.valueOf(list.getAddons().size())).child(String.valueOf(list.getPrice())).child("quantity");
                }else{
                    databaseReference2  = FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("cart").child(list.postid).child(String.valueOf(0)).child(String.valueOf(list.getPrice())).child("quantity");

                }
                final DatabaseReference databaseReference3= FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("cart").child("items").child(String.valueOf(list.getPrice())).child("quantity");
                databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int b=dataSnapshot.getValue(Integer.class);
                        if(b<=5 && b>1){
                            b--;
                            databaseReference3.setValue(b);
                            databaseReference2.setValue(b);
                        }
                        else {


                            int curposition=uploads.indexOf(list);
                            uploads.remove(curposition);
                            notifyItemRemoved(curposition);

                            DatabaseReference data= FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("cart");
                            data.child("items").child(String.valueOf(list.getPrice())).removeValue();
                            DatabaseReference data2 = FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("cart");

                            if(list.getAddons()!=null) {
                                data2.child(list.getPostid()).child(String.valueOf(list.addons.size())).child(String.valueOf(list.getPrice())).removeValue();
                            }else{
                                data2.child(list.getPostid()).child(String.valueOf(0)).child(String.valueOf(list.getPrice())).removeValue();

                            }

                            Toast.makeText(context,"Item Deleted",Toast.LENGTH_SHORT).show();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


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
        TextView price,poff,notprice,desc;
        LinearLayout deliverycard;
        TextView quantity;
        RecyclerView recyclerView;
        TextView main_name,main_price,grandtotal;
        Button delete,addtowish;
        ImageView plus,minus;
        LinearLayout details,showdetails;
        ImageView toggal;


        public ViewHolder(final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.itemname);
            imageView=itemView.findViewById(R.id.itemimage);
            price=itemView.findViewById(R.id.itemprice);
            deliverycard=itemView.findViewById(R.id.freedeliverycard);
            quantity=itemView.findViewById(R.id.quantity);
            recyclerView=itemView.findViewById(R.id.preshoppingbill);
            delete=itemView.findViewById(R.id.delete);
            addtowish=itemView.findViewById(R.id.wish);
            grandtotal=itemView.findViewById(R.id.grandtotal);
            desc=itemView.findViewById(R.id.itemdesc);
            notprice=itemView.findViewById(R.id.notprice);
            poff=itemView.findViewById(R.id.poff);
            plus=itemView.findViewById(R.id.plus);
            showdetails=itemView.findViewById(R.id.showdetails);
            details=itemView.findViewById(R.id.details);
            toggal=itemView.findViewById(R.id.toggal);
            main_name=itemView.findViewById(R.id.main_name);
            main_price=itemView.findViewById(R.id.main_price);
            minus=itemView.findViewById(R.id.minus);
            this.item=itemView;

        }



    }







}


