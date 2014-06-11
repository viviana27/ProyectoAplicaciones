package com.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.datos.DBRoles;
import com.entidades.Roles;

public class listaRolUsuarioController extends
		GenericForwardComposer<Component> {
	@Wire
	Toolbarbutton toolbarbutton_Editar;
	private Textbox textbox_buscar;
	private Button button_buscar;
	Listbox listbox_roles_usuarios;
	boolean confirmacion = false;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		actualizarLista();
	}


	public void actualizarLista() {
		DBRoles dbr = new DBRoles();
		List<Roles> lista = dbr.buscarRoles();
		// establecer esta lista como modelo de dalos pasra el listbox
		ListModelList<Roles> listModel = new ListModelList<Roles>(lista);
		// establecer el modelo de datos
		listbox_roles_usuarios.setModel(listModel);

	}
}