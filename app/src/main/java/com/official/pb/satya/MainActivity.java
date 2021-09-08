package com.official.pb.satya;

import android.animation.ArgbEvaluator;
import android.app.Dialog;

import android.content.Context;

import android.graphics.Color;
import android.content.Intent;

import android.graphics.drawable.ColorDrawable;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.BuildConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.official.pb.satya.Genre_details.genre_detail_items_recycler_view_adapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ImageView cart;
    TextView num_items_in_cart;
    Random r;
    ImageView search,locations;
    String num,ch;
    LinearLayout progressbar;
    int appversion;
    List<user_review> reviews;
    RecyclerView user_reviews;
    Dialog logout_dialog,update_dialog;
    LinearLayout mask_Section;



    DatabaseReference databaseReference,databaseReference2,databaseReference4;
    FirebaseAuth firebaseAuth;

    RecyclerView sections;
    List<main_activity_section_list> section_list;


    List<Address> addresses;
    private TabLayout tabLayout;

    ViewPager viewPager2;
    daily_Deals_vp_adapter adapter;
    List<Banner_list> models;
    private int currentpage=-1;
    private LinearLayout dots_layout;
    private ImageView[] dots;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    DatabaseReference databaseReference3;


    String uname, phnnumber, urlpic;
    addlocation_adapter addlocation_adapter;
    Dialog dialog;
    RecyclerView locationrecyclerview;
    LinearLayout logout_yes,logout_no;
    user_review_adapter user_review_adapter;



    TextView nolocation,selectlocation;
    DatabaseReference databaseReferencelocation;
    LinearLayout addbewlocation,cancel_it,positivebutton;
    boolean bool = false;
    main_product_line_adapter main_product_line_adapter;

    EditText Housenumber, streetnumber, colony, city, nearbyedit;
    List<SliderItem> items;

    LinearLayout womens,girls,kids,marriage,simple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        womens=findViewById(R.id.womens_block);
        girls=findViewById(R.id.girls_block);
        kids=findViewById(R.id.kids_block);
        marriage=findViewById(R.id.marriage_block);
        simple=findViewById(R.id.simple_block);

        reviews=new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();

        user_reviews=findViewById(R.id.user_review_rv);
        mask_Section=findViewById(R.id.mask_section);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false);

        user_reviews.setLayoutManager(linearLayoutManager2);


        databaseReferencelocation = FirebaseDatabase.getInstance().getReference("Userdata").child(firebaseAuth.getCurrentUser().getPhoneNumber()).child("Locations");

        databaseReference = FirebaseDatabase.getInstance().getReference("Userdata");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Userdata");
        databaseReference4=FirebaseDatabase.getInstance().getReference("Banners");


        sections=findViewById(R.id.main_product_line);
        section_list=new ArrayList<>();

        databaseReference3=FirebaseDatabase.getInstance().getReference("Sections");

        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                section_list.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    main_activity_section_list main=dataSnapshot1.getValue(main_activity_section_list.class);
                    section_list.add(main);
                }
                main_product_line_adapter=new main_product_line_adapter(MainActivity.this,section_list);
                sections.setAdapter(main_product_line_adapter);
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        firebaseAuth = FirebaseAuth.getInstance();

        dots_layout = findViewById(R.id.ll_dots);
           // Call the function callInstamojo to start payment here

        Toolbar toolbar = findViewById(R.id.admintoolbar);
        setSupportActionBar(toolbar);
        dialog = new Dialog(MainActivity.this);
        logout_dialog = new Dialog(MainActivity.this);
        update_dialog=new Dialog(MainActivity.this);


        reviews.add(new user_review("Harvinder Kaur","Satya is an awsome app where i found all the needy stuff that i can wear at home and for office as well. Apart from that the Services are so trust worthy. Order can be placed very simple method. I will give 5 out of 5 rating for this app.","https://firebasestorage.googleapis.com/v0/b/satya-8b1a7.appspot.com/o/my%20own%20pics%2Froobi.jpg?alt=media&token=1e510839-98f9-43ec-b93f-b3c122024f82"));
        reviews.add(new user_review("Inderjit Kaur","Industry needs such a great so that they can connect any of the needy citize, most of the e-commerce apps are there who target only on some specific area of population, but this app serves all the levels of society very pleasantly. ","https://firebasestorage.googleapis.com/v0/b/satya-8b1a7.appspot.com/o/my%20own%20pics%2F44110198_2021036884619789_3272512359381336064_n.jpg?alt=media&token=f2816492-9e26-4f1d-bcbf-d36694eb0a55"));
        reviews.add(new user_review("Sejal Aggrawal","My friend had recommend me to use this app and trust me i got an amazing and satisfactory results from this android Application , the staff is very lenient and helpfull as much as they can, I will give 5 out of five to this android application.  ","https://firebasestorage.googleapis.com/v0/b/satya-8b1a7.appspot.com/o/my%20own%20pics%2Fsejal.jpg?alt=media&token=064df9a6-d4c5-4027-bb85-7218b3b9fb06"));

        user_review_adapter=new user_review_adapter(MainActivity.this,reviews);
        user_reviews.setAdapter(user_review_adapter);




        progressbar=findViewById(R.id.progress);
        locations=findViewById(R.id.locations);
        num_items_in_cart=findViewById(R.id.num_items_in_cart);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logout_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        update_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(R.layout.add_location_popup);
        logout_dialog.setContentView(R.layout.logout_pop_up);
        update_dialog.setContentView(R.layout.version_update_pop_up);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        logout_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
       update_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

       positivebutton=update_dialog.findViewById(R.id.positivebutton);
       cancel_it=update_dialog.findViewById(R.id.cancel_it);



        logout_no=logout_dialog.findViewById(R.id.cancel_it_log);
        logout_yes=logout_dialog.findViewById(R.id.yes_do_it);


        locationrecyclerview = dialog.findViewById(R.id.locations);
        nolocation = dialog.findViewById(R.id.nolocation);
        selectlocation=dialog.findViewById(R.id.select_location);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
        locationrecyclerview.setLayoutManager(layoutManager);
        cart = findViewById(R.id.cart);
        r=new Random();
        LinearLayout drawer = dialog.findViewById(R.id.drawer);
        addbewlocation = dialog.findViewById(R.id.addnewlocation);
        addbewlocation.setVisibility(View.GONE);
        locationrecyclerview.setVisibility(View.VISIBLE);
        Housenumber = dialog.findViewById(R.id.housenumber);
        streetnumber = dialog.findViewById(R.id.streetnumber);
        progressbar.setVisibility(View.VISIBLE);
        colony = dialog.findViewById(R.id.colony);
        viewPager2 = findViewById(R.id.viewpager);

        viewPager2.setPadding(30, 0, 30, 0);
        viewPager2.setClipToPadding(false);

        city = dialog.findViewById(R.id.city);
        order_number_generator();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
        sections.setLayoutManager(linearLayoutManager);


        nearbyedit = dialog.findViewById(R.id.nearby);
        final Button addit = dialog.findViewById(R.id.addgivenit);


        final Animation transintion = AnimationUtils.loadAnimation(MainActivity.this, R.anim.transition_upward);
        drawer.setAnimation(transintion);
        LinearLayout remove = dialog.findViewById(R.id.remove_from_wishlist);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
        params.width = metrics.widthPixels;
        navigationView.setLayoutParams(params);

        databaseReference2=FirebaseDatabase.getInstance().getReference("version");
        appversion= BuildConfig.VERSION_CODE;

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()&& !dataSnapshot.getValue(String.class).equals(appversion+"")){
                    update_dialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        search = findViewById(R.id.search);


        create_dots(0);
        models = new ArrayList<>();

        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Banner_list url=dataSnapshot1.getValue(Banner_list.class);
                    models.add(url);
                }
                adapter = new daily_Deals_vp_adapter(models,MainActivity.this);
                viewPager2.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mask_Section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new Mask_section()).commit();

            }
        });







        positivebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=com.official.pb.satya");
                startActivity(new Intent(Intent.ACTION_VIEW, marketUri));

            }
        });

        cancel_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               update_dialog.cancel();
            }
        });


        womens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("key","womens");
                age_wise_distribution frag= new age_wise_distribution();
                frag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,frag).commit();

            }
        });
        girls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("key","girls");
                age_wise_distribution frag= new age_wise_distribution();
                frag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,frag).commit();

            }
        });
        kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("key","kids");
                age_wise_distribution frag= new age_wise_distribution();
                frag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,frag).commit();

            }
        });
        marriage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("key","marriage");
                age_wise_distribution frag= new age_wise_distribution();
                frag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,frag).commit();

            }
        });
        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("key","home,simple");
                age_wise_distribution frag= new age_wise_distribution();
                frag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,frag).commit();

            }
        });



        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                viewPager2.setCurrentItem(++currentpage,true);
                if(currentpage==models.size()-1){
                    currentpage=-1;
                }
            }
        };



        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }

        },500,4000);

        viewPager2.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                create_dots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header, navigationView, false);
        navigationView.addHeaderView(headerView);





        selectlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool = (!bool);

                if (bool == true) {
                    addbewlocation.setVisibility(View.VISIBLE);
                    locationrecyclerview.setVisibility(View.GONE);

                }
                if (bool == false) {
                    addbewlocation.setVisibility(View.GONE);
                    locationrecyclerview.setVisibility(View.VISIBLE);
                }

            }
        });



        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, cart.class);
                startActivity(intent);
            }

        });
        final ImageButton profileeditor = headerView.findViewById(R.id.profileeditor);
        final TextView username = headerView.findViewById(R.id.username);
        final TextView number = headerView.findViewById(R.id.phonenumber);
        final RoundedImageView url = headerView.findViewById(R.id.profilepic);
         navigationView.setNavigationItemSelectedListener(MainActivity.this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.closed);
        drawerLayout.addDrawerListener(toggle);

        toggle.setDrawerSlideAnimationEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.color1));
        toggle.syncState();



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.getKey().equals(firebaseAuth.getCurrentUser().getPhoneNumber()) ) {
                        num_items_in_cart.setText(postSnapshot.child("cart").child("items").getChildrenCount() + "");
                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.getKey().equals(firebaseAuth.getCurrentUser().getPhoneNumber()) ) {
                        username.setText(postSnapshot.child("name").getValue().toString());
                        number.setText(postSnapshot.child("phonenumber").getValue().toString());
                        uname = postSnapshot.child("name").getValue().toString();
                        phnnumber = postSnapshot.child("phonenumber").getValue().toString();

                        try {
                            if (url.getDrawable() == null)
                                Picasso.with(MainActivity.this).load(postSnapshot.child("url").getValue().toString()).fit().centerCrop().into(url);
                            urlpic = postSnapshot.child("url").getValue().toString();
                        } catch (Exception e) {

                        }


                    }


                }


            }


            @Override


            public void onCancelled(@NonNull DatabaseError databaseError) {


            }


        });


        profileeditor.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, profile_editor.class);


                i.putExtra("name", uname);
                i.putExtra("phnnumber", phnnumber);
                i.putExtra("url", urlpic);
                startActivity(i);


            }
        });


        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bool = (!bool);

                if (bool == true) {
                    addbewlocation.setVisibility(View.VISIBLE);
                    locationrecyclerview.setVisibility(View.GONE);

                }
                if (bool == false) {
                    addbewlocation.setVisibility(View.GONE);
                    locationrecyclerview.setVisibility(View.VISIBLE);
                }


            }
        });


        addit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String key = databaseReference.push().getKey();
                if (TextUtils.isEmpty(Housenumber.getText().toString())) {
                    Toast.makeText(MainActivity.this, "House number is Required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(streetnumber.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Street number is Required", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(colony.getText().toString())) {
                    Toast.makeText(MainActivity.this, "colony name is Required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(city.getText().toString())) {
                    Toast.makeText(MainActivity.this, "city name is Required", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(nearbyedit.getText().toString())) {
                    Toast.makeText(MainActivity.this, "nearby place is Required", Toast.LENGTH_SHORT).show();

                } else {


                    Address address = new Address(Housenumber.getText().toString(), streetnumber.getText().toString(), colony.getText().toString(), city.getText().toString(), nearbyedit.getText().toString(), key, false);


                    databaseReferencelocation.child(key).setValue(address);


                    InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                    Toast.makeText(MainActivity.this, "Location Added", Toast.LENGTH_SHORT).show();
                    locationrecyclerview.setVisibility(View.VISIBLE);
                    Housenumber.setText("");
                    streetnumber.setText("");
                    colony.setText("");


                    city.setText("");
                    nearbyedit.setText("");
                    addbewlocation.setVisibility(View.GONE);



                }
            }
        });

        locations.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                dialog.show();
            }
        });




        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, search.class);
                startActivity(i);


            }
        });
        addresses = new ArrayList<>();


        databaseReferencelocation.addValueEventListener(new

                                                                ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                        addresses.clear();
                                                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {



                                                                            Address address1 = dataSnapshot1.getValue(Address.class);
                                                                            addresses.add(0, address1);


//                                                                            if (address1.bool == true) {
//                                                                                location.setVisibility(View.VISIBLE);
//
//
//                                                                                address.setText("House no. " + address1.getHousenumber().trim() + "," + " street " + address1.getStreetnumber().trim() + ", " + address1.colony.trim() + ", " + address1.getCity().trim() + ", " + "Near by " + address1.getNearby().trim());
//
//
//                                                                            }

                                                                        }


                                                                        if (addresses.size() > 0) {






                                                                            addlocation_adapter = new addlocation_adapter(MainActivity.this, addresses);

                                                                            locationrecyclerview.setAdapter(addlocation_adapter);
                                                                            nolocation.setVisibility(View.GONE);


                                                                        } else {
                                                                            nolocation.setVisibility(View.VISIBLE);
                                                                        }




                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }


                                                                });
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (fragment != null) {


            fragmentTransaction.remove(fragment);

            fragmentTransaction.commit();

        } else {
            super.onBackPressed();


        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent z = new Intent(MainActivity.this, MainActivity.class);
                startActivity(z);
                finish();
                break;


            case R.id.wishlist:

                Intent i = new Intent(MainActivity.this, Wishlist_.class);
                startActivity(i);
                break;
            case R.id.seach_by_category:
                search_by_category frag= new search_by_category();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,frag).commit();

                break;
            case R.id.trackyourorder:
                Intent k = new Intent(MainActivity.this,track_your_order.class);
                startActivity(k);
                break;
            case R.id.switchlocation:


                dialog.show();

                break;
            case R.id.satyawallet:

                Intent wallet=new Intent(MainActivity.this,Satya_wallet.class);
                startActivity(wallet);

                break;
            case R.id.aboutSatya:
                Intent j=new Intent(MainActivity.this,About_us.class);
                startActivity(j);

                break;

            case R.id.logout:

                logout_dialog.show();

                logout_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout_dialog.cancel();
                    }
                });

                logout_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        logout_dialog.cancel();
                        Intent logout = new Intent(MainActivity.this, login.class);
                        startActivity(logout);
                        finish();

                    }
                });



                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    private void order_number_generator(){

        if(r.nextInt(999)<10 && r.nextInt(999)>=0){
            num ="00"+r.nextInt(999);
        }else  if(r.nextInt(999)>=10 && r.nextInt(999)<100){
            num ="0"+r.nextInt(999);
        }else  if(r.nextInt(999)>=100 && r.nextInt(999)<1000){
            num =String.valueOf(r.nextInt(999));
        }


        Random i=new Random();
        Random j=new Random();
        Random k=new Random();

        String[] alpha={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};


        String alphabet=alpha[i.nextInt(26)]+alpha[j.nextInt(26)]+alpha[k.nextInt(26)];

        Log.d("tagori", "order_number_generator: "+"STY"+num+alphabet);
//        String ordernumber="STY"+String.valueOf(r.nextInt(999));

    }


    private void create_dots(int position) {
        if (dots_layout != null)
            dots_layout.removeAllViews();

        dots = new ImageView[4];
        for (int i = 0; i <4; i++) {
            dots[i] = new ImageView(this);
            if (i == position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dots[i].setImageDrawable(getDrawable(R.drawable.dots_active));

                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dots[i].setImageDrawable(getDrawable(R.drawable.dots_inactive));
                }

            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            dots_layout.addView(dots[i], params);
        }

    }




}