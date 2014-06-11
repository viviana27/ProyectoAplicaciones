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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.datos.DBRoles;
import com.datos.DBUsuario;
import com.entidades.Persona;
import com.entidades.Roles;
import com.entidades.UsuarioArea;
import com.entidades.Usuarios;

public class listaRolUsuarioController extends
		GenericForwardComposer<Component> {
	@Wire
	Listbox listbox_roles_usuarios;
	private Window winModificarRol;
	private Textbox textbox_Usuario;
	private Button button_Guardar;
	DBUsuario dbusuarios = new DBUsuario();
	Usuarios u;
	int idRol;

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

	public void onClick$button_Guardar() throws Exception {
		boolean result = false;
		
		if (listbox_roles_usuarios.getSelectedItem() == null) {
			alert("Seleccione un rol");
		}else{
			Roles r = (Roles) listbox_roles_usuarios.getSelectedItem().getValue();
			u = (Usuarios) winModificarRol.getAttribute("usuario");
			result = dbusuarios.cambiarRol(u.getId(), r.getRol_id());
			if (result) {
				alert("rol asignado correctamente");
			} else {
				alert("no se pudo completar la asignacion");

			}
		}
		
	}

	public void onCreate$winModificarRol() {

		u = (Usuarios) winModificarRol.getAttribute("usuario");
		if (u != null) {
			textbox_Usuario.setText(u.getPersona().getPer_nombre() + " "
					+ u.getPersona().getPer_apellido());

		}
	}

}