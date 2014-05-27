package com.entidades;


public class Usuarios {

	private int id;
	private String usuario;

	private int clave;
	//constructor por defectos
	public Usuarios(){}
	
	//Constructor por parametros
	

	public int getId() {
		return id;
	}

	public Usuarios(int id, String usuario, int clave) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.clave = clave;
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

	public int getClave() {
		return clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}
	
	
	
}
