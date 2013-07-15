package com.sovarb.cibus;

import java.util.ArrayList;
import java.util.List;

import com.sovarb.cibus.adapter.AdaptadorTitulos;
import com.sovarb.cibus.adapter.Titulos;
import com.sovarb.cibus.db.Aditivo;
import com.sovarb.cibus.db.Alimento;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ActivityDetalleAlimentos extends Activity implements OnItemClickListener {

	int currentTab = 0;
	TextView nombreAl, tipoAl, descripcionAl, formatoAl, marcaAl, submarcaAl;
	ImageView flechaD;
	ListView listaAditivos;
	boolean expand = false;
	//String[] infoAlimento, 
	String[] ingredientesAlimento, nutrientesAlimento, aditivosAlimento;
	Alimento infoAlimento;
	
	TabHost pestañas;
	TabHost.TabSpec tabAux;
	TableLayout tablaNutrientes, tablaIngredientes;
    TableRow.LayoutParams layoutRow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_alimentos);

		initViews();

		startTabHost();

		Intent i = getIntent();
		//infoAlimento = i.getStringArrayExtra(S.INFO);
		infoAlimento = i.getParcelableExtra(S.INFO);
		setInfo();
		
		ingredientesAlimento = i.getStringArrayExtra(S.INGREDIENTES);
		agregarIngredientes(ingredientesAlimento);
		
		nutrientesAlimento = i.getStringArrayExtra(S.NUTRIENTES);
		agregarNutrientes(nutrientesAlimento);
		
		aditivosAlimento = i.getStringArrayExtra(S.ADITIVOS);
		agregarAditivos(aditivosAlimento);

		expandableDescription();
		
		listaAditivos.setOnItemClickListener(this);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    // Read values from the "savedInstanceState"-object and put them in your textview
	    currentTab = savedInstanceState.getInt("tab");
	    pestañas.setCurrentTab(currentTab);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {		
	    // Save the values you need from your textview into "outState"-object
	    currentTab = pestañas.getCurrentTab();
		outState.putInt("tab", currentTab);
		super.onSaveInstanceState(outState);
	}

	public void pulsaAtras(View view){
		finish();
	}

	private final void initViews(){
		
		// Tab Info
		nombreAl = (TextView) findViewById(R.id.nombreAlimento);
		tipoAl = (TextView) findViewById(R.id.tipoAlimento);
		descripcionAl = (TextView) findViewById(R.id.descripcionAlimento);
		formatoAl = (TextView) findViewById(R.id.formatoAlimento);
		marcaAl = (TextView) findViewById(R.id.marcaAlimento);
		submarcaAl = (TextView) findViewById(R.id.submarcaAlimento);
		flechaD = (ImageView) findViewById(R.id.flechaDescripcion);
		
		// Tab Ingredientes
		tablaIngredientes = (TableLayout) findViewById(R.id.tablaIngredientes);
		
		// Tab Nutrientes
		tablaNutrientes = (TableLayout) findViewById(R.id.tablaNutrientes);
        layoutRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        
        // Tab Aditivos
        listaAditivos = (ListView) findViewById(R.id.tabAditivos);
        
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
	
	private final void setInfo(){
		
		try{
			nombreAl.setText(infoAlimento.getNombre());
			tipoAl.setText(infoAlimento.getTipo());
			descripcionAl.setText(infoAlimento.getDescripcion());
			formatoAl.setText(infoAlimento.getFormato());
			marcaAl.setText(infoAlimento.getMarca());
			submarcaAl.setText(infoAlimento.getSubmarca());
		}catch(Exception e){ }
	}

	private final void agregarIngredientes(String[] pIngredientes){

		TableRow fila;
		TextView txtNombre;
		int background = R.drawable.celda_impar;

		if(pIngredientes != null && pIngredientes.length >= 2){
		    TextView txtCantidad;

		    for(int i = 0; i < pIngredientes.length - 1; i = i + 2){
		    	if(background == R.drawable.celda_impar) background = R.drawable.celda_par;
		    	else background = R.drawable.celda_impar;	    	 
	
		    	fila = new TableRow(this);
		        fila.setLayoutParams(layoutRow);
	
		        txtNombre = new TextView(this);
		        txtCantidad = new TextView(this);
	
		        txtNombre.setText(pIngredientes[i]);
		        txtNombre.setGravity(Gravity.LEFT);
		        txtNombre.setTextAppearance(this,R.style.filaNombre);
		        txtNombre.setBackgroundResource(background);
		        txtNombre.setPadding(4, 0, 0, 0);
		        txtNombre.setLayoutParams(layoutRow);
	
		        txtCantidad.setText(pIngredientes[i+1]);
		        txtCantidad.setGravity(Gravity.CENTER);
		        txtCantidad.setTextAppearance(this,R.style.filaCantidad);
		        txtCantidad.setBackgroundResource(background);
		        txtCantidad.setLayoutParams(layoutRow);
	
		        fila.addView(txtNombre);
		        fila.addView(txtCantidad);
		        tablaIngredientes.addView(fila);
		    }
		}else{
			fila = new TableRow(this);
			fila.setLayoutParams(layoutRow);
			txtNombre = new TextView(this);
	        txtNombre.setText(S.NOT_FOUND_7);
	        txtNombre.setGravity(Gravity.LEFT);
	        txtNombre.setTextAppearance(this,R.style.filaNombre);
	        txtNombre.setBackgroundResource(background);
	        txtNombre.setPadding(4, 0, 0, 0);
	        txtNombre.setLayoutParams(layoutRow);
	        fila.addView(txtNombre);
	        tablaIngredientes.addView(fila);
		}
	}
	
	private final void agregarNutrientes(String[] pNutrientes){

		TableRow fila;
	    TextView txtNombre;
	    int background = R.drawable.celda_impar;
	    
		if(pNutrientes != null && pNutrientes.length >= 2){			
		    TextView txtCantidad;
		         
		    for(int i = 0; i < pNutrientes.length - 1; i = i + 2){
		    	if(background == R.drawable.celda_impar) background = R.drawable.celda_par;
		    	else background = R.drawable.celda_impar;	    	 
	
		    	fila = new TableRow(this);
		        fila.setLayoutParams(layoutRow);
	
		        txtNombre = new TextView(this);
		        txtCantidad = new TextView(this);
	
		        txtNombre.setText(pNutrientes[i]);
		        txtNombre.setGravity(Gravity.LEFT);
		        txtNombre.setTextAppearance(this,R.style.filaNombre);
		        txtNombre.setBackgroundResource(background);
		        txtNombre.setPadding(4, 0, 0, 0);
		        txtNombre.setLayoutParams(layoutRow);
	
		        txtCantidad.setText(pNutrientes[i+1]);
		        txtCantidad.setGravity(Gravity.CENTER);
		        txtCantidad.setTextAppearance(this,R.style.filaCantidad);
		        txtCantidad.setBackgroundResource(background);
		        txtCantidad.setLayoutParams(layoutRow);
	
		        fila.addView(txtNombre);
		        fila.addView(txtCantidad);
		        tablaNutrientes.addView(fila);
		    }
		}else{
			fila = new TableRow(this);
			fila.setLayoutParams(layoutRow);
			txtNombre = new TextView(this);
	        txtNombre.setText(S.NOT_FOUND_8);
	        txtNombre.setGravity(Gravity.LEFT);
	        txtNombre.setTextAppearance(this,R.style.filaNombre);
	        txtNombre.setBackgroundResource(background);
	        txtNombre.setPadding(4, 0, 0, 0);
	        txtNombre.setLayoutParams(layoutRow);
	        fila.addView(txtNombre);
	        tablaNutrientes.addView(fila);
		}
	}

	private final void agregarAditivos(String[] pAditivos){
		List<Titulos> datos = new ArrayList<Titulos>();
		if(pAditivos.length != 0){        				
	        for(int i=0; i < aditivosAlimento.length; i = i + 2){
	         Titulos aux = new Titulos(aditivosAlimento[i], aditivosAlimento[i+1]);
	         datos.add(aux);
	        }
        }else{
        	Titulos aux = new Titulos(S.NOT_FOUND_6, "");
	        datos.add(aux);
	        listaAditivos.setEnabled(false);
        }
        AdaptadorTitulos adaptador = new AdaptadorTitulos(this, datos, R.layout.list_item_2_1titulos);
        listaAditivos.setAdapter(adaptador);        
	}

	private final void expandableDescription(){
		
		if(descripcionAl.getText().length() > S.MINCHAR){
			flechaD.setVisibility(View.VISIBLE);
			descripcionAl.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(expand){
						expand = false;
						flechaD.setImageResource(R.drawable.navigation_expand);
						descripcionAl.setMaxLines(S.MAXLINES);
					}
					else{
						expand = true;
						flechaD.setImageResource(R.drawable.navigation_collapse);
						descripcionAl.setMaxLines(Integer.MAX_VALUE);
					}
				}
			});
			
			flechaD.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(expand){
						expand = false;
						flechaD.setImageResource(R.drawable.navigation_expand);
						descripcionAl.setMaxLines(S.MAXLINES);
					}
					else{
						expand = true;
						flechaD.setImageResource(R.drawable.navigation_collapse);
						descripcionAl.setMaxLines(Integer.MAX_VALUE);
					}
				}
			});
		}else{
			flechaD.setVisibility(View.GONE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> a, View view, int position, long id) {

		String seleccion = ((Titulos)a.getAdapter().getItem(position)).getTitulo();
		S.data_base.openR();
		Aditivo infoAditivo = S.data_base.consultaAditivoXNumero(seleccion);
		S.data_base.close();
		if(infoAditivo == null){
			Intent i = new Intent(this, ActivityNotFound.class);
			i.putExtra(S.MENSAJE_NOT_FOUND, S.NOT_FOUND_1 + " '" + seleccion + "'.");
			startActivity(i);			
		}else{		
			Intent i = new Intent(this, ActivityDetalleAditivos.class);
			i.putExtra(S.INFO, infoAditivo);
			startActivity(i);
		}
	}

}