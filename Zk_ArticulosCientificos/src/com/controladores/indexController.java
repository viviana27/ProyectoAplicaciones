package com.controladores;

import org.jfree.text.TextBox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.datos.DBUsuario;
import com.entidades.Usuarios;


public class indexController extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	// enlazamos los componentes de la vista con variables de referencia
	@Wire
	Menubar menubar_opciones;
	Center centro;
	Window winindex;
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		crearMenu();
	}
	public void crearMenu() {
		Menu menuR = new Menu("Registros");
		Menupopup menupopup = new Menupopup();
		Menuitem menuitemR1 = new Menuitem("Registrar Nuevo Usuario");
		Menuitem menuitemR2 = new Menuitem("Listar Usuarios");
		menuitemR1.setValue("VisualizarPerfil.zul");
		menuitemR2.setValue("listaUsuarios.zul");
		menuitemR1.addEventListener("onClick", new MenuListener());
		menuitemR2.addEventListener("onClick", new MenuListener());
		menupopup.appendChild(menuitemR1);
		menupopup.appendChild(menuitemR2);
		menuR.appendChild(menupopup);
		menubar_opciones.appendChild(menuR);	
		
		Menu menuP = new Menu("Perfil de usuario");
		Menupopup menupopupP = new Menupopup();
		Menuitem menuitemP1 = new Menuitem("Visualizar Perfil");
		Menuitem menuitemP2 = new Menuitem("Cambiar Contraseña");
		menuitemP1.setValue("VisualizarPerfil.zul");
		menuitemP2.setValue("");
		menuitemP1.addEventListener("onClick", new MenuListener());
		menuitemP2.addEventListener("onClick", new MenuListener());
		menupopupP.appendChild(menuitemP1);
		menupopupP.appendChild(menuitemP2);
		menuP.appendChild(menupopupP);
		menubar_opciones.appendChild(menuP);	
		
		Menu menuA = new Menu("Articulos ....");
		Menupopup menupopupA = new Menupopup();
		Menuitem menuitemA1 = new Menuitem("Listar Articulos");
		Menuitem menuitemA2 = new Menuitem("Evaluar Articulo");
		Menuitem menuitemA3 = new Menuitem("Subir Nuevo Articulo");
		Menuitem menuitemA4 = new Menuitem("Asignar Evaluadores a un Articulo");
		menuitemA1.setValue("Articulo/Listar Articulos.zul");
		menuitemA2.setValue("Articulo/Evaluacion.zul");
		menuitemA3.setValue("Articulo/Indexacion.zul");
		menuitemA4.setValue("Articulo/Revision.zul");
		menuitemA1.addEventListener("onClick", new MenuListener());
		menuitemA2.addEventListener("onClick", new MenuListener());
		menuitemA3.addEventListener("onClick", new MenuListener());
		menuitemA4.addEventListener("onClick", new MenuListener());
		menupopupA.appendChild(menuitemA1);
		menupopupA.appendChild(menuitemA2);
		menupopupA.appendChild(menuitemA3);
		menupopupA.appendChild(menuitemA4);
		menuA.appendChild(menupopupA);
		menubar_opciones.appendChild(menuA);
		
		Menu menuM = new Menu("Mantenimiento");
		Menupopup menupopupM = new Menupopup();
		Menuitem menuitemM1 = new Menuitem("Listar Areas ");
		Menuitem menuitemM2 = new Menuitem("Listar Roles");
		Menuitem menuitemM3 = new Menuitem("Listar Tipos de Artículos");
		Menuitem menuitemM4 = new Menuitem("Listar Parámetros de Evaluación");
		Menuitem menuitemM5 = new Menuitem("Asignar rol a un usuario");
		Menuitem menuitemM6 = new Menuitem("Asignar área a un usuario");
		menuitemM1.setImage("");
		menuitemM2.setImage("");
		menuitemM3.setImage("");
		menuitemM4.setImage("");
		menuitemM1.setValue("Mantenimiento/Areas/listaAreas.zul");
		menuitemM2.setValue("Mantenimiento/Roles/listaRoles.zul");
		menuitemM3.setValue("Mantenimiento/TipoArticulo/listaTipoArticulo.zul");
		menuitemM4.setValue("Mantenimiento/ParametrosEvaluacion/listaParametrosEvaluacion.zul");
		menuitemM5.setValue("Mantenimiento/RolesUsuario/listaRolesUsuarios.zul");
		menuitemM6.setValue("Mantenimiento/Areas/listaAreasUsuarios.zul");
		menuitemM1.addEventListener("onClick", new MenuListener());
		menuitemM2.addEventListener("onClick", new MenuListener());
		menuitemM3.addEventListener("onClick", new MenuListener());
		menuitemM4.addEventListener("onClick", new MenuListener());
		menuitemM5.addEventListener("onClick", new MenuListener());
		menuitemM6.addEventListener("onClick", new MenuListener());
		menupopupM.appendChild(menuitemM1);
		menupopupM.appendChild(menuitemM2);
		menupopupM.appendChild(menuitemM3);
		menupopupM.appendChild(menuitemM4);
		menupopupM.appendChild(menuitemM5);
		menupopupM.appendChild(menuitemM6);
		menuM.appendChild(menupopupM);
		menubar_opciones.appendChild(menuM);
		
		Menu menuN = new Menu("Notificaciones");
		Menupopup menupopupN = new Menupopup();
		Menuitem menuitemN1 = new Menuitem("Enviar Notificaciones");
		Menuitem menuitemN2 = new Menuitem("Revisar Notificaciones");
		menuitemN1.setValue("Notificaciones/enviarNotificaciones.zul");
		menuitemN2.setValue("Notificaciones/listaNotificaciones.zul");
		menuitemN1.addEventListener("onClick", new MenuListener());
		menuitemN2.addEventListener("onClick", new MenuListener());
		menupopupN.appendChild(menuitemN1);
		menupopupN.appendChild(menuitemN2);
		menuN.appendChild(menupopupN);
		menubar_opciones.appendChild(menuN);
	}
	

	@Wire
	private Textbox textbox_User;
	private Textbox textbox_Password;
	Button button_Ingresar;
	Button button_Cancelar;
	
	
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
	
}
