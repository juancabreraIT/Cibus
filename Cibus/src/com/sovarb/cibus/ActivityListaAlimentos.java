package com.sovarb.cibus;

import java.util.ArrayList;
import java.util.List;

import com.sovarb.cibus.adapter.AdaptadorTitulos;
import com.sovarb.cibus.adapter.Titulos;
import com.sovarb.cibus.db.Alimento;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityListaAlimentos extends Activity implements OnItemClickListener {

	String[] listado;
	TextView tituloActivity;
	ListView listaAlimentos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_alimentos);
		
		listaAlimentos = (ListView) findViewById(R.id.listaAlimentos);
		tituloActivity = (TextView) findViewById(R.id.consultaAlimento);
		
		Intent i = getIntent();
		listado = i.getStringArrayExtra(S.LISTADO);
		String aux = i.getStringExtra(S.TIPO);
		if(aux != null){
			tituloActivity.setText(aux);
		}
		cargarLista(listado);	
		
		listaAlimentos.setOnItemClickListener(this);
	}

	/*
	 * Carga la lista de aditivos.
	 */
	public void cargarLista(String[] pListaAlimentos){
		List<Titulos> datos = new ArrayList<Titulos>();

		for(int i = 0; i < pListaAlimentos.length; i = i + 3){
			Titulos aux = new Titulos(pListaAlimentos[i+1], pListaAlimentos[i+2]);
			datos.add(aux);
		}
		AdaptadorTitulos adaptador = new AdaptadorTitulos(this, datos, R.layout.list_item_2_0titulos);

		listaAlimentos.setAdapter(adaptador);
	}

	public void pulsaAtras(View view){
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		
		String alimentoSeleccionado = listado[position * 3];
		S.data_base.openR();
		Alimento infoAlimento = S.data_base.consultaAlimentoXId(alimentoSeleccionado);
		String[] ingredientes = S.data_base.consultaIngredientesAlimentoXId(alimentoSeleccionado);
		String[] nutrientes = S.data_base.consultaNutrientesAlimentoXId(alimentoSeleccionado);		
		String[] aditivos = S.data_base.consultaAditivosAlimentoXId(alimentoSeleccionado);
		S.data_base.close();
		
		Intent i = new Intent(this, ActivityDetalleAlimentos.class);
		i.putExtra(S.INFO, infoAlimento);
		i.putExtra(S.INGREDIENTES, ingredientes);
		i.putExtra(S.NUTRIENTES, nutrientes);
		i.putExtra(S.ADITIVOS, aditivos);
		startActivity(i);
	}

}