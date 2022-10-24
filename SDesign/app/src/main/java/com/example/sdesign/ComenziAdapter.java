package com.example.sdesign;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class ComenziAdapter extends ArrayAdapter<Comenzi> {

    private Context mContext;
    private List<Comenzi> lista_com=new ArrayList<>();
    public ComenziAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Comenzi> comenziList) {
        super(context,0,comenziList);
        mContext=context;
        lista_com=comenziList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View list_item=convertView;
        if (list_item==null){
            list_item= LayoutInflater.from(mContext).inflate(R.layout.lista_comenzi,parent,false);
        }
        Comenzi comanda_curenta=lista_com.get(position);
        ImageView img=(ImageView)list_item.findViewById(R.id.id_poza_scafa);
        img.setImageResource(comanda_curenta.getImagine());

        TextView tip=(TextView)list_item.findViewById(R.id.id_nume_comanda);
        tip.setText(comanda_curenta.getTitlu_comanda());

        return list_item;
    }


}
