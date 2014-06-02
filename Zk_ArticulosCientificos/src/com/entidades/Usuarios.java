package com.entidades;



public class Usuarios {

	private int id;
	private String usuario;
	private Persona persona;
	private String clave;
	private int estado;
	private int id_rol;
	private String rol_descripcion;

	//constructor por defectos
	public Usuarios(){}
	
	//Constructor por parametros
	

	public Usuarios(int id, String usuario,String clave, int estado,Persona persona, int id_rol) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.clave = clave;
		this.estado= estado;
		this.persona= persona;
		this.id_rol= id_rol;
		
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getId_rol() {
		return id_rol;
	}

	public void setId_rol(int id_rol) {
		this.id_rol = id_rol;
	}

	public String getRol_descripcion() {
		return rol_descripcion;
	}

	public void setRol_descripcion(String rol_descripcion) {
		this.rol_descripcion = rol_descripcion;
	}
	
	
	
	
}
