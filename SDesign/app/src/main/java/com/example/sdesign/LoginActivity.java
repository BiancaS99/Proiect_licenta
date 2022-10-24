package com.example.sdesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import static android.widget.Toast.LENGTH_LONG;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
private TextView mTitle,mRegister,mFogPass;
private EditText mEmail,mPass;
private Button mLog;
private Spinner mTipLogin;
private FirebaseAuth fauth;
private Utilizatori util;
private FirebaseAuth.AuthStateListener fAuthListener;
private FirebaseDatabase database;
    private DatabaseReference ref;
    private static  final   String TAG="Login";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTitle=findViewById(R.id.l_Title);
        mRegister=findViewById(R.id.l_register);
        mFogPass=findViewById(R.id.l_forget_pass);
        mEmail=findViewById(R.id.l_email);
        mPass=findViewById(R.id.l_pass);
        mLog=findViewById(R.id.l_login);
        mTipLogin=findViewById(R.id.spinner_tip);
        List<String> tip_login=new ArrayList<String>();
        tip_login.add("Client");
        tip_login.add("Producator");
        ArrayAdapter<String> tip_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tip_login);
        tip_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTipLogin.setAdapter(tip_adapter);
        fauth=FirebaseAuth.getInstance();
//        fAuthListener= new FirebaseAuth.AuthStateListener(){
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = fauth.getCurrentUser();
//                if(user!=null){
//                    Log.d(TAG, "signInWithEmail:success"+user.getUid());
//                }else{
//                    Log.d(TAG, "signInWithEmail:denied");
//                }
//
//
//            }
//        };

        mLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String pass = mPass.getText().toString().trim();
                if (validate()) {
                   // Toast.makeText(LoginActivity.this, util.getTip(), LENGTH_LONG).show();
                  //  if (util.getTip() == "Producator") {
//                       database=FirebaseDatabase.getInstance();
//                       ref=database.getReference("user");
//                       ref.addValueEventListener(new ValueEventListener() {
//                           @Override
//                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                               String email_base=dataSnapshot.getValue(Utilizatori.class).toString();
//                               String pass_base=dataSnapshot.getValue(Utilizatori.class).toString();
//                               if(email==email_base && pass==pass_base){
//                                   Toast.makeText(LoginActivity.this, "Autentificarea s-a efectuat cu succes", Toast.LENGTH_SHORT).show();
//                                   Intent intent20 = new Intent(LoginActivity.this, DevizActivity.class);
//                                   startActivity(intent20);
//                               }else{
//                                   Toast.makeText(LoginActivity.this, "Autentificarea NU s-a efectuat cu succes", Toast.LENGTH_SHORT).show();
//                               }
//                           }
//
//                           @Override
//                           public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                           }
//                       });





                        fauth.signInWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            String tip_login=mTipLogin.getSelectedItem().toString();
                                           if (tip_login=="Producator"){
                                                Toast.makeText(LoginActivity.this,"Ati fost autentificat cu succes ca si producator", LENGTH_LONG).show();
                                                Intent intent11=new Intent(LoginActivity.this,VizualizareActivity.class);
                                                startActivity(intent11);
                                            }else{
                                                Toast.makeText(LoginActivity.this,"Ati fost autentificat cu succes ca si client", LENGTH_LONG).show();
                                                Intent intent112=new Intent(LoginActivity.this,Collection.class);
                                                startActivity(intent112);
                                            }
//                                            Toast.makeText(LoginActivity.this, "Autentificarea s-a efectuat cu succes", Toast.LENGTH_SHORT).show();
//                                            Intent intent20 = new Intent(LoginActivity.this, DevizActivity.class);
//                                            startActivity(intent20);


                                        } else {
                                            Toast.makeText(LoginActivity.this, "Autentificarea nu s-a efectuat cu succes", LENGTH_LONG).show();

                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(LoginActivity.this,"Nu ai suficiente date in campuri", LENGTH_LONG).show();
//                        fauth.signInWithEmailAndPassword(email, pass)
//                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(LoginActivity.this, "Autentificarea s-a efectuat cu succes", Toast.LENGTH_SHORT).show();
//                                            Intent intent10 = new Intent(LoginActivity.this, Collection.class);
//                                            startActivity(intent10);
//
//
//                                        } else {
//                                            Toast.makeText(LoginActivity.this, "Autentificarea nu s-a efectuat cu succes", LENGTH_LONG).show();
//
//                                        }
//                                    }
//                                });
                  //  }
                }
            }






        });
        SpannableString ss= new SpannableString(getString(R.string.inreg));
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent11=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent11);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setTypeface(Typeface.DEFAULT_BOLD);
            }
        },36,41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mRegister.setText(ss);
        mRegister.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString sfp= new SpannableString(getString(R.string.forgot_pass));
        sfp.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent10=new Intent(LoginActivity.this,ResetPass.class);
                startActivity(intent10);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setTypeface(Typeface.DEFAULT_BOLD);
            }
        },0,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mFogPass.setText(sfp);
        mFogPass.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public boolean validate(){
        boolean valid=true;
        String email= mEmail.getText().toString();
        String pass=mPass.getText().toString();

        if(email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Email-ul nu are formatul corect!");
            valid=false;
        }
        if(pass.isEmpty()||pass.length()<6){
            mPass.setError("Parola trebuie sa aiba mai mult de 6 carectere");

            valid=false;
        }
        return valid;
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fauth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

}
