package com.entidades;


public class Persona {
	//atributos
	private int per_id;
	private String per_nombre;
	private String per_apellido;
	private String per_cedula;
	private String per_email;
	private String per_direccion;
	private String per_telefono;
	private String per_celular;
	private String per_institucion_pertenece;
	private String per_direccion_institucion;
	private int per_estado;
	//constructor por defecto
		public Persona(){}
		//constructor con parametros
		public Persona(int per_id, String per_nombre, String per_apellido, String per_cedula,String per_email,
				String per_direccion , String per_telefono, String per_celular, String per_institucion_pertenece, 
				String per_direccion_institucion) {
			super();
			this.per_id = per_id;
			this.per_nombre = per_nombre;
			this.per_apellido = per_apellido;
			this.per_cedula = per_cedula;
			this.per_email = per_email;
			this.per_direccion = per_direccion;
			this.per_telefono = per_telefono;
			this.per_celular=  per_celular;
			this.per_institucion_pertenece = per_institucion_pertenece;
			this.per_direccion_institucion= per_direccion_institucion;
		}
		//metodos 
		public int getPer_id() {
			return per_id;
		}
		public void setPer_id(int per_id) {
			this.per_id = per_id;
		}
		public String getPer_nombre() {
			return per_nombre;
		}
		public void setPer_nombre(String per_nombre) {
			this.per_nombre = per_nombre;
		}
		public String getPer_apellido() {
			return per_apellido;
		}
		public void setPer_apellido(String per_apellido) {
			this.per_apellido = per_apellido;
		}
		public String getPer_cedula() {
			return per_cedula;
		}
		public void setPer_cedula(String per_cedula) {
			this.per_cedula = per_cedula;
		}
		public String getPer_email() {
			return per_email;
		}
		public void setPer_email(String per_email) {
			this.per_email = per_email;
		}
		public String getPer_direccion() {
			return per_direccion;
		}
		public void setPer_direccion(String per_direccion) {
			this.per_direccion = per_direccion;
		}
		public String getPer_telefono() {
			return per_telefono;
		}
		public void setPer_telefono(String per_telefono) {
			this.per_telefono = per_telefono;
		}
		public String getPer_celular() {
			return per_celular;
		}
		public void setPer_celular(String per_celular) {
			this.per_celular = per_celular;
		}
		public String getPer_institucion_pertenece() {
			return per_institucion_pertenece;
		}
		public void setPer_institucion_pertenece(String per_institucion_pertenece) {
			this.per_institucion_pertenece = per_institucion_pertenece;
		}
		public String getPer_direccion_institucion() {
			return per_direccion_institucion;
		}
		public void setPer_direccion_institucion(String per_direccion_institucion) {
			this.per_direccion_institucion = per_direccion_institucion;
		}
		public int getPer_estado() {
			return per_estado;
		}
		public void setPer_estado(int per_estado) {
			this.per_estado = per_estado;
		}
		
		
		
	}


