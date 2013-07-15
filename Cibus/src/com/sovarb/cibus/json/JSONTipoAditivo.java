package com.sovarb.cibus.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.GestorDB.TTiposAditivos;
import com.sovarb.cibus.db.TipoAditivo;

public final class JSONTipoAditivo {

	private JSONObject jObject; 

	public JSONTipoAditivo() {	}

	public void readJSONTiposAditivos() throws JSONException{
		jObject = JSONManager.getJSONfromURL(S.HTTP_SERVER + S.SERVICE_TIPO_ADITIVO);
		if(jObject != null){			
			parseJSONTiposAditivos(jObject.getJSONArray(TTiposAditivos.NOMBRE_TABLA));
		}
	}
	
	private void parseJSONTiposAditivos(JSONArray tiposAditivosArray) throws JSONException{
		
		TipoAditivo tipoAditivo = new TipoAditivo();
		S.data_base.openW();
		for(int i = 0; i < tiposAditivosArray.length(); i++){			
			tipoAditivo.setId(tiposAditivosArray.getJSONObject(i).getInt(TTiposAditivos.NOMBRE_COLUMNA_ID));
			tipoAditivo.setNombre(tiposAditivosArray.getJSONObject(i).getString(TTiposAditivos.NOMBRE_COLUMNA_NOMBRE));
			tipoAditivo.setRango(tiposAditivosArray.getJSONObject(i).getString(TTiposAditivos.NOMBRE_COLUMNA_RANGO));
				
			S.data_base.insertarTipoAditivo(tipoAditivo);
		}
		S.data_base.close();
	}

}
