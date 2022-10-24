package com.example.sdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddSimulation extends AppCompatActivity {
private Button mScafe,mPanou;
private TextView mText3;
//private Fragment sFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_simulation);
        //sFragment=new ScafeFragment();
        mScafe=findViewById(R.id.a_scafe);
        mPanou=findViewById(R.id.a_panel);
        mText3=findViewById(R.id.add_choice);

        mScafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//frame_container is your layout name in xml filetransaction.replace(R.id.frame_container, fragment);
                transaction.replace(R.id.frame_specificatii,new ScafeFragment());
                transaction.commit();
                transaction.addToBackStack(null);
//                Intent intent1=new Intent(AddSimulation.this,ScafeFragment.class);
//                startActivity(intent1);
            //    Intent intent1=new Intent(AddSimulation.this,MainActivity.class);
               // startActivity(intent1);
            }
        });
        mPanou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//frame_container is your layout name in xml filetransaction.replace(R.id.frame_container, fragment);
                transaction.replace(R.id.frame_specificatii,new PanouriFragment());
                transaction.commit();
                transaction.addToBackStack(null);
            }
        });

    }

    private void loadFragment(Fragment sFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//frame_container is your layout name in xml filetransaction.replace(R.id.frame_container, fragment);
        transaction.add(R.id.frame_specificatii,new ScafeFragment());
        transaction.commit();
        transaction.addToBackStack(null);

    }
}
