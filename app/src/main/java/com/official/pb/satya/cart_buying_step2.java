package com.official.pb.satya;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


import static android.app.Activity.RESULT_OK;


public class cart_buying_step2 extends Fragment {

    cart_buying_process_step2_recycler_view_adapter adapter;
    RecyclerView allitems;
    TextView grandtotal, numofitems, servicetax, username, userphone, deliveryaddress, deliverycharges;
    List<cart_list> list;
    Button countinue;
   Dialog dialog,dialogcod ;

    public static final String PAYTM_PACKAGE_NAME = "net.one97.paytm";
    DatabaseReference databaseReference, mdatabase, databaseReference3, databaseReference2,databaseReference4,databaseReference5;
    FirebaseAuth auth;
    int userbalance;
    RadioButton radio1,radio2,radio3;
    RadioGroup group;
    LinearLayout freedeliverycard,fastdeliverycard;
    RoundedImageView imageView;
    EditText extranumber;
    String status;
    Uri uri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart_buying_step2, container, false);
        allitems = v.findViewById(R.id.allitems);
        username = v.findViewById(R.id.username);
        dialog=new Dialog(getContext());

        radio1=v.findViewById(R.id.radio1);
        radio2=v.findViewById(R.id.radio2);
        radio3=v.findViewById(R.id.radio3);
        group=v.findViewById(R.id.radio_group);

        dialogcod=new Dialog(getContext());
        userphone = v.findViewById(R.id.userphonenumber);
        fastdeliverycard = v.findViewById(R.id.fastdeliverycard);
        freedeliverycard=v.findViewById(R.id.freedeliverycard);
        deliveryaddress = v.findViewById(R.id.useraddress);
        imageView = v.findViewById(R.id.userpic);
        servicetax = v.findViewById(R.id.servicetax);
        countinue = v.findViewById(R.id.countinue);
        grandtotal = v.findViewById(R.id.total_price2);
        extranumber = v.findViewById(R.id.extranumber);
        username = v.findViewById(R.id.username);
        userphone = v.findViewById(R.id.userphonenumber);
        numofitems = v.findViewById(R.id.numofitems);
        deliverycharges = v.findViewById(R.id.deliverycharges);
        auth = FirebaseAuth.getInstance();
        databaseReference4=FirebaseDatabase.getInstance().getReference("Transactions");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber());
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Admin");
        databaseReference = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("Orders").child("cartorder");
        mdatabase = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        databaseReference5=FirebaseDatabase.getInstance().getReference("Master_prices");
        allitems.setLayoutManager(layoutManager);


        list = new ArrayList<>();

        databaseReference.child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    cart_list cartorder = dataSnapshot1.getValue(cart_list.class);
                    list.add(0, cartorder);
                }

                numofitems.setText("(" + list.size() + " Items)");
                adapter = new cart_buying_process_step2_recycler_view_adapter(getContext(), list);
                allitems.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        countinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radio1.isChecked()){
                    databaseReference.child("extraphonenumber").setValue(extranumber.getText().toString());
                    extranumber.clearFocus();

                    String vpa = "9805144775@okbizaxis";

                    if(username.getText().toString() != null && vpa != null && grandtotal.getText().toString() != null && list.get(0).getName() != null){

                        uri = getPayTmUri(username.getText().toString(), vpa, list.get(0).getName(),grandtotal.getText().toString().substring(2));
                        payWithPayTm(PAYTM_PACKAGE_NAME);

                    }
                }else if(radio2.isChecked()){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.pay_by_wallet_pop_up);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    final Button no, addmoney,yesproceed;
                    final TextView symbol=dialog.findViewById(R.id.symbol);
                    final TextView balance=dialog.findViewById(R.id.balance);
                    final TextView discription=dialog.findViewById(R.id.dis);
                    yesproceed=dialog.findViewById(R.id.yesproceed);
                    addmoney = dialog.findViewById(R.id.addmoney);

                    no = dialog.findViewById(R.id.no);
                    addmoney.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    addmoney.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent i=new Intent(getContext(),add_money.class);
                            i.putExtra("cart_key","SW");
                            startActivity(i);


                        }
                    });

                    yesproceed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String orderid = wallet_getRandomNumber();

                                    cartorder cartorder = dataSnapshot.getValue(cartorder.class);
                                    cartorder.setOrderid(orderid);
                                    cartorder.setStatus("success");
                                    Date currentdate=Calendar.getInstance().getTime();
                                    cartorder.setOrderDate(currentdate);
                                    cartorder.setPaymentmode("wallet");
                                    int allamount=Integer.valueOf(cartorder.getGrandtotal())+cartorder.getDeliverycharges();
                                    Transactions transactions=new Transactions(orderid,"Order Placed",cartorder.getOrderDate(),allamount);
                                    databaseReference2.child("Orders").child(orderid).setValue(cartorder);
                                    databaseReference3.child("Confirmed_orders").child(orderid).setValue(orderid);
                                    int balanceremain=userbalance-(allamount);
                                    databaseReference.child("extraphonenumber").setValue(extranumber.getText().toString());
                                    extranumber.clearFocus();
                                    databaseReference4.child(auth.getCurrentUser().getPhoneNumber()).child(cartorder.getOrderDate().getTime()+"").setValue(transactions);
                                    databaseReference3.child("balance").setValue(balanceremain);
                                    Intent i = new Intent(getContext(), finish_success_cart.class);
                                    i.putExtra("orderid", orderid);
                                    startActivity(i);
                                    (getActivity()).finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });



                    databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            userbalance=dataSnapshot.child("balance").getValue(Integer.class);
                            if(dataSnapshot.child("balance").getValue(Integer.class)>=Integer.valueOf(grandtotal.getText().toString().substring(2))){

                                balance.setText("Account Balance : ₹" + userbalance);
                                discription.setText("Really want to place this order?");
                                addmoney.setVisibility(View.GONE);
                                yesproceed.setVisibility(View.VISIBLE);
                                symbol.setText(" ? ");
                                dialog.show();

                            }else{
                                discription.setText("Sorry! Your wallet account has not \n enough balance.");
                                balance.setText("Account Balance : ₹" + userbalance);
                                addmoney.setVisibility(View.VISIBLE);
                                yesproceed.setVisibility(View.GONE);
                                symbol.setText(" ! ");
                                dialog.show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else if(radio3.isChecked()){
                    dialogcod.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogcod.setContentView(R.layout.confirmation_popup);
                    dialogcod.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    Button no, yes;
                    yes = dialogcod.findViewById(R.id.yes);
                    no = dialogcod.findViewById(R.id.no);
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String orderid = getRandomNumber();
                                    cartorder cartorder = dataSnapshot.getValue(cartorder.class);
                                    cartorder.setOrderid(orderid);
                                    cartorder.setOrderDate(Calendar.getInstance().getTime());
                                    cartorder.setStatus("status=success");
                                    cartorder.setPaymentmode("COD");
                                    databaseReference2.child("Orders").child(orderid).setValue(cartorder);
                                    databaseReference3.child("Confirmed_orders").child(orderid).setValue(orderid);
                                    databaseReference.child("extraphonenumber").setValue(extranumber.getText().toString());
                                    extranumber.clearFocus();

                                    Intent i = new Intent(getContext(), finish_success_cart.class);
                                    i.putExtra("orderid", orderid);
                                    startActivity(i);
                                    (getActivity()).finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogcod.dismiss();
                        }
                    });


                    dialogcod.show();
                }else {
                    Toast.makeText(getContext(), "Please Select Your Payment Method", Toast.LENGTH_SHORT).show();
                }



            }


        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                grandtotal.setText(String.valueOf("₹ "+String.valueOf(dataSnapshot.child("grandtotal").getValue(Integer.class)+dataSnapshot.child("deliverycharges").getValue(Integer.class)+dataSnapshot.child("servicetax").getValue(Integer.class))));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }



    private String getRandomNumber() {
        Date currenttime = Calendar.getInstance().getTime();
        String month = "" + (currenttime.getMonth() + 1);
        if (Integer.valueOf(month) < 10) {
            month = "0" + month;
        }
        String mdate = "" + (currenttime.getDate());
        if (Integer.valueOf(mdate) < 10) {
            mdate = "0" + mdate;
        }
        String dayofmonth = "" + (currenttime.getDay());
        if (Integer.valueOf(dayofmonth) < 10) {
            dayofmonth = "0" + dayofmonth;
        }
        Random rnd = new Random();
        int number = rnd.nextInt(99999999);

        // this will convert any number sequence into 6 character.
        String no = String.format("%08d", number);
        String myrand = "SATYA" + month + mdate + "C" + dayofmonth + "D" + no;

        return myrand;
    }
    private String wallet_getRandomNumber() {
        Date currenttime = Calendar.getInstance().getTime();
        String month = "" + (currenttime.getMonth() + 1);
        if (Integer.valueOf(month) < 10) {
            month = "0" + month;
        }
        String mdate = "" + (currenttime.getDate());
        if (Integer.valueOf(mdate) < 10) {
            mdate = "0" + mdate;
        }
        String dayofmonth = "" + (currenttime.getDay());
        if (Integer.valueOf(dayofmonth) < 10) {
            dayofmonth = "0" + dayofmonth;
        }
        Random rnd = new Random();
        int number = rnd.nextInt(99999999);

        // this will convert any number sequence into 6 character.
        String no = String.format("%08d", number);
        String myrand = "SATYA" + month + mdate + "S" + dayofmonth + "W" + no;
        return myrand;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            status = data.getStringExtra("Status").toLowerCase();
        }
        if ((RESULT_OK == resultCode) && status.equals("success")) {
            String response=data.getStringExtra("ApprovalRefNo");
            Toast.makeText(getContext(), "Transaction successful." + data.getStringExtra("ApprovalRefNo"), Toast.LENGTH_SHORT).show();
            final String myorderid = "SATYA" + response.substring(4);


            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cartorder cartorder = dataSnapshot.getValue(cartorder.class);
                    cartorder.setOrderid(myorderid);
                    cartorder.setStatus(status);
                    final Date date=Calendar.getInstance().getTime();
                    cartorder.setOrderDate(date);
                    cartorder.setPaymentmode("Online");
                    databaseReference2.child("Orders").child(myorderid).setValue(cartorder);
                    databaseReference3.child("Confirmed_orders").child(myorderid).setValue(myorderid);

                    Intent i=new Intent(getContext(),finish_success.class);
                    i.putExtra("orderid",myorderid);
                    startActivity(i);
                    (getActivity()).finish();
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else {
            final Dialog dialog = new Dialog(getContext());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.payment_failure_popup);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            TextView r=dialog.findViewById(R.id.Reason);
            Button cancel,retry;
            cancel=dialog.findViewById(R.id.cancel);
            retry=dialog.findViewById(R.id.retry);
            r.setText(data.getStringExtra("Reason"));

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference.child("extraphonenumber").setValue(extranumber.getText().toString());
                    extranumber.clearFocus();

                    String vpa = "9805144775@okbizaxis";

                    if(username.getText().toString() != null && vpa != null && grandtotal.getText().toString().substring(2) != null && list.get(0).getName() != null){

                        uri = getPayTmUri(username.getText().toString(), vpa, list.get(0).getName(),grandtotal.getText().toString().substring(2));
                        payWithPayTm(PAYTM_PACKAGE_NAME);

                    }
                }
            });

            dialog.show();

            Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
        }

    }

    private static Uri getPayTmUri(String name, String upiId, String note, String amount) {
        return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();
    }

    private void payWithPayTm(String packageName) {

        if (isAppInstalled(getContext(), packageName)) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(uri);
            i.setPackage(packageName);
            startActivityForResult(i, 0);

        } else {
            Toast.makeText(getContext(), "Paytm is not installed Please install and try again.", Toast.LENGTH_SHORT).show();
        }


    }

}
