package com.sovarb.cibus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sovarb.cibus.db.GestorDB;

public class ActivityMain extends Activity {

	Button botonConsultaAditivos, botonConsultaAlimentos, botonConsultaFiltros;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		botonConsultaAditivos = (Button) findViewById(R.id.botonConsultaAditivos);
		botonConsultaAlimentos = (Button) findViewById(R.id.botonConsultaAlimentos);
		botonConsultaFiltros = (Button) findViewById(R.id.botonConsultaFiltros);

		S.data_base = new GestorDB(this);
		S.data_base.openR();

		S.data_base.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId() == R.id.about){
			//aboutCibus();
			Toast.makeText(this, "About Cibus", Toast.LENGTH_LONG).show();
		}
		return true;
	}

	public final void pulsaAditivos(View view){
		Intent i = new Intent(this, ActivityConsultaAditivos.class);
		startActivity(i);
	}

	public final void pulsaAlimentos(View view){
		Intent i = new Intent(this, ActivityConsultaAlimentos.class);
		startActivity(i);
	}

	public final void pulsaFiltros(View view){
		Intent i = new Intent(this, ActivityConsultaFiltros.class);
		startActivity(i);
	}
}