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

import com.datos.DBPermiso;
import com.datos.DBUsuario;
import com.entidades.Usuarios;

public class indexController extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	// enlazamos los componentes de la vista con variables de referencia
	@Wire
	Menubar menubar_opciones;
	Center centro;
	Window winindex;
	DBPermiso dbp = new DBPermiso();
	int idtipousuario=0;
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		crearMenu();
	}

	public void crearMenu() {
		Session session = Sessions.getCurrent();
		Usuarios u = (Usuarios) session.getAttribute("User");
		 idtipousuario= u.getId_rol();
			
		Menu menuR = new Menu(". Registros");
		menuR.setStyle("font-weight: bold");
		menuR.setImage("Imagenes/users.png");
		Menupopup menupopup = new Menupopup();
		Menuitem menuitemR1 = new Menuitem("Registrar Nuevo Usuario");
		Menuitem menuitemR2 = new Menuitem("Listar Usuarios");
		menuitemR1.setValue("Usuarios/nuevoUsuario.zul");
		menuitemR1.setImage("Imagenes/nuevo.png");
		menuitemR2.setValue("Usuarios/listaUsuarios.zul");
		menuitemR2.setImage("Imagenes/listaU.png");
		menuitemR1.addEventListener("onClick", new MenuListener());
		menuitemR2.addEventListener("onClick", new MenuListener());
		if(dbp.ConsultarPermisos(idtipousuario, 15)){
			menupopup.appendChild(menuitemR1);}
		if(dbp.ConsultarPermisos(idtipousuario, 25)){
			menupopup.appendChild(menuitemR2);}
		menuR.appendChild(menupopup);
		menubar_opciones.appendChild(menuR);
		
		
		 
		Menu menuP = new Menu(". Perfil de usuario");
		menuP.setImage("Imagenes/user.png");
		Menupopup menupopupP = new Menupopup();
		Menuitem menuitemP1 = new Menuitem("Visualizar Perfil");
		menuitemP1.setImage("Imagenes/perfil.png");
		Menuitem menuitemP2 = new Menuitem("Cambiar Contraseña");
		menuitemP2.setImage("Imagenes/icambio.png");
		menuitemP1.setValue("Usuarios/verUsuario.zul");
		menuitemP2.setValue("Usuarios/modificarClave.zul");
		menuitemP1.addEventListener("onClick", new MenuListener());
		menuitemP2.addEventListener("onClick", new MenuListener());
		if(dbp.ConsultarPermisos(idtipousuario, 6)){
			menupopupP.appendChild(menuitemP1);}
		if(dbp.ConsultarPermisos(idtipousuario, 7)){
			menupopupP.appendChild(menuitemP2);}
		menuP.appendChild(menupopupP);
		menubar_opciones.appendChild(menuP);
	
	
		
		Menu menuA = new Menu(". Artículos ....");
		menuA.setImage("Imagenes/folder.png");
		Menupopup menupopupA = new Menupopup();
		Menuitem menuitemA3 = new Menuitem("Subir Nuevo Artículo");
		menuitemA3.setImage("Imagenes/subirNuevo.png");
		Menuitem menuitemA1 = new Menuitem("Listar Artículos");
		menuitemA1.setImage("Imagenes/listarArt.png");
		Menuitem menuitemA6 = new Menuitem("Listar Artículos A Corregir");
		menuitemA6.setImage("Imagenes/Correc.png");
		Menuitem menuitemA7 = new Menuitem("Historial De Los Artículos");
		menuitemA7.setImage("Imagenes/historial.png");
	//	Menuitem menuitemA2 = new Menuitem("Evaluar Articulo");
		Menuitem menuitemA4 = new Menuitem("Asignar Evaluadores A Un Artículo");
		menuitemA4.setImage("Imagenes/eval.png");
		Menuitem menuitemA5 = new Menuitem("Articulos Asignados A Evaluar");
		menuitemA5.setImage("Imagenes/aEvaluar.png");
		
		
		menuitemA1.setValue("Articulo/Listar Articulos.zul");
		//menuitemA2.setValue("Articulo/Evaluacion.zul");
		menuitemA3.setValue("Articulo/Subir Articulo.zul");
		menuitemA4.setValue("Articulo/Revision.zul");
		menuitemA5.setValue("Articulo/Vista_Evaluador.zul");
		menuitemA6.setValue("Articulo/ListarArticuloAutor.zul");
		menuitemA7.setValue("Articulo/Historial.zul");
		menuitemA1.addEventListener("onClick", new MenuListener());
	//	menuitemA2.addEventListener("onClick", new MenuListener());
		menuitemA3.addEventListener("onClick", new MenuListener());
		menuitemA4.addEventListener("onClick", new MenuListener());
		menuitemA5.addEventListener("onClick", new MenuListener());
		menuitemA6.addEventListener("onClick", new MenuListener());
		menuitemA7.addEventListener("onClick", new MenuListener());
			
		if(dbp.ConsultarPermisos(idtipousuario, 8)){
		menupopupA.appendChild(menuitemA1);}
		if(dbp.ConsultarPermisos(idtipousuario, 10)){
		menupopupA.appendChild(menuitemA3);}
		if(dbp.ConsultarPermisos(idtipousuario, 11)){
			menupopupA.appendChild(menuitemA4);}
		if(dbp.ConsultarPermisos(idtipousuario, 12)){
			menupopupA.appendChild(menuitemA5);}
		if(dbp.ConsultarPermisos(idtipousuario, 24)){
		 menupopupA.appendChild(menuitemA6);}
		menupopupA.appendChild(menuitemA7);
		menuA.appendChild(menupopupA);
		menubar_opciones.appendChild(menuA);
				
		Menu menuM = new Menu(".  Mantenimiento");
		menuM.setImage("Imagenes/mant.png");
		Menupopup menupopupM = new Menupopup();
		Menuitem menuitemM1 = new Menuitem("Listar Áreas ");
		menuitemM1.setImage("Imagenes/listaArea.png");
		Menuitem menuitemM2 = new Menuitem("Listar Roles");
		menuitemM2.setImage("Imagenes/listaRoles.png");
		Menuitem menuitemM3 = new Menuitem("Listar Tipos De Artículos");
		menuitemM3.setImage("Imagenes/listaTipo.png");
		Menuitem menuitemM4 = new Menuitem("Listar Parámetros De Evaluación");
		menuitemM4.setImage("Imagenes/listaParametro.png");
		Menuitem menuitemM5 = new Menuitem("Asignar Rol A Un Usuario");
		menuitemM5.setImage("Imagenes/asignaRol.png");
		Menuitem menuitemM6 = new Menuitem("Asignar Área A Un Usuario");
		menuitemM6.setImage("Imagenes/listaArea.png");
		Menuitem menuitemM7 = new Menuitem("Asignar Permisos A Usuarios");
		menuitemM7.setImage("Imagenes/asignaPermiso.png");
		menuitemM1.setValue("Mantenimiento/Areas/listaAreas.zul");
		menuitemM2.setValue("Mantenimiento/Roles/listaRoles.zul");
		menuitemM3.setValue("Mantenimiento/TipoArticulo/listaTipoArticulo.zul");
		menuitemM4
				.setValue("Mantenimiento/ParametrosEvaluacion/listaParametrosEvaluacion.zul");
		menuitemM5
				.setValue("Mantenimiento/RolesUsuario/listaRolesUsuarios.zul");
		menuitemM6.setValue("Mantenimiento/Areas/listaAreasUsuarios.zul");
		menuitemM7.setValue("Permiso/Permiso.zul");
		menuitemM1.addEventListener("onClick", new MenuListener());
		menuitemM2.addEventListener("onClick", new MenuListener());
		menuitemM3.addEventListener("onClick", new MenuListener());
		menuitemM4.addEventListener("onClick", new MenuListener());
		menuitemM5.addEventListener("onClick", new MenuListener());
		menuitemM6.addEventListener("onClick", new MenuListener());
		menuitemM7.addEventListener("onClick", new MenuListener());		
		if(dbp.ConsultarPermisos(idtipousuario, 17)){
			menupopupM.appendChild(menuitemM1);}
		if(dbp.ConsultarPermisos(idtipousuario, 18)){
			menupopupM.appendChild(menuitemM2);}
		if(dbp.ConsultarPermisos(idtipousuario, 19)){
			menupopupM.appendChild(menuitemM3);}
		if(dbp.ConsultarPermisos(idtipousuario, 20)){
			menupopupM.appendChild(menuitemM4);}
		if(dbp.ConsultarPermisos(idtipousuario, 21)){
			menupopupM.appendChild(menuitemM5);}
		if(dbp.ConsultarPermisos(idtipousuario, 22)){
			menupopupM.appendChild(menuitemM6);}
		if(dbp.ConsultarPermisos(idtipousuario, 23)){
			menupopupM.appendChild(menuitemM7);}
		
		menuM.appendChild(menupopupM);
		menubar_opciones.appendChild(menuM);

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
