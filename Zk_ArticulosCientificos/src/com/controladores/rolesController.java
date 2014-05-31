package com.controladores;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;

public class rolesController extends GenericForwardComposer<Component>{
	@Wire
	Combobox combo_rol;
	Label lblDescripcion;
	Button btn_Reservar;
	Session s;
	public boolean  banderaReservacion=false;	

		
		}

