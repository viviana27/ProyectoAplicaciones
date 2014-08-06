package com.controladores;

import java.util.List;

import com.entidades.*;
import com.datos.*;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class UsuarioController extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox textbox_Usuario;
	
	private Label label_Usuario;
	private Textbox textbox_Password;
	private Textbox textbox_Nombres;
	private Label label_Nombres;
	private Textbox textbox_Apellidos;
	private Label label_Apellidos;
	private Textbox textbox_Cedula;
	private Label label_Cedula;
	private Textbox textbox_Email;
	private Label label_Email;
	private Textbox textbox_Direccion;
	private Label label_Direccion;
	private Textbox textbox_Telefono;
	private Label label_Telefono;
	private Textbox textbox_celular;
	private Label label_celular;
	private Textbox textbox_institucion;
	private Label label_institucion;
	private Textbox textbox_dirinstitucion;
	private Label label_dirinstitucion;
	private Textbox textbox_clave_anterior;
	private Textbox textbox_clave_nueva;
	private Textbox textbox_rep_clave,textbox_Titulo,textbox_tipoar,textbox_area;
	private Button button_Registrar;
	private Button button_cambiar_clave;
	private Window winVerUsuario;
	private Window winNuevoUsuario;
	private Window winCambioClave;
	
	private Usuarios u = null;
	
	DBUsuario dbusuarios = new DBUsuario();
	int idarea=0;

	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
	}

	public void onCreate$winVerUsuario() {
		Session session = Sessions.getCurrent();
		Usuarios u = (Usuarios) session.getAttribute("User");
		if (u != null) {
			label_Usuario.setValue(u.getUsuario());
			label_Nombres.setValue(u.getPersona().getPer_nombre());
			label_Apellidos.setValue(u.getPersona().getPer_apellido());
			label_Cedula.setValue(u.getPersona().getPer_cedula());
			label_Email.setValue(u.getPersona().getPer_email());
			label_Direccion.setValue(u.getPersona().getPer_direccion());
			label_Telefono.setValue(u.getPersona().getPer_telefono());
			label_celular.setValue(u.getPersona().getPer_celular());
			label_institucion.setValue(u.getPersona()
					.getPer_institucion_pertenece());
			label_dirinstitucion.setValue(u.getPersona()
					.getPer_direccion_institucion());
		} else
			alert("Error al obtener informaci�n del usuario");
	}

	
	public void onBlur$textbox_Nombres(){
		
		}
	
	
	
	public void onClick$button_Registrar() throws Exception {
		boolean result = false;

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
					textbox_Password.getValue(), 1, persona, 2);
			// usuario.setClave(textbox_Password.getValue());

			result = dbusuarios.crearUsuario(usuario);

		}

		if (result) {
			alert("El usuario ha sido registrado con �xito");
			// evaluar desde donde fue llamada esta ventana
			String opcion = (String) winNuevoUsuario.getAttribute("opcion");
			if (opcion != null && opcion.equals("listaUsuarios")) {
				// entonces la ventana ufe llamada desde lista usuarios
				// cerrar ventana
				// actualizar la lista de usuarios
				listaUsuarioController luc = (listaUsuarioController) winNuevoUsuario
						.getAttribute("controladorOrigen");
				if (luc != null)
					luc.actualizarLista();
				// luc.actualizarLista();
				winNuevoUsuario.detach();
			}
		} else {
			alert("No se pudo realizar el registro del usuario");
		}
		winNuevoUsuario.detach();
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

	public void onClick$button_cambiar_clave() throws Exception {
		boolean result = false;
		Session session = Sessions.getCurrent();
		Usuarios u = (Usuarios) session.getAttribute("User");
		if (textbox_clave_anterior != null) {
			result = dbusuarios.verificarClave(u.getUsuario().toString(),
					textbox_clave_anterior.getValue());
			if (result) {
				alert("Clave Correcta");
				if (textbox_clave_nueva.getValue().toString()
						.equals(textbox_rep_clave.getValue().toString())) {
					result = dbusuarios.modificarClave(
							textbox_clave_nueva.getValue(), u.getId());
					if (result) {
						alert("Clave Actualizada");
					} else {
						alert("No se logr� cambiar su clave");
					}
				} else {
					alert("Por favor digite correctamente su nueva clave, " +
							"y al repetirla procure que coincidan");
					textbox_clave_nueva.setValue("");
					textbox_rep_clave.setValue("");
				}
			} else {
				alert("La clave anterior es incorrecta");
			}
		} else {
			alert("Es necesario que ingrese la clave anterior");
		}
	}

}
