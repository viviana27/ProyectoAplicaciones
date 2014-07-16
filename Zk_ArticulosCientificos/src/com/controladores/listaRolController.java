package com.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;

import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.datos.DBRoles;

import com.entidades.Roles;
import com.entidades.Usuarios;

public class listaRolController extends GenericForwardComposer<Component> {
	@Wire
	Toolbarbutton toolbarbutton_Eliminar;
	Toolbarbutton toolbarbutton_Nuevo;
	Toolbarbutton toolbarbutton_Editar;
	private Textbox textbox_buscar;
	private Button button_buscar;
	Listbox listbox_Roles;

	boolean confirmacion = false;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

		Session session = Sessions.getCurrent();
		Usuarios u = (Usuarios) session.getAttribute("User");
		if (u != null) {
			if (u.getId_rol() == 1) {
				actualizarLista();
			} else if (u.getId_rol() == 2 || u.getId_rol() == 3) {
				Executions
						.sendRedirect("http://localhost:8080/Zk_ArticulosCientificos/index.zul");
			}
		}

	}

	public void onClick$button_buscar() {
		actualizarLista();
	}

	public void actualizarLista() {
		DBRoles dbr = new DBRoles();
		List<Roles> lista = dbr.buscarRoles(textbox_buscar.getValue());
		ListModelList<Roles> listModel = new ListModelList<Roles>(lista);
		listbox_Roles.setModel(listModel);
	}

	public void onClick$toolbarbutton_Nuevo() {
		Window win = (Window) Executions.createComponents(
				"Mantenimiento/Roles/registroRol.zul", null, null);
		win.setClosable(true);
		win.doModal();
		win.setAttribute("opcion", "listaRoles");
		win.setAttribute("controladOrigen", this);
	}

	public void onClick$toolbarbutton_Editar() {
		if (listbox_Roles.getSelectedItem() == null) {
			alert("Seleccione por favor un usuario");
			return;
		}
		Window win = (Window) Executions.createComponents(
				"Mantenimiento/Roles/registroRol.zul", null, null);
		win.setClosable(true);
		win.doModal();
		win.setAttribute("opcion", "listaRoles");
		win.setAttribute("controladorOrigen", this);
		Roles rol = (Roles) listbox_Roles.getSelectedItem().getValue();
		win.setAttribute("rol", rol);
	}

	public void onClick$toolbarbutton_Eliminar() {
		boolean result = false;
		if (listbox_Roles.getSelectedItem() == null) {
			alert("Seleccione el rol que desea eliminar");
			return;
		}
		Messagebox.show("Esta seguro de eliminar el rol?", "confirmacion",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event evento) throws Exception {
						if (evento.getName().equals("onOK")) {
							confirmacion = true;
						}
					}
				});

		if (confirmacion) {
			Roles rol = (Roles) listbox_Roles.getSelectedItem().getValue();
			DBRoles roles = new DBRoles();
			result = roles.eliminarRoles(rol);
			if (result != false) {
				alert("rol eliminado correctamente");
			}
		} else {
			alert("Eliminacion Cancelada");
		}
		actualizarLista();

	}

}
