package com.sovarb.cibus;

import java.util.ArrayList;
import java.util.List;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sovarb.cibus.adapter.AdaptadorTitulos;
import com.sovarb.cibus.adapter.Titulos;
import com.sovarb.cibus.db.Alimento;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityConsultaAlimentos extends Activity implements OnKeyListener, OnItemClickListener {

	Button botonAtras, botonBuscar, botonScan, botonEntradaM;
	TextView titleConsulta;
	AutoCompleteTextView textoBuscar;
	ListView listaCategorias;
	String contenidoScan, formatoScan;

	/** OVERRIDE **/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_alimentos);
		
		botonAtras = (Button) findViewById(R.id.botonBack);
		botonBuscar = (Button) findViewById(R.id.botonLupa);
		botonScan = (Button) findViewById(R.id.botonScan);
		botonEntradaM = (Button) findViewById(R.id.botonEntradaM);
		titleConsulta = (TextView) findViewById(R.id.consultaAlimento);
		textoBuscar = (AutoCompleteTextView) findViewById(R.id.textoBuscar);		
		listaCategorias = (ListView) findViewById(R.id.listaCategoriasAlimentos);

		cargarLista(S.CATEGORIAS);
		
		listaCategorias.setOnItemClickListener(this);
		textoBuscar.setOnKeyListener(this);
	}

	@Override
	protected void onResume(){
		super.onResume();
		
		cargarLista(S.CATEGORIAS);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
	
		S.data_base.openR();
		String[] alimentosXCategoria = S.data_base.consultaAlimentosXCategoria(S.CATEGORIAS[position]);
		S.data_base.close();
		
		if(alimentosXCategoria == null){
			Intent i = new Intent(this, ActivityNotFound.class);
			i.putExtra(S.MENSAJE_NOT_FOUND, S.NOT_FOUND_3 + " '" + S.CATEGORIAS[position] + "'.");
			startActivity(i);
		}else{
			Intent i = new Intent(this, ActivityListaAlimentos.class);
			i.putExtra(S.LISTADO, alimentosXCategoria);
			i.putExtra(S.TIPO, S.CATEGORIAS[position]);
			startActivity(i);
		}		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		getMenuInflater().inflate(R.menu.activity_consulta_alimentos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId() == R.id.new_alimento){
			newAlimento();
		}
		return true;
	}

	@Override
	public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
		
		if(keyEvent.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){

			String nombreAlimento = textoBuscar.getText().toString();
			S.data_base.openR();
			String[] infoAlimento = S.data_base.consultaAlimentoXNombre(nombreAlimento);
			S.data_base.close();
			
			if(infoAlimento == null){
				Intent i = new Intent(this, ActivityNotFoundAlimento.class);
				i.putExtra(S.MENSAJE_NOT_FOUND, S.NOT_FOUND_4 + " '" + nombreAlimento + "'.");
				startActivity(i);
				return false;				
			}else{
				Intent i = new Intent(this, ActivityListaAlimentos.class);					
				i.putExtra(S.LISTADO, infoAlimento);
				startActivity(i);
				return false;	
			}
		}
		return false;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null && resultCode == RESULT_OK && (contenidoScan = scanResult.getContents()) != null
				&& (formatoScan = scanResult.getFormatName()) != null) {

			consultarCodigo(contenidoScan, formatoScan);
	    }else {
			Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show();	    	  
		}
	}

	/** BOTONES **/
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
		
		// AutoComplete
		S.data_base.openR();
		String[] alimentos = S.data_base.consultaNombreAlimentos();
		S.data_base.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, alimentos);
		textoBuscar.setAdapter(adapter);
		textoBuscar.setThreshold(3);
	}

	/** OTROS **/
	public void cargarLista(String[] pListaCategorias){

		List<Titulos> datos = new ArrayList<Titulos>();
		S.data_base.openR();
		String[] numAlimentos = S.data_base.consultaNumAlimentosXTipo();
		S.data_base.close();
		
		for(int i=0; i < pListaCategorias.length; i++){
			Titulos aux = new Titulos(pListaCategorias[i], numAlimentos[i] + " elementos");
			datos.add(aux);
		}
		AdaptadorTitulos adaptador = new AdaptadorTitulos(this, datos, R.layout.list_item_2_0titulos);

		listaCategorias.setAdapter(adaptador);
	}
	
	private void newAlimento(){
		
		Intent i = new Intent(this, ActivityAddAlimento.class);
		startActivity(i);
	}

	public void scanCode(View view){

		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}

	public void entradaManual(View view){
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
		  consultarCodigo(value, "ENTRADA_MANUAL");
		  }
		});

		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
	}

	private void consultarCodigo(String contenidoScan, String formatoScan){
		
    	S.data_base.openR();
    	Alimento infoAlimento = S.data_base.consultaAlimentoXCodigo(contenidoScan, formatoScan);
		S.data_base.close();

		if(infoAlimento != null){
	    	S.data_base.openR();
			String[] nutrientes = S.data_base.consultaNutrientesAlimentoXCodigo(contenidoScan, formatoScan);
			String[] aditivos = S.data_base.consultaAditivosAlimentoXCodigo(contenidoScan, formatoScan);
			S.data_base.close();

			Intent i = new Intent(this, ActivityDetalleAlimentos.class);
			Bundle b = new Bundle();				
			b.putParcelable(S.INFO, infoAlimento);
			i.putExtras(b);
			i.putExtra(S.NUTRIENTES, nutrientes);
			i.putExtra(S.ADITIVOS, aditivos);
			startActivity(i);
		}else{
			Intent i = new Intent(this, ActivityNotFoundAlimento.class);
			i.putExtra(S.MENSAJE_NOT_FOUND, S.NOT_FOUND_5 + " (" + contenidoScan + ")");
			if(formatoScan != S.QR_FORMAT)
				i.putExtra(S.CODIGO, contenidoScan);
			startActivity(i);					
		}
	}

	public void restaurarIU(){
		botonBuscar.setVisibility(View.VISIBLE);
		titleConsulta.setVisibility(View.VISIBLE);
		textoBuscar.setVisibility(View.GONE);
		textoBuscar.setText("");
	}
	
}