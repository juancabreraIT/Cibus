package com.sovarb.cibus.db;

import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.GestorDB.TNutrientesXAlimento;
import com.sovarb.cibus.http.HttpClientManager;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class NutrientesXAlimento extends FItem implements Parcelable {

	private String cantidad;
	
	public NutrientesXAlimento(){}
	
	public NutrientesXAlimento(int idAAlimento, int idNutriente, String cantidad){
		this.setIdForeign1(idAAlimento);
		this.setIdForeign2(idNutriente);
		this.setCantidad(cantidad);
	}

	public NutrientesXAlimento(Parcel in){
		readFromParcel(in);
	}
	
	
	/** GETTERS SETTERS **/
	
	public int getIdAlimento(){
		return this.getIdForeign1();
	}
	public void setIdAlimento(int id){
		this.setIdForeign1(id);
	}
	
	public int getIdNutriente(){
		return this.getIdForeign2();
	}
	public void setIdNutriente(int id){
		this.setIdForeign2(id);
	}
	
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad.trim();
	}


	/** HTTP POST **/

	public boolean saveNutrientesXAlimentoCloud(Activity activity){
		HttpClientManager httpclient = new HttpClientManager(activity);
	    httpclient.addNameValue(TNutrientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO, String.valueOf(this.getIdAlimento()));
	    httpclient.addNameValue(TNutrientesXAlimento.NOMBRE_COLUMNA_ID_NUTRIENTE, String.valueOf(this.getIdNutriente()));
	    httpclient.addNameValue(TNutrientesXAlimento.NOMBRE_COLUMNA_CANTIDAD, this.getCantidad());

	    String ResponseBody = httpclient.executeHttpPostAsync(S.HTTP_SERVER + S.SERVICE_NUTRIENTESXALIMENTO_ADD);
		    	try {
		    		JSONObject json = new JSONObject(ResponseBody);
		    		if (json.getString(S.KEY_SUCCESS) != null) {
		    			if((Integer.parseInt(json.getString(S.KEY_SUCCESS)) == 1)){
		    				return true;

		    			}else{return true;	}
		    		}else{return true;	}

		    	}catch(JSONException e){
		    		return true;
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
		dest.writeInt(this.getIdNutriente());
		dest.writeString(this.getCantidad());
	}
	
	private void readFromParcel(Parcel in) {	
		this.setIdAlimento(in.readInt());
		this.setIdNutriente(in.readInt());
		this.setCantidad(in.readString());
	}

	public static final Parcelable.Creator<NutrientesXAlimento> CREATOR = new Parcelable.Creator<NutrientesXAlimento>() {
		public NutrientesXAlimento createFromParcel(Parcel in){
			return new NutrientesXAlimento(in);
		}
		
		public NutrientesXAlimento[] newArray(int size){
			return new NutrientesXAlimento[size];
		}
	};
	
}
