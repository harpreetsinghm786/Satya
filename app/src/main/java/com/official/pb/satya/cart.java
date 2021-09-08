package com.official.pb.satya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.SubtitleCollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cart extends AppCompatActivity {
    DatabaseReference mdatabase, mdatabase1;
    List<cart_list> list;
    RecyclerView recyclerView;
    shopping_cart_adapter adapter;
    LinearLayout cartbox;
    String name,userpic,k;
    Button ptb;
    FirebaseAuth auth;
    TextView final_price;
    LinearLayout emptycart;
    ProgressBar progressBar;
    TextView cart_qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ptb = findViewById(R.id.proceedtobuy);
        final_price=findViewById(R.id.final_price);
        recyclerView = findViewById(R.id.cart_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(cart.this, RecyclerView.VERTICAL, false);

        emptycart = findViewById(R.id.emptycart);
        cartbox = findViewById(R.id.cartbox);
        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        recyclerView.setLayoutManager(manager);
        cart_qty=findViewById(R.id.cart_qty);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        list = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.color1));
        }

        mdatabase = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber());
        mdatabase1 = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("Orders").child("cartorder");



        ptb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mdatabase.child("Locations").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                            Address address=dataSnapshot1.getValue(Address.class);
                            if(address.bool==true){
                                k ="House no. " + address.getHousenumber().trim() + "," + " street " + address.getStreetnumber().trim() + ", " + address.colony.trim() + ", " + address.getCity().trim() + ", " + "Near by " + address.getNearby().trim();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

                mdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       name= dataSnapshot.child("name").getValue(String.class);
                       userpic=dataSnapshot.child("url").getValue(String.class);

                        cartorder cartorder = new cartorder(Integer.valueOf(final_price.getText().toString().substring(2)),name, auth.getCurrentUser().getPhoneNumber(),"null",0,0,"null","null",userpic,k,"normal","null",  list,0,null,null,null,null);
                        mdatabase1.setValue(cartorder);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                Intent i = new Intent(cart.this, cart_buying_process.class);
                i.putExtra("price",final_price.getText().toString());
                startActivity(i);
                finish();

            }
        });


        mdatabase.child("cart").child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    cart_list cart_list = dataSnapshot1.getValue(cart_list.class);
                    list.add(0, cart_list);
                }

                if (list.size() > 0) {
                    adapter = new shopping_cart_adapter(cart.this, list);

                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    cartbox.setVisibility(View.VISIBLE);
                    int s = 0;
                    for (int i = 0; i < list.size(); i++) {
                        s = s + list.get(i).getPrice() * list.get(i).getQuantity();
                    }

                    final_price.setText("₹ "+s+"");
                    cart_qty.setText(+list.size()+" Items Only");

                } else {
                    emptycart.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
