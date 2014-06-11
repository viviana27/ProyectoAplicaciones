package com.entidades;

public class UsuarioArea {
	private int per_area_id;
	private int area_id;
	private String area_nombre;
	private PersonaArea personaarea;
    private int per_area_estado;
    private int usuario_id;
    private String usuario_nombre;
    
    
    public UsuarioArea(){
    	
    }
  
	
	public UsuarioArea(int per_area_id, int area_id, PersonaArea personaarea,
			int per_area_estado) {
		super();
		this.per_area_id = per_area_id;
		this.area_id = area_id;
		this.personaarea = personaarea;
		this.per_area_estado = per_area_estado;
	}


	public int getPer_area_id() {
		return per_area_id;
	}
	public void setPer_area_id(int per_area_id) {
		this.per_area_id = per_area_id;
	}
	
	public int getArea_id() {
		return area_id;
	}

	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}

	public String getArea_nombre() {
		return area_nombre;
	}
	public void setArea_nombre(String area_nombre) {
		this.area_nombre = area_nombre;
	}

	public int getPer_area_estado() {
		return per_area_estado;
	}

	public void setPer_area_estado(int per_area_estado) {
		this.per_area_estado = per_area_estado;
	}

	public String getUsuario_nombre() {
		return usuario_nombre;
	}

	public void setUsuario_nombre(String usuario_nombre) {
		this.usuario_nombre = usuario_nombre;
	}

	public int getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}

	public PersonaArea getPersonaarea() {
		return personaarea;
	}

	public void setPersonaarea(PersonaArea personaarea) {
		this.personaarea = personaarea;
	}

	
    
    
    
	
}
