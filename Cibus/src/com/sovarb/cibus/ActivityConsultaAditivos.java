package com.sovarb.cibus;

import java.util.ArrayList;
import java.util.List;

import com.sovarb.cibus.adapter.AdaptadorTitulos;
import com.sovarb.cibus.adapter.Titulos;
import com.sovarb.cibus.db.Aditivo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityConsultaAditivos extends Activity implements OnKeyListener, OnItemClickListener {

	Button botonAtras, botonBuscar;
	TextView titleConsulta;
	AutoCompleteTextView textoBuscar;
	ListView listaTipos;
	String[] Tipos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_aditivos);

		botonAtras = (Button) findViewById(R.id.botonBack);
		botonBuscar = (Button) findViewById(R.id.botonLupa);
		titleConsulta = (TextView) findViewById(R.id.consultaAditivo);
		textoBuscar = (AutoCompleteTextView) findViewById(R.id.textoBuscar);
		listaTipos = (ListView) findViewById(R.id.listaTiposAditivos);

		S.data_base.openR();
		Tipos = S.data_base.consultaTiposAditivosC();
		S.data_base.close();
		cargarLista(Tipos);
		
		listaTipos.setOnItemClickListener(this);
		textoBuscar.setOnKeyListener(this);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		S.data_base.openR();
		Tipos = S.data_base.consultaTiposAditivosC();
		S.data_base.close();
		cargarLista(Tipos);
	}
	
	/*
	 * Carga la lista de tipos de aditivos.
	 */
	public void cargarLista(String[] pListaTipos){

		List<Titulos> datos = new ArrayList<Titulos>();
		if(pListaTipos != null){
			for(int i=0; i < pListaTipos.length; i = i + 2){
				Titulos aux = new Titulos(pListaTipos[i], pListaTipos[i+1]);
				datos.add(aux);
			}
		}
		AdaptadorTitulos adaptador = new AdaptadorTitulos(this, datos, R.layout.list_item_2_0titulos);
		
		listaTipos.setAdapter(adaptador);
	}
	
	public void pulsaAtras(View view){
		if(textoBuscar.getVisibility() == View.GONE){
			finish();
		}else{
			restaurarIU();
		}
	}
	
	public void pulsaBuscar(View view){
		botonBuscar.setVisibility(View.INVISIBLE);
		titleConsulta.setVisibility(View.GONE);		
		textoBuscar.setVisibility(View.VISIBLE);
		textoBuscar.setText("");
		textoBuscar.requestFocus();

		// AutoComplete
		S.data_base.openR();
		String[] aditivos = S.data_base.consultaNombreNummeroAditivos();
		S.data_base.close();
		if(aditivos == null)
			aditivos = new String[0];
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, aditivos);
		textoBuscar.setAdapter(adapter);
		textoBuscar.setThreshold(2);
	}
	
	public void restaurarIU(){
		botonBuscar.setVisibility(View.VISIBLE);
		titleConsulta.setVisibility(View.VISIBLE);
		textoBuscar.setVisibility(View.GONE);
		textoBuscar.setText("");
	}

	@Override
	public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

		if(keyEvent.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){

			String numeroAditivo = textoBuscar.getText().toString().trim();
			S.data_base.openR();
			Aditivo infoAditivo = S.data_base.consultaAditivoXNumero(numeroAditivo);
			if(infoAditivo == null){
				infoAditivo = S.data_base.consultaAditivoXNombre(numeroAditivo);
			}
			S.data_base.close();
			if(infoAditivo == null){
				Intent i = new Intent(this, ActivityNotFound.class);
				i.putExtra(S.MENSAJE_NOT_FOUND, S.NOT_FOUND_1 + " '" + numeroAditivo + "'.");
				startActivity(i);
				return false;

			}else{
				Intent i = new Intent(this, ActivityDetalleAditivos.class);
				Bundle b = new Bundle();
				b.putParcelable(S.INFO, infoAditivo);
				i.putExtras(b);
				startActivity(i);
				return false;
			}
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> a, View v, int position, long id) {

		String seleccion = ((Titulos)a.getAdapter().getItem(position)).getTitulo();
		seleccion = seleccion.substring(0, seleccion.indexOf('(') - 1);
		S.data_base.openR();
		String[] aditivosXTipo = S.data_base.consultaAditivosXTipo(seleccion);
		S.data_base.close();
		
		if(aditivosXTipo == null){
			Intent i = new Intent(this, ActivityNotFound.class);
			i.putExtra(S.MENSAJE_NOT_FOUND, S.NOT_FOUND_2 + " '" + seleccion + "'.");
			startActivity(i);			
		}else{
			Intent i = new Intent(this, ActivityListaAditivos.class);
			i.putExtra(S.LISTADO, aditivosXTipo);
			i.putExtra(S.TIPO, seleccion);
			startActivity(i);
		}
	}

}