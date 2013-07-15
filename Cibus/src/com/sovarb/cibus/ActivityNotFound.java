package com.sovarb.cibus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityNotFound extends Activity {
	
	TextView mensaje;
	String mensajeNotFound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_not_found);

		mensaje = (TextView) findViewById(R.id.textWarning);
		
		Intent i = getIntent();
		mensajeNotFound = i.getStringExtra(S.MENSAJE_NOT_FOUND);
		
		mensaje.setText(mensajeNotFound);
	}
	
	public void pulsaAtras(View view){
		finish();
	}
}