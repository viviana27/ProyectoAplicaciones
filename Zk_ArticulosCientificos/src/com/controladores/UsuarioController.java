package com.controladores;

import com.entidades.*;
import com.datos.*;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class UsuarioController extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox textbox_Usuario;
	private Textbox textbox_Password;
	private Textbox textbox_Nombres;
	private Textbox textbox_Apellidos;
	private Textbox textbox_Cedula;
	private Textbox textbox_Email;
	private Textbox textbox_Direccion;
	private Textbox textbox_Telefono;
	private Textbox textbox_celular;
	private Textbox textbox_institucion;
	private Textbox textbox_dirinstitucion;
	private Button button_Registrar;
	private Window winVerUsuario;
	private Window winNuevoUsuario;
	private Usuarios u = null;

	public void onCreate$winVerUsuario() {
		Session session = Sessions.getCurrent();
		Usuarios u = (Usuarios) session.getAttribute("user");
		if (u != null) {
			textbox_Usuario.setText(u.getUsuario());
			textbox_Nombres.setText(u.getPersona().getPer_nombre());
			textbox_Apellidos.setText(u.getPersona().getPer_apellido());
			textbox_Cedula.setText(u.getPersona().getPer_cedula());
			textbox_Email.setText(u.getPersona().getPer_email());
			textbox_Direccion.setText(u.getPersona().getPer_direccion());
			textbox_Telefono.setText(u.getPersona().getPer_telefono());
			textbox_celular.setText(u.getPersona().getPer_celular());
			textbox_institucion.setText(u.getPersona()
					.getPer_institucion_pertenece());
			textbox_dirinstitucion.setText(u.getPersona()
					.getPer_direccion_institucion());
		} else
			alert("Error al obtener información");
	}

	public void onClick$button_Registrar() {
		boolean result = false;
		DBUsuario dbusuarios = new DBUsuario();
		// verificar si es nuevo usuario o usuario a editar
		if (u != null) {
			// existe estoy editando
			u.setUsuario(textbox_Usuario.getValue());
			u.setClave(textbox_Password.getValue());
			u.getPersona().setPer_nombre(textbox_Nombres.getValue());
			u.getPersona().setPer_apellido(textbox_Apellidos.getValue());
			u.getPersona().setPer_cedula(textbox_Cedula.getValue());
			u.getPersona().setPer_email(textbox_Email.getValue());
			u.getPersona().setPer_direccion(textbox_Direccion.getValue());
			u.getPersona().setPer_telefono(textbox_Telefono.getValue());
			u.getPersona().setPer_celular(textbox_celular.getValue());
			u.getPersona().setPer_institucion_pertenece(
					textbox_institucion.getValue());
			u.getPersona().setPer_direccion_institucion(
					textbox_dirinstitucion.getValue());
			// llamo al metodo para actualizar los datos
			result = dbusuarios.actualizarUsuario(u);

		} else {
			// no existe, es nuevo
			// llamar a la capa logica de datos
			// DBUsuarios dbusuarios=new DBUsuarios();
			Persona persona = new Persona(0, textbox_Nombres.getValue(),
					textbox_Apellidos.getValue(), textbox_Cedula.getValue(),
					textbox_Email.getValue(), textbox_Direccion.getValue(),
					textbox_Telefono.getValue(), textbox_celular.getValue(),
					textbox_institucion.getValue(),
					textbox_dirinstitucion.getValue());
			Usuarios usuario = new Usuarios(0, textbox_Usuario.getValue(),
					persona);
			usuario.setClave(textbox_Password.getValue());

			result = dbusuarios.crearUsuario(usuario);
		}

		if (result) {
			alert("Usuario registrado con exito");
			// evaluar desde donde fue llamada esta ventana
			String opcion = (String) winNuevoUsuario.getAttribute("opcion");
			if (opcion != null && opcion.equals("listaUsuarios")) {
				// entonces la ventana ufe llamada desde lista usuarios
				// cerrar ventana
				winNuevoUsuario.detach();
				// actualizar la lista de usuarios
				listaUsuarioController luc = (listaUsuarioController) winNuevoUsuario
						.getAttribute("controladorOrigen");
				if (luc != null)
					luc.actualizarLista();
				// luc.actualizarLista();

			}
		} else {
			alert("No se pudo realizar el registro");
		}

	}

	public void onCreate$winNuevoUsuario() {
		u = (Usuarios) winNuevoUsuario.getAttribute("usuario");
		if (u != null) {
			textbox_Usuario.setText(u.getUsuario());
			textbox_Nombres.setText(u.getPersona().getPer_nombre());
			textbox_Apellidos.setText(u.getPersona().getPer_apellido());
			textbox_Cedula.setText(u.getPersona().getPer_cedula());
			textbox_Email.setText(u.getPersona().getPer_email());
			textbox_Direccion.setText(u.getPersona().getPer_direccion());
			textbox_Telefono.setText(u.getPersona().getPer_telefono());
			textbox_celular.setText(u.getPersona().getPer_celular());
			textbox_institucion.setText(u.getPersona()
					.getPer_institucion_pertenece());
			textbox_dirinstitucion.setText(u.getPersona()
					.getPer_direccion_institucion());

		}
	}

}
