package com.sovarb.cibus.adapter;

import java.util.List;

import com.sovarb.cibus.R;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorMultiple extends ArrayAdapter<Titulos>{
	
	Activity context;
	List<Titulos> datos;
	int layout;
	
	public AdaptadorMultiple(Activity context, List<Titulos> pDatos, int pLayoutId) {
		super(context, pLayoutId, pDatos);		
		this.context = context;
		this.datos = pDatos;
		this.layout = pLayoutId;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = context.getLayoutInflater();
		
		if(datos.get(position).getTitulo() == "SEPARADOR"){
	        convertView = inflater.inflate(R.layout.list_item_1titulo, null);
	        TextView separador = (TextView) convertView.findViewById(R.id.titulo);
	        separador.setText(datos.get(position).getSubtitulo());
	        separador.setBackgroundColor(Color.rgb(128, 128, 128));
	        separador.setTextColor(Color.WHITE);
		}else{
			convertView = inflater.inflate(R.layout.list_item_2_1titulos, null);
	        TextView titulo = (TextView) convertView.findViewById(R.id.titulo);
	        titulo.setText(datos.get(position).getTitulo());

	        TextView subtitulo = (TextView) convertView.findViewById(R.id.subtitulo);
	        subtitulo.setText(datos.get(position).getSubtitulo());
		}

        return(convertView);
	}
}