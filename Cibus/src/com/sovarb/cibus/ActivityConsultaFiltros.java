package com.sovarb.cibus;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.sovarb.cibus.adapter.AdaptadorMultiple;
import com.sovarb.cibus.adapter.Titulos;

public class ActivityConsultaFiltros extends Activity implements OnItemClickListener {

	private int numFiltrosUser = 0;
	Button botonAtras, botonAdd;
	ListView listaFiltros;
	List<Titulos> datos = new ArrayList<Titulos>();
	AdaptadorMultiple adaptador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_filtros);

		botonAtras = (Button) findViewById(R.id.botonBack);
		botonAdd = (Button) findViewById(R.id.botonAdd);
		listaFiltros = (ListView) findViewById(R.id.listaFiltros);

		cargarLista();

		listaFiltros.setOnItemClickListener(this);
	}

	@Override
	protected void onResume(){
		super.onResume();
		cargarLista();
	}

	public void cargarLista(){

		datos.clear();
		// Inicializando el adaptador y la lista
		Titulos aux;

		// Añadiendo cabecera de filtros de usuario
		aux = new Titulos(S.FILTROS_USUARIO[0], S.FILTROS_USUARIO[1]);
		datos.add(aux);

		// Añadiendo filtros de usuario
		S.data_base.openR();
		String[] filtros = S.data_base.consultaFiltros();
		S.data_base.close();
		if(filtros != null){
			numFiltrosUser = filtros.length;
			for(int i = 0; i < numFiltrosUser; i = i + 2){
				aux = new Titulos(filtros[i], filtros[i+1]);
				datos.add(aux);
			}
		}

		añadirFiltrosDefault();
		adaptador = new AdaptadorMultiple(this, datos, R.layout.list_item_2_1titulos);
		listaFiltros.setAdapter(adaptador);
	}

	private final void añadirFiltrosDefault(){
		Titulos aux = new Titulos(S.FILTROS_DEFAULT[0], S.FILTROS_DEFAULT[1]);
		datos.add(aux);

		aux = new Titulos(S.FILTRO_DEFAULT_1[0], S.FILTRO_DEFAULT_1[1]);
		datos.add(aux);

		aux = new Titulos(S.FILTRO_DEFAULT_2[0], S.FILTRO_DEFAULT_2[1]);
		datos.add(aux);

		aux = new Titulos(S.FILTRO_DEFAULT_3[0], S.FILTRO_DEFAULT_3[1]);
		datos.add(aux);
	}
	
	public void pulsaAtras(View view){	
		finish();	
	}

	public void pulsaNewFiltro(View view){
		
		Intent i = new Intent(this, ActivityAddFiltros.class);		
		startActivity(i);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {		

		Titulos seleccion = (Titulos) listaFiltros.getItemAtPosition(position);		
		String[] extra = {seleccion.getTitulo(), seleccion.getSubtitulo()};

		if(position != 0 && position != ((numFiltrosUser/2) + 1)){
			Intent i;
			if(position <= numFiltrosUser/2){	// Filtros User
				i = new Intent(this, ActivityDetalleFiltros.class);					
			}else{							// Filtros Default
				i = new Intent(this, ActivityDetalleFiltrosDefault.class);
			}	
			i.putExtra(S.FILTRO, extra);
			startActivity(i);
		}
	}

}