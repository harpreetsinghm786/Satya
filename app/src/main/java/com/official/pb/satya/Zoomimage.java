package com.official.pb.satya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

public class Zoomimage extends AppCompatActivity {
    ZoomageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoomimage);

        image=findViewById(R.id.iv_auto_image_slider);
        Picasso.with(Zoomimage.this).load(getIntent().getStringExtra("url")).into(image);

    }
}
