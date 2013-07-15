package com.sovarb.cibus.db;

import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.GestorDB.TAditivos;
import com.sovarb.cibus.http.HttpClientManager;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class Aditivo extends NItem implements Parcelable {

	private String numero;
	private String caracter;
	private int tipoAditivo;

	public Aditivo(){}

	public Aditivo(int pId, String pNumero, String pNombre, String pDescripcion, String pCaracter, int pTipo){
		
		this.setId(pId);
		this.setNumero(pNumero);
		this.setNombre(pNombre);
		this.setDescripcion(pDescripcion);
		this.setCaracter(pCaracter);
		this.setTipoAditivo(pTipo);
	}

	public Aditivo(String pNumero, String pNombre, String pDescripcion, String pCaracter, int pTipo){
		
		this.setNumero(pNumero);
		this.setNombre(pNombre);
		this.setDescripcion(pDescripcion);
		this.setCaracter(pCaracter);
		this.setTipoAditivo(pTipo);
	}

	public Aditivo(Parcel in){
		readFromParcel(in);
	}


	/** GET & SET **/
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero.trim();
	}

	public String getCaracter() {
		return caracter;
	}

	public void setCaracter(String caracter) {
		this.caracter = caracter.trim();
	}

	public int getTipoAditivo(){
		return this.tipoAditivo;
	}

	public void setTipoAditivo(int tipo){
		this.tipoAditivo = tipo;
	}


	/** HTTP POST **/

	public int updateAditivoCloud(Activity activity){
		HttpClientManager httpclient = new HttpClientManager(activity);
		httpclient.addNameValue(TAditivos.NOMBRE_COLUMNA_NUMERO, this.getNumero());
		httpclient.addNameValue(TAditivos.NOMBRE_COLUMNA_NOMBRE, this.getNombre());
	    httpclient.addNameValue(TAditivos.NOMBRE_COLUMNA_DESCRIPCION, this.getDescripcion());
	    httpclient.addNameValue(TAditivos.NOMBRE_COLUMNA_CARACTER, this.getCaracter());
	    httpclient.addNameValue(TAditivos.NOMBRE_COLUMNA_ID_TIPO, String.valueOf(this.getTipoAditivo()));

	    String ResponseBody = httpclient.executeHttpPostAsync(S.HTTP_SERVER + S.SERVICE_ADITIVO_UPDATE);
    	try {
    		JSONObject json = new JSONObject(ResponseBody);
    		if (json.getString(S.KEY_SUCCESS) != null) {
    			if((Integer.parseInt(json.getString(S.KEY_SUCCESS)) == 1)){
    				if(json.has("aditivo")){
    					return parseJSONAditivoUpdate(json.getJSONObject("aditivo"));
    				}else{
    					return -1;
    				}
    			}else{return -1;	}
    		}else{return -1;	}

    	}catch(JSONException e){
    		return -1;
    	}
	}

	public Aditivo saveAditivoCloud(Activity activity){
		HttpClientManager httpclient = new HttpClientManager(activity);
		httpclient.addNameValue(TAditivos.NOMBRE_COLUMNA_NUMERO, this.getNumero());
		httpclient.addNameValue(TAditivos.NOMBRE_COLUMNA_NOMBRE, this.getNombre());
	    httpclient.addNameValue(TAditivos.NOMBRE_COLUMNA_DESCRIPCION, this.getDescripcion());
	    httpclient.addNameValue(TAditivos.NOMBRE_COLUMNA_CARACTER, this.getCaracter());
	    httpclient.addNameValue(TAditivos.NOMBRE_COLUMNA_ID_TIPO, String.valueOf(this.getTipoAditivo()));

	    String ResponseBody = httpclient.executeHttpPostAsync(S.HTTP_SERVER + S.SERVICE_ADITIVO_ADD);
    	try {
    		JSONObject json = new JSONObject(ResponseBody);
    		if (json.getString(S.KEY_SUCCESS) != null) {
    			if((Integer.parseInt(json.getString(S.KEY_SUCCESS)) == 1)){
    				if(json.has("aditivo")){
    					return parseJSONAditivo(json.getJSONObject("aditivo"));
    				}else{
    					return null;
    				}
    			}else{return null;	}
    		}else{return null;	}

    	}catch(JSONException e){
    		return null;
    	}
	}

	private Aditivo parseJSONAditivo(JSONObject jsonAditivo) throws JSONException{
		this.setId(jsonAditivo.getInt(TAditivos.NOMBRE_COLUMNA_ID));
		return this;
	}

	private int parseJSONAditivoUpdate(JSONObject jsonAditivo)throws JSONException{
		return jsonAditivo.getInt("numRows");
	}


	/** PARCEL **/

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeInt(getId());
		dest.writeString(getNumero());
		dest.writeString(getNombre());
		dest.writeString(getDescripcion());
		dest.writeString(getCaracter());
		dest.writeInt(getTipoAditivo());
	}

	private void readFromParcel(Parcel in) {
		
		this.setId(in.readInt());
		this.setNumero(in.readString());
		this.setNombre(in.readString());
		this.setDescripcion(in.readString());
		this.setCaracter(in.readString());
		this.setTipoAditivo(in.readInt());
	}

	public static final Parcelable.Creator<Aditivo> CREATOR = new Parcelable.Creator<Aditivo>() {
		public Aditivo createFromParcel(Parcel in){
			return new Aditivo(in);
		}
		
		public Aditivo[] newArray(int size){
			return new Aditivo[size];
		}
	};

}
