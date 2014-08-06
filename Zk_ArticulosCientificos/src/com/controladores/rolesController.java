package com.controladores;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.datos.DBRoles;
import com.datos.DBUsuario;
import com.entidades.Areas;
import com.entidades.Persona;
import com.entidades.Roles;
import com.entidades.Usuarios;

public class rolesController extends GenericForwardComposer<Component> {
	@Wire
	private Textbox textbox_rol;
	private Checkbox check_estado;
	private Button button_Registrar;
	private Window WinRegistrarRoles;
	private Roles rol = null;
	private int estado = 0;

	public void onClick$button_Registrar() throws Exception {
		boolean result = false;
		DBRoles dbroles = new DBRoles();
		// verificar si es nuevo usuario o usuario a editar
		if (rol != null) {
			// existe estoy editando
			rol.setRol_descripcion(textbox_rol.getValue());
			if (check_estado.isChecked()) {
				estado = 1;
			} else {
				estado = 0;
			}

			rol.setRol_estado(estado);
			// llamo al metodo para actualizar los datos
			result = dbroles.Actualizar_Roles(rol);

		} else {
			if (check_estado.isChecked()) {
				estado = 1;
			} else {
				estado = 0;
			}
			Roles rol = new Roles(0, textbox_rol.getValue(), estado);
			// usuario.setClave(textbox_Password.getValue());

			result = dbroles.CrearRoles(rol);
		}

		if (result) {
			alert("El rol ha sido registrado con éxito");
			// evaluar desde donde fue llamada esta ventana
			String opcion = (String) WinRegistrarRoles.getAttribute("opcion");
			if (opcion != null && opcion.equals("listaRoles")) {
				listaRolController luc = (listaRolController) WinRegistrarRoles
						.getAttribute("controladorOrigen");
				if (luc != null)
					luc.actualizarLista();
				// luc.actualizarLista();
				WinRegistrarRoles.detach();

			}
		} else {
			alert("No se pudo realizar el registro del área");
		}
		WinRegistrarRoles.detach();
	}

	public void onCreate$WinRegistrarRoles() {

		Session session = Sessions.getCurrent();
		Usuarios u = (Usuarios) session.getAttribute("User");
		if (u != null) {
			if (u.getId_rol() == 1) {

				rol = (Roles) WinRegistrarRoles.getAttribute("rol");
				if (rol != null) {
					textbox_rol.setText(rol.getRol_descripcion());
					if (rol.getRol_estado() == 1) {
						// chkvista.isChecked();
						check_estado.setChecked(true);
					}

				}
			} else if (u.getId_rol() == 2 || u.getId_rol() == 3) {
				Executions
						.sendRedirect("http://localhost:8080/Zk_ArticulosCientificos/index.zul");
			}
		}

	}

}
