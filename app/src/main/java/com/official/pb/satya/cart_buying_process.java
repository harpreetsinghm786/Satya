package com.official.pb.satya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cart_buying_process extends AppCompatActivity {
    RecyclerView recyclerView;
    paymentprocess_location_adapter addlocation_adapter;
    DatabaseReference databaseReference,databaseReference2,databaseReference3;
    FirebaseAuth auth;
    TextView total_price;
    Button open, addlocation, countinue;
    EditText Housenumber, streetnumber, colony, city, nearbyedit;
    boolean bool = false;
    TextView nolocation;
    String s;
    boolean mode=false;
    TextView dmname1,dmdays1,dmprice1,dmname2,dmdays2,dmprice2;
    CardView dmcard1,dmcard2;
    ImageView dm1,dm2;
    ProgressBar loader;
    LinearLayout addnewlocation;
    LinearLayout snackbar;
    List<Address> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying_process);
        Housenumber = findViewById(R.id.housenumber);
        streetnumber = findViewById(R.id.streetnumber);
        total_price=findViewById(R.id.total_price);
        snackbar = findViewById(R.id.snackbar);
        countinue = findViewById(R.id.countinue);
        colony = findViewById(R.id.colony);
        loader = findViewById(R.id.loader);
        nolocation = findViewById(R.id.nolocation);
        dmcard1 = findViewById(R.id.dmcard1);
        dmcard2 = findViewById(R.id.dmcard2);
        dm1 = findViewById(R.id.dmpic1);
        dm2 = findViewById(R.id.dmpic2);
        dmname1 = findViewById(R.id.dmtext1);
        dmprice1 = findViewById(R.id.dmtext3);
        dmdays1 = findViewById(R.id.dmtext2);
        dmdays2 = findViewById(R.id.dmdays2);
        dmname2 = findViewById(R.id.dmname2);
        dmprice2 = findViewById(R.id.dmprice2);
        city = findViewById(R.id.city);
        nearbyedit = findViewById(R.id.nearby);
        auth = FirebaseAuth.getInstance();
        addnewlocation = findViewById(R.id.putlocation);
        open = findViewById(R.id.open);
        addlocation = findViewById(R.id.addgivenit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.color1));
        }
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Master_prices");
        databaseReference = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber());
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber());

        recyclerView = findViewById(R.id.locations_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(cart_buying_process.this, 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        list = new ArrayList<>();



        databaseReference3.child("Orders").child("cartorder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                total_price.setText("₹ "+String.valueOf(dataSnapshot.child("grandtotal").getValue(Integer.class)+dataSnapshot.child("servicetax").getValue(Integer.class)+dataSnapshot.child("deliverycharges").getValue(Integer.class)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dmcard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("Orders").child("cartorder").child("deliverymode").setValue("normal");

                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int a = dataSnapshot.child("normal_charges").getValue(Integer.class);
                        databaseReference.child("Orders").child("cartorder").child("deliverycharges").setValue(a);

                        int b= dataSnapshot.child("service_tax").getValue(Integer.class);
                        databaseReference.child("Orders").child("cartorder").child("servicetax").setValue(b);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dmcard1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color1)));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dm1.getDrawable().setTint(getResources().getColor(R.color.white));
                }

                dmname1.setTextColor(getResources().getColor(R.color.white));
                dmprice1.setTextColor(getResources().getColor(R.color.white));
                dmdays1.setTextColor(getResources().getColor(R.color.white));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dmcard2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dm2.getDrawable().setTint(getResources().getColor(R.color.color1));
                }

                dmname2.setTextColor(getResources().getColor(R.color.color1));
                dmprice2.setTextColor(getResources().getColor(R.color.color1));
                dmdays2.setTextColor(getResources().getColor(R.color.color1));
            }
        });

        dmcard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode=true;

                databaseReference3.child("Orders").child("cartorder").child("deliverymode").setValue("fast");


                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int a = dataSnapshot.child("fast_delivery").getValue(Integer.class);
                        databaseReference.child("Orders").child("cartorder").child("deliverycharges").setValue(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                snackbar.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(cart_buying_process.this, R.anim.transition_upward);
                snackbar.setAnimation(animation);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dmcard1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dm1.getDrawable().setTint(getResources().getColor(R.color.color1));
                }

                dmname1.setTextColor(getResources().getColor(R.color.color1));
                dmprice1.setTextColor(getResources().getColor(R.color.color1));
                dmdays1.setTextColor(getResources().getColor(R.color.color1));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dmcard2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color1)));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dm2.getDrawable().setTint(getResources().getColor(R.color.white));
                }

                dmname2.setTextColor(getResources().getColor(R.color.white));
                dmprice2.setTextColor(getResources().getColor(R.color.white));
                dmdays2.setTextColor(getResources().getColor(R.color.white));


                Handler h=new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Animation animation2 = AnimationUtils.loadAnimation(cart_buying_process.this, R.anim.fade_out);
                        snackbar.setAnimation(animation2);
                        snackbar.setVisibility(View.GONE);
                    }
                },1000);



            }
        });


        databaseReference.child("Locations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Address address = dataSnapshot1.getValue(Address.class);
                    list.add(0, address);
                    if (address.bool == true) {
                        s = "House no. " + address.getHousenumber().trim() + "," + " street " + address.getStreetnumber().trim() + ", " + address.colony.trim() + ", " + address.getCity().trim() + ", " + "Near by " + address.getNearby().trim();

                    }
                }

                databaseReference.child("Orders").child("cartorder").child("address").setValue(s);


                if (list.size() > 0) {
                    addlocation_adapter = new paymentprocess_location_adapter(cart_buying_process.this, list);
                    recyclerView.setAdapter(addlocation_adapter);
                    nolocation.setVisibility(View.GONE);

                    loader.setVisibility(View.GONE);

                } else {
                    nolocation.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        countinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() > 0) {


                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().add(R.id.process_container, new cart_buying_step2());
                    transaction.commit();
                } else {
                    Toast.makeText(cart_buying_process.this, "Please add your Location", Toast.LENGTH_SHORT).show();
                }
            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool = !bool;

                if (bool == true) {
                    addnewlocation.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) cart_buying_process.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(v.getRootView(), 0);
                    Housenumber.requestFocus();


                } else if (bool == false) {
                    addnewlocation.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) cart_buying_process.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                }

            }
        });

        addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = databaseReference.push().getKey();
                if (TextUtils.isEmpty(Housenumber.getText().toString())) {
                    Toast.makeText(cart_buying_process.this, "House number is Required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(streetnumber.getText().toString())) {
                    Toast.makeText(cart_buying_process.this, "Street number is Required", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(colony.getText().toString())) {
                    Toast.makeText(cart_buying_process.this, "colony name is Required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(city.getText().toString())) {
                    Toast.makeText(cart_buying_process.this, "city name is Required", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(nearbyedit.getText().toString())) {
                    Toast.makeText(cart_buying_process.this, "nearby place is Required", Toast.LENGTH_SHORT).show();

                } else {


                    Address address = new Address(Housenumber.getText().toString(), streetnumber.getText().toString(), colony.getText().toString(), city.getText().toString(), nearbyedit.getText().toString(), key, false);


                    databaseReference.child("Locations").child(key).setValue(address);


                    InputMethodManager imm = (InputMethodManager) cart_buying_process.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                    Toast.makeText(cart_buying_process.this, "Location Added", Toast.LENGTH_SHORT).show();
                    Housenumber.setText("");
                    streetnumber.setText("");
                    colony.setText("");
                    city.setText("");
                    nearbyedit.setText("");
                    addnewlocation.setVisibility(View.GONE);
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

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.process_container);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (fragment != null) {
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        } else {

            super.onBackPressed();
        }
    }
}
