package com.sovarb.cibus.adapter;

import java.util.List;

import com.sovarb.cibus.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorReglas extends ArrayAdapter<Regla> {

	Activity context;
	List<Regla> datos;
	int layout;

	public AdaptadorReglas(Activity context, List<Regla> pDatos, int pLayoutId) {
		super(context, pLayoutId, pDatos);
		this.context = context;
		this.datos = pDatos;
		this.layout = pLayoutId;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(this.layout, null);

        TextView regla = (TextView) item.findViewById(R.id.titulo);
        regla.setText(datos.get(position).getRegla());
        if(position % 2 == 0){
        	regla.setBackgroundResource(R.drawable.regla_background01);
        }else{
        	regla.setBackgroundResource(R.drawable.regla_background02);
        }
        
        return(item);
	}
}
