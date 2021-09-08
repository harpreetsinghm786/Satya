package com.official.pb.satya;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {
    Animation animation;
    TextView title;
    ImageView quard1,quard2,quard3,quard4,quard5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        quard1=findViewById(R.id.quard1);
        quard2=findViewById(R.id.quard2);
        quard3=findViewById(R.id.quard3);
        quard4=findViewById(R.id.quard4);
        quard5=findViewById(R.id.quard5);
        title=findViewById(R.id.title);
        animation= AnimationUtils.loadAnimation(this,R.anim.fade_in);
        title.startAnimation(animation);
        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    Intent i;
                    i = new Intent(splash.this, Introslides.class);
                    startActivity(i);
                    finish();
                }else{
                    startActivity(new Intent(splash.this,MainActivity.class));
                    finish();
                }



            }
        },2000);



        quard1.animate()
                .scaleYBy(1.2f)
                .scaleXBy(1.2f)
                .setDuration(1000);

        quard2.animate()
                .scaleYBy(1.2f)
                .scaleXBy(1.2f)
                .setDuration(1000);
        quard3.animate()
                .scaleYBy(1.2f)
                .scaleXBy(1.2f)
                .setDuration(1000);

        quard4.animate()
                .scaleYBy(1.2f)
                .scaleXBy(1.2f)
                .setDuration(1000);
        quard5.animate()
                .scaleYBy(1.2f)
                .scaleXBy(1.2f)
                .setDuration(1000);




    }
}
