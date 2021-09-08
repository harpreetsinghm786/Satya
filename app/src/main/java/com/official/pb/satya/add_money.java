package com.official.pb.satya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class add_money extends AppCompatActivity {

    EditText amount;
    TextView balance,rupee,error;
    FirebaseAuth auth;
    DatabaseReference databaseReference,databaseReference2;
    InputMethodManager inputMethodManager;
    Dialog dialog;
    String status;
    String key;
    int userbalance;
    public static final String PAYTM_PACKAGE_NAME = "net.one97.paytm";
    Uri uri;
    String username;
    TextView available;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        amount=findViewById(R.id.amount);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        available=findViewById(R.id.wallet_balance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        key=getIntent().getStringExtra("cart_key");




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.color1));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        balance=findViewById(R.id.wallet_balance);
        rupee=findViewById(R.id.rupee);
        auth=FirebaseAuth.getInstance();
        databaseReference2= FirebaseDatabase.getInstance().getReference("Transactions").child(auth.getCurrentUser().getPhoneNumber());

        databaseReference= FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber());
        error=findViewById(R.id.error);
        dialog = new Dialog(add_money.this);
        amount.requestFocus();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                available.setText(dataSnapshot.child("balance").getValue(Integer.class)+"");
                username=dataSnapshot.child("name").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==1&& s.toString().startsWith("0")){
                    s.clear();
                }else{
                       error.setVisibility(View.GONE);}
            }
        });

    }


    public void Add_Amount_to_wallet(View view) {


        amount.clearFocus();
        inputMethodManager= (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
       inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        if(TextUtils.isEmpty(amount.getText().toString())){
            error.setVisibility(View.VISIBLE);
        }else{

            String vpa = "9805144775@okbizaxis";

            if(username != null && vpa != null && amount.getText().toString() != null ){

                uri = getPayTmUri(username, vpa, "Wallet Recharge",amount.getText().toString());
                payWithPayTm(PAYTM_PACKAGE_NAME);

            }
        }
    }


//
//    private void initListener() {
//        listener = new InstapayListener() {
//            @Override
//            public void onSuccess(String response) {
//                String[] components = response.split(":");
//                final Date date= Calendar.getInstance().getTime();
//                final String status = components[0];
//                String paymentid = components[3];
//                String[] id = paymentid.split("=");
//                final String myorderid = "SATYA" + id[1].substring(4);
//
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        userbalance=dataSnapshot.child("balance").getValue(Integer.class);
//                        databaseReference.child("balance").setValue(userbalance+Integer.valueOf(amount.getText().toString()));
//                        Transactions transactions =new Transactions(myorderid,"Wallet Recharge",date,Integer.valueOf(amount.getText().toString()));
//                        databaseReference2.child(date.getTime()+"").setValue(transactions);
//
//                        if(key.equals("SW")){
//
//                            onBackPressed();
//
//                        }else{
//
//                        Intent i=new Intent(add_money.this,Satya_wallet.class);
//                        startActivity(i);
//
//                        finish();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                Log.d("mydata", "onSuccess: "+ status +" "+ myorderid);
//
//            }
//
//            @Override
//            public void onFailure(int code, String reason) {
//
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.payment_failure_popup);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                TextView r=dialog.findViewById(R.id.Reason);
//                Button cancel,retry;
//                cancel=dialog.findViewById(R.id.cancel);
//                retry=dialog.findViewById(R.id.retry);
//                r.setText(reason);
//
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.cancel();
//                    }
//                });
//
//                retry.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        callInstamojoPay("gopy48@gmail.com",auth.getCurrentUser().getPhoneNumber().substring(3),amount.getText().toString(),"order","Guru");
//
//                    }
//                });
//
//                dialog.show();
//
//
//
//
//
//            }
//        };
//
//
//    }

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
            Toast.makeText(this, "Transaction successful." + data.getStringExtra("ApprovalRefNo"), Toast.LENGTH_SHORT).show();
            //String[] components = response.split(":");

//            final String status = components[0];
//            String paymentid = components[3];
//            String[] id = paymentid.split("=");
            final String myorderid = "SATYA" + response.substring(4);


            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final Date date= Calendar.getInstance().getTime();
                    userbalance=dataSnapshot.child("balance").getValue(Integer.class);
                    databaseReference.child("balance").setValue(userbalance+Integer.valueOf(amount.getText().toString()));
                    Transactions transactions =new Transactions(myorderid,"Wallet Recharge",date,Integer.valueOf(amount.getText().toString()));
                    databaseReference2.child(date.getTime()+"").setValue(transactions);

                    if(key.equals("SW")){

                        onBackPressed();

                    }else{

                        Intent i=new Intent(add_money.this,Satya_wallet.class);
                        startActivity(i);

                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //Log.d("mydata", "onSuccess: "+ status +" "+ myorderid);
        } else {
            final Dialog dialog = new Dialog(add_money.this);
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


                    String vpa = "9805144775@okbizaxis";

                    if(username != null && vpa != null && amount.getText().toString() != null ){

                        uri = getPayTmUri(username, vpa, "Wallet Recharge",amount.getText().toString());
                        payWithPayTm(PAYTM_PACKAGE_NAME);

                    }
                }
            });

            dialog.show();

            Toast.makeText(add_money.this, "failed", Toast.LENGTH_SHORT).show();
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

        if (isAppInstalled(add_money.this, packageName)) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(uri);
            i.setPackage(packageName);
            startActivityForResult(i, 0);

        } else {
            Toast.makeText(add_money.this, "Paytm is not installed Please install and try again.", Toast.LENGTH_SHORT).show();
        }


    }

}
