package com.sovarb.cibus.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.Alimento;
import com.sovarb.cibus.db.GestorDB.TAlimentos;

public final class JSONAlimento {

	private JSONObject jObject; 
	
	public JSONAlimento(){}
	
	public void readJSONAlimentos() throws JSONException{
		jObject = JSONManager.getJSONfromURL(S.HTTP_SERVER + S.SERVICE_ALIMENTO);
		if(jObject != null){
			parseJSONAlimentos(jObject.getJSONArray(TAlimentos.NOMBRE_TABLA));
		}
	}

	private void parseJSONAlimentos(JSONArray alimentosArray) throws JSONException{
	
		Alimento alimento = new Alimento();
		S.data_base.openW();
		for(int i = 0; i < alimentosArray.length(); i++){			
			alimento.setId(alimentosArray.getJSONObject(i).getInt(TAlimentos.NOMBRE_COLUMNA_ID));
			alimento.setNombre(alimentosArray.getJSONObject(i).getString(TAlimentos.NOMBRE_COLUMNA_NOMBRE));
			alimento.setTipo(alimentosArray.getJSONObject(i).getString(TAlimentos.NOMBRE_COLUMNA_TIPO));
			alimento.setDescripcion(alimentosArray.getJSONObject(i).getString(TAlimentos.NOMBRE_COLUMNA_DESCRIPCION));
			alimento.setFormato(alimentosArray.getJSONObject(i).getString(TAlimentos.NOMBRE_COLUMNA_FORMATO));
			alimento.setMarca(alimentosArray.getJSONObject(i).getString(TAlimentos.NOMBRE_COLUMNA_MARCA));
			alimento.setSubmarca(alimentosArray.getJSONObject(i).getString(TAlimentos.NOMBRE_COLUMNA_SUBMARCA));
			alimento.setCodigo1D(alimentosArray.getJSONObject(i).getString(TAlimentos.NOMBRE_COLUMNA_CODIGO1D));
			alimento.setCodigo2D(alimentosArray.getJSONObject(i).getString(TAlimentos.NOMBRE_COLUMNA_CODIGO2D));

			S.data_base.insertarAlimentoLocal(alimento);		
		}
		S.data_base.close();
	}	
}
