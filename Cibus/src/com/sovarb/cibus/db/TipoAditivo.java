package com.sovarb.cibus.db;

public class TipoAditivo extends Item {
	
	private String nombre;
	private String rango;
	
	public TipoAditivo(){}
	
	public TipoAditivo(int pId, String pNombre, String pRango){
		
		this.setId(pId);
		this.setNombre(pNombre);
		this.setRango(pRango);
	}
	
	// #########
	// GET & SET
	// #########
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre.trim();
	}
	
	public String getRango() {
		return rango;
	}
	
	public void setRango(String rango) {
		this.rango = rango.trim();
	}
	
}
