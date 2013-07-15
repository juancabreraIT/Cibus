package com.sovarb.cibus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sovarb.cibus.db.Aditivo;

public class ActivityEditarAditivos extends Activity implements OnClickListener {

	String numAditivo, caracter="";
	Aditivo aditivo;
	TextView numero;
	EditText nombre, descripcion;
	Spinner listTipos;
	Button botonInof, botonSosp, botonPeli;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_aditivos);
		
		Intent i = getIntent();
		aditivo = i.getParcelableExtra(S.ADITIVOS);

		if(aditivo == null){
			Toast.makeText(this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
		}else{
			initViews();
			numero.setText(aditivo.getNumero());
			nombre.setText(aditivo.getNombre());
			descripcion.setText(aditivo.getDescripcion());
			cargarTipos();
			initCaracter();
		}

		botonInof.setOnClickListener(this);
		botonSosp.setOnClickListener(this);
		botonPeli.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == botonInof.getId()){
			if(!caracter.equals(S.CARACTER.inofensivo.name()) ){
				botonInof.setBackgroundResource(R.drawable.boton_background_green);
				botonSosp.setBackgroundResource(android.R.drawable.btn_default_small);
				botonPeli.setBackgroundResource(android.R.drawable.btn_default_small);
				caracter = S.CARACTER.inofensivo.name();
			}else{
				botonInof.setBackgroundResource(android.R.drawable.btn_default_small);
				caracter = "";
			}
		}else if(v.getId() == botonSosp.getId()){
			if(!caracter.equals(S.CARACTER.sospechoso.name()) ){
				botonInof.setBackgroundResource(android.R.drawable.btn_default_small);
				botonSosp.setBackgroundResource(R.drawable.boton_background_yellow);
				botonPeli.setBackgroundResource(android.R.drawable.btn_default_small);
				caracter = S.CARACTER.sospechoso.name();
			}else{
				botonSosp.setBackgroundResource(android.R.drawable.btn_default_small);
				caracter = "";
			}
		}else{
			if(!caracter.equals(S.CARACTER.peligroso.name()) ){
				botonInof.setBackgroundResource(android.R.drawable.btn_default_small);
				botonSosp.setBackgroundResource(android.R.drawable.btn_default_small);
				botonPeli.setBackgroundResource(R.drawable.boton_background_red);
				caracter = S.CARACTER.peligroso.name();
			}else{
				botonPeli.setBackgroundResource(android.R.drawable.btn_default_small);
				caracter = "";
			}
		}
		
	}
	
	private void initViews(){
		
		numero = (TextView) findViewById(R.id.numeroAditivo);
		nombre = (EditText) findViewById(R.id.editNombreAditivo);
		listTipos = (Spinner) findViewById(R.id.spinnerTipos);
		descripcion = (EditText) findViewById(R.id.editDescripcionAditivo);
		botonInof = (Button) findViewById(R.id.botonInofensivo);
		botonSosp = (Button) findViewById(R.id.botonSospechoso);
		botonPeli = (Button) findViewById(R.id.botonPeligroso);
	}

	private void cargarTipos(){
		
		S.data_base.openR();
		String[] tipos = S.data_base.consultaTiposAditivos();
		S.data_base.close();

		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, tipos);

		listTipos.setAdapter(adaptador);
		S.data_base.openR();
		String nombreTipo = S.data_base.consultaNombreTipoAditivoXId(aditivo.getTipoAditivo());
		S.data_base.close();
		if(!nombreTipo.equals("")){			
			for(int i = 0; i < tipos.length; i++){				
				if(tipos[i].equals(nombreTipo)){
					listTipos.setSelection(i);
					i = tipos.length;
				}
			}
		}
	}

	private void initCaracter(){

		if(aditivo.getCaracter().equals(S.CARACTER.inofensivo.name())){
			botonInof.setBackgroundResource(R.drawable.boton_background_green);
			botonSosp.setBackgroundResource(android.R.drawable.btn_default_small);
			botonPeli.setBackgroundResource(android.R.drawable.btn_default_small);
			caracter = S.CARACTER.inofensivo.name();
		}else if(aditivo.getCaracter().equals(S.CARACTER.sospechoso.name())){
			botonInof.setBackgroundResource(android.R.drawable.btn_default_small);
			botonSosp.setBackgroundResource(R.drawable.boton_background_yellow);
			botonPeli.setBackgroundResource(android.R.drawable.btn_default_small);
			caracter = S.CARACTER.sospechoso.name();
		}else if(aditivo.getCaracter().equals(S.CARACTER.peligroso.name())){
			botonInof.setBackgroundResource(android.R.drawable.btn_default_small);
			botonSosp.setBackgroundResource(android.R.drawable.btn_default_small);
			botonPeli.setBackgroundResource(R.drawable.boton_background_red);
			caracter = S.CARACTER.peligroso.name();
		}else{
			caracter = "";
			botonInof.setBackgroundResource(android.R.drawable.btn_default_small);
			botonSosp.setBackgroundResource(android.R.drawable.btn_default_small);
			botonPeli.setBackgroundResource(android.R.drawable.btn_default_small);
		}
	}

	/** BOTONES **/
	public void pulsaAtras(View view){
		mostrarDialogo();
	}

	public void pulsaGuardar(View view){
		if(nombre.getText().toString().trim().length() > 0 ){
			saveAditivo();
			finish();
		}else{
			Toast.makeText(this, S.MENSAJE_SAVE_FILTRO_ERROR, Toast.LENGTH_LONG).show();
		}
	}

	private void mostrarDialogo(){

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		 
		dialog.setMessage(S.MENSAJE_NO_GUARDAR);
		dialog.setCancelable(false);
		dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

		  @Override
		  public void onClick(DialogInterface dialog, int which) {			  
			  finish();
		  }
		});
		
		dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		      dialog.cancel();
		   }
		});
		dialog.show();
	}

	private void saveAditivo(){
		S.data_base.openW();
		int idTipo = S.data_base.consultaIdTipoAditivoXNombre(listTipos.getSelectedItem().toString());
		Aditivo update = new Aditivo(numero.getText().toString(), 
									nombre.getText().toString().trim(),
									descripcion.getText().toString().trim(), 
									caracter,
									idTipo);

		S.data_base.actualizarAditivo(update, this);
		Aditivo aux = S.data_base.consultaAditivoXNumero(update.getNumero());
		S.data_base.close();
		
		
		if(aux.getNombre().equals(update.getNombre()) && aux.getDescripcion().equals(update.getDescripcion()) &&
			aux.getCaracter().equals(update.getCaracter()) && aux.getTipoAditivo() == update.getTipoAditivo()){
			Intent i = new Intent();
			i.putExtra(S.ADITIVOS, update);
			setResult(RESULT_OK, i);
		}else{
			Toast.makeText(this, S.FAIL_UPDATE_ADITIVO, Toast.LENGTH_LONG).show();
		}
	}
}