package com.example.sdesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
  private EditText re_email,re_pass,re_nume;
  private EditText re_data_n;
  private Spinner re_tip;
  private Button re_inreg;
  private FirebaseAuth fAuth;

  private DatabaseReference dbUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbUser= FirebaseDatabase.getInstance().getReference("user");
        re_email=findViewById(R.id.r_email);
        re_pass=findViewById(R.id.r_pass);
        re_nume=findViewById(R.id.r_nume);
        re_data_n=findViewById(R.id.r_birth);
        re_inreg=findViewById(R.id.r_sign);
        re_tip=findViewById(R.id.r_tip);
        fAuth=FirebaseAuth.getInstance();
        List<String> tip=new ArrayList<String>();
        tip.add("Client");
        tip.add("Producator");
        ArrayAdapter<String> tip_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tip);
        tip_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        re_tip.setAdapter(tip_adapter);
//        if(fAuth.getCurrentUser()!=null){
//            startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
//            finish();
//        }
        re_inreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=re_email.getText().toString();
                String pass=re_pass.getText().toString();
                String nume=re_nume.getText().toString();
                String data_n=re_data_n.getText().toString();
                String tip=re_tip.getSelectedItem().toString();
                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pass)){
                    String id=dbUser.push().getKey();
                    Utilizatori user=new Utilizatori(id,email,pass,nume,data_n,tip);
                    dbUser.child(id).setValue(user);
                    fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if (user.getTip()=="Producator"){
                                Toast.makeText(RegisterActivity.this,"Ati fost inregistrat cu succes ca si producator",Toast.LENGTH_LONG).show();
                                Intent intent11=new Intent(RegisterActivity.this,VizualizareActivity.class);
                                startActivity(intent11);
                            }else{
                                Toast.makeText(RegisterActivity.this,"Ati fost inregistrat cu succes ca si client",Toast.LENGTH_LONG).show();
                                Intent intent112=new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent112);
                            }

//                            Toast.makeText(RegisterActivity.this,"a reusit inregistrarea",Toast.LENGTH_SHORT).show();
//                            Intent intent11=new Intent(RegisterActivity.this,LoginActivity.class);
                        }else{
                            Toast.makeText(RegisterActivity.this,"Inregistrarea a fost facuta cu succes",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                }else{
                    Toast.makeText(RegisterActivity.this,"Inregistrare nu a fost facuta cu succes",Toast.LENGTH_LONG).show();
                }
//                String email=re_email.getText().toString().trim();
//                String pass=re_pass.getText().toString().trim();
//                fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(RegisterActivity.this,"a reusit inregistrarea",Toast.LENGTH_SHORT).show();
//                            Intent intent11=new Intent(RegisterActivity.this,LoginActivity.class);
//                        }else{
//                            Toast.makeText(RegisterActivity.this,"NU a reusit inregistrarea",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });

    }
    private void addUser(){
        String email=re_email.getText().toString();
        String pass=re_pass.getText().toString();
        String nume=re_nume.getText().toString();
        String data_n=re_data_n.getText().toString();
        String tip=re_tip.getSelectedItem().toString();
        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pass)){
            String id=dbUser.push().getKey();
            Utilizatori user=new Utilizatori(id,email,pass,nume,data_n,tip);
            dbUser.child(id).setValue(user);
            if (user.getTip()=="Producator"){
                Toast.makeText(RegisterActivity.this,"Ati fost autentificat cu succes ca si producator",Toast.LENGTH_LONG).show();
                Intent intent11=new Intent(RegisterActivity.this,VizualizareActivity.class);
            }else{
                Toast.makeText(RegisterActivity.this,"Ati fost autentificat cu succes ca si client",Toast.LENGTH_LONG).show();
                Intent intent112=new Intent(RegisterActivity.this,LoginActivity.class);
            }

        }else{
            Toast.makeText(RegisterActivity.this,"Inregistrare nu a fost facuta cu succes",Toast.LENGTH_LONG).show();
        }
    }
}
