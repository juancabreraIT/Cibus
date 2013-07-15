package com.sovarb.cibus;

import java.util.ArrayList;
import java.util.List;

import com.sovarb.cibus.adapter.AdaptadorTitulos;
import com.sovarb.cibus.adapter.Titulos;
import com.sovarb.cibus.db.Aditivo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityListaAditivos extends Activity implements OnItemClickListener {

	String[] listado;
	TextView tiposAditivos;
	ListView listaAditivos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_aditivos);

		listaAditivos = (ListView) findViewById(R.id.listaAditivos);
		tiposAditivos = (TextView) findViewById(R.id.consultaAditivo);
		
		Intent i = getIntent();
		listado = i.getStringArrayExtra(S.LISTADO);
		tiposAditivos.setText(i.getStringExtra(S.TIPO));
		cargarLista(listado);
		
		listaAditivos.setOnItemClickListener(this);
	}

	@Override
	protected void onResume(){
		super.onResume();

		S.data_base.openR();
		listado = S.data_base.consultaAditivosXTipo(tiposAditivos.getText().toString());
		S.data_base.close();
		
		cargarLista(listado);
	}
	
	/*
	 * Carga la lista de aditivos.
	 */
	public void cargarLista(String[] pListaAditivos){
		
		List<Titulos> datos = new ArrayList<Titulos>();
		
		for(int i=0; i < pListaAditivos.length; i = i + 2){
			Titulos aux = new Titulos(pListaAditivos[i], pListaAditivos[i+1]);
			datos.add(aux);
		}
		AdaptadorTitulos adaptador = new AdaptadorTitulos(this, datos, R.layout.list_item_2_0titulos);
		
		listaAditivos.setAdapter(adaptador);
		
	}
	
	public void pulsaAtras(View view){
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> a, View v, int position, long id) {

		String seleccion = ((Titulos)a.getAdapter().getItem(position)).getTitulo();
		
		S.data_base.openR();
		Aditivo infoAditivo = S.data_base.consultaAditivoXNumero(seleccion);
		S.data_base.close();
		
		Intent i = new Intent(this, ActivityDetalleAditivos.class);
		i.putExtra(S.INFO, infoAditivo);
		startActivity(i);
	}
	
}