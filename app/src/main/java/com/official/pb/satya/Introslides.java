package com.official.pb.satya;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Introslides extends AppCompatActivity {
    ViewPager viewPager;
    Adapter adapter;

    List<Model> models;
    Button Signup;
    private int currentpage=-1;
    Integer[] colors = null;
    private LinearLayout dots_layout;
    private ImageView[] dots;
    private static final int PERMISSION_SEND_SMS = 123;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introslides);
        //requestSmsPermission();
        dots_layout = findViewById(R.id.ll_dots);
        Signup = findViewById(R.id.login);
        create_dots(0);
        models = new ArrayList<>();
        models.add(new Model(R.drawable.intro2, "Welocome to Satya", "Satya is a Free mobile app for placing Order for all types of female dressing stuff."));
        models.add(new Model(R.drawable.intro1, "Bridel Dresses", "Satya allows to BuynowOrder all kind of bridel dresses on customer demands"));
        models.add(new Model(R.drawable.intr4, "Patiala Shahi", "Serves the option of premium Royal Patiala Shahi dresses with various styles."));
        models.add(new Model(R.drawable.intro3, "Customization", "Customers can fully customise  dresses with there own fabrics, Laces, and accesories etc"));
        adapter = new Adapter(models, this);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(50, 0, 50, 0);
        viewPager.setClipToPadding(false);

        Integer[] color_temp = {getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color3)};
        colors = color_temp;






        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {
                create_dots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Introslides.this, login.class));
                finish();
            }
        });

    }

    private void requestSmsPermission() {

        // check permission is given
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.READ_SMS,
                            Manifest.permission.RECEIVE_SMS},
                    PERMISSION_SEND_SMS);
        } else {
            // permission already granted run sms send
            sendSms();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_SEND_SMS: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    sendSms();
                } else {
                    // permission denied
                }
                return;
            }
        }
    }

    private void sendSms(){
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void create_dots(int position) {
        if (dots_layout != null)
            dots_layout.removeAllViews();

        dots = new ImageView[4];
        for (int i = 0; i <4; i++) {
            dots[i] = new ImageView(this);
            if (i == position) {
                dots[i].setImageDrawable(getDrawable(R.drawable.dots_active));
            } else {
                dots[i].setImageDrawable(getDrawable(R.drawable.dots_inactive));

            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            dots_layout.addView(dots[i], params);
        }

    }
}