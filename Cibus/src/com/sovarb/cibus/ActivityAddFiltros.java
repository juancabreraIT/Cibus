package com.sovarb.cibus;

import java.util.ArrayList;
import java.util.List;

import com.sovarb.cibus.adapter.AdaptadorReglas;
import com.sovarb.cibus.adapter.Regla;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityAddFiltros extends Activity {

	EditText editNombre, editDescripcion;
	ListView listaReglas;
	AdaptadorReglas adaptador;
	ArrayList<Regla> reglas = new ArrayList<Regla>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_filtros);

		editNombre = (EditText) findViewById(R.id.nombreFiltro);
		editDescripcion = (EditText) findViewById(R.id.descripcionFiltro);
		listaReglas = (ListView) findViewById(R.id.listaFiltros);
		
		cargarLista();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		
		if(requestCode == S.REQUEST_CODE_0 && resultCode == RESULT_OK){	

			reglas = data.getParcelableArrayListExtra(S.REGLA);						
		}
		cargarLista();
	}
	
	private void cargarLista(){
		
		adaptador = new AdaptadorReglas(this, reglas, R.layout.list_item_1titulo);
		listaReglas.setAdapter(adaptador);
	}
	
	/**	BOTONES **/
	public void pulsaAtras(View view){	
		
		if(editNombre.getText().length() > 0 && listaReglas.getCount() > 0){
			mostrarDialogo();
		}else{
			finish();
		}
	}

	public void pulsaGuardarFiltro(View view){	
		if(editNombre.getText().toString().trim().length() > 0 && listaReglas.getCount() > 0){
			saveFiltro();
			finish();
		}else{
			Toast.makeText(this, S.MENSAJE_SAVE_FILTRO_ERROR, Toast.LENGTH_LONG).show();
		}
	}

	public void pulsaAddRegla(View view){
		Intent i = new Intent(this, ActivityAddRegla.class);
		Bundle b = new Bundle();
		b.putParcelableArrayList(S.INFO, reglas);
		i.putExtras(b);
		startActivityForResult(i, S.REQUEST_CODE_0);
	}

	private void mostrarDialogo(){

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		 
		dialog.setMessage(S.MENSAJE_SAVE_CHANGES);
		dialog.setCancelable(false);
		dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

		  @Override
		  public void onClick(DialogInterface dialog, int which) {			     
			  saveFiltro();
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

	private void saveFiltro(){

		String nombre, descripcion;
		int num_reglas = listaReglas.getCount();
		List<String> aux = new ArrayList<String>();
		
		for(int i = 0; i < num_reglas; i++){
			aux.add(reglas.get(i).getRegla());
		}
		String[] pReglas = new String[aux.size()];
		aux.toArray(pReglas);
		
		nombre = editNombre.getText().toString().trim();
		descripcion = editDescripcion.getText().toString().trim();

		S.data_base.openW();
		S.data_base.insertFiltro(nombre, descripcion, pReglas);
		S.data_base.close();
	}

}