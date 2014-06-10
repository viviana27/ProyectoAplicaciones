package com.entidades;

public class ParametrosEvaluacion {
	private int param_id;
	private String param_descripcion;
	private int param_valor;
	private int param_estado;
	private String param_estadoString;
	
	public ParametrosEvaluacion() {}
	public ParametrosEvaluacion(int param_id, String param_descripcion,
			int param_valor, int param_estado) {
		super();
		this.param_id = param_id;
		this.param_descripcion = param_descripcion;
		this.param_valor = param_valor;
		this.param_estado = param_estado;
	}
	public int getParam_id() {
		return param_id;
	}
	public void setParam_id(int param_id) {
		this.param_id = param_id;
	}
	public String getParam_descripcion() {
		return param_descripcion;
	}
	public void setParam_descripcion(String param_descripcion) {
		this.param_descripcion = param_descripcion;
	}
		public String getParam_estadoString() {
		return param_estadoString;
	}
	public void setParam_estadoString(String param_estadoString) {
		this.param_estadoString = param_estadoString;
	}
	public int getParam_valor() {
		return param_valor;
	}
	public void setParam_valor(int param_valor) {
		this.param_valor = param_valor;
	}
	public int getParam_estado() {
		return param_estado;
	}
	public void setParam_estado(int param_estado) {
		this.param_estado = param_estado;
	}
	
}
