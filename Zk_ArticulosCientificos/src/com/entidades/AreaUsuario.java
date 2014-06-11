package com.entidades;

public class AreaUsuario {

	private int id;
	private String usuario;
	private PersonaArea personaarea;
	private String clave;
	private int estado;
	private int id_rol;
	private String rol_descripcion;
	private int id_area;
	private String area_nombre;

	//constructor por defectos
	public AreaUsuario(){}
	
	//Constructor por parametros
	

	public AreaUsuario(int id, String usuario, PersonaArea personaarea, int id_area) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.personaarea= personaarea;
		this.id_area=id_area;
		
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
	

	public PersonaArea getPersonaarea() {
		return personaarea;
	}

	public void setPersonaarea(PersonaArea personaarea) {
		this.personaarea = personaarea;
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

	public int getId_area() {
		return id_area;
	}

	public void setId_area(int id_area) {
		this.id_area = id_area;
	}

	public String getArea_nombre() {
		return area_nombre;
	}

	public void setArea_nombre(String area_nombre) {
		this.area_nombre = area_nombre;
	}
	
	
	
	
}
