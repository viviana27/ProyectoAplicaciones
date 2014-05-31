package com.controladores;


import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.datos.DBUsuario;
import com.entidades.Usuarios;

public class indexloginController extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	// enlazamos los componentes de la vista con variables de referencia
	@Wire
	private Textbox textbox_User;
	private Textbox textbox_Password;
	private Label label_Mensaje;
	Menubar menubar_opciones1;
	Center centro;
	Button button_Ingresar;
	Button button_Cancelar;
	Window windlogin;

	// se ejecuta cuando se produce el evento oncreate de la ventana winindex
	public void onCreate$winindex() {

	}

	// se ejecuta justo despues de crear los componentes
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		// crear menu
		crearMenu();
	}

	public void crearMenu() {
		Menu menu = new Menu("Noticias");
		Menupopup menupopup = new Menupopup();
		Menuitem menuitem = new Menuitem("");
		Menuitem menuitemU1 = new Menuitem("");
		menuitem.setImage("");
		menuitemU1.setImage("");
		menuitem.setValue("");
		menuitemU1.setValue("");
		// agrego escucha de evento al menu item
		menuitem.addEventListener("onClick", new MenuListener());
		menuitemU1.addEventListener("onClick", new MenuListener());
		// enlazar
		menupopup.appendChild(menuitem);
		menupopup.appendChild(menuitemU1);
		menu.appendChild(menupopup);
		menubar_opciones1.appendChild(menu);

		Menu menuR1 = new Menu("Datos de sesion");
		Menupopup menupopupR1 = new Menupopup();
		Menuitem menuitemR1 = new Menuitem("Registrarse");
		Menuitem menuitemR2 = new Menuitem("Iniciar Sesión");
		menuitemR1.setValue("VisualizarPerfil.zul");
		menuitemR2.setValue("login.zul");
		menuitemR1.setImage("imagenes/listarReser.png");
		menuitemR2.setImage("imagenes/nuevaReser.png");
		menuitemR1.addEventListener("onClick", new MenuListener());
		menuitemR2.addEventListener("onClick", new MenuListener());
		menupopupR1.appendChild(menuitemR1);
		menupopupR1.appendChild(menuitemR2);
		menuR1.appendChild(menupopupR1);
		menubar_opciones1.appendChild(menuR1);
	}

	// clases internas
	// clase que implementa la interfaz de escucha de eventos
	class MenuListener implements EventListener {
		// metodo que se ejecute cuando se produce el evento
		@Override
		public void onEvent(Event arg0) throws Exception {
			// eliminar lo anterior del centro
			if (centro.getFirstChild() != null) {
				// detach quita todo lo que esta dentro
				centro.getFirstChild().detach();
				// colocamos lo nuevo
				// leer valor almacenado en menuitem
				String pagina = ((Menuitem) arg0.getTarget()).getValue();
				Window win = (Window) Executions.createComponents(pagina,
						centro, null);
			}

		}

	}

	public void onClick$button_Ingresar() {
		// comprobar que el usuario existe.
		// 1. Comprobar que usuario ingreso datos
		if (textbox_User.getValue().isEmpty()
				|| textbox_Password.getValue().isEmpty()) {
			alert("campos requeridos");
			// evaluar si datos son correctos
		} else {
			DBUsuario dbusuario = new DBUsuario();
			Usuarios usuario = dbusuario.buscarUsuario(textbox_User.getValue(),
					textbox_Password.getValue());
			if (usuario != null) {

				// almacenar datos del usuario en la sesion
				Session session;
				session = Sessions.getCurrent();
				// guardar objeto usuario en la session
				session.setAttribute("User", usuario);
				
				// Redirreccionar a la pagina principal

				Executions.sendRedirect("index.zul");

			} else

			{
				// datos no son correctos
				alert("datos incorrectos");

			}
		}
	}

	public void onClick$button_Cancelar(){
		Executions.sendRedirect("index-login.zul");
	}
}
