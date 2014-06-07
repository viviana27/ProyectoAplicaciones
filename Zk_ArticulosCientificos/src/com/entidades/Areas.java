package com.entidades;

public class Areas {
	private int area_id;
	private String area_nombre;
	private String area_descripcion;
	private int area_estado;
	
	private String estadostring;
	
	public Areas(int area_id, String area_nombre, String area_descripcion,
			int area_estado) {
		super();
		this.area_id = area_id;
		this.area_nombre = area_nombre;
		this.area_descripcion = area_descripcion;
		this.area_estado = area_estado;
	}


	public Areas() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getEstadostring() {
		return estadostring;
	}


	public void setEstadostring(String estadostring) {
		this.estadostring = estadostring;
	}


	public int getArea_id() {
		return area_id;
	}


	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}


	public String getArea_nombre() {
		return area_nombre;
	}


	public void setArea_nombre(String area_nombre) {
		this.area_nombre = area_nombre;
	}


	public String getArea_descripcion() {
		return area_descripcion;
	}


	public void setArea_descripcion(String area_descripcion) {
		this.area_descripcion = area_descripcion;
	}


	public int getArea_estado() {
		return area_estado;
	}


	public void setArea_estado(int area_estado) {
		this.area_estado = area_estado;
	}
	
	
	
}
