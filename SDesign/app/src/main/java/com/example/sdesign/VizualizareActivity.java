package com.example.sdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class VizualizareActivity extends AppCompatActivity {

    private ListView lista_producator;
    private ComenziAdapter mAdapter;
    private List<Comenzi> comenziList=new ArrayList<>();
    public static final int ADD_COMANDA_CODE_UPDATE=202;

    private int pozitie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizare);
        lista_producator=findViewById(R.id.lista_prod);
        ArrayList<Comenzi> comenziArrayList=new ArrayList<>();
        comenziArrayList.add(new Comenzi(R.drawable.scafal,"Comanda #1"));

        mAdapter=new ComenziAdapter(this,comenziArrayList);
        lista_producator.setAdapter(mAdapter);

        lista_producator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent deviz_intent=new Intent(VizualizareActivity.this,DevizActivity.class);
                startActivity(deviz_intent);
            }
        });




    }
}