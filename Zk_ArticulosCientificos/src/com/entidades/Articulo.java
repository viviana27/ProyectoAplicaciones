package com.entidades;

import java.util.Date;

public class Articulo {
	private int art_id;
	private String art_titulo;
	private String art_archivo;
	private String art_resumen;
	private String art_palabras_clave;
	private Date art_fecha_subida;
	private int art_estado;
	private String nom_colaborador;
	private String nom_colaborador1;
	private int tipo_id;
	private String tipo_nombre;
	private int id_area;
	private String area_nombre;
	private int per_id;
	private String per_nombre;
	private String per_apellido;
	private int id_estado;
	private String per_institucion1;
	private String per_institucion2;

	public Articulo() {
		super();
	}

	public Articulo(int art_id, String art_titulo, String art_archivo,
			String art_resumen, String art_palabras_clave,
			Date art_fecha_subida, int art_estado, int tipo_id, int id_area,
			int per_id, int id_estado) {
		super();
		this.art_id = art_id;
		this.art_titulo = art_titulo;
		this.art_archivo = art_archivo;
		this.art_resumen = art_resumen;
		this.art_palabras_clave = art_palabras_clave;
		this.art_fecha_subida = art_fecha_subida;
		this.art_estado = art_estado;
		this.tipo_id = tipo_id;
		this.id_area = id_area;
		this.per_id = per_id;
		this.id_estado = id_estado;
	}

	
	
	public String getNom_colaborador1() {
		return nom_colaborador1;
	}

	public void setNom_colaborador1(String nom_colaborador1) {
		this.nom_colaborador1 = nom_colaborador1;
	}

	public String getPer_institucion1() {
		return per_institucion1;
	}

	public void setPer_institucion1(String per_institucion1) {
		this.per_institucion1 = per_institucion1;
	}

	public String getPer_institucion2() {
		return per_institucion2;
	}

	public void setPer_institucion2(String per_institucion2) {
		this.per_institucion2 = per_institucion2;
	}

	public int getArt_id() {
		return art_id;
	}

	public void setArt_id(int art_id) {
		this.art_id = art_id;
	}

	public String getArt_titulo() {
		return art_titulo;
	}

	public void setArt_titulo(String art_titulo) {
		this.art_titulo = art_titulo;
	}

	public String getArt_archivo() {
		return art_archivo;
	}

	public void setArt_archivo(String art_archivo) {
		this.art_archivo = art_archivo;
	}

	public String getArt_resumen() {
		return art_resumen;
	}

	public void setArt_resumen(String art_resumen) {
		this.art_resumen = art_resumen;
	}

	public String getArt_palabras_clave() {
		return art_palabras_clave;
	}

	public void setArt_palabras_clave(String art_palabras_clave) {
		this.art_palabras_clave = art_palabras_clave;
	}

	public Date getArt_fecha_subida() {
		return art_fecha_subida;
	}

	public void setArt_fecha_subida(Date art_fecha_subida) {
		this.art_fecha_subida = art_fecha_subida;
	}

	public int getArt_estado() {
		return art_estado;
	}

	public void setArt_estado(int art_estado) {
		this.art_estado = art_estado;
	}

	public int getTipo_id() {
		return tipo_id;
	}

	public void setTipo_id(int tipo_id) {
		this.tipo_id = tipo_id;
	}

	public int getId_area() {
		return id_area;
	}

	public void setId_area(int id_area) {
		this.id_area = id_area;
	}

	public int getPer_id() {
		return per_id;
	}

	public void setPer_id(int per_id) {
		this.per_id = per_id;
	}

	public int getId_estado() {
		return id_estado;
	}

	public void setId_estado(int id_estado) {
		this.id_estado = id_estado;
	}

	public String getTipo_nombre() {
		return tipo_nombre;
	}

	public void setTipo_nombre(String tipo_nombre) {
		this.tipo_nombre = tipo_nombre;
	}

	public String getArea_nombre() {
		return area_nombre;
	}

	public void setArea_nombre(String area_nombre) {
		this.area_nombre = area_nombre;
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

	public String getNom_colaborador() {
		return nom_colaborador;
	}

	public void setNom_colaborador(String nom_colaborador) {
		this.nom_colaborador = nom_colaborador;
	}


	
	
	

}
