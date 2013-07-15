package com.sovarb.cibus.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.Aditivo;
import com.sovarb.cibus.db.GestorDB.TAditivos;

public final class JSONAditivo {

	private JSONObject jObject;

	public JSONAditivo(){}
	
	public void readJSONAditivos() throws JSONException{
		jObject = JSONManager.getJSONfromURL(S.HTTP_SERVER + S.SERVICE_ADITIVO);
		if(jObject != null){
			parseJSONAditivos(jObject.getJSONArray(TAditivos.NOMBRE_TABLA));
		}
	}

	private void parseJSONAditivos(JSONArray aditivosArray) throws JSONException{
		
		Aditivo aditivo = new Aditivo();
		S.data_base.openW();
		for(int i = 0; i < aditivosArray.length(); i++){			
			aditivo.setId(aditivosArray.getJSONObject(i).getInt(TAditivos.NOMBRE_COLUMNA_ID));
			aditivo.setNumero(aditivosArray.getJSONObject(i).getString(TAditivos.NOMBRE_COLUMNA_NUMERO));
			aditivo.setNombre(aditivosArray.getJSONObject(i).getString(TAditivos.NOMBRE_COLUMNA_NOMBRE));
			aditivo.setDescripcion(aditivosArray.getJSONObject(i).getString(TAditivos.NOMBRE_COLUMNA_DESCRIPCION));
			aditivo.setCaracter(aditivosArray.getJSONObject(i).getString(TAditivos.NOMBRE_COLUMNA_CARACTER));
			aditivo.setTipoAditivo(aditivosArray.getJSONObject(i).getInt(TAditivos.NOMBRE_COLUMNA_ID_TIPO));
	     
			S.data_base.insertarAditivoLocal(aditivo);			
		}		
		S.data_base.close();
	}
}
