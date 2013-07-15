package com.sovarb.cibus;

import com.sovarb.cibus.db.Aditivo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityDetalleAditivos extends Activity {

	TextView numeroAd, nombreAd, tipoAd, descripcionAd, caracterAd;
	ImageView flecha;
	boolean expand = false;
	String tipoAditivo;
	Aditivo aditivo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_aditivos);

		numeroAd = (TextView) findViewById(R.id.numeroAditivo);
		nombreAd = (TextView) findViewById(R.id.nombreAditivo);
		tipoAd = (TextView) findViewById(R.id.tipoAditivo);
		descripcionAd = (TextView) findViewById(R.id.descripcionAditivo);
		caracterAd = (TextView) findViewById(R.id.caracterAditivo);
		flecha = (ImageView) findViewById(R.id.flecha);
		
		Intent i = getIntent();
		aditivo = i.getParcelableExtra(S.INFO);

		if(aditivo == null){
			Toast.makeText(this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
		}else{
			loadDetails();
			expandableDescription();
		}		
	}

	public void pulsaAtras(View view){
		finish();
	}

	public void pulsaEditar(View view){
		
		Intent i = new Intent(this, ActivityEditarAditivos.class);
		i.putExtra(S.ADITIVOS, aditivo);
		startActivityForResult(i, S.REQUEST_CODE_1);
	}

	private void loadDetails(){
		
		numeroAd.setText(aditivo.getNumero());
		nombreAd.setText(aditivo.getNombre());
		S.data_base.openR();
		tipoAd.setText(S.data_base.consultaNombreTipoAditivoXId(aditivo.getTipoAditivo()));
		S.data_base.close();
		descripcionAd.setText(aditivo.getDescripcion());
		caracterAd.setText(aditivo.getCaracter());
		customCaracter();
	}

	private void customCaracter(){
		
		if(aditivo.getCaracter().equals(S.CARACTER.inofensivo.name())){			
			caracterAd.setTypeface(null, Typeface.BOLD);
			caracterAd.setTextColor(Color.rgb(0, 200, 0));
		}else if(aditivo.getCaracter().equals(S.CARACTER.sospechoso.name())){
			caracterAd.setTextColor(Color.rgb(230, 200, 0));
			caracterAd.setTypeface(null, Typeface.BOLD);
		}else if(aditivo.getCaracter().equals(S.CARACTER.peligroso.name())){
			caracterAd.setTextColor(Color.rgb(210, 000, 0));
			caracterAd.setTypeface(null, Typeface.BOLD);
		}else{
			caracterAd.setTypeface(null, Typeface.NORMAL);
		}
	}
	
	public void expandableDescription(){
		
		if(descripcionAd.getText().length() > S.MINCHAR){
			flecha.setVisibility(View.VISIBLE);
			descripcionAd.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(expand){
						expand = false;
						flecha.setImageResource(R.drawable.navigation_expand);
						descripcionAd.setMaxLines(S.MAXLINES);
					}
					else{
						expand = true;
						flecha.setImageResource(R.drawable.navigation_collapse);
						descripcionAd.setMaxLines(Integer.MAX_VALUE);
					}
				}
			});
			
			flecha.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(expand){
						expand = false;
						flecha.setImageResource(R.drawable.navigation_expand);
						descripcionAd.setMaxLines(S.MAXLINES);
					}
					else{
						expand = true;
						flecha.setImageResource(R.drawable.navigation_collapse);
						descripcionAd.setMaxLines(Integer.MAX_VALUE);
					}
				}
			});
		}else{
			flecha.setVisibility(View.GONE);
		}		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data){

		if(requestCode == S.REQUEST_CODE_1 && resultCode == RESULT_OK){	
			
			Aditivo aux  = data.getParcelableExtra(S.ADITIVOS);

			if(aux != null){
				aditivo = aux;
				loadDetails();
			}
		}
	}

}