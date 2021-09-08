package com.official.pb.satya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class About_us extends AppCompatActivity {


    Animation fadein;
    TextView p1;
    Dialog dialog;
    DatabaseReference databaseReference;
    ImageView adminlogin;
    ImageView instagram,facebook,twitter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        instagram=findViewById(R.id.instagram);
        facebook=findViewById(R.id.facebook);
        twitter=findViewById(R.id.twitter);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        p1=findViewById(R.id.p1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.color1));
        }

        fadein=AnimationUtils.loadAnimation(About_us.this,R.anim.fade_in);
        fadein.setDuration(1000);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            p1.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/official.satya.fashion/"));
                startActivity(browserIntent);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/official.satya.fashion"));
                startActivity(browserIntent);

            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/FashionSatya"));
                startActivity(browserIntent);

            }
        });
    }


}
