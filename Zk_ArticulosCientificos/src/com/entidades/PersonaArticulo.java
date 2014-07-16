package com.entidades;

public class PersonaArticulo {
	private int per_art_id;
	private int pers_id;
	private String per_nombre;
	private String per_apellido;
	private int arti_id;
	private String art_titulo;
	private int per_art_estado;
	private int per_id_registra;

	
	
	public PersonaArticulo() {
		super();
	}
	

	public PersonaArticulo(int per_art_id, int pers_id, int arti_id,
			int per_art_estado, int per_id_registra) {
		super();
		this.per_art_id = per_art_id;
		this.pers_id = pers_id;
		this.arti_id = arti_id;
		this.per_art_estado = per_art_estado;
		this.per_id_registra = per_id_registra;
	}

	public int getPer_art_id() {
		return per_art_id;
	}

	public void setPer_art_id(int per_art_id) {
		this.per_art_id = per_art_id;
	}

	public int getPers_id() {
		return pers_id;
	}

	public void setPers_id(int pers_id) {
		this.pers_id = pers_id;
	}

	public int getArti_id() {
		return arti_id;
	}

	public void setArti_id(int arti_id) {
		this.arti_id = arti_id;
	}

	public int getPer_art_estado() {
		return per_art_estado;
	}

	public void setPer_art_estado(int per_art_estado) {
		this.per_art_estado = per_art_estado;
	}

	public int getPer_id_registra() {
		return per_id_registra;
	}

	public void setPer_id_registra(int per_id_registra) {
		this.per_id_registra = per_id_registra;
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


	public String getArt_titulo() {
		return art_titulo;
	}


	public void setArt_titulo(String art_titulo) {
		this.art_titulo = art_titulo;
	}
	
	

}