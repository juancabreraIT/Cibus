package com.sovarb.cibus.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.GestorDB.TIngredientes;
import com.sovarb.cibus.db.Ingrediente;

public final class JSONIngrediente {

	private JSONObject jObject; 

	public JSONIngrediente(){}

	public void readJSONIngredientes() throws JSONException{
		jObject = JSONManager.getJSONfromURL(S.HTTP_SERVER + S.SERVICE_INGREDIENTE);
		if(jObject != null){
			parseJSONIngredientes(jObject.getJSONArray(TIngredientes.NOMBRE_TABLA));
		}
	}

	private void parseJSONIngredientes(JSONArray ingredientesArray) throws JSONException{

		Ingrediente ingre = new Ingrediente();
		S.data_base.openW();
		for(int i = 0; i < ingredientesArray.length(); i++){			
			ingre.setId(ingredientesArray.getJSONObject(i).getInt(TIngredientes.NOMBRE_COLUMNA_ID));
			ingre.setNombre(ingredientesArray.getJSONObject(i).getString(TIngredientes.NOMBRE_COLUMNA_NOMBRE));
			ingre.setDescripcion(ingredientesArray.getJSONObject(i).getString(TIngredientes.NOMBRE_COLUMNA_DESCRIPCION));

			S.data_base.insertarIngredienteLocal(ingre);	
		}
		S.data_base.close();
	}		

}
