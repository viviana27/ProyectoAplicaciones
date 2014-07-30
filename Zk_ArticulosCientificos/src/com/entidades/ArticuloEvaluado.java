package com.entidades;

import java.sql.Date;


public class ArticuloEvaluado {
	private int eval_id;
	private Date eval_fecha;
	private double eval_promedio;
	private int eval_cantidad;
	private int estad_id;
	private int eval_estado;
	private int ar_id;
	private String Nombre;
	private String Direccion;
	
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	private String eval_observacion;
	public int getEval_id() {
		return eval_id;
	}
	public void setEval_id(int eval_id) {
		this.eval_id = eval_id;
	}
	
	public Date getEval_fecha() {
		return eval_fecha;
	}
	public void setEval_fecha(Date eval_fecha) {
		this.eval_fecha = eval_fecha;
	}
	public double getEval_promedio() {
		return eval_promedio;
	}
	public void setEval_promedio(double eval_promedio) {
		this.eval_promedio = eval_promedio;
	}
	public int getEval_cantidad() {
		return eval_cantidad;
	}
	public void setEval_cantidad(int eval_cantidad) {
		this.eval_cantidad = eval_cantidad;
	}
	
	public int getEstad_id() {
		return estad_id;
	}
	public void setEstad_id(int estad_id) {
		this.estad_id = estad_id;
	}
	public int getEval_estado() {
		return eval_estado;
	}
	public void setEval_estado(int eval_estado) {
		this.eval_estado = eval_estado;
	}
	public int getAr_id() {
		return ar_id;
	}
	public void setAr_id(int ar_id) {
		this.ar_id = ar_id;
	}

	public String getEval_observacion() {
		return eval_observacion;
	}
	public void setEval_observacion(String eval_observacion) {
		this.eval_observacion = eval_observacion;
	}
	public ArticuloEvaluado() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*public ArticuloEvaluado(int eval_id, double eval_promedio,
			int eval_cantidad, int vol_id, int estad_id, int eval_estado,
			int ar_id, String eval_observacion, Date eval_fecha) {
		super();
		this.eval_id = eval_id;
		this.eval_fecha = eval_fecha;
		this.eval_promedio = eval_promedio;
		this.eval_cantidad = eval_cantidad;
		this.vol_id = vol_id;
		this.estad_id = estad_id;
		this.eval_estado = eval_estado;
		this.ar_id = ar_id;
		this.eval_observacion = eval_observacion;
	}*/
	public ArticuloEvaluado(int eval_id, Date eval_fecha, double eval_promedio,
			int eval_cantidad, int estad_id, int eval_estado,
			int ar_id, String nombre, String direccion, String eval_observacion) {
		super();
		this.eval_id = eval_id;
		this.eval_fecha = eval_fecha;
		this.eval_promedio = eval_promedio;
		this.eval_cantidad = eval_cantidad;
		this.estad_id = estad_id;
		this.eval_estado = eval_estado;
		this.ar_id = ar_id;
		Nombre = nombre;
		Direccion = direccion;
		this.eval_observacion = eval_observacion;
	}
	
	
	

}
