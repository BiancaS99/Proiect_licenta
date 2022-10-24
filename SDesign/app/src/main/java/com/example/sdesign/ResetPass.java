package com.example.sdesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPass extends AppCompatActivity {

    private Button rsB;
    private EditText rsEmail;
    private TextView rsText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        mAuth=FirebaseAuth.getInstance();

        rsText=findViewById(R.id.rs_text);
        rsEmail=findViewById(R.id.rs_email);
        rsB=findViewById(R.id.rs_button);
        rsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=rsEmail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(ResetPass.this,"Campul trebuie completat",Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPass.this,"A fost trimis linkul de resetare al parolei",Toast.LENGTH_SHORT).show();
                                Intent intent9=new Intent(ResetPass.this,LoginActivity.class);
                                startActivity(intent9);
                            }else{
                                String mesaj=task.getException().getMessage();
                                Toast.makeText(ResetPass.this,"Ups...a intervenit o eroare..."+mesaj,Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }
        });
    }
}