package com.sovarb.cibus;

import java.util.ArrayList;

import com.sovarb.cibus.adapter.AdaptadorTitulos;
import com.sovarb.cibus.adapter.Titulos;
import com.sovarb.cibus.db.Aditivo;
import com.sovarb.cibus.db.Alimento;
import com.sovarb.cibus.db.Ingrediente;
import com.sovarb.cibus.db.Nutriente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityAddAlimento extends Activity {

	int currentTab = 0;
	EditText nombreAlimento, codigoAlimento, descripcionAlimento, formatoAlimento, marcaAlimento, submarcaAlimento;
	Spinner tiposAlimento;
	ArrayAdapter<String> adaptador;
	ListView listaAditivos;
	ArrayList<Titulos> datosAditivos = new ArrayList<Titulos>();
	ArrayList<String> datosIngredientes = new ArrayList<String>();
	ArrayList<String> datosNutrientes = new ArrayList<String>();

	TabHost pestañas;
	TabHost.TabSpec tabAux;
	TableLayout tablaNutrientes, tablaIngredientes;
    TableRow.LayoutParams layoutRow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_alimento);

		initViews();

		startTabHost();
		
		@SuppressWarnings({ "unchecked", "deprecation" })
		ArrayList<Titulos> aux = (ArrayList<Titulos>) getLastNonConfigurationInstance();
		
		if(aux != null){
			this.datosAditivos = aux;
		}
		
		mostrarIngredientes();
		mostrarNutrientes();
		mostrarAditivos();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
	    return this.datosAditivos;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    // Read values from the "savedInstanceState"-object and put them in your textview
	    currentTab = savedInstanceState.getInt("tab");
	    pestañas.setCurrentTab(currentTab);
	    datosIngredientes = savedInstanceState.getStringArrayList(S.INGREDIENTES);
	    datosNutrientes = savedInstanceState.getStringArrayList(S.NUTRIENTES);
	    mostrarIngredientes();
	    mostrarNutrientes();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {		
	    // Save the values you need from your textview into "outState"-object
	    currentTab = pestañas.getCurrentTab();
		outState.putInt("tab", currentTab);
		outState.putStringArrayList(S.INGREDIENTES, datosIngredientes);
		outState.putStringArrayList(S.NUTRIENTES, datosNutrientes);
		super.onSaveInstanceState(outState);
	}

	private final void initViews(){

		nombreAlimento = (EditText) findViewById(R.id.editNombreAlimento);
		codigoAlimento =  (EditText) findViewById(R.id.editCodigoAlimento);
		descripcionAlimento = (EditText) findViewById(R.id.editDescripcionAlimento);
		formatoAlimento = (EditText) findViewById(R.id.editFormato);
		marcaAlimento = (EditText) findViewById(R.id.editMarcaAlimento);
		submarcaAlimento = (EditText) findViewById(R.id.editSubMarcaAlimento);
		tiposAlimento = (Spinner) findViewById(R.id.spinnerTipoAlimento);

		Intent i = getIntent();
		String codigo = i.getStringExtra(S.CODIGO);
		if(codigo != null)
			codigoAlimento.setText(codigo);
		
		adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, S.CATEGORIAS_SPINNER);
		tiposAlimento.setAdapter(adaptador);

		// Tab Ingredientes
		tablaIngredientes = (TableLayout) findViewById(R.id.tablaIngredientes);

		// Tab Nutrientes
		tablaNutrientes = (TableLayout) findViewById(R.id.tablaNutrientes);
        layoutRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);

        // Tab Aditivos
        listaAditivos = (ListView) findViewById(R.id.listaAditivos);
	}

	private final void startTabHost(){
		
		pestañas = (TabHost) findViewById(android.R.id.tabhost);
        pestañas.setup();
        
        tabAux = pestañas.newTabSpec(S.PESTAÑA_ALIMENTO);
        tabAux.setContent(R.id.tabInfo);
        tabAux.setIndicator(S.INFO_ALIMENTO);
        pestañas.addTab(tabAux);
        
        tabAux = pestañas.newTabSpec(S.PESTAÑA_INGREDIENTES);
        tabAux.setContent(R.id.tabIngredientes);
        tabAux.setIndicator(S.INGREDIENTES);
        pestañas.addTab(tabAux);
        
        tabAux = pestañas.newTabSpec(S.PESTAÑA_NUTRIENTES);
        tabAux.setContent(R.id.tabNutrientes);
        tabAux.setIndicator(S.NUTRIENTES);
        pestañas.addTab(tabAux);

        tabAux = pestañas.newTabSpec(S.PESTAÑA_ADITIVOS);
        tabAux.setContent(R.id.tabAditivos);
        tabAux.setIndicator(S.ADITIVOS);
        pestañas.addTab(tabAux);
        
        pestañas.setCurrentTab(currentTab);
	}

	/** BOTONES **/
	public void pulsaAtras(View view){
		finish();
	}

	public final void pulsaAddIngrediente(View v){
		
		
		S.data_base.openR();
		String[] ingredientes = S.data_base.consultaNombreIngredientes();
		S.data_base.close();
		
		// AutoComplete
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ingredientes);
        final AutoCompleteTextView ingrediente = new AutoCompleteTextView(this);
        ingrediente.setAdapter(adapter);
        ingrediente.setThreshold(2);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(S.TITLE_AÑADIR_INGREDIENTE);

		// Set an EditText view to get user input
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		ingrediente.setHint(S.INGREDIENTE);
		layout.addView(ingrediente);
		
		final EditText cantidad = new EditText(this);
		cantidad.setHint(S.CANTIDAD);
		layout.addView(cantidad);
		alert.setView(layout);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String ingred = ingrediente.getText().toString().trim();
		  String cant = cantidad.getText().toString().trim();
		  addIngrediente(ingred, cant);		  
		  }
		});

		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
	}

	public final void pulsaAddNutriente(View v){
		
		S.data_base.openR();
		String[] nutrientes = S.data_base.consultaNombreNutrientes();
		S.data_base.close();
		
		// AutoComplete
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nutrientes);
        final AutoCompleteTextView nutriente = new AutoCompleteTextView(this);
        nutriente.setAdapter(adapter);
        nutriente.setThreshold(2);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(S.TITLE_AÑADIR_NUTRIENTE);

		// Set an EditText view to get user input
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		nutriente.setHint(S.NUTRIENTE);
		layout.addView(nutriente);
		
		final EditText cantidad = new EditText(this);
		cantidad.setHint(S.CANTIDAD);
		layout.addView(cantidad);
		alert.setView(layout);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String nutri = nutriente.getText().toString().trim();
		  String cant = cantidad.getText().toString().trim();
		  addNutriente(nutri, cant);
		  }
		});

		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
	}

	public final void pulsaAddAditivo(View v){
		
		S.data_base.openR();
		String[] aditivos = S.data_base.consultaNombreNummeroAditivos();
		S.data_base.close();
		
		// AutoComplete
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, aditivos);
        final AutoCompleteTextView aditivo = new AutoCompleteTextView(this);
        aditivo.setAdapter(adapter);
        aditivo.setThreshold(2);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(S.TITLE_AÑADIR_ADITIVO);

		// Set an EditText view to get user input 
		aditivo.setInputType(InputType.TYPE_CLASS_TEXT);
		alert.setView(aditivo);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String adit = aditivo.getText().toString().trim();
			  
				S.data_base.openR();
				Aditivo aux = S.data_base.consultaAditivoXNombre(adit);
				if(aux == null){
					aux = S.data_base.consultaAditivoXNumero(adit);
				}
				S.data_base.close();
				if(aux != null){
					addAditivo(aux.getNumero(), aux.getNombre());
				}else{
					Toast.makeText(getBaseContext(), "No existe el aditivo introducido", Toast.LENGTH_LONG).show();
				}
			}
		});

		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
	}

	public final void pulsaGuardarAlimento(View v){

		if(nombreAlimento.getText().toString().trim().length() > 0 && 
			tiposAlimento.getSelectedItemPosition() != 0){

			S.data_base.openW();
			
			final Alimento alimento = new Alimento(
					nombreAlimento.getText().toString(),
					tiposAlimento.getSelectedItem().toString(),
					descripcionAlimento.getText().toString(),
					formatoAlimento.getText().toString(),
					marcaAlimento.getText().toString(),
					submarcaAlimento.getText().toString(),
					codigoAlimento.getText().toString(), "");			
			
			ArrayList<Ingrediente> arrayIngredientes = new ArrayList<Ingrediente>();
			ArrayList<String> cantIngredientes = new ArrayList<String>();
			ArrayList<Nutriente> arrayNutrientes = new ArrayList<Nutriente>();
			ArrayList<String> cantNutrientes = new ArrayList<String>();
			ArrayList<Integer> arrayAditivos = new ArrayList<Integer>();

			for(int i = 0; i < datosIngredientes.size(); i = i + 2){
				arrayIngredientes.add(new Ingrediente(datosIngredientes.get(i), ""));
				cantIngredientes.add(datosIngredientes.get(i + 1));				
			}

			for(int i = 0; i < datosNutrientes.size(); i = i + 2){
				arrayNutrientes.add(new Nutriente(datosNutrientes.get(i), ""));
				cantNutrientes.add(datosNutrientes.get(i + 1));				
			}

			for(int i = 0; i < datosAditivos.size(); i++){
				arrayAditivos.add(S.data_base.consultaAditivoXNumero(datosAditivos.get(i).getTitulo()).getId());
			}

			
			S.data_base.insertarAlimento(alimento, arrayIngredientes, cantIngredientes,
										arrayNutrientes, cantNutrientes, arrayAditivos, this);
			S.data_base.close();			
			finish();
		}else{
			Toast.makeText(this, S.MENSAJE_SAVE_ALIMENTO_ERROR, Toast.LENGTH_LONG).show();
			pestañas.setCurrentTab(0);
		}
	}

	/** ADD UPDATE Ingredientes Nutrientes Aditivos **/
	private final void addIngrediente(String pIngrediente, String pCantidad){

		TableRow fila = new TableRow(this);
		TextView txtNombre = new TextView(this), txtCantidad = new TextView(this);
		int background;

    	if(datosIngredientes.size() % 2 == 0) 
    		background = R.drawable.celda_par;
    	else 
    		background = R.drawable.celda_impar;	    	 

        fila.setLayoutParams(layoutRow);

        txtNombre.setText(pIngrediente);
        txtNombre.setGravity(Gravity.LEFT);
        txtNombre.setTextAppearance(this,R.style.filaNombre);
        txtNombre.setBackgroundResource(background);
        txtNombre.setPadding(4, 0, 0, 0);
        txtNombre.setLayoutParams(layoutRow);

        txtCantidad.setText(pCantidad);
        txtCantidad.setGravity(Gravity.CENTER);
        txtCantidad.setTextAppearance(this,R.style.filaCantidad);
        txtCantidad.setBackgroundResource(background);
        txtCantidad.setLayoutParams(layoutRow);

        fila.addView(txtNombre);
        fila.addView(txtCantidad);
        tablaIngredientes.addView(fila);
        datosIngredientes.add(pIngrediente);
        datosIngredientes.add(pCantidad);
	}

	private final void addNutriente(String pNutriente, String pCantidad){

		TableRow fila = new TableRow(this);
		TextView txtNombre = new TextView(this), txtCantidad = new TextView(this);
		int background;

    	if(datosIngredientes.size() % 2 == 0) 
    		background = R.drawable.celda_par;
    	else 
    		background = R.drawable.celda_impar;	    	 

        fila.setLayoutParams(layoutRow);

        txtNombre.setText(pNutriente);
        txtNombre.setGravity(Gravity.LEFT);
        txtNombre.setTextAppearance(this,R.style.filaNombre);
        txtNombre.setBackgroundResource(background);
        txtNombre.setPadding(4, 0, 0, 0);
        txtNombre.setLayoutParams(layoutRow);

        txtCantidad.setText(pCantidad);
        txtCantidad.setGravity(Gravity.CENTER);
        txtCantidad.setTextAppearance(this,R.style.filaCantidad);
        txtCantidad.setBackgroundResource(background);
        txtCantidad.setLayoutParams(layoutRow);

        fila.addView(txtNombre);
        fila.addView(txtCantidad);
        tablaNutrientes.addView(fila);
        datosNutrientes.add(pNutriente);
        datosNutrientes.add(pCantidad);
	}

	private final void addAditivo(String numero, String nombre){

		Titulos aux = new Titulos(numero, nombre);
		datosAditivos.add(aux);
	        
        AdaptadorTitulos adaptador = new AdaptadorTitulos(this, datosAditivos, R.layout.list_item_2_1titulos);
        listaAditivos.setAdapter(adaptador);        
	}

	private final void mostrarNutrientes(){

		TableRow fila;
	    TextView txtNombre;
	    int background = R.drawable.celda_impar;
	    
		if(datosNutrientes != null && datosNutrientes.size() >= 2){			
		    TextView txtCantidad;
		         
		    for(int i = 0; i < datosNutrientes.size() - 1; i = i + 2){
		    	if(background == R.drawable.celda_impar) background = R.drawable.celda_par;
		    	else background = R.drawable.celda_impar;	    	 
	
		    	fila = new TableRow(this);
		        fila.setLayoutParams(layoutRow);
	
		        txtNombre = new TextView(this);
		        txtCantidad = new TextView(this);
	
		        txtNombre.setText(datosNutrientes.get(i));
		        txtNombre.setGravity(Gravity.LEFT);
		        txtNombre.setTextAppearance(this,R.style.filaNombre);
		        txtNombre.setBackgroundResource(background);
		        txtNombre.setPadding(4, 0, 0, 0);
		        txtNombre.setLayoutParams(layoutRow);
	
		        txtCantidad.setText(datosNutrientes.get(i+1));
		        txtCantidad.setGravity(Gravity.CENTER);
		        txtCantidad.setTextAppearance(this,R.style.filaCantidad);
		        txtCantidad.setBackgroundResource(background);
		        txtCantidad.setLayoutParams(layoutRow);
	
		        fila.addView(txtNombre);
		        fila.addView(txtCantidad);
		        tablaNutrientes.addView(fila);
		    }
		}
	}

	private final void mostrarIngredientes(){

		TableRow fila;
		TextView txtNombre;
		int background = R.drawable.celda_impar;

		if(datosIngredientes != null && datosIngredientes.size() >= 2){
		    TextView txtCantidad;

		    for(int i = 0; i < datosIngredientes.size() - 1; i = i + 2){
		    	if(background == R.drawable.celda_impar) background = R.drawable.celda_par;
		    	else background = R.drawable.celda_impar;	    	 
	
		    	fila = new TableRow(this);
		        fila.setLayoutParams(layoutRow);
	
		        txtNombre = new TextView(this);
		        txtCantidad = new TextView(this);
	
		        txtNombre.setText(datosIngredientes.get(i));
		        txtNombre.setGravity(Gravity.LEFT);
		        txtNombre.setTextAppearance(this,R.style.filaNombre);
		        txtNombre.setBackgroundResource(background);
		        txtNombre.setPadding(4, 0, 0, 0);
		        txtNombre.setLayoutParams(layoutRow);
	
		        txtCantidad.setText(datosIngredientes.get(i+1));
		        txtCantidad.setGravity(Gravity.CENTER);
		        txtCantidad.setTextAppearance(this,R.style.filaCantidad);
		        txtCantidad.setBackgroundResource(background);
		        txtCantidad.setLayoutParams(layoutRow);
	
		        fila.addView(txtNombre);
		        fila.addView(txtCantidad);
		        tablaIngredientes.addView(fila);
		    }
		}
	}

	private final void mostrarAditivos(){
				
        AdaptadorTitulos adaptador = new AdaptadorTitulos(this, datosAditivos, R.layout.list_item_2_1titulos);
        listaAditivos.setAdapter(adaptador);        
	}

}