package com.sovarb.cibus;

import java.util.ArrayList;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sovarb.cibus.S.CARACTER;
import com.sovarb.cibus.adapter.AdaptadorReglas;
import com.sovarb.cibus.adapter.Regla;
import com.sovarb.cibus.db.Aditivo;
import com.sovarb.cibus.db.Alimento;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityDetalleFiltrosDefault extends Activity {

	String[] filtro;
	String contenidoScan, formatoScan;
	TextView nombre, descripcion;
	ListView listaReglas;
	AdaptadorReglas adaptador;

	/** OVERRIDE **/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_filtros_default);

		nombre = (TextView) findViewById(R.id.nombreFiltro);
		descripcion = (TextView) findViewById(R.id.descripcionFiltro);
		listaReglas = (ListView) findViewById(R.id.listadoReglas);
		
		Intent i = getIntent();
		filtro = i.getStringArrayExtra(S.FILTRO);

		nombre.setText(filtro[0]);
		descripcion.setText(filtro[1]);

		cargarReglas();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){

		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

		if (scanResult != null && resultCode == RESULT_OK && requestCode!= S.REQUEST_CODE_2 && 
		    (contenidoScan = scanResult.getContents()) != null && 
		    (formatoScan = scanResult.getFormatName()) != null) {	    	
			
			consultaCodigo(contenidoScan, formatoScan);
	    	
		}else if(requestCode == S.REQUEST_CODE_3 && resultCode == RESULT_OK){
			IntentIntegrator integrator = new IntentIntegrator(this);
			integrator.initiateScan();
		}else{
			Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show();
		}
	}

	/** BOTONES **/
	public void pulsaAtras(View view){	
		finish();	
	}

	public void pulsaUseFiltro(View view){
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}
	
	public void pulsaEntradaManual(View view){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle(S.TITLE_ENTRADA_MANUAL);
		alert.setMessage(S.MENSAJE_ENTRADA_MANUAL);

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String value = input.getText().toString().trim();
		  consultaCodigo(value, "ENTRADA_MANUAL");
		  }
		});

		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
	}
	
	/** OTROS **/
	private void cargarReglas(){
		
		ArrayList<Regla> reglas = new ArrayList<Regla>();
		String[] aux;
		if(nombre.getText().equals(S.FILTRO_DEFAULT_1[0])){
			aux = S.FILTRO_DEFAULT_1;
		}else if(nombre.getText().equals(S.FILTRO_DEFAULT_2[0])){
			aux = S.FILTRO_DEFAULT_2;
		}else {
			aux = S.FILTRO_DEFAULT_3;
		}
		
		for(int i = 2; i < aux.length; i++){
			Regla newRegla = new Regla(aux[i]);
			reglas.add(newRegla);
		}
		
		if(reglas != null){
			adaptador = new AdaptadorReglas(this, reglas, R.layout.list_item_1titulo);

			listaReglas.setAdapter(adaptador);
		}
	}
	
	private void consultaCodigo(String contenidoScan, String formatoScan){

    	S.data_base.openR();
		Alimento alimento = S.data_base.consultaAlimentoXCodigo(contenidoScan, formatoScan);
		S.data_base.close();

		if(alimento != null){
			S.data_base.openR();
			String[] ingredientes = S.data_base.consultaNombreIngredientesAlimentoXCodigo(contenidoScan, formatoScan);
			String[] aditivos = S.data_base.consultaAditivosAlimentoXCodigo(contenidoScan, formatoScan);
			
			S.RESULTADO valido = S.RESULTADO.SIN_DATOS;
			int i;
			for(i = 0; i < adaptador.getCount() && valido != S.RESULTADO.INVALIDO; i++){
				valido = filtrarRegla(adaptador.getItem(i).getRegla(), ingredientes, aditivos);				
			}
			S.data_base.close();

			Intent intent = new Intent(this, ActivitySemaforo.class);
			intent.putExtra(S.NOMBRE, alimento.getNombre());

			if(valido == S.RESULTADO.VALIDO){
				intent.putExtra(S.SEMAFORO_RESULT, S.RESULTADO.VALIDO.name());
			}else if(valido == S.RESULTADO.INVALIDO){
				intent.putExtra(S.SEMAFORO_RESULT, S.RESULTADO.INVALIDO.name());
				intent.putExtra(S.REGLA, adaptador.getItem(i-1).getRegla());
			}else{
				intent.putExtra(S.SEMAFORO_RESULT, S.RESULTADO.SIN_DATOS.name());
			}
			startActivityForResult(intent, S.REQUEST_CODE_3);

		}else{
			Intent i = new Intent(this, ActivityNotFoundAlimento.class);
			i.putExtra(S.MENSAJE_NOT_FOUND, S.NOT_FOUND_5 + " (" + contenidoScan + ")");
			i.putExtra(S.CODIGO, contenidoScan);
			startActivity(i);
		}
	}

	private final S.RESULTADO filtrarRegla(String pRegla, String[] pIngredientes, String[] pAditivos){

		pRegla = pRegla.trim();
		String regla = pRegla.substring(0, pRegla.indexOf('|')).trim();
		String condicion = pRegla.substring(pRegla.indexOf('|') + 2, pRegla.lastIndexOf('|')).trim();

		if(regla.equals(S.listaReglas[0])){ 			// Nombre ingrediente
			String ingrediente = pRegla.substring(pRegla.indexOf('"') + 1, pRegla.lastIndexOf('"')).trim();
			int i;
			boolean found = false;
			for(i = 0; i < pIngredientes.length && !found; i++){
				if(pIngredientes[i].equals(ingrediente)){
					found = true;
				}
			}

			if(condicion.equals(S.listaReglasExist[0])){ // es igual a
				if(found){	return S.RESULTADO.VALIDO;
				}else{		return S.RESULTADO.INVALIDO;	}
			}else {											// es distinto a
				if(found){	return S.RESULTADO.INVALIDO;
				}else{		return S.RESULTADO.VALIDO;		}
			}

		}else if(regla.equals(S.listaReglas[1]) || regla.equals(S.listaReglas[2])){		// Nombre o Numero Aditivo
					
			String aditivo = pRegla.substring(pRegla.indexOf('"') + 1, pRegla.lastIndexOf('"')).trim();
			int i;
			boolean found = false;
			for(i = 0; i < pAditivos.length; i++){				
				if(pAditivos[i].equals(aditivo)){
					found = true;
				}
			}

			if(condicion.equals(S.listaReglasExist[0])){ // es igual a
				if(found){	return S.RESULTADO.VALIDO;				
				} else {	return S.RESULTADO.INVALIDO;	}
			}else{											// es distinto a
				if(found){	return S.RESULTADO.INVALIDO;
				} else {	return S.RESULTADO.VALIDO;		}
			}

		}else if(regla.equals(S.listaReglas[3])){					// Caracter Aditivo
			String caracter = pRegla.substring(pRegla.indexOf('"') + 1, pRegla.lastIndexOf('"')).trim();
			if(condicion.equals(S.listaReglasCantidad[0])){ 		// es igual a
				int i;
				boolean found = false;
				for(i = 0; i < pAditivos.length; i = i + 2){
					Aditivo aux = S.data_base.consultaAditivoXNumero(pAditivos[i]);
					
					if(aux != null && aux.getCaracter() != null && !aux.getCaracter().equals(caracter)){
						found = true;
					}
				}
				if(!found){	return S.RESULTADO.VALIDO;
				}else{		return S.RESULTADO.INVALIDO;}

			}else {
				CARACTER elegido = CARACTER.valueOf(caracter);
				if(condicion.equals(S.listaReglasCantidad[1])){		// es al menos
					int i;
					boolean found = false;
					for(i = 0; i < pAditivos.length; i = i + 2){
						Aditivo aux = S.data_base.consultaAditivoXNumero(pAditivos[i]);

						if(aux != null && !aux.getCaracter().equals("") && CARACTER.valueOf(aux.getCaracter()).ordinal() < elegido.ordinal()){
							found = true;
						}
					}

					if(!found){	return S.RESULTADO.VALIDO;					
					}else{				return S.RESULTADO.INVALIDO; }

				}else if(condicion.equals(S.listaReglasCantidad[2])){	// es como mucho
					int i;
					boolean found = false;
					for(i = 0; i < pAditivos.length; i = i + 2){
						Aditivo aux = S.data_base.consultaAditivoXNumero(pAditivos[i]);				
						if(aux != null && !aux.getCaracter().equals("")){
							if(elegido.ordinal() < CARACTER.valueOf(aux.getCaracter()).ordinal()){
								found = true;
							}
						}					
					}
					
					if(!found){	return S.RESULTADO.VALIDO;
					}else{		return S.RESULTADO.INVALIDO;}		
				}
			}
		}
		return S.RESULTADO.SIN_DATOS;
	}
}