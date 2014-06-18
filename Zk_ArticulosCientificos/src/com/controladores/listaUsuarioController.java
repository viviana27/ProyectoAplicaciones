package com.controladores;

import com.entidades.*;
import com.datos.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.idom.Text;

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

public class listaUsuarioController extends GenericForwardComposer<Component> {
	@Wire
	Toolbarbutton toolbarbutton_Eliminar;
	Toolbarbutton toolbarbutton_Nuevo;
	Toolbarbutton toolbarbutton_Editar;
	Toolbarbutton toolbarbutton_EditarUR;
	Textbox textbox_buscar;
	Button button_buscarr;
	Button button_buscarUR;
	Listbox listbox_Miembros;
	Listbox listbox_MiembrosRoles;
	boolean confirmacion = false;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		// actualizarLista();
		Session session = Sessions.getCurrent();
		Usuarios us = (Usuarios) session.getAttribute("User");
		if (us != null) {
			if (us.getId_rol() == 1) {
				// actualizarLista();
			} else {
				if (us.getId_rol() == 2 || us.getId_rol() == 3) {
					Executions
							.sendRedirect("http://localhost:8080/Zk_ArticulosCientificos/index.zul");
				}
			}
		}
	}

	public void onClick$toolbarbutton_Nuevo() {
		// crear ventana de nuevo usuario
		Window win = (Window) Executions.createComponents(
				"Usuarios/nuevoUsuario.zul", null, null);
		win.setClosable(true);
		win.doModal();
		win.setAttribute("opcion", "listaUsuarios");
		win.setAttribute("controladorOrigen", this);

	}

	public void onClick$button_buscar() {
		actualizarLista();
	}

	public void actualizarLista() {
		// obtener datos de la base
		// lista de usuarios
		DBUsuario dbu = new DBUsuario();
		// lista con usuarios encontrados
		List<Usuarios> lista = dbu.buscarUsuarios(textbox_buscar.getValue());
		// establecer esta lista como modelo de dalos pasra el listbox
		ListModelList<Usuarios> listModel = new ListModelList<Usuarios>(lista);
		// establecer el modelo de datos
		listbox_Miembros.setModel(listModel);

	}

	public void onClick$toolbarbutton_Editar() {
		// verificar q usuario haya seleccionado un elemento de la lista
		if (listbox_Miembros.getSelectedItem() == null) {
			alert("Seleccione por favor un usuario");
			return;
		}

		Window win = (Window) Executions.createComponents(
				"VisualizarPerfil.zul", null, null);
		win.setClosable(true);
		win.doModal();
		// guardar atributos en ventana
		win.setAttribute("opcion", "listaUsuarios");
		win.setAttribute("controladorOrigen", this);
		// dos opciones
		// pasar todo el objeto usuario
		// pasar un identificador id de Usuario
		Usuarios u = (Usuarios) listbox_Miembros.getSelectedItem().getValue();
		win.setAttribute("usuario", u);
	}

	public void onClick$toolbarbutton_EditarUR() {
		// verificar q usuario haya seleccionado un elemento de la lista
		if (listbox_MiembrosRoles.getSelectedItem() == null) {
			alert("Seleccione por favor un usuario");
			return;
		}
		Window win = (Window) Executions.createComponents(
				"Mantenimiento/RolesUsuario/ModificarRolUsuario.zul", null,
				null);
		win.setClosable(true);
		win.doModal();
		win.setAttribute("opcion", "listaUsuarios");
		win.setAttribute("controladorOrigen", this);
		Usuarios u = (Usuarios) listbox_MiembrosRoles.getSelectedItem()
				.getValue();
		win.setAttribute("usuario", u);

	}

	public void onClick$toolbarbutton_Eliminar() {
		// alert("Click en boton");

		boolean result = false;
		if (listbox_Miembros.getSelectedItem() == null) {
			alert("Seleccione el usuario que desea eliminar");
			return;
		}
		// mesaagebox
		Messagebox.show("Esta seguro de eliminar el usluario?", "confirmacion",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event evento) throws Exception {
						// TODO Auto-generated method stub
						if (evento.getName().equals("onOK")) {
							confirmacion = true;
						}
					}
				});
		if (confirmacion) {
			Usuarios u = (Usuarios) listbox_Miembros.getSelectedItem()
					.getValue();
			DBUsuario user = new DBUsuario();
			result = user.eliminarUsuario(u);
			if (result) {
				alert("usuario eliminado correctamente");
			}
		} else {
			alert("Eliminacion Cancelada");
		}
		actualizarLista();

	}

	public void onClick$button_buscarUR() {
		// actualiz<ar la lista segun el criterio de busqueda

		actualizarListaRolesUsuarios();
	}

	public void actualizarListaRolesUsuarios() {
		// obtener datos de la base
		// lista de usuarios
		DBUsuario dbu = new DBUsuario();
		// lista con usuarios encontrados
		List<Usuarios> lista = dbu.buscarRolesUsuarios(textbox_buscar
				.getValue());
		// establecer esta lista como modelo de dalos pasra el listbox
		ListModelList<Usuarios> listModel = new ListModelList<Usuarios>(lista);
		// establecer el modelo de datos
		listbox_MiembrosRoles.setModel(listModel);

	}

	/*
	 * public void onClick$toolbarbutton_Nuevo() { Window win = (Window)
	 * Executions.createComponents( "VisualizarPerfil.zul", null, null);
	 * win.setClosable(true); win.doModal(); }
	 * 
	 * public void onClick$toolbarbutton_Editar() { Window win = (Window)
	 * Executions.createComponents( "VisualizarPerfil.zul", null, null);
	 * win.setClosable(true); win.doModal(); }
	 */

}
