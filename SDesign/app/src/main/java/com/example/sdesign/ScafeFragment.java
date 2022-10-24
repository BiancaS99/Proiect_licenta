package com.example.sdesign;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ScafeFragment extends Fragment {

    private ScafeViewModel mViewModel;
    private TextView sColor,sTip,sLungime;
    private Button sSimulare,sCereOferta;
    public static Spinner sColorChoice,sPlacaChoice;
    private EditText sLungimePerete;

    private FirebaseFirestore db;



    public  ScafeFragment ()  {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.scafe_fragment, container, false);
        sColor=view.findViewById(R.id.s_color);
        sColorChoice=view.findViewById(R.id.s_color_choice);
        String[] optCulori={"","Alb","Verde","Mov","Albastru","Portocaliu"};
        ArrayAdapter<String> adaptorCulori=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item,optCulori);
        sColorChoice.setAdapter(adaptorCulori);
//        if (sColorChoice.getSelectedItem() == "Alb") {
//            sColorChoice.setBackgroundColor(Color.BLUE);
//        }else if(sColorChoice.getSelectedItem()=="Verde"){
//            sColorChoice.setBackgroundColor(Color.GREEN);
        //trebuie facut cu un for ca sa schimbi cuoarea backgroundului in functie de ce culoare am ales
//        }
//        for(int i=0;i<adaptorCulori.getCount();i++){
//            if(sColorChoice.getSelectedItem(i)=="Alb"){
//                sColorChoice.setBackgroundColor(Color.WHITE);
//            }
//            if(adaptorCulori.getItem(i)=="Verde"){
//                sColorChoice.setBackgroundColor(Color.GREEN);
//            }
//        }
        if (sColorChoice.getSelectedItem() == "Alb")
            sColorChoice.setBackgroundColor(Color.WHITE);
        if (sColorChoice.getSelectedItem() == "Verde")
            sColorChoice.setBackgroundColor(Color.GREEN);
        if (sColorChoice.getSelectedItem() == "Mov")
            sColorChoice.setBackgroundColor(Color.MAGENTA);
        if (sColorChoice.getSelectedItem() == "Albastru")
            sColorChoice.setBackgroundColor(Color.BLUE);
        if (sColorChoice.getSelectedItem() == "Portocaliu")
            sColorChoice.setBackgroundColor(Color.YELLOW);

        sTip=view.findViewById(R.id.s_tip);
        sLungime=view.findViewById(R.id.s_lung_perete);
        sLungimePerete=view.findViewById(R.id.s_l_perete);
        sSimulare=view.findViewById(R.id.s_simulare);
        sCereOferta=view.findViewById(R.id.s_cereoferta);

        sPlacaChoice=view.findViewById(R.id.s_tip_choice);
        String[] optPlaca={"12.5","13.5","Aqua","Placa de foc","Acustica"};
        ArrayAdapter<String> adaptorPlaca=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item,optPlaca);
        sPlacaChoice.setAdapter(adaptorPlaca);
        //sLungimePerete=view.findViewById(R.id.s_lung_perete);
        sSimulare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentmain=new Intent(getActivity(),MainActivity.class);
                startActivity(intentmain);
            }
        });

        sCereOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDataToJson();
                saveDataInBD();
            }


        });
        return view;





    }
    private void saveDataInBD() {
        db=FirebaseFirestore.getInstance();

        String culoare_scafe=sColorChoice.getSelectedItem().toString();
        String tipPlaca=sPlacaChoice.getSelectedItem().toString();
        String lungime=sLungimePerete.getText().toString().trim();
        String produs="Scafa";

        DocumentReference dbcomenzi=db.collection("comenzi").document("scafa");
         Scafa scafa=new Scafa(produs,culoare_scafe,tipPlaca,lungime);
         dbcomenzi.set(scafa);


    }

    private void saveDataToJson() {

        try{
            String culoare_scafe=sColorChoice.getSelectedItem().toString();
            String tipPlaca=sPlacaChoice.getSelectedItem().toString();
            String lungime=String.valueOf(sLungimePerete);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Produs","scafa");
            jsonObject.put("Culoare Scafe", culoare_scafe);
            jsonObject.put("Tip Placa", tipPlaca);
            jsonObject.put("Lungime Perete", lungime);
            String scafaString = jsonObject.toString();// Define the File Path and its Name
            File file = new File(Environment.getExternalStorageDirectory(),"scafa.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(scafaString);
            bufferedWriter.close();
            //Toast.makeText(this,"Datele au fost salvate cu succes si trimise catre producator. Va multumim!",Toast.LENGTH_LONG).show();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        // Convert JsonObject to String Format


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScafeViewModel.class);
        // TODO: Use the ViewModel

    }

}
