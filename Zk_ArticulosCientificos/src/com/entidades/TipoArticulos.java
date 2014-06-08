package com.entidades;

public class TipoArticulos {
	private int tipo_id;
	private String tipo_nombre;
	private String tipo_descripcion;
	private int tipo_estado;
	private String tipoestado;
	
	
	public TipoArticulos(){}
	
	public TipoArticulos(int tipo_id, String tipo_nombre,String tipo_descripcion, int tipo_estado) {
		super();
		this.tipo_id = tipo_id;
		this.tipo_nombre = tipo_nombre;
		this.tipo_descripcion = tipo_descripcion;
		this.tipo_estado = tipo_estado;
	}

	public int getTipo_id() {
		return tipo_id;
	}

	public void setTipo_id(int tipo_id) {
		this.tipo_id = tipo_id;
	}

	public String getTipo_nombre() {
		return tipo_nombre;
	}

	public void setTipo_nombre(String tipo_nombre) {
		this.tipo_nombre = tipo_nombre;
	}

	public String getTipo_descripcion() {
		return tipo_descripcion;
	}

	public void setTipo_descripcion(String tipo_descripcion) {
		this.tipo_descripcion = tipo_descripcion;
	}

	public int getTipo_estado() {
		return tipo_estado;
	}

	public void setTipo_estado(int tipo_estado) {
		this.tipo_estado = tipo_estado;
	}

	public String getTipoestado() {
		return tipoestado;
	}

	public void setTipoestado(String tipoestado) {
		this.tipoestado = tipoestado;
	}
	
	
}
