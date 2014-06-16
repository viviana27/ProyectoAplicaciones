package com.entidades;

public class Articulo {
	private int art_id;
	private String art_titulo;
	private String art_archivo;
	private String art_resumen;
	private String art_palabras_clave;
	private String art_fecha_subida;
	private int art_estado;
	private int tipo_id;
	private int id_area;
	
	public Articulo(int art_id, String art_titulo, String art_archivo,
			String art_resumen, String art_palabras_clave,
			String art_fecha_subida, int art_estado, int tipo_id,
			int area_id_padre, int id_estado) {
		super();
		this.art_id = art_id;
		this.art_titulo = art_titulo;
		this.art_archivo = art_archivo;
		this.art_resumen = art_resumen;
		this.art_palabras_clave = art_palabras_clave;
		this.art_fecha_subida = art_fecha_subida;
		this.art_estado = art_estado;
		this.tipo_id = tipo_id;
		this.area_id_padre = area_id_padre;
		this.id_estado = id_estado;
	}

	public int getId_area() {
		return id_area;
	}

	public void setId_area(int id_area) {
		this.id_area = id_area;
	}

	public Articulo() {
		super();
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

	public String getArt_fecha_subida() {
		return art_fecha_subida;
	}

	public void setArt_fecha_subida(String art_fecha_subida) {
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

	public int getArea_id_padre() {
		return area_id_padre;
	}

	public void setArea_id_padre(int area_id_padre) {
		this.area_id_padre = area_id_padre;
	}

	public int getId_estado() {
		return id_estado;
	}

	public void setId_estado(int id_estado) {
		this.id_estado = id_estado;
	}

	private int area_id_padre;
	private int id_estado;

}
