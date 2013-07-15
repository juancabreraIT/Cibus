package com.sovarb.cibus.db;

import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.GestorDB.TIngredientes;
import com.sovarb.cibus.http.HttpClientManager;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class Ingrediente extends NItem implements Parcelable {

	public Ingrediente(){}

	public Ingrediente(int pId, String pNombre, String pDescripcion){
		
		this.setId(pId);
		this.setNombre(pNombre);
		this.setDescripcion(pDescripcion);
	}
	
	public Ingrediente(String pNombre, String pDescripcion){
		this.setNombre(pNombre);
		this.setDescripcion(pDescripcion);
	}

	public Ingrediente(Parcel in){
		readFromParcel(in);
	}


	/** HTTP POST **/
	
	public Ingrediente saveIngredienteCloud(Activity activity){
		HttpClientManager httpclient = new HttpClientManager(activity);
	    httpclient.addNameValue(TIngredientes.NOMBRE_COLUMNA_NOMBRE, this.getNombre());
	    httpclient.addNameValue(TIngredientes.NOMBRE_COLUMNA_DESCRIPCION, this.getDescripcion());

	    String ResponseBody = httpclient.executeHttpPostAsync(S.HTTP_SERVER + S.SERVICE_INGREDIENTES_ADD);

    	try {
    		JSONObject json = new JSONObject(ResponseBody);
    		if (json.getString(S.KEY_SUCCESS) != null) {
    			if((Integer.parseInt(json.getString(S.KEY_SUCCESS)) == 1)){
    				if(json.has("ingrediente")){
    					return parseJSONIngrediente(json.getJSONObject("ingrediente"));
    				}else{
    					return null;
    				}

    			}else{return null;	}
    		}else{return null;	}

    	}catch(JSONException e){
    		return null;
    	}
	}

	private Ingrediente parseJSONIngrediente(JSONObject jsonIngrediente) throws JSONException{
		this.setId(jsonIngrediente.getInt(TIngredientes.NOMBRE_COLUMNA_ID));
		return this;
	}


	/** PARCEL **/
	
	@Override
	public int describeContents() {

		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeInt(getId());
		dest.writeString(getNombre());
		dest.writeString(getDescripcion());
	}

	private void readFromParcel(Parcel in){
		this.setId(in.readInt());
		this.setNombre(in.readString());
		this.setDescripcion(in.readString());
	}

	public static final Parcelable.Creator<Ingrediente> CREATOR = new Parcelable.Creator<Ingrediente>() {
		public Ingrediente createFromParcel(Parcel in){			
			return new Ingrediente(in);
		}
		
		public Ingrediente[] newArray(int size){
			return new Ingrediente[size];
		}
	};
}
