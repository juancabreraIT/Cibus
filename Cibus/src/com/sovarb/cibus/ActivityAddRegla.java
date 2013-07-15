package com.sovarb.cibus;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.sovarb.cibus.adapter.Regla;

public class ActivityAddRegla extends Activity implements OnItemSelectedListener, OnClickListener {

	private String nombreFiltro;
	private Spinner listaOpciones, listaCriterios;
	private AutoCompleteTextView textoFiltro;
	private Button botonInof, botonSosp, botonPeli;
	private ArrayAdapter<String> adaptador1;
	private ArrayAdapter<String> adaptador2;
	ArrayList<Regla> reglasPre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_add_regla);

		Intent i = getIntent();
		reglasPre = i.getParcelableArrayListExtra(S.INFO);
		nombreFiltro = i.getStringExtra(S.NOMBRE_FILTRO);
		
		initViews();
		
		listaOpciones.setOnItemSelectedListener(this);
		listaCriterios.setOnItemSelectedListener(this);
		
		botonInof.setOnClickListener(this);
		botonSosp.setOnClickListener(this);
		botonPeli.setOnClickListener(this);
	}
	
	private void initViews(){
		listaOpciones = (Spinner) findViewById(R.id.spinnerOpciones);
		listaCriterios = (Spinner) findViewById(R.id.spinnerCriterio);
		textoFiltro = (AutoCompleteTextView) findViewById(R.id.editFiltro);
		botonInof = (Button) findViewById(R.id.botonInofensivo);
		botonSosp = (Button) findViewById(R.id.botonSospechoso);
		botonPeli = (Button) findViewById(R.id.botonPeligroso);

		adaptador1 = new ArrayAdapter<String>(this, 
				android.R.layout.simple_expandable_list_item_1, S.listaReglas);

		adaptador2 = new ArrayAdapter<String>(this, 
				android.R.layout.simple_expandable_list_item_1, S.listaReglasExist);

		listaOpciones.setAdapter(adaptador1);
		listaCriterios.setAdapter(adaptador2);
	}
	
	public void pulsaAtras(View view){	
		
		if(textoFiltro.getText().length() > 0){
			mostrarDialogo();
		}else{
			Intent i = new Intent();
			setResult(RESULT_CANCELED, i);
			finish();
		}
	}

	public void pulsaGuardarRegla(View view){
		
		if(textoFiltro.getText().length() > 0){
			returnIntent();
			finish();
		}else{
			Toast.makeText(this, S.MENSAJE_SAVE_CHANGES_ERROR, Toast.LENGTH_LONG).show();
		}
	}

	private void mostrarDialogo(){

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		 
		dialog.setMessage(S.MENSAJE_SAVE_CHANGES);
		dialog.setCancelable(false);
		dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

		  @Override
		  public void onClick(DialogInterface dialog, int which) {			     
			  returnIntent();
			  finish();
		  }
		});
		
		dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		      dialog.cancel();
		      finish();
		   }
		});
		dialog.show();
	}

	private void returnIntent(){
		Intent i = new Intent();
		  String regla = listaOpciones.getSelectedItem().toString() + "  |  " + 
				  			listaCriterios.getSelectedItem().toString() + "  |  \"" +
				  			textoFiltro.getText().toString().trim() + "\"";

		  if(nombreFiltro != null){
			  S.data_base.openR();
			  int idRegla = S.data_base.consultaIdRegla(regla);
			  int idFiltro = S.data_base.consultaIdFiltro(nombreFiltro);
		  
			  if(!S.data_base.consultaExisteReglaXFiltro(idRegla, idFiltro) &&
				 !reglasPre.contains(regla)){
				  reglasPre.add(new Regla(regla));
			  }
			  S.data_base.close();
		  }else{
			  Regla newRegla = new Regla(regla);			  
			  reglasPre.add(newRegla);			  
		  }
		  
		  Bundle b = new Bundle();
		  b.putParcelableArrayList(S.REGLA, reglasPre);
		  i.putExtras(b);
		  setResult(RESULT_OK, i);
	}

	@Override
	public void onClick(View v) {

		if(v.getId() == botonInof.getId()){
			botonInof.setBackgroundResource(R.drawable.boton_background_green);
			botonSosp.setBackgroundResource(android.R.drawable.btn_default_small);
			botonPeli.setBackgroundResource(android.R.drawable.btn_default_small);
			textoFiltro.setText(S.CARACTER.inofensivo.name());
		}else if(v.getId() == botonSosp.getId()){
			botonInof.setBackgroundResource(android.R.drawable.btn_default_small);
			botonSosp.setBackgroundResource(R.drawable.boton_background_yellow);
			botonPeli.setBackgroundResource(android.R.drawable.btn_default_small);
			textoFiltro.setText(S.CARACTER.sospechoso.name());
		}else{
			botonInof.setBackgroundResource(android.R.drawable.btn_default_small);
			botonSosp.setBackgroundResource(android.R.drawable.btn_default_small);
			botonPeli.setBackgroundResource(R.drawable.boton_background_red);
			textoFiltro.setText(S.CARACTER.peligroso.name());
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

		if(parent.getId() == listaOpciones.getId()){
			ArrayAdapter<String> adaptador;

			textoFiltro.setText("");
			
			if(position < 3){		// Nombre Ingrediente, Nombre Aditivo, Numero Aditivo
				botonInof.setVisibility(View.GONE);
				botonSosp.setVisibility(View.GONE);
				botonPeli.setVisibility(View.GONE);
				adaptador = new ArrayAdapter<String>(this,
					android.R.layout.simple_expandable_list_item_1, S.listaReglasExist);
				textoFiltro.setEnabled(true);
				
				// AutoComplete
				String[] datos = new String[0];
				S.data_base.openR();				
				if(position == 0){		// Nombre Ingrediente
					datos = S.data_base.consultaNombreIngredientes();
				}else if(position == 1){ // Nombre Aditivo
					datos = S.data_base.consultaNombreAditivos();
				}else if(position == 2){ // Numero Aditivo
					datos = S.data_base.consultaNumeroAditivos();
				}
				S.data_base.close();
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datos);
				textoFiltro.setAdapter(adapter);
				textoFiltro.setThreshold(1);
				
			}else{					// Caracter Aditivo
				restaurarBotones();
				botonInof.setVisibility(View.VISIBLE);
				botonSosp.setVisibility(View.VISIBLE);
				botonPeli.setVisibility(View.VISIBLE);
				botonInof.setEnabled(true);
				botonPeli.setEnabled(true);
				adaptador = new ArrayAdapter<String>(this,
						android.R.layout.simple_expandable_list_item_1, S.listaReglasCantidad);
				textoFiltro.setEnabled(false);
			}
	
			listaCriterios.setAdapter(adaptador);
		}else if(parent.getId() == listaCriterios.getId()){
			if(listaCriterios.getCount() == 3){
				textoFiltro.setText("");
				if(position == 1){					// es al menos
					botonInof.setEnabled(false);
					botonInof.setBackgroundResource(android.R.drawable.btn_default_small);
					botonPeli.setEnabled(true);
				}else if(position == 2){			// es como mucho
					botonInof.setEnabled(true);
					botonPeli.setEnabled(false);
					botonPeli.setBackgroundResource(android.R.drawable.btn_default_small);
				}
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) { }

	private void restaurarBotones(){
		botonInof.setBackgroundResource(R.drawable.boton_background_green);
		botonSosp.setBackgroundResource(R.drawable.boton_background_yellow);
		botonPeli.setBackgroundResource(R.drawable.boton_background_red);
	}
	
}