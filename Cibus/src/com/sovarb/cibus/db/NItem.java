package com.sovarb.cibus.db;

public abstract class NItem extends Item{

	private String nombre;
	private String descripcion;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.trim();
	}

	
	public String getDescripcion() {
		return descripcion;
	}

	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.trim();
	}
		
}
