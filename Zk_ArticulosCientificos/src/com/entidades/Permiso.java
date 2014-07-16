package com.entidades;

public class Permiso {
	private int id_permiso;
	private int id_formulario ;
	private String nombreFormulario;
	private int id_rol;
	private int permiso;
	private String permisoEstado;
	public Permiso(int id_permiso, int id_formulario, String nombreFormulario,
			int id_rol, int permiso, String permisoEstado) {
		super();
		this.id_permiso = id_permiso;
		this.id_formulario = id_formulario;
		this.nombreFormulario = nombreFormulario;
		this.id_rol = id_rol;
		this.permiso = permiso;
		this.permisoEstado = permisoEstado;
	}
public Permiso(){}
public int getId_permiso() {
	return id_permiso;
}
public void setId_permiso(int id_permiso) {
	this.id_permiso = id_permiso;
}
public int getId_formulario() {
	return id_formulario;
}
public void setId_formulario(int id_formulario) {
	this.id_formulario = id_formulario;
}
public String getNombreFormulario() {
	return nombreFormulario;
}
public void setNombreFormulario(String nombreFormulario) {
	this.nombreFormulario = nombreFormulario;
}
public int getId_rol() {
	return id_rol;
}
public void setId_rol(int id_rol) {
	this.id_rol = id_rol;
}
public int getPermiso() {
	return permiso;
}
public void setPermiso(int permiso) {
	this.permiso = permiso;
}
public String getPermisoEstado() {
	return permisoEstado;
}
public void setPermisoEstado(String permisoEstado) {
	this.permisoEstado = permisoEstado;
};

}
