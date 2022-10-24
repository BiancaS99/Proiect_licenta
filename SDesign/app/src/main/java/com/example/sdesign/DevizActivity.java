package com.example.sdesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DevizActivity extends AppCompatActivity {
    EditText mDimPerete,mNrScafe,mNrPanouri,mCuloare,mTipPlaca,mCostPlaca,mCostLipiciDev,mCostIpsos,mCostManaLucru,mTotal;
    Button mExport;
    Uri uri_attach;
    private static final int PICK_FROM_DOCUMENTS=101;
    int columnindex;
    String attachmentFile;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private DocumentReference noteref=db.document("comenzi/scafa");



    Bitmap header,scaledheader;
    int pageWidth=1200;

    float[] preturi=new float[]{0,20,25,27,70};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviz);
        mDimPerete=findViewById(R.id.id_dim_perete);
        mNrScafe=findViewById(R.id.id_nr_scafe_calc);
        mNrPanouri=findViewById(R.id.id_nr_pan_calc);
        mCuloare=findViewById(R.id.id_culoare_dev);
        mTipPlaca=findViewById(R.id.id_tip_dev);
        mCostPlaca=findViewById(R.id.id_cost_placa_dev);
        mCostLipiciDev=findViewById(R.id.id_cost_lipici_dev);
        mCostIpsos=findViewById(R.id.id_cost_ipsos_dev);
        mCostManaLucru=findViewById(R.id.id_cost_mana_lucru);
        mTotal=findViewById(R.id.id_total_dev);
        mExport=findViewById(R.id.id_button_export);
        header= BitmapFactory.decodeResource(getResources(),R.drawable.headerscafe);
        scaledheader=Bitmap.createScaledBitmap(header,1200,508,false);
        loadData();



        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        mExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDF();
                sendEmail();


            }
        });


    }

    private void loadData() {
        noteref.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String culoare=documentSnapshot.getString("culoare");
                            mCuloare.setText(culoare);
                            String lungime=documentSnapshot.getString("lungime");
                            mDimPerete.setText(lungime);
                            String tip_placa=documentSnapshot.getString("tipPlaca");
                            mTipPlaca.setText(tip_placa);
                        }else{
                            Toast.makeText(DevizActivity.this,"Inregistrarea nu exista",Toast.LENGTH_SHORT);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data_attach){
        if (requestCode==PICK_FROM_DOCUMENTS && resultCode== RESULT_OK){
            uri_attach=data_attach.getData();
            String[] filePath={MediaStore.Images.Media.DATA};
            Cursor cursor= getContentResolver().query(uri_attach,filePath,null,null,null);
            cursor.moveToFirst();
            columnindex=cursor.getColumnIndex(filePath[0]);
           attachmentFile=cursor.getString(columnindex);
           uri_attach=Uri.parse("/Deviz.pdf");
           cursor.close();
        }
    }
    public void sendEmail(){
        try{
            String email="solomonbianca72@gmail.com";
            String subiect="Deviz Incercari";
            String mesaj =" Termina o data cu disertatia ca sa bem sticla aia de cognac";
            final Intent emailIntent= new Intent((Intent.ACTION_SEND));
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
            emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{subiect});
            if(uri_attach!=null){
                emailIntent.putExtra(Intent.EXTRA_STREAM,uri_attach);
            }
           // emailIntent.putExtra(Intent.EXTRA_TEXT,new String[]{mesaj});
            this.startActivity(Intent.createChooser(emailIntent,"Se trimite email-ul către client"));


        }catch(Throwable t){
            Toast.makeText(this,"Email-ul nu a putut fi trimis"+t.toString(),Toast.LENGTH_LONG).show();
        }
    }


    private void createPDF(){
//        mExport.setOnClickListener((view) -> {
            if(mDimPerete.getText().toString().length()==0||
                    mNrScafe.getText().toString().length()==0||
                    mNrPanouri.getText().toString().length()==0||
                    mCuloare.getText().toString().length()==0||
                    mTipPlaca.getText().toString().length()==0||
                    mCostPlaca.getText().toString().length()==0||
                    mCostLipiciDev.getText().toString().length()==0||
                    mCostIpsos.getText().toString().length()==0||
                    mCostManaLucru.getText().toString().length()==0||
                    mTotal.getText().toString().length()==0){
                Toast.makeText(this,"Please complete all fields",Toast.LENGTH_LONG).show();
            }else {
                PdfDocument deviz = new PdfDocument();
                Paint imag_antet = new Paint();
                Paint titlu_antet=new Paint();

                PdfDocument.PageInfo deviz_info = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                PdfDocument.Page deviz_1 = deviz.startPage(deviz_info);
                Canvas imagine = deviz_1.getCanvas();

                imagine.drawBitmap(scaledheader,0,0,imag_antet);
                titlu_antet.setTextAlign(Paint.Align.CENTER);
                titlu_antet.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD_ITALIC));
                titlu_antet.setTextSize(70);
                imagine.drawText("Deviz",pageWidth/2,270,titlu_antet);

                imag_antet.setStyle(Paint.Style.STROKE);
                imag_antet.setStrokeWidth(2);
                imagine.drawRect(20,780,pageWidth-20,860,imag_antet);
                imag_antet.setTextAlign(Paint.Align.LEFT);
                imag_antet.setStyle(Paint.Style.FILL);
                imag_antet.setTextSize(40);
                imagine.drawText("Detalii tehnice",40,830,imag_antet);
                imagine.drawText("Cantitate",860,830,imag_antet);
                imagine.drawLine(680,790,680,840,imag_antet);
                imagine.drawText("Lungime perete",40,950,imag_antet);

                imagine.drawText(mDimPerete.getText().toString(),900,950,imag_antet);
                imagine.drawText("Numar scafe",40,1000,imag_antet);
                imagine.drawText(mNrScafe.getText().toString(),900,1000,imag_antet);
                imagine.drawText("Numar panouri",40,1050,imag_antet);
                imagine.drawText(mNrPanouri.getText().toString(),900,1050,imag_antet);
                imagine.drawText("Culoare",40,1100,imag_antet);
                imagine.drawText(mCuloare.getText().toString(),900,1100,imag_antet);
                imagine.drawText("Tip Placa",40,1150,imag_antet);
                imagine.drawText(mTipPlaca.getText().toString(),900,1150,imag_antet);

                //Materiale cost

                imag_antet.setStyle(Paint.Style.STROKE);
                imag_antet.setStrokeWidth(2);
                imagine.drawRect(20,1250,pageWidth-20,1330,imag_antet);
                imag_antet.setTextAlign(Paint.Align.LEFT);
                imag_antet.setStyle(Paint.Style.FILL);
                imag_antet.setTextSize(40);
                imagine.drawText("Materiale",40,1300,imag_antet);
                imagine.drawText("Cost",860,1300,imag_antet);
                imagine.drawLine(680,1260,680,1310,imag_antet);
                imagine.drawText("Cost Placă",40,1400,imag_antet);
                imagine.drawText(mCostPlaca.getText().toString(),900,1400,imag_antet);
                imagine.drawText("Cost Lipici",40,1450,imag_antet);
                imagine.drawText(mCostLipiciDev.getText().toString(),900,1450,imag_antet);
                imagine.drawText("Cost Ipsos",40,1500,imag_antet);
                imagine.drawText(mCostIpsos.getText().toString(),900,1500,imag_antet);
                imagine.drawText("Cost Mana Lucru",40,1550,imag_antet);
                imagine.drawText(mCostManaLucru.getText().toString(),900,1550,imag_antet);

            //Total:

                imag_antet.setStyle(Paint.Style.STROKE);
                imag_antet.setStrokeWidth(2);
                imagine.drawRect(20,1650,pageWidth-20,1730,imag_antet);
                imag_antet.setTextAlign(Paint.Align.LEFT);
                imag_antet.setStyle(Paint.Style.FILL);
                imag_antet.setTextSize(50);
                imagine.drawText("Total",40,1700,imag_antet);
                imagine.drawText(mTotal.getText().toString(),860,1700,imag_antet);
                imagine.drawLine(680,1660,680,1710,imag_antet);






                deviz.finishPage(deviz_1);
                File fisier = new File(Environment.getExternalStorageDirectory(), "/Deviz.pdf");
                try {
                    deviz.writeTo(new FileOutputStream(fisier));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                deviz.close();
            }

    //    });
    }
}