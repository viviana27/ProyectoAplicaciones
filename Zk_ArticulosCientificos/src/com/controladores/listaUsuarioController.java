package com.controladores;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;


public class listaUsuarioController extends GenericForwardComposer<Component> {
	@Wire
	Toolbarbutton toolbarbutton_Eliminar;
	Toolbarbutton toolbarbutton_Nuevo;
	Toolbarbutton toolbarbutton_Editar;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);	
	}

	public void onSelect$listbox_Categorias() {
		
	}

	public void onClick$toolbarbutton_Nuevo() {
		Window win = (Window) Executions.createComponents(
				"VisualizarPerfil.zul", null, null);
		win.setClosable(true);
		win.doModal();
	}

	public void onClick$toolbarbutton_Editar() {
		Window win = (Window) Executions.createComponents(
				"VisualizarPerfil.zul", null, null);
		win.setClosable(true);
		win.doModal();
	}

	public void onClick$toolbarbutton_Eliminar() {
	
	}

	
}
