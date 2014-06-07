package com.entidades;

public class Roles {
	private int rol_id;
	private String rol_descripcion;
	private int rol_estado;
	private String rolestado;
	
	
	public Roles(){}
	
	public Roles(int rol_id, String rol_descripcion, int rol_estado) {
		super();
		this.rol_id = rol_id;
		this.rol_descripcion = rol_descripcion;
		this.rol_estado = rol_estado;
	}
	public int getRol_id() {
		return rol_id;
	}
	public void setRol_id(int rol_id) {
		this.rol_id = rol_id;
	}
	public String getRol_descripcion() {
		return rol_descripcion;
	}
	public void setRol_descripcion(String rol_descripcion) {
		this.rol_descripcion = rol_descripcion;
	}
	public int getRol_estado() {
		return rol_estado;
	}
	public void setRol_estado(int rol_estado) {
		this.rol_estado = rol_estado;
	}

	public String getRolestado() {
		return rolestado;
	}

	public void setRolestado(String rolestado) {
		this.rolestado = rolestado;
	}
	
	
	
	
}
