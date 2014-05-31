package com.entidades;



public class Usuarios {

	private int id;
	private String usuario;
	private Persona persona;
	private String clave;
	private int estado;

	//constructor por defectos
	public Usuarios(){}
	
	//Constructor por parametros
	

	public Usuarios(int id, String usuario, Persona persona) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.persona= persona;
		
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
	
	
	
	
}
