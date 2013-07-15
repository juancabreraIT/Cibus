package com.sovarb.cibus.db;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.GestorDB.TNutrientes;
import com.sovarb.cibus.http.HttpClientManager;

public class Nutriente extends NItem implements Parcelable{

	public Nutriente(){}
	
	public Nutriente(int pId, String pNombre, String pDescripcion){
		this.setId(pId);
		this.setNombre(pNombre);
		this.setDescripcion(pDescripcion);
	}

	public Nutriente(String pNombre, String pDescripcion){
		this.setNombre(pNombre);
		this.setDescripcion(pDescripcion);
	}
	
	public Nutriente(Parcel in){
		readFromParcel(in);
	}


	/** HTTP POST**/

	public Nutriente saveNutrienteCloud(Activity activity){
		HttpClientManager httpclient = new HttpClientManager(activity);
	    httpclient.addNameValue(TNutrientes.NOMBRE_COLUMNA_NOMBRE, this.getNombre());
	    httpclient.addNameValue(TNutrientes.NOMBRE_COLUMNA_DESCRIPCION, this.getDescripcion());

	    String ResponseBody = httpclient.executeHttpPostAsync(S.HTTP_SERVER + S.SERVICE_NUTRIENTE_ADD);
    	try {
    		JSONObject json = new JSONObject(ResponseBody);
    		if (json.getString(S.KEY_SUCCESS) != null) {
    			if((Integer.parseInt(json.getString(S.KEY_SUCCESS)) == 1)){
    				if(json.has("nutriente")){
    					return parseJSONNutriente(json.getJSONObject("nutriente"));
    				}else{
    					return null;
    				}

    			}else{ return null; }
    		}else{ return null;	}

    	}catch(JSONException e){
    		return null;
    	}
	}

	private Nutriente parseJSONNutriente(JSONObject jsonNutriente) throws JSONException{
		this.setId(jsonNutriente.getInt(TNutrientes.NOMBRE_COLUMNA_ID));
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

	public static final Parcelable.Creator<Nutriente> CREATOR = new Parcelable.Creator<Nutriente>() {
		public Nutriente createFromParcel(Parcel in){
			return new Nutriente(in);
		}
		
		public Nutriente[] newArray(int size){
			return new Nutriente[size];
		}
	};	

}
