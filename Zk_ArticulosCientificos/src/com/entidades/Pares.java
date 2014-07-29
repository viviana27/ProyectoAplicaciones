package com.entidades;

public class Pares {
	private int par_id;
	private int articulos_id;
	private int personas_id;
	private String per_nombre;
	private String per_apellido;
	private int estado_id;
	private int cant_par;
	private int par_estado;
	private String per_email;
	public Pares(int par_id, int articulos_id, int personas_id,
			int estado_id, int cant_par, int par_estado) {
		super();
		this.par_id = par_id;
		this.articulos_id = articulos_id;
		this.personas_id = personas_id;
		this.estado_id = estado_id;
		this.cant_par = cant_par;
		this.par_estado = par_estado;
	}
	public Pares() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getPar_id() {
		return par_id;
	}
	public void setPar_id(int par_id) {
		this.par_id = par_id;
	}

	
	
	public String getPer_email() {
		return per_email;
	}
	public void setPer_email(String per_email) {
		this.per_email = per_email;
	}
	public int getArticulos_id() {
		return articulos_id;
	}
	public void setArticulos_id(int articulos_id) {
		this.articulos_id = articulos_id;
	}
	public int getPersonas_id() {
		return personas_id;
	}
	public void setPersonas_id(int personas_id) {
		this.personas_id = personas_id;
	}
	public String getPer_nombre() {
		return per_nombre;
	}
	public void setPer_nombre(String per_nombre) {
		this.per_nombre = per_nombre;
	}
	public int getEstado_id() {
		return estado_id;
	}
	public void setEstado_id(int estado_id) {
		this.estado_id = estado_id;
	}
	public int getCant_par() {
		return cant_par;
	}
	
	public String getPer_apellido() {
		return per_apellido;
	}
	public void setPer_apellido(String per_apellido) {
		this.per_apellido = per_apellido;
	}
	public void setCant_par(int cant_par) {
		this.cant_par = cant_par;
	}
	public int getPar_estado() {
		return par_estado;
	}
	public void setPar_estado(int par_estado) {
		this.par_estado = par_estado;
	}
	

}
