package com.official.pb.satya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class finish_success extends AppCompatActivity {
    Button countinueshopping, trackyourorder;
    DatabaseReference databaseReference, databaseReference2;
    RecyclerView recyclerView, cityname;
    List<Addons> list;
    bil_list_adapter adapter;
    delivery_cities_adapter delivery_cities_adapter;
    List<String> cities;
    LinearLayout cod_card;
    FirebaseAuth auth;
    TextView itemnum;
    Dialog dialog,dialog2;
    List<cart_list>itemlist;
    finish_success_cart_adapter finish_success_cart_adapter;
    TextView terms;
    String formattedDate;


    TextView username, userphonenumber, useraddress, servicetax, grandtotal, deliverydate, itemname, main_name, itemprice, main_price, deliverycharges, orderid, orderstatus;
    CardView fastdeliverycard;
    RoundedImageView imageView, itemimage;
    ProgressBar finish_progressbar;
    ImageButton close;

    String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_sucess);

        countinueshopping = findViewById(R.id.countinueshopping);

        itemimage = findViewById(R.id.itemimage);
        servicetax = findViewById(R.id.servicetax);
        terms = findViewById(R.id.terms);
        cod_card=findViewById(R.id.cod_card);

        dialog = new Dialog(finish_success.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.terms_condn);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(finish_success.this, 2, RecyclerView.VERTICAL, false);
        cityname = dialog.findViewById(R.id.devliverycities);
        cityname.setLayoutManager(gridLayoutManager);
        itemnum=findViewById(R.id.itemnum);
        close = dialog.findViewById(R.id.close);
        trackyourorder = findViewById(R.id.trackyourorders);
        itemname = findViewById(R.id.itemname);
        cities = new ArrayList<>();
        final LinearLayout drawer = dialog.findViewById(R.id.drawer);
        finish_progressbar = findViewById(R.id.finish_progress);
        itemprice = findViewById(R.id.itemprice);
        main_name = findViewById(R.id.main_name);
        deliverydate = findViewById(R.id.deliverydate);
        orderid = findViewById(R.id.orderid);
        orderstatus = findViewById(R.id.order_status);
        deliverycharges = findViewById(R.id.deliverycharges);
        itemprice = findViewById(R.id.itemprice);
        fastdeliverycard = findViewById(R.id.fastdeliverycard);
        main_price = findViewById(R.id.normalprice);

        auth = FirebaseAuth.getInstance();
        grandtotal = findViewById(R.id.grandtotal);
        LinearLayoutManager manager = new LinearLayoutManager(finish_success.this, RecyclerView.VERTICAL, false);
        recyclerView = findViewById(R.id.items);

        order_id=getIntent().getStringExtra("orderid");
        // final String order_id = "SATYA0821Z05A34566653";
        Log.d("tai", "onCreate: "+order_id);


        username = findViewById(R.id.username);
        userphonenumber = findViewById(R.id.userphonenumber);
        useraddress = findViewById(R.id.useraddress);
        imageView = findViewById(R.id.userpic);
        recyclerView.setLayoutManager(manager);
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Orders");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Delivery_cities");

        dialog2 = new Dialog(finish_success.this);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setContentView(R.layout.payment_success_popup);
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        Button cancel;
        final TextView content_text=dialog2.findViewById(R.id.content_text);

        cancel=dialog2.findViewById(R.id.cancel);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });





        list = new ArrayList<>();
        itemlist=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    cartorder cartorder = dataSnapshot1.getValue(cartorder.class);



                    if (cartorder.getOrderid().equals(order_id)) {

                        itemlist= cartorder.getList();
                        itemnum.setText("Grand Total ("+itemlist.size()+" item)");
                        finish_success_cart_adapter=new finish_success_cart_adapter(finish_success.this,itemlist);
                        recyclerView.setAdapter(finish_success_cart_adapter);


                        orderid.setText(cartorder.getOrderid());
                        content_text.setText(cartorder.getOrderid());
                        servicetax.setText(cartorder.getServicetax() + "");


                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        formattedDate = df.format(cartorder.getOrderDate());
                        SimpleDateFormat dff=new SimpleDateFormat("hh:mm:ss a",Locale.getDefault());



                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(df.parse(formattedDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String output;

                        if(cartorder.getDeliverymode().equals("normal")){
                            c.add(Calendar.DATE, 7);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                        }else {
                            c.add(Calendar.DATE, 4);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                        }
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
                        output = sdf1.format(c.getTime());



                        deliverydate.setText(output);
                        deliverycharges.setText(cartorder.getDeliverycharges() + "");
                        grandtotal.setText(String.valueOf(cartorder.getGrandtotal()+cartorder.getDeliverycharges()));

                        finish_progressbar.setVisibility(View.GONE);
                        if(cartorder.getPaymentmode().equals("COD")){
                            cod_card.setVisibility(View.VISIBLE);
                        }
                        if (Float.valueOf(cartorder.getDeliverycharges()) > 0) {
                            fastdeliverycard.setVisibility(View.VISIBLE);
                        }
                    }
                }
                dialog2.show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        trackyourorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(finish_success.this,track_your_order.class);
                i.putExtra("orderid",order_id);
                i.putExtra("deliveredby",formattedDate);
                startActivity(i);
                finish();
            }
        });

        countinueshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(finish_success.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Animation transintion = AnimationUtils.loadAnimation(finish_success.this, R.anim.transition_upward);
                drawer.setAnimation(transintion);


                dialog.show();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation transintion2 = AnimationUtils.loadAnimation(finish_success.this, R.anim.transition_down);
                drawer.setAnimation(transintion2);
                dialog.dismiss();
            }
        });


        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cities.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if(dataSnapshot.exists()){
                        String name = dataSnapshot1.getValue(String.class);
                        cities.add(name);}
                }

                delivery_cities_adapter = new delivery_cities_adapter(finish_success.this, cities);
                cityname.setAdapter(delivery_cities_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
