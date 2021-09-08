package com.official.pb.satya;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smileyrating.SmileyRating;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Item_detail_and_customisation extends Fragment {

    SliderView sliderView;
    List<Images> images;
    EditText comment;
    String postid;
    String key;
    ImageView toggle;
    List<cart_list> carts;
    LinearLayout cycler;
    int sum = 0;
    Button dropdown;
    String k;
    ImageView button;
    RelativeLayout containerbox;
    List<cart_list> cart_list_data;
    List<cart_list> list;
    TextView main_name, main_price, subtotal;
    List<Addons> addons, billaddons, firebaselist;
    RecyclerView Addon, comment_recyclerview, acute_bill;
    Addon_recycler_view_adapter adapter_addons;
    FirebaseAuth auth;
    cart_list cart_list;

    boolean bool = false;
    SmileyRating smileRating;
    Button addtocart;
    int a = 0;
    int b = 0;
    TextView itemname, Price, Category, item_components, Notprice, stataus, percentageoff, saveprice, Rating;
    ImageButton share, like, addtowishlist;
    product_detail product_detail;
    String name;

    TextView nrlikes;
    Button buynow;
    ProgressBar progressBar;
    String urlmain;
    LinearLayout addones_button;
    String userpic,username;
    String sharename;
    RecyclerView user_reviews;
    List<user_review> reviews;
    private List<comment_list> post;
    user_review_adapter user_review_adapter;
    comment_list_recycler_view_adapter adapter;
    DatabaseReference mDatabase, mdatabase1, mdatabase2, mdatabase3, mdatabase4, mdatabase5,mdatabase6;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_item_detail_and_customisation, container, false);
        comment_recyclerview = v.findViewById(R.id.recycler_view_comment_box);
        Bundle bundle = this.getArguments();

        reviews=new ArrayList<>();
        addones_button=v.findViewById(R.id.add_ones_button);

        user_reviews=v.findViewById(R.id.user_review_rv);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);

        user_reviews.setLayoutManager(linearLayoutManager2);


        reviews.add(new user_review("Harvinder Kaur","Satya is an awsome app where i found all the needy stuff that i can wear at home and for office as well. Apart from that the Services are so trust worthy. Order can be placed very simple method. I will give 5 out of 5 rating for this app.","https://firebasestorage.googleapis.com/v0/b/satya-8b1a7.appspot.com/o/my%20own%20pics%2Froobi.jpg?alt=media&token=1e510839-98f9-43ec-b93f-b3c122024f82"));
        reviews.add(new user_review("Inderjit Kaur","Industry needs such a great so that they can connect any of the needy citize, most of the e-commerce apps are there who target only on some specific area of population, but this app serves all the levels of society very pleasantly. ","https://firebasestorage.googleapis.com/v0/b/satya-8b1a7.appspot.com/o/my%20own%20pics%2F44110198_2021036884619789_3272512359381336064_n.jpg?alt=media&token=f2816492-9e26-4f1d-bcbf-d36694eb0a55"));
        reviews.add(new user_review("Sejal Aggrawal","My friend had recommend me to use this app and trust me i got an amazing and satisfactory results from this android Application , the staff is very lenient and helpfull as much as they can, I will give 5 out of five to this android application.  ","https://firebasestorage.googleapis.com/v0/b/satya-8b1a7.appspot.com/o/my%20own%20pics%2Fsejal.jpg?alt=media&token=064df9a6-d4c5-4027-bb85-7218b3b9fb06"));

        user_review_adapter=new user_review_adapter(getContext(),reviews);
        user_reviews.setAdapter(user_review_adapter);



        postid = bundle.getString("postid");
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        comment_recyclerview.setLayoutManager(gridLayoutManager);
        auth = FirebaseAuth.getInstance();
        smileRating = v.findViewById(R.id.ratingbar);
        progressBar = v.findViewById(R.id.progressbar);
        main_name = v.findViewById(R.id.main_name);
        main_price = v.findViewById(R.id.main_price);
        toggle = v.findViewById(R.id.toggle);
        cycler = v.findViewById(R.id.cycler);
        dropdown = v.findViewById(R.id.dropdown);

        itemname = v.findViewById(R.id.item_name);
        buynow=v.findViewById(R.id.buynow);
        nrlikes = v.findViewById(R.id.nrlikes);
        Price = v.findViewById(R.id.price);
        Category = v.findViewById(R.id.item_category);
        containerbox = v.findViewById(R.id.container);
        item_components = v.findViewById(R.id.item_components);
        Notprice = v.findViewById(R.id.not_price);
        stataus = v.findViewById(R.id.status);
        Addon = v.findViewById(R.id.addons);
        acute_bill = v.findViewById(R.id.acutebill);
        addons = new ArrayList<>();
        addtowishlist = v.findViewById(R.id.addtowishlist);
        percentageoff = v.findViewById(R.id.percentege_off);
        saveprice = v.findViewById(R.id.save_price);
        share = v.findViewById(R.id.share);
        carts = new ArrayList<>();
        list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        comment_recyclerview.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager_acute = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        acute_bill.setLayoutManager(layoutManager_acute);

        like = v.findViewById(R.id.like);
        subtotal = v.findViewById(R.id.subtotal);
        post = new ArrayList<>();
        firebaselist = new ArrayList<>();
        cart_list_data = new ArrayList<>();
        addtocart = v.findViewById(R.id.addtocart);
        mdatabase1 = FirebaseDatabase.getInstance().getReference("Addons");
        mdatabase2 = FirebaseDatabase.getInstance().getReference("Userdata");
        mDatabase = FirebaseDatabase.getInstance().getReference("Comments").child(postid);
        mdatabase3 = FirebaseDatabase.getInstance().getReference("Comments").child(postid);
        mdatabase4 = FirebaseDatabase.getInstance().getReference("search_list").child(postid);
        mdatabase5 = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber());
        mdatabase6 = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("Orders");



        mdatabase3.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                post.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    comment_list comment_list = postSnapshot.getValue(comment_list.class);
                    post.add(0, comment_list);

                }
                //creating adapter
                adapter = new comment_list_recycler_view_adapter(getActivity(), post);
                comment_recyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bool = (!bool);

                if (bool == true) {
                    toggle.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    cycler.setVisibility(View.VISIBLE);


                }
                if (bool == false) {


                    cycler.setVisibility(View.GONE);


                    toggle.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }


            }
        });


        comment = v.findViewById(R.id.comment_text);
        button = v.findViewById(R.id.post);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!comment.getText().toString().isEmpty()){

                    addcomments();
                }

            }
        });


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like.getTag().equals("like")) {
                    FirebaseDatabase.getInstance().getReference().child("search_list").child(postid).child("likes").child(auth.getUid()).setValue(true);

                } else {
                    FirebaseDatabase.getInstance().getReference().child("search_list").child(postid).child("likes").child(auth.getUid()).removeValue();
                }
            }
        });


        islikes(postid, like);
        nrlikes(nrlikes, postid);

        images = new ArrayList<>();
        sliderView = v.findViewById(R.id.imageSlider);


        mdatabase4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                images.clear();
                if (dataSnapshot.exists()) {

                    product_detail = dataSnapshot.getValue(product_detail.class);


                    images.add(new Images(product_detail.getUrl_main()));
                    images.add(new Images(product_detail.getUrl2()));
                    images.add(new Images(product_detail.getUrl3()));


                    itemname.setText(product_detail.getName());

                    item_components.setText(product_detail.getItem_components());


                    smileRating.setRating((int) product_detail.getRating(), true);
                    smileRating.disallowSelection(true);

                    main_name.setText(product_detail.getName());
                    main_price.setText("₹ " + product_detail.getPrice());
                    sum = product_detail.getPrice();
                    subtotal.setText(String.valueOf(product_detail.getPrice()));
                    Category.setText("Genre : " + product_detail.getGenre());

                    if (product_detail.getStatus().equals("Trending")) {
                        stataus.setVisibility(View.VISIBLE);
                    } else {
                        stataus.setVisibility(View.GONE);
                    }

                    if(product_detail.getTags().toLowerCase().contains("mask")){
                        addones_button.setVisibility(View.GONE);
                    }else {
                        addones_button.setVisibility(View.VISIBLE);
                    }

                    percentageoff.setText(product_detail.getPercentage_off() + "%\nOff");


                    Notprice.setText("MRP:₹ " + product_detail.getNot_price());
                    Notprice.setPaintFlags(Notprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    Price.setText("₹ " + product_detail.getPrice());
                    int save = product_detail.getNot_price() - product_detail.getPrice();
                    saveprice.setText("Save ₹ " + save);

                    progressBar.setVisibility(View.GONE);
                    containerbox.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
                }

                product_images_adapter adapter = new product_images_adapter(images, getActivity());
                sliderView.setSliderAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                firebaselist.clear();
                for (int i = 0; i < acute_bill.getChildCount(); i++) {

                    TextView name = acute_bill.getChildAt(i).findViewById(R.id.addonname);
                    TextView price = acute_bill.getChildAt(i).findViewById(R.id.addonprice);
                    Addons addons = new Addons(name.getText().toString(), Integer.valueOf(price.getText().toString()));
                    firebaselist.add(addons);
                }

                mdatabase5.child("Locations").addListenerForSingleValueEvent(new ValueEventListener() {
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
                mdatabase5.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        list.clear();
                        username= dataSnapshot.child("name").getValue(String.class);
                        userpic=dataSnapshot.child("url").getValue(String.class);

                        firebaselist.clear();
                        for (int i = 0; i < acute_bill.getChildCount(); i++) {

                            TextView name = acute_bill.getChildAt(i).findViewById(R.id.addonname);
                            TextView price = acute_bill.getChildAt(i).findViewById(R.id.addonprice);
                            Addons addons = new Addons(name.getText().toString(), Integer.valueOf(price.getText().toString()));
                            firebaselist.add(addons);
                        }

                        cart_list = new cart_list(product_detail.getName(), Integer.valueOf(subtotal.getText().toString()), 1, product_detail.getUrl_main(), postid, firebaselist, product_detail.getPrice(),product_detail.getItem_components(),String.valueOf(product_detail.getPercentage_off()),String.valueOf(product_detail.getNot_price()));
                        list.add(cart_list);
                        cartorder cartorder = new cartorder(Integer.valueOf(subtotal.getText().toString()),name, auth.getCurrentUser().getPhoneNumber(),"null",0,0,"null","null",userpic,k,"normal","null",  list,0,null,null,null,null);
                        mdatabase6.child("Buynoworder").setValue(cartorder);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent i=new Intent(getContext(),buying_process.class);
                startActivity(i);

            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sharename + "\n\n" + urlmain);
                sendIntent.setType("text/plain");
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                v.getContext().startActivity(shareIntent);
            }
        });


        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 2);
        Addon.setLayoutManager(gridLayoutManager2);
        billaddons = new ArrayList<>();

        //adding an event listener to fetch values
        mdatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addons.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Addons add = dataSnapshot1.getValue(Addons.class);
                    addons.add(add);
                }
                adapter_addons = new Addon_recycler_view_adapter(getContext(), addons, acute_bill, subtotal, sum);
                Addon.setAdapter(adapter_addons);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setAutoCycle(false);//set scroll delay in seconds :


        addtowishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtowishlist();
                Toast.makeText(getActivity(), "Adding to wishlist...", Toast.LENGTH_SHORT).show();
            }
        });


        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtocart();
            }
        });
        mdatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.getKey().equals(auth.getCurrentUser().getPhoneNumber())) {
                        name = (postSnapshot.child("name").getValue().toString());


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

    private void addcomments() {




        final Date currenttime = Calendar.getInstance().getTime();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comment_list comment_list = new comment_list(name, comment.getText().toString(), currenttime);
                String k = mDatabase.push().getKey();
                mDatabase.child(k).setValue(comment_list);
                comment.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void islikes(String postid, final ImageButton imageView) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("search_list").child(postid).child("likes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                    imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
                    imageView.setTag("liked");
                } else {
                    imageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void nrlikes(final TextView likes, String postid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("search_list").child(postid).child("likes");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likes.setText(dataSnapshot.getChildrenCount() + " likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addtowishlist() {


        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("wishlist");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Wishlist_list list = new Wishlist_list(product_detail.getName(), product_detail.getPrice(), product_detail.getRating(), product_detail.getUrl_main(), postid);
                reference.child(postid).setValue(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void addtocart() {

        firebaselist.clear();
        for (int i = 0; i < acute_bill.getChildCount(); i++) {

            TextView name = acute_bill.getChildAt(i).findViewById(R.id.addonname);
            TextView price = acute_bill.getChildAt(i).findViewById(R.id.addonprice);
            Addons addons = new Addons(name.getText().toString(), Integer.valueOf(price.getText().toString()));
            firebaselist.add(addons);
        }

        cart_list = new cart_list(product_detail.getName(), Integer.valueOf(subtotal.getText().toString()), 1, product_detail.getUrl_main(), postid, firebaselist, product_detail.getPrice(),product_detail.getItem_components(),String.valueOf(product_detail.getPercentage_off()),String.valueOf(product_detail.getNot_price()));


        mdatabase5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("cart")) {

                    if (dataSnapshot.child("cart").hasChild(postid)) {
                        if (dataSnapshot.child("cart").child(postid).hasChild(String.valueOf(firebaselist.size()))) {
                            if (dataSnapshot.child("cart").child(postid).child(String.valueOf(firebaselist.size())).hasChild(subtotal.getText().toString())) {
                                if (dataSnapshot.child("cart").child(postid).child(String.valueOf(firebaselist.size())).child(subtotal.getText().toString()).hasChild("addons")) {
                                    for (int i = 0; i < dataSnapshot.child("cart").child(postid).child(String.valueOf(firebaselist.size())).child(subtotal.getText().toString()).child("addons").getChildrenCount(); i++) {
                                        for (int j = 0; j < firebaselist.size(); j++) {
                                            if (dataSnapshot.child("cart").child(postid).child(String.valueOf(firebaselist.size())).child(subtotal.getText().toString()).child("addons").child(String.valueOf(i)).child("name").getValue(String.class).equals(firebaselist.get(j).getName())) {
                                                a = dataSnapshot.child("cart").child(postid).child(String.valueOf(firebaselist.size())).child(subtotal.getText().toString()).child("quantity").getValue(Integer.class);
                                                if (a < 5) {
                                                    a++;
                                                    mdatabase5.child("cart").child(postid).child(String.valueOf(firebaselist.size())).child(String.valueOf(subtotal.getText().toString())).child("quantity").setValue(a);
                                                    mdatabase5.child("cart").child("items").child(subtotal.getText().toString()).child("quantity").setValue(a);
                                                    Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Toast.makeText(getContext(), "This is the maximum Limit of BuynowOrder for this dress", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        }
                                    }
                                } else {

                                    int x = dataSnapshot.child("cart").child(postid).child(String.valueOf(firebaselist.size())).child(subtotal.getText().toString()).child("quantity").getValue(Integer.class);
                                    if (x < 5) {
                                        x++;
                                        mdatabase5.child("cart").child(postid).child(String.valueOf(firebaselist.size())).child(String.valueOf(subtotal.getText().toString())).child("quantity").setValue(x);
                                        mdatabase5.child("cart").child("items").child(subtotal.getText().toString()).child("quantity").setValue(x);
                                        Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "This is the maximum Limit of BuynowOrder for this dress", Toast.LENGTH_SHORT).show();

                                    }
                                }

                            } else {
                                mdatabase5.child("cart").child(postid).child(String.valueOf(firebaselist.size())).child(String.valueOf(subtotal.getText().toString())).setValue(cart_list);
                                mdatabase5.child("cart").child("items").child(subtotal.getText().toString()).setValue(cart_list);
                                Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();

                            }


                        } else {
                            mdatabase5.child("cart").child(postid).child(String.valueOf(firebaselist.size())).child(String.valueOf(subtotal.getText().toString())).setValue(cart_list);
                            mdatabase5.child("cart").child("items").child(subtotal.getText().toString()).setValue(cart_list);
                            Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        mdatabase5.child("cart").child(postid).child(String.valueOf(firebaselist.size())).child(String.valueOf(subtotal.getText().toString())).setValue(cart_list);
                        mdatabase5.child("cart").child("items").child(subtotal.getText().toString()).setValue(cart_list);
                        Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();

                    }


                } else {
                    mdatabase5.child("cart").child(postid).child(String.valueOf(firebaselist.size())).child(String.valueOf(subtotal.getText().toString())).setValue(cart_list);
                    mdatabase5.child("cart").child("items").child(subtotal.getText().toString()).setValue(cart_list);
                    Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}