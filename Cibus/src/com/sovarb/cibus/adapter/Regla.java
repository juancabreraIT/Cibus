package com.sovarb.cibus.adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class Regla implements Parcelable {
	
	private String regla;
	
	public Regla(String pRegla){
		regla = pRegla;
	}
	
	public Regla(Parcel in){
		readFromParcel(in);
	}
	
	public String getRegla(){
		return regla;
	}

	public void setRegla(String pRegla){
		this.regla = pRegla.trim();
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(getRegla());
	}
	
	private void readFromParcel(Parcel in){
		
		this.setRegla(in.readString());
	}
	
	public static final Parcelable.Creator<Regla> CREATOR = new Parcelable.Creator<Regla>() {
		public Regla createFromParcel(Parcel in){
			return new Regla(in);
		}
		
		public Regla[] newArray(int size){
			return new Regla[size];
		}
	};
}
