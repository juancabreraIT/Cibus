package com.sovarb.cibus.db;

import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.GestorDB.TIngredientesXAlimento;
import com.sovarb.cibus.http.HttpClientManager;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class IngredientesXAlimento extends FItem implements Parcelable {

	private String cantidad;

	public IngredientesXAlimento() { }

	public IngredientesXAlimento(int idAlimento, int idIngrediente, String cantidad){
		this.setIdForeign1(idAlimento);
		this.setIdForeign2(idIngrediente);
		this.setCantidad(cantidad);
	}

	public IngredientesXAlimento(Parcel in){
		readFromParcel(in);
	}


	/** GETTERS SETTERS **/

	public int getIdAlimento(){
		return this.getIdForeign1();
	}

	public void setIdAlimento(int id){
		this.setIdForeign1(id);
	}
	
	public int getIdIngrediente(){
		return this.getIdForeign2();
	}
	
	public void setIdIngrediente(int id){
		this.setIdForeign2(id);
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}


	/** HTTP POST **/

	public boolean saveIngredientesXAlimentoCloud(Activity activity){
		HttpClientManager httpclient = new HttpClientManager(activity);
	    httpclient.addNameValue(TIngredientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO, String.valueOf(this.getIdAlimento()));
	    httpclient.addNameValue(TIngredientesXAlimento.NOMBRE_COLUMNA_ID_INGREDIENTE, String.valueOf(this.getIdIngrediente()));
	    httpclient.addNameValue(TIngredientesXAlimento.NOMBRE_COLUMNA_CANTIDAD, this.getCantidad());

	    String ResponseBody = httpclient.executeHttpPostAsync(S.HTTP_SERVER + S.SERVICE_INGREDIENTESXALIMENTO_ADD);
    	try {
    		JSONObject json = new JSONObject(ResponseBody);		    		
    		if (json.getString(S.KEY_SUCCESS) != null) {
    			if((Integer.parseInt(json.getString(S.KEY_SUCCESS)) == 1)){
    				return true;

    			}else{return false;	}
    		}else{return false;	}

    	}catch(JSONException e){
    		return false;
    	}
	}


	/** PARCEL **/

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeInt(this.getIdAlimento());
		dest.writeInt(this.getIdIngrediente());
		dest.writeString(this.getCantidad());
	}
	
	private void readFromParcel(Parcel in) {	
		this.setIdAlimento(in.readInt());
		this.setIdIngrediente(in.readInt());
		this.setCantidad(in.readString());
	}

	public static final Parcelable.Creator<IngredientesXAlimento> CREATOR = new Parcelable.Creator<IngredientesXAlimento>() {
		public IngredientesXAlimento createFromParcel(Parcel in){
			return new IngredientesXAlimento(in);
		}
		
		public IngredientesXAlimento[] newArray(int size){
			return new IngredientesXAlimento[size];
		}
	};
	
}
