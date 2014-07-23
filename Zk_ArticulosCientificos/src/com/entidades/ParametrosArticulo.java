package com.entidades;

public class ParametrosArticulo {
	private int param_art_id;
	private int param_art_valor;
	private int param_id;
	private int person_id;
	private int articul_id;

	public ParametrosArticulo() {
		super();
	}

	public ParametrosArticulo(int param_art_id, int param_art_valor,
			int param_id, int person_id, int articul_id) {
		super();
		this.param_art_id = param_art_id;
		this.param_art_valor = param_art_valor;
		this.param_id = param_id;
		this.person_id = person_id;
		this.articul_id = articul_id;
	}

	public int getParam_art_id() {
		return param_art_id;
	}

	public void setParam_art_id(int param_art_id) {
		this.param_art_id = param_art_id;
	}

	public int getParam_art_valor() {
		return param_art_valor;
	}

	public void setParam_art_valor(int param_art_valor) {
		this.param_art_valor = param_art_valor;
	}

	public int getParam_id() {
		return param_id;
	}

	public void setParam_id(int param_id) {
		this.param_id = param_id;
	}

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public int getArticul_id() {
		return articul_id;
	}

	public void setArticul_id(int articul_id) {
		this.articul_id = articul_id;
	}

}
