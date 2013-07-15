package com.sovarb.cibus.db;

import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.GestorDB.TAlimentos;
import com.sovarb.cibus.http.HttpClientManager;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class Alimento extends NItem implements Parcelable {

	private String tipo;
	private String formato;
	private String marca;
	private String submarca;
	private String codigo1D;
	private String codigo2D;
	
	public Alimento(){
		this.setNombre("");
		this.setTipo("");
		this.setDescripcion("");
		this.setFormato("");
		this.setMarca("");
		this.setSubmarca("");
		this.setCodigo1D("");
		this.setCodigo2D("");
	}
	
	public Alimento(int pId, String pNombre, String pTipo, String pDescripcion, 
					String pFormato, String pMarca, String pSubmarca, 
					String pCodigo1D, String pCodigo2D){
		
		this.setId(pId);
		this.setNombre(pNombre);
		this.setTipo(pTipo);
		this.setDescripcion(pDescripcion);
		this.setFormato(pFormato);
		this.setMarca(pMarca);
		this.setSubmarca(pSubmarca);
		this.setCodigo1D(pCodigo1D);
		this.setCodigo2D(pCodigo2D);
	}
	
	public Alimento(String pNombre, String pTipo, String pDescripcion, 
			String pFormato, String pMarca, String pSubmarca, 
			String pCodigo1D, String pCodigo2D){

			this.setNombre(pNombre);
			this.setTipo(pTipo);
			this.setDescripcion(pDescripcion);
			this.setFormato(pFormato);
			this.setMarca(pMarca);
			this.setSubmarca(pSubmarca);
			this.setCodigo1D(pCodigo1D);
			this.setCodigo2D(pCodigo2D);
			}

	public Alimento(Parcel in){
		readFromParcel(in);
	}


	/** GET & SET **/
	
	public String getTipo() {
		return tipo;
	}	
	public void setTipo(String tipo) {
		this.tipo = tipo.trim();
	}

	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato.trim();
	}

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca.trim();
	}

	public String getSubmarca() {
		return submarca;
	}
	public void setSubmarca(String submarca) {
		this.submarca = submarca.trim();
	}

	public String getCodigo1D() {
		return codigo1D;
	}
	public void setCodigo1D(String codigo1d) {
		codigo1D = codigo1d.trim();
	}

	public String getCodigo2D() {
		return codigo2D;
	}
	public void setCodigo2D(String codigo2d) {
		codigo2D = codigo2d.trim();
	}


	/** HTTP POST **/

	public Alimento saveAlimentoCloud(Activity activity){
		HttpClientManager httpclient = new HttpClientManager(activity);
	    httpclient.addNameValue(TAlimentos.NOMBRE_COLUMNA_NOMBRE, this.getNombre());
	    httpclient.addNameValue(TAlimentos.NOMBRE_COLUMNA_TIPO, this.getTipo());	    
	    httpclient.addNameValue(TAlimentos.NOMBRE_COLUMNA_DESCRIPCION, this.getDescripcion());
	    httpclient.addNameValue(TAlimentos.NOMBRE_COLUMNA_FORMATO, this.getFormato());
	    httpclient.addNameValue(TAlimentos.NOMBRE_COLUMNA_MARCA, this.getMarca());
	    httpclient.addNameValue(TAlimentos.NOMBRE_COLUMNA_SUBMARCA, this.getSubmarca());
	    httpclient.addNameValue(TAlimentos.NOMBRE_COLUMNA_CODIGO1D, this.getCodigo1D());
	    httpclient.addNameValue(TAlimentos.NOMBRE_COLUMNA_CODIGO2D, this.getCodigo2D());

	    String ResponseBody = httpclient.executeHttpPostAsync(S.HTTP_SERVER + S.SERVICE_ALIMENTO_ADD);
    	try {
    		JSONObject json = new JSONObject(ResponseBody);
    		if (json.getString(S.KEY_SUCCESS) != null) {
    			if((Integer.parseInt(json.getString(S.KEY_SUCCESS)) == 1)){
    				if(json.has("alimento")){
    					return parseJSONAlimento(json.getJSONObject("alimento"));
    				}else{
    					return null;
    				}
    			}else{return null;	}
    		}else{return null;	}

    	}catch(JSONException e){
    		return null;
    	}
	}

	private Alimento parseJSONAlimento(JSONObject jsonAlimento) throws JSONException{
		this.setId(jsonAlimento.getInt(TAlimentos.NOMBRE_COLUMNA_ID));
		return this;
	}

	
	/** PARCEL **/

	@Override
	public int describeContents(){
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags){
		
		dest.writeInt(getId());
		dest.writeString(getNombre());
		dest.writeString(getDescripcion());
		dest.writeString(getTipo());
		dest.writeString(getFormato());
		dest.writeString(getMarca());
		dest.writeString(getSubmarca());
		dest.writeString(getCodigo1D());
		dest.writeString(getCodigo2D());
	}
	
	private void readFromParcel(Parcel in){
		
		this.setId(in.readInt());
		this.setNombre(in.readString());
		this.setDescripcion(in.readString());
		this.setTipo(in.readString());
		this.setFormato(in.readString());
		this.setMarca(in.readString());
		this.setSubmarca(in.readString());
		this.setCodigo1D(in.readString());
		this.setCodigo2D(in.readString());
	}

	public static final Parcelable.Creator<Alimento> CREATOR = new Parcelable.Creator<Alimento>() {
		public Alimento createFromParcel(Parcel in){
			return new Alimento(in);
		}
		
		public Alimento[] newArray(int size){
			return new Alimento[size];
		}
	};

}
