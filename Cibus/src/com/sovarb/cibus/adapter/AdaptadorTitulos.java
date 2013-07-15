package com.sovarb.cibus.adapter;

import java.util.List;

import com.sovarb.cibus.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorTitulos extends ArrayAdapter<Titulos> {

	Activity context;
	List<Titulos> datos;
	int layout;

	public AdaptadorTitulos(Activity context, List<Titulos> pDatos, int pLayoutId) {
		super(context, pLayoutId, pDatos);
		this.context = context;
		this.datos = pDatos;
		this.layout = pLayoutId;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(this.layout, null);

        TextView titulo = (TextView) item.findViewById(R.id.titulo);
        titulo.setText(datos.get(position).getTitulo());

        TextView subtitulo = (TextView) item.findViewById(R.id.subtitulo);
        subtitulo.setText(datos.get(position).getSubtitulo());

        return(item);
	}
}
