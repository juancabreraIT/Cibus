package com.sovarb.cibus.adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class Titulos implements Parcelable {
	
	private String titulo;
	private String subtitulo;
	
	public Titulos(String pTitulo, String pSubtitulo){
		titulo = pTitulo;
		subtitulo = pSubtitulo;
	}
	
	public Titulos(Parcel in){
		readFromParcel(in);
	}
	
	
	/** GET & SET **/
	public String getTitulo(){
		return titulo;
	}
	public void setTitulo(String pTitulo){
		this.titulo = pTitulo.trim();
	}
	
	public String getSubtitulo(){
		return subtitulo;
	}
	public void setSubtitulo(String pSubtitulo){
		this.subtitulo = pSubtitulo.trim();
	}


	/** PARCELABLE **/
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(getTitulo());
		dest.writeString(getSubtitulo());
	}
	
	private void readFromParcel(Parcel in){
		
		this.setTitulo(in.readString());
		this.setSubtitulo(in.readString());
	}

	public static final Parcelable.Creator<Titulos> CREATOR = new Parcelable.Creator<Titulos>() {
		public Titulos createFromParcel(Parcel in){
			return new Titulos(in);
		}
		
		public Titulos[] newArray(int size){
			return new Titulos[size];
		}
	};
}
