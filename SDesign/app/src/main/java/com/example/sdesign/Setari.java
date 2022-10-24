package com.example.sdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Setari extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setari);
        if(findViewById(R.id.fragment)!=null){
            if(savedInstanceState!=null){
                return;
            }
            getFragmentManager().beginTransaction().add(R.id.fragment,new Preferinte()).commit();
        }
    }
}