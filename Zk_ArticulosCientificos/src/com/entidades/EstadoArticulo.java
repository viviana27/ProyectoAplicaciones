package com.entidades;

import java.util.Date;

public class EstadoArticulo {
private int id;
private int id_articulo;
private int id_estado;
private int id_persona;
private Date fecha;
private int id_utl_estado;
public EstadoArticulo() {
	super();
}
public EstadoArticulo(int id, int id_articulo, int id_estado,int id_utl_estado, int id_persona,
		Date fecha) {
	super();
	this.id = id;
	this.id_articulo = id_articulo;
	this.id_estado = id_estado;
	this.id_utl_estado=id_utl_estado;
	this.id_persona = id_persona;
	this.fecha = fecha;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getId_articulo() {
	return id_articulo;
}
public void setId_articulo(int id_articulo) {
	this.id_articulo = id_articulo;
}
public int getId_estado() {
	return id_estado;
}
public void setId_estado(int id_estado) {
	this.id_estado = id_estado;
}
public int getId_utl_estado() {
	return id_utl_estado;
}
public void setId_utl_estado(int id_utl_estado) {
	this.id_utl_estado = id_utl_estado;
}
public int getId_persona() {
	return id_persona;
}
public void setId_persona(int id_persona) {
	this.id_persona = id_persona;
}
public Date getFecha() {
	return fecha;
}
public void setFecha(Date fecha) {
	this.fecha = fecha;
}

}
