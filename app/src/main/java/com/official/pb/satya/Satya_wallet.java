package com.official.pb.satya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.SubtitleCollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class Satya_wallet extends AppCompatActivity {

    SubtitleCollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView recyclerView;
    AppBarLayout appBarLayout;
    DatabaseReference databaseReference,databaseReference2;
    FirebaseAuth auth;
    ProgressBar progressBar;
    FloatingActionButton addmoney;
    RoundedImageView imageView;
    wallet_transaction_adapter wallet_transaction_adapter;
    List<Transactions> transactions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satya_wallet);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.transactions);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Satya_wallet.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        auth=FirebaseAuth.getInstance();
        appBarLayout=findViewById(R.id.app_bar_layout);
        addmoney=findViewById(R.id.add_money);
        transactions=new ArrayList<>();
        imageView=findViewById(R.id.walletcover);
        progressBar=findViewById(R.id.wallet_progress);
        databaseReference2=FirebaseDatabase.getInstance().getReference("Transactions").child(auth.getCurrentUser().getPhoneNumber());
        databaseReference= FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber());
        collapsingToolbarLayout=findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.color1));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        toolbar.getOverflowIcon().setColorFilter(getResources().getColor(R.color.color1), PorterDuff.Mode.SRC_ATOP);
        collapsingToolbarLayout.setExpandedTitleTextColor(getResources().getColor(R.color.color2));
        collapsingToolbarLayout.setCollapsedSubtitleTextColor(getResources().getColor(R.color.color1));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.color2));
        collapsingToolbarLayout.setExpandedSubtitleTextColor(getResources().getColor(R.color.color1));
        collapsingToolbarLayout.setExpandedSubtitleTextAppearance(R.style.expandsubtitlebarsimple2);
        collapsingToolbarLayout.setCollapsedSubtitleTextAppearance(R.style.collapsed_title);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.Collapsedbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapssubtitlesimple);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                collapsingToolbarLayout.setTitle("₹ "+dataSnapshot.child("balance").getValue(Integer.class)+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transactions.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Transactions tran=dataSnapshot1.getValue(Transactions.class);
                    transactions.add(0,tran);
                }
                wallet_transaction_adapter=new wallet_transaction_adapter(Satya_wallet.this,transactions);
                recyclerView.setAdapter(wallet_transaction_adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Satya_wallet.this,add_money.class);
                i.putExtra("cart_key","null");
                startActivity(i);
                finish();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wallet_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.sort_by_amount:
                item.setChecked(true);
                progressBar.setVisibility(View.VISIBLE);
                databaseReference2.orderByChild("amount").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        transactions.clear();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            Transactions tran=dataSnapshot1.getValue(Transactions.class);
                            transactions.add(0,tran);
                        }
                        wallet_transaction_adapter=new wallet_transaction_adapter(Satya_wallet.this,transactions);
                        recyclerView.setAdapter(wallet_transaction_adapter);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;
            case R.id.sort_by_date:
                item.setChecked(true);
                progressBar.setVisibility(View.VISIBLE);
                databaseReference2.orderByChild("date").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        transactions.clear();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            Transactions tran=dataSnapshot1.getValue(Transactions.class);
                            transactions.add(0,tran);
                        }
                        wallet_transaction_adapter=new wallet_transaction_adapter(Satya_wallet.this,transactions);
                        recyclerView.setAdapter(wallet_transaction_adapter);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;

        }
        return true;

    }




}
