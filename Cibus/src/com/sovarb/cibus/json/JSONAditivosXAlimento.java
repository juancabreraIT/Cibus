package com.sovarb.cibus.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.AditivosXAlimento;
import com.sovarb.cibus.db.GestorDB.TAditivosXAlimento;

public final class JSONAditivosXAlimento {

	private JSONObject jObject; 

	public JSONAditivosXAlimento(){}
	
	public void readJSONAditivosXAlimento() throws JSONException{
		jObject = JSONManager.getJSONfromURL(S.HTTP_SERVER + S.SERVICE_ADITIVOSXALIMENTO);
		if(jObject != null){
			parseJSONAditivosXAlimento(jObject.getJSONArray(TAditivosXAlimento.NOMBRE_TABLA));
		}
	}
	
	private void parseJSONAditivosXAlimento(JSONArray aditivosXalimentosArray) throws JSONException{
		
		AditivosXAlimento aditivos = new AditivosXAlimento();
		S.data_base.openW();
		for(int i = 0; i < aditivosXalimentosArray.length(); i++){			
			aditivos.setIdAlimento(aditivosXalimentosArray.getJSONObject(i).getInt(TAditivosXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO));
			aditivos.setIdAditivo(aditivosXalimentosArray.getJSONObject(i).getInt(TAditivosXAlimento.NOMBRE_COLUMNA_ID_ADITIVO));

			S.data_base.insertarAditivosXAlimentoLocal(aditivos);			
		}		
		S.data_base.close();
	}
}
