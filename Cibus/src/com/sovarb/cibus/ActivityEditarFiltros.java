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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityEditarFiltros extends Activity implements OnItemClickListener{
	
	String nombre, descripcion;
	EditText nombreF, descripcionF;
	ListView listaReglas;
	AdaptadorReglas adaptador;
	ArrayList<Regla> reglas = new ArrayList<Regla>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_filtros);

		nombreF = (EditText) findViewById(R.id.nombreFiltro);
		descripcionF = (EditText) findViewById(R.id.descripcionFiltro);
		listaReglas = (ListView) findViewById(R.id.listadoReglas);
		
		Intent i = getIntent();
		nombre = i.getStringExtra(S.NOMBRE_FILTRO);
		descripcion = i.getStringExtra(S.DESCRIPCION_FILTRO);
		
		nombreF.setText(nombre);
		descripcionF.setText(descripcion);
		
		cargarReglas();
		listaReglas.setOnItemClickListener(this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		
		if(requestCode == S.REQUEST_CODE_1 && resultCode == RESULT_OK){	
			ArrayList<Regla> aux = data.getParcelableArrayListExtra(S.REGLA);			
			if(aux != null){
				reglas = aux;
				adaptador = new AdaptadorReglas(this, reglas, R.layout.list_item_1titulo);
				
				listaReglas.setAdapter(adaptador);
			}
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		
		Regla regla = ((Regla)a.getAdapter().getItem(position));
		mostrarDialogo2(regla, position);
	}
	
	private void cargarReglas(){
		
		S.data_base.openR();
		reglas = S.data_base.consultaReglasXFiltro(nombre);
		S.data_base.close();
		adaptador = new AdaptadorReglas(this, reglas, R.layout.list_item_1titulo);

		listaReglas.setAdapter(adaptador);
	}
	
	/**	BOTONES **/
	public void pulsaAtras(View view){	
		finish();	
	}

	public void pulsaAddRegla(View view){
		
		Intent i = new Intent(this, ActivityAddRegla.class);
		i.putExtra(S.NOMBRE_FILTRO, nombre);
		Bundle b = new Bundle();
		b.putParcelableArrayList(S.INFO, reglas);
		i.putExtras(b);
		startActivityForResult(i, S.REQUEST_CODE_1);
	}
	
	public void pulsaGuardarFiltro(View view){
		
		if(nombreF.getText().length() > 0 && listaReglas.getCount() > 0){
			updateFiltro();			
			finish();
		}else{
			Toast.makeText(this, S.MENSAJE_SAVE_FILTRO_ERROR, Toast.LENGTH_LONG).show();
		}
	}
	
	private void updateFiltro(){

		int num_reglas = listaReglas.getCount();
		List<String> aux = new ArrayList<String>();
		
		for(int i = 0; i < num_reglas; i++){
			aux.add(reglas.get(i).getRegla());
		}

		String[] pReglas = new String[aux.size()];
		aux.toArray(pReglas);
		
		String editNombre = nombreF.getText().toString();
		String editDescripcion = descripcionF.getText().toString();
		
		S.data_base.openW();
		S.data_base.actualizarFiltro(nombre, editNombre, editDescripcion, pReglas);
		S.data_base.close();
		Intent i = new Intent();
		i.putExtra(S.NOMBRE_FILTRO, editNombre);
		i.putExtra(S.DESCRIPCION_FILTRO, editDescripcion);
		setResult(RESULT_OK, i);
	}

	public void pulsaEliminarFiltro(View view){
		mostrarDialogo();
	}
	
	private void mostrarDialogo(){

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		 
		dialog.setMessage(S.MENSAJE_DELETE_FILTER);
		dialog.setCancelable(false);
		dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

		  @Override
		  public void onClick(DialogInterface dialog, int which) {			     
			  eliminarFiltro();
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
	
	private void mostrarDialogo2(final Regla pRegla, final int position){
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		 
		dialog.setMessage(S.MENSAJE_DELETE_REGLA);
		dialog.setCancelable(false);
		dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

		  @Override
		  public void onClick(DialogInterface dialog, int which) {			     
			  eliminarRegla(pRegla.getRegla(), position);
			  
			  //cargarReglas();
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
	
	private final void eliminarFiltro(){
		S.data_base.openW();
		S.data_base.suprimirFiltro(nombre);
		S.data_base.close();
		Intent i = new Intent();
		setResult(RESULT_CANCELED, i);
	}
	
	private final void eliminarRegla(String pRegla, int position){
		if(listaReglas.getCount() == 1){
			Toast.makeText(this, S.NO_PUEDE_ELIMINAR_REGLA, Toast.LENGTH_LONG).show();
		}else{
			S.data_base.openW();
			S.data_base.suprimirRegla(pRegla, nombre);
			S.data_base.close();
			
			reglas.remove(position);
			adaptador = new AdaptadorReglas(this, reglas, R.layout.list_item_1titulo);

			listaReglas.setAdapter(adaptador);
		}
	}


}