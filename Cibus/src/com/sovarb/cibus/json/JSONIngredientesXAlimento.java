package com.sovarb.cibus.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.GestorDB.TIngredientesXAlimento;
import com.sovarb.cibus.db.IngredientesXAlimento;

public final class JSONIngredientesXAlimento {

	private JSONObject jObject; 

	public JSONIngredientesXAlimento(){}
	
	public void readJSONIngredientesXAlimento() throws JSONException{
		jObject = JSONManager.getJSONfromURL(S.HTTP_SERVER + S.SERVICE_INGREDIENTESXALIMENTO);
		if(jObject != null){
			parseJSONIngredientesXAlimento(jObject.getJSONArray(TIngredientesXAlimento.NOMBRE_TABLA));
		}
	}
	
	private void parseJSONIngredientesXAlimento(JSONArray ingredientesXalimentosArray) throws JSONException{
			
		IngredientesXAlimento ingredientes = new IngredientesXAlimento();
		S.data_base.openW();
		for(int i = 0; i < ingredientesXalimentosArray.length(); i++){	
			ingredientes.setIdAlimento(ingredientesXalimentosArray.getJSONObject(i).getInt(TIngredientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO));
			ingredientes.setIdIngrediente(ingredientesXalimentosArray.getJSONObject(i).getInt(TIngredientesXAlimento.NOMBRE_COLUMNA_ID_INGREDIENTE));
			ingredientes.setCantidad(ingredientesXalimentosArray.getJSONObject(i).getString(TIngredientesXAlimento.NOMBRE_COLUMNA_CANTIDAD));
			
			S.data_base.insertarIngredientesXAlimentoLocal(ingredientes);			
		}		
		S.data_base.close();
	}	
	
}
