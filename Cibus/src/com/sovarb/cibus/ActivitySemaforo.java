package com.sovarb.cibus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivitySemaforo extends Activity {

	TextView mensaje, regla;
	ImageView semaforo;
	LinearLayout layout;
	String result, nombreAlimento;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_semaforo);

		mensaje = (TextView) findViewById(R.id.textMensaje);
		regla = (TextView) findViewById(R.id.textReglaFail);
		layout = (LinearLayout) findViewById(R.id.layoutSemaforo);
		semaforo = (ImageView) findViewById(R.id.imageSemaforo);

		Intent i = getIntent();
		result = i.getStringExtra(S.SEMAFORO_RESULT);
		nombreAlimento = i.getStringExtra(S.NOMBRE);
		
		if(result.equals(S.RESULTADO.VALIDO.name())){
			semaforo.setImageResource(R.drawable.ic_green_apple);
			layout.setBackgroundColor(Color.rgb(60, 255, 40));
			mensaje.setText(nombreAlimento + S.CUMPLE_CRITERIO);
			mensaje.setTextColor(Color.WHITE);
			regla.setText(" ");
		}else if(result.equals(S.RESULTADO.INVALIDO.name())){
			regla.setText(i.getStringExtra(S.REGLA));
			semaforo.setImageResource(R.drawable.ic_red_apple);
			layout.setBackgroundColor(Color.rgb(255, 60, 40));
			mensaje.setText(nombreAlimento + S.NO_CUMPLE_CRITERIO);
			mensaje.setTextColor(Color.WHITE);
			
		}else{
			semaforo.setImageResource(R.drawable.ic_yellow_apple);
			layout.setBackgroundColor(Color.rgb(245, 245, 170));
			mensaje.setText(S.NOT_FOUND_9 + nombreAlimento);
			mensaje.setTextColor(Color.BLACK);
			regla.setText(" ");
		}
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		layout.setBackgroundColor(Color.rgb(221, 221, 221));
	}
	
	public void pulsaAtras(View view){	
		Intent i = new Intent();
		setResult(RESULT_OK, i);
		finish();	
	}

}