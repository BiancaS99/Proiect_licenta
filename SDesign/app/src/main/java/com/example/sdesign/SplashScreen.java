package com.example.sdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class SplashScreen extends AppCompatActivity {

    Animation anim;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
       //
        imageView=(ImageView)findViewById(R.id.imageView2);
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run(){
//
//                Intent i=new Intent(SplashScreen.this,LoginActivity.class);
//                startActivity(i);
//
//                finish();
//            }
//        },2500);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent i=new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(i);
               // startActivity(new Intent(this,LoginActivity.class));
                // HomeActivity.class is the activity to go after showing the splash screen.
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(anim);
    }


}
