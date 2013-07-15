package com.sovarb.cibus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityNotFoundAlimento extends Activity {
	
	TextView mensaje;
	String mensajeNotFound, codigo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_not_found_alimento);

		mensaje = (TextView) findViewById(R.id.textWarning);
		
		Intent i = getIntent();
		mensajeNotFound = i.getStringExtra(S.MENSAJE_NOT_FOUND);
		codigo = i.getStringExtra(S.CODIGO);
		
		mensaje.setText(mensajeNotFound);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		finish();
	}
	
	public void pulsaAtras(View view){
		finish();
	}
	
	public void pulsaAddAlimento(View view){
		
		Intent i = new Intent(this, ActivityAddAlimento.class);
		if(codigo != null)
			i.putExtra(S.CODIGO, codigo);
		startActivity(i);
	}
}