package com.official.pb.satya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.official.pb.satya.Genre.Tab_layout_adapter;

import java.util.Calendar;
import java.util.Date;

public class track_your_order extends AppCompatActivity {

    LinearLayout pointer,ring1,ring2,ring3,ring4,ring5,ring6,ring7;
    ImageView image1,image2,image3,image4,image5,image6,image7;
    ImageView dot1,dot2,dot3,dot4,dot5,dot6,dot7;
    View v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14;
    TextView text1,text2,text3,text4,text5,text6,text7;
    LinearLayout l1,l2,l3,l4,l5,l6,l7;
    LinearLayout track_layout;
    DatabaseReference databaseReference,databaseReference2,databaseReference3;
    String orderid,dod;
    FirebaseAuth auth;
    int progstatus,allamount;
    Dialog dialogreason;
    Dialog dialog;
    TextView tvorderid,tvdeliveredby;
    Button cancelorder,orderdetails,rclose;
    RadioButton r1,r2,r3,r4;
    EditText reson;
    String reason=null;
    TabLayout tabLayout;
    ViewPager viewPager;
    cartorder cartorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_your_order);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.tab_bar_view_pager_track_order);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabLayout.setBackgroundColor(getResources().getColor(R.color.color1));
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.color1));
        }
        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Admin").child("Orders");
        databaseReference2=FirebaseDatabase.getInstance().getReference("Transactions").child(auth.getCurrentUser().getPhoneNumber());
        databaseReference3=FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber());
        createReferences();
        orderid=getIntent().getStringExtra("orderid");
        dod=getIntent().getStringExtra("deliveredby");
        if (orderid!=null){
            track_layout.setVisibility(View.VISIBLE);
            tvdeliveredby.setText(dod);
            tvorderid.setText(orderid);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        if(dataSnapshot1.getKey().equals(orderid)){
                            cartorder=dataSnapshot1.getValue(cartorder.class);
                            progstatus=  cartorder.getProg_status();
                            allamount=Integer.valueOf(cartorder.getGrandtotal())+cartorder.getDeliverycharges()+cartorder.getServicetax();
                            cancelorder.setEnabled(true);

                            movepointer(progstatus);

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            track_layout.setVisibility(View.GONE);



        }


        orderdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(track_your_order.this,finish_success_cart.class);
                i.putExtra("orderid",orderid);
                i.putExtra("show","0");
                startActivity(i);
                finish();

            }
        });

        cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(progstatus==0){
                    cancelorder.setEnabled(true);
                    cancelOrder();
                }else{
                    Toast.makeText(track_your_order.this, "You cannot cancel any order in progress", Toast.LENGTH_SHORT).show();

                }
            }
        });






        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




    }

    private void createReferences() {


        pointer=findViewById(R.id.pointer);
        ring1=findViewById(R.id.ring1);
        ring2=findViewById(R.id.ring2);
        ring3=findViewById(R.id.ring3);
        ring4=findViewById(R.id.ring4);
        ring5=findViewById(R.id.ring5);
        ring6=findViewById(R.id.ring6);
        ring7=findViewById(R.id.ring7);


        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        image3=findViewById(R.id.image3);
        image4=findViewById(R.id.image4);
        image5=findViewById(R.id.image5);
        image6=findViewById(R.id.image6);
        image7=findViewById(R.id.image7);




        dot1=findViewById(R.id.dot1);
        dot2=findViewById(R.id.dot2);
        dot3=findViewById(R.id.dot3);
        dot4=findViewById(R.id.dot4);
        dot5=findViewById(R.id.dot5);
        dot6=findViewById(R.id.dot6);
        dot7=findViewById(R.id.dot7);



        v2=findViewById(R.id.v2);
        v3=findViewById(R.id.v3);
        v4=findViewById(R.id.v4);
        v5=findViewById(R.id.v5);
        v6=findViewById(R.id.v6);
        v7=findViewById(R.id.v7);
        v8=findViewById(R.id.v8);
        v9=findViewById(R.id.v9);
        v10=findViewById(R.id.v10);
        v11=findViewById(R.id.v11);
        v12=findViewById(R.id.v12);
        v13=findViewById(R.id.v13);


        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        text3=findViewById(R.id.text3);
        text4=findViewById(R.id.text4);
        text5=findViewById(R.id.text5);
        text6=findViewById(R.id.text6);
        text7=findViewById(R.id.text7);

        l1=findViewById(R.id.l1);
        l2=findViewById(R.id.l2);
        l3=findViewById(R.id.l3);
        l4=findViewById(R.id.l4);
        l5=findViewById(R.id.l5);
        l6=findViewById(R.id.l6);
        l7=findViewById(R.id.l7);

        track_layout=findViewById(R.id.track_layout);
        cancelorder=findViewById(R.id.cancelorder);
        orderdetails=findViewById(R.id.orderdetail);

        tvorderid=findViewById(R.id.orderid);
        tvdeliveredby=findViewById(R.id.deliveredby);






    }
    private void movepointer(int id) {
        paintwhite(null,v2,ring1,dot1,image1,text1,R.color.color1,R.drawable.ring_black);
        if (id > 0) {
            pointer.animate().translationYBy(id*l1.getHeight()).setDuration(2000).withEndAction(new Runnable() {
                @Override
                public void run() {
                    switch(progstatus){
                        case 0:

                            break;
                        case 1:
                            paintwhite(null,v2,ring1,dot1,image1,text1,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v3,v4,ring2,dot2,image2,text2,R.color.white,R.drawable.rightdraw);

                            break;
                        case 2:
                            paintwhite(null,v2,ring1,dot1,image1,text1,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v3,v4,ring2,dot2,image2,text2,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v5,v6,ring3,dot3,image3,text3,R.color.white,R.drawable.rightdraw);

                            break;
                        case 3:
                            paintwhite(null,v2,ring1,dot1,image1,text1,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v3,v4,ring2,dot2,image2,text2,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v5,v6,ring3,dot3,image3,text3,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v7,v8,ring4,dot4,image4,text4,R.color.white,R.drawable.rightdraw);

                            break;
                        case 4:
                            paintwhite(null,v2,ring1,dot1,image1,text1,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v3,v4,ring2,dot2,image2,text2,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v5,v6,ring3,dot3,image3,text3,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v7,v8,ring4,dot4,image4,text4,R.color.color2,R.drawable.color2_border_ring);;
                            paintwhite(v9,v10,ring5,dot5,image5,text5,R.color.white,R.drawable.rightdraw);

                            break;
                        case 5:
                            paintwhite(null,v2,ring1,dot1,image1,text1,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v3,v4,ring2,dot2,image2,text2,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v5,v6,ring3,dot3,image3,text3,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v7,v8,ring4,dot4,image4,text4,R.color.color2,R.drawable.color2_border_ring);;
                            paintwhite(v9,v10,ring5,dot5,image5,text5,R.color.color2,R.drawable.color2_border_ring);;
                            paintwhite(v11,v12,ring6,dot6,image6,text6,R.color.white,R.drawable.rightdraw);

                            break;
                        case 6:
                            paintwhite(null,v2,ring1,dot1,image1,text1,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v3,v4,ring2,dot2,image2,text2,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v5,v6,ring3,dot3,image3,text3,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v7,v8,ring4,dot4,image4,text4,R.color.color2,R.drawable.color2_border_ring);;
                            paintwhite(v9,v10,ring5,dot5,image5,text5,R.color.color2,R.drawable.color2_border_ring);;
                            paintwhite(v11,v12,ring6,dot6,image6,text6,R.color.color2,R.drawable.color2_border_ring);
                            paintwhite(v13,null,ring7,dot7,image7,text7,R.color.white,R.drawable.rightdraw);


                            break;

                    }

                }
            });
        }else {
            pointer.animate().translationYBy(0).withEndAction(new Runnable() {
                @Override
                public void run() {
                    // pointer.setVisibility(View.VISIBLE);
                    paintwhite(null,v2,ring1,dot1,image1,text1,R.color.white,R.drawable.rightdraw);

                }
            });
        }
    }

    private void paintwhite(View v1,View v2,LinearLayout ring,ImageView dot,ImageView icon,TextView text,int color,int drawable){
        if(v1!=null)
            v1.setBackgroundColor(getResources().getColor(color));
        if(v2!=null)
            v2.setBackgroundColor(getResources().getColor(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dot.getDrawable().setTint(getResources().getColor(color));
            icon.getDrawable().setTint(getResources().getColor(color));
        }
        text.setTextColor(getResources().getColor(color));
        ring.setBackground(getResources().getDrawable(drawable));

    }

    private void cancelOrder(){
        dialog = new Dialog(track_your_order.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.confirmation_popup);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        Button no,yes,cancelorder,rclose;
        final RadioGroup radioGroup;
        final LinearLayout cancelreason,confirm;
        TextView msg;

        r1=dialog.findViewById(R.id.r1);
        r2=dialog.findViewById(R.id.r2);
        r3=dialog.findViewById(R.id.r3);
        r4=dialog.findViewById(R.id.r4);
        msg=dialog.findViewById(R.id.title_text_alert);
        msg.setText("Really want to cancel this Order ?");
        reson=dialog.findViewById(R.id.otherreason);
        radioGroup=dialog.findViewById(R.id.radiogroup);

        cancelreason=dialog.findViewById(R.id.cancelreason);
        confirm=dialog.findViewById(R.id.confirm);
        yes=dialog.findViewById(R.id.yes);
        no=dialog.findViewById(R.id.no);
        rclose= dialog.findViewById(R.id.rclose);


        cancelorder=dialog.findViewById(R.id.cancelorder);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.setVisibility(View.GONE);
                cancelreason.setVisibility(View.VISIBLE);


            }
        });

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson.setVisibility(View.GONE);
                reson.setText("");

            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson.setVisibility(View.GONE);
                reson.setText("");

            }
        });

        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson.setVisibility(View.GONE);
                reson.setText("");

            }
        });


        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson.setVisibility(View.VISIBLE);
                reson.requestFocus();

            }
        });

        rclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date currentdate= Calendar.getInstance().getTime();

                switch (radioGroup.getCheckedRadioButtonId()){

                    case R.id.r1:
                        reason=r1.getText().toString();
                        break;
                    case R.id.r2:
                        reason=r2.getText().toString();
                        break;
                    case R.id.r3:
                        reason=r3.getText().toString();
                        break;
                    case R.id.r4:
                        if(TextUtils.isEmpty(reson.getText().toString())){
                            Toast.makeText(track_your_order.this, "Please enter cause of cancellation", Toast.LENGTH_SHORT).show();
                        }else{
                            reason=reson.getText().toString();}
                        break;

                }


                if(reason!=null) {
                    databaseReference.child(orderid).child("dateofcancellation").setValue(currentdate);
                    databaseReference.child(orderid).child("reason_of_cancellation").setValue(reason);
                    databaseReference.child(orderid).child("prog_status").setValue(10);
                    Transactions transactions = new Transactions(orderid, "Order Cancelled", currentdate, allamount);

                    databaseReference2.child(currentdate.getTime() + "").setValue(transactions);


                    databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(!cartorder.getPaymentmode().equals("COD")){
                            int amount = dataSnapshot.child("balance").getValue(Integer.class);
                            databaseReference3.child("balance").setValue(amount + allamount);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    cancel_order_details cancelled_order = new cancel_order_details();


                    Bundle bundle = new Bundle();
                    bundle.putString("orderid", orderid);


                    cancelled_order.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, cancelled_order).commit();


                    dialog.dismiss();
                    track_layout.setVisibility(View.GONE);

                }else{
                    Toast.makeText(track_your_order.this, "Please enter cause of cancellation", Toast.LENGTH_SHORT).show();
                }
            }
        });


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    private void setupViewPager(ViewPager viewPager) {
        Tab_layout_adapter tab_layout_adapter = new Tab_layout_adapter(getSupportFragmentManager());
        tab_layout_adapter.addFragment(new pending_orders(), "Pending");
        tab_layout_adapter.addFragment(new completed_orders(), "Completed");
        tab_layout_adapter.addFragment(new cancelled_order(), "Cancelled");

        viewPager.setAdapter(tab_layout_adapter);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (fragment != null) {

            track_layout.setVisibility(View.GONE);
            fragmentTransaction.remove(fragment);

            fragmentTransaction.commit();


        } else {
            if(track_layout.getVisibility()==View.VISIBLE){

                track_layout.setVisibility(View.GONE);
            }else {
                super.onBackPressed();

            }
        }
    }
}