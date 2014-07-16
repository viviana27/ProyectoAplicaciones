package com.entidades;

public class Estados {
	private int id_estado;
	private String estado_descripcion;
	
	public Estados() {
		super();
	}
	public Estados(int id_estado, String estado_descripcion) {
		super();
		this.id_estado = id_estado;
		this.estado_descripcion = estado_descripcion;
	}
	public int getId_estado() {
		return id_estado;
	}
	public void setId_estado(int id_estado) {
		this.id_estado = id_estado;
	}
	public String getEstado_descripcion() {
		return estado_descripcion;
	}
	public void setEstado_descripcion(String estado_descripcion) {
		this.estado_descripcion = estado_descripcion;
	}
	
}
