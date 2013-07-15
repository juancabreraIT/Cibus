package com.sovarb.cibus.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.GestorDB.TNutrientesXAlimento;
import com.sovarb.cibus.db.NutrientesXAlimento;

public final class JSONNutrientesXAlimento {

	private JSONObject jObject; 
	
	public JSONNutrientesXAlimento(){}
	
	public void readJSONNutrientesXAlimento() throws JSONException{
		jObject = JSONManager.getJSONfromURL(S.HTTP_SERVER + S.SERVICE_NUTRIENTESXALIMENTO);
		if(jObject != null){
			parseJSONNutrientesXAlimento(jObject.getJSONArray(TNutrientesXAlimento.NOMBRE_TABLA));
		}
	}
	
	private void parseJSONNutrientesXAlimento(JSONArray nutrientesXalimentosArray) throws JSONException{
			
		NutrientesXAlimento nutrientes = new NutrientesXAlimento();
		S.data_base.openW();
		for(int i = 0; i < nutrientesXalimentosArray.length(); i++){			
			nutrientes.setIdAlimento(nutrientesXalimentosArray.getJSONObject(i).getInt(TNutrientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO));
			nutrientes.setIdNutriente(nutrientesXalimentosArray.getJSONObject(i).getInt(TNutrientesXAlimento.NOMBRE_COLUMNA_ID_NUTRIENTE));
			nutrientes.setCantidad(nutrientesXalimentosArray.getJSONObject(i).getString(TNutrientesXAlimento.NOMBRE_COLUMNA_CANTIDAD));
			
			S.data_base.insertarNutrientesXAlimentoLocal(nutrientes);			
		}
		S.data_base.close();
	}
}
