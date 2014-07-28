package com.entidades;

public class ObservacionesEvaluadores {
private String eval_observacion;
private String nombre;
private String direccion;

public ObservacionesEvaluadores(){}
public ObservacionesEvaluadores(String eval_observacion, String nombre,
		String direccion) {
	super();
	this.eval_observacion = eval_observacion;
	this.nombre = nombre;
	this.direccion = direccion;
}
public String getEval_observacion() {
	return eval_observacion;
}
public void setEval_observacion(String eval_observacion) {
	this.eval_observacion = eval_observacion;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getDireccion() {
	return direccion;
}
public void setDireccion(String direccion) {
	this.direccion = direccion;
}

}
