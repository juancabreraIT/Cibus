package com.sovarb.cibus.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.GestorDB.TNutrientes;
import com.sovarb.cibus.db.Nutriente;

public final class JSONNutriente {

	private JSONObject jObject; 
	
	public JSONNutriente(){}
	
	public void readJSONNutrientes() throws JSONException {
		jObject = JSONManager.getJSONfromURL(S.HTTP_SERVER + S.SERVICE_NUTRIENTE);
		if(jObject != null){
			parseJSONNutrientes(jObject.getJSONArray(TNutrientes.NOMBRE_TABLA));
		}
	}
	
	private void parseJSONNutrientes(JSONArray nutrientesArray) throws JSONException{
			
		Nutriente nutriente = new Nutriente();
		S.data_base.openW();
		for(int i = 0; i < nutrientesArray.length(); i++){			
			nutriente.setId(nutrientesArray.getJSONObject(i).getInt(TNutrientes.NOMBRE_COLUMNA_ID));
			nutriente.setNombre(nutrientesArray.getJSONObject(i).getString(TNutrientes.NOMBRE_COLUMNA_NOMBRE));
			nutriente.setDescripcion(nutrientesArray.getJSONObject(i).getString(TNutrientes.NOMBRE_COLUMNA_DESCRIPCION));

			S.data_base.insertarNutrienteLocal(nutriente);
		}
		S.data_base.close();
	}
	
}
