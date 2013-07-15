package com.sovarb.cibus.db;

import org.json.JSONException;
import org.json.JSONObject;

import com.sovarb.cibus.S;
import com.sovarb.cibus.db.GestorDB.TAditivosXAlimento;
import com.sovarb.cibus.http.HttpClientManager;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class AditivosXAlimento extends FItem implements Parcelable {

	public AditivosXAlimento() {	}

	public AditivosXAlimento(int idAlimento, int idAditivo) {	
		this.setIdForeign1(idAlimento);
		this.setIdForeign2(idAditivo);
	}
	
	public AditivosXAlimento(Parcel in){
		readFromParcel(in);
	}
	
	
	/** GETTERS SETTERS **/
	
	public int getIdAlimento(){
		return this.getIdForeign1();
	}
	
	public void setIdAlimento(int id){
		this.setIdForeign1(id);
	}
	
	public int getIdAditivo(){
		return this.getIdForeign2();
	}
	
	public void setIdAditivo(int id){
		this.setIdForeign2(id);
	}


	/** HTTP POST **/

	public boolean saveAditivosXAlimentoCloud(Activity activity){
		HttpClientManager httpclient = new HttpClientManager(activity);
	    httpclient.addNameValue(TAditivosXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO, String.valueOf(this.getIdAlimento()));
	    httpclient.addNameValue(TAditivosXAlimento.NOMBRE_COLUMNA_ID_ADITIVO, String.valueOf(this.getIdAditivo()));

	    String ResponseBody = httpclient.executeHttpPostAsync(S.HTTP_SERVER + S.SERVICE_ADITIVOSXALIMENTO_ADD);
	    
    	try {
    		JSONObject json = new JSONObject(ResponseBody);
    		if (json.getString(S.KEY_SUCCESS) != null) {
    			if((Integer.parseInt(json.getString(S.KEY_SUCCESS)) == 1)){
    				return true;

    			}else{ return false;	}
    		}else{ return false;	}

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
		dest.writeInt(this.getIdAditivo());
	}

	private void readFromParcel(Parcel in) {
		
		this.setIdAlimento(in.readInt());
		this.setIdAditivo(in.readInt());
	}
	
	public static final Parcelable.Creator<AditivosXAlimento> CREATOR = new Parcelable.Creator<AditivosXAlimento>() {
		public AditivosXAlimento createFromParcel(Parcel in){
			return new AditivosXAlimento(in);
		}
		
		public AditivosXAlimento[] newArray(int size){
			return new AditivosXAlimento[size];
		}
	};
	
}
