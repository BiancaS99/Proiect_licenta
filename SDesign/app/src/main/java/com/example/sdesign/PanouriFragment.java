package com.example.sdesign;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class PanouriFragment extends Fragment {
    TextView pText;
     static Spinner pColorChoice;
    Button pSimulare;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_panouri, container, false);
       pText=view.findViewById(R.id.p_cul);
       pColorChoice=view.findViewById(R.id.p_color_choice);
       String[] culori_panou={"Alb","Verde","Albastru"};
       ArrayAdapter<String> adaptorCuloriPanouri=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item,culori_panou);
        pColorChoice.setAdapter(adaptorCuloriPanouri);
        pSimulare=view.findViewById(R.id.p_sim);
        pSimulare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(getActivity(),PanouriActivity.class);
                startActivity(intent5);
            }
        });

        return view;
    }
}