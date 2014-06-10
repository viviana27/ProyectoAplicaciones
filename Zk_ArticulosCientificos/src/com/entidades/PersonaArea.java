package com.entidades;

public class PersonaArea {
	private int per_id;
	private String per_nombre;
	private String per_apellido;
    private int estado;
    private int perso_id;
	
	public PersonaArea(){
		
	}
	
	public PersonaArea(int per_id, String per_nombre, String per_apellido) {
		super();
		this.per_id = per_id;
		this.per_nombre = per_nombre;
		this.per_apellido = per_apellido;
	}
	public int getPer_id() {
		return per_id;
	}
	public void setPer_id(int per_id) {
		this.per_id = per_id;
	}
	public String getPer_nombre() {
		return per_nombre;
	}
	public void setPer_nombre(String per_nombre) {
		this.per_nombre = per_nombre;
	}
	public String getPer_apellido() {
		return per_apellido;
	}
	public void setPer_apellido(String per_apellido) {
		this.per_apellido = per_apellido;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getPerso_id() {
		return perso_id;
	}

	public void setPerso_id(int perso_id) {
		this.perso_id = perso_id;
	}
	
	
}
