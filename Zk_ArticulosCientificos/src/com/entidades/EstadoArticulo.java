package com.entidades;

import java.util.Date;

public class EstadoArticulo {
private int id;
private int id_articulo;
private int id_estado;
private int id_persona;
private double calif;
private String fecha;
private String info_adicional;
private int id_utl_estado;
private Estados estados;
private Persona persona;
private Articulo articulo;

public String getInfo_adicional() {
	return info_adicional;
}
public void setInfo_adicional(String info_adicional) {
	this.info_adicional = info_adicional;
}
public double getCalif() {
	return calif;
}
public void setCalif(double calif) {
	this.calif = calif;
}
public EstadoArticulo() {
	super();
}
public EstadoArticulo(int id, int id_articulo, int id_estado,int id_utl_estado, int id_persona,
		String fecha) {
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
public String getFecha() {
	return fecha;
}
public void setFecha(String fecha) {
	this.fecha = fecha;
}
public Estados getEstados() {
	return estados;
}
public void setEstados(Estados estados) {
	this.estados = estados;
}
public Persona getPersona() {
	return persona;
}
public void setPersona(Persona persona) {
	this.persona = persona;
}
public Articulo getArticulo() {
	return articulo;
}
public void setArticulo(Articulo articulo) {
	this.articulo = articulo;
}



}
