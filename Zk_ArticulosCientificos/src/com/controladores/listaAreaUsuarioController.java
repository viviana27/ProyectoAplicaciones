package com.controladores;


import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.datos.DBUsuario;
import com.datos.DBUsuarioArea;
import com.entidades.AreaUsuario;
import com.entidades.Areas;
import com.entidades.UsuarioArea;
import com.entidades.Usuarios;


public class listaAreaUsuarioController extends GenericForwardComposer<Component> {
	@Wire
	Toolbarbutton toolbarbutton_Editar;
	private Textbox textbox_buscar;
	private Button button_buscar;
	Listbox listbox_Miembros;
	boolean confirmacion=false;
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);	
		Session session = Sessions.getCurrent();
		Usuarios u = (Usuarios) session.getAttribute("User");
		if(u!=null){
			if(u.getId_rol()==1){
				actualizarLista();
			}
			else
				
					Executions.sendRedirect("http://localhost:8080/Zk_ArticulosCientificos/index.zul");
				
		}
		
		
	}


	public void onClick$toolbarbutton_Editar() {
		//verificar que este seleccionado un elemento de la lista
				if(listbox_Miembros.getSelectedItem()==null){
					alert("Selecciona un área por usuario");
					return;
				}
				Window win = (Window) Executions.createComponents(
						"Mantenimiento/Areas/AsignarAreaUsuario.zul", null, null);
				win.setClosable(true);
				win.doModal();
				// guardar atributos en ventana
				win.setAttribute("opcion","listaAreasUsuarios");
				win.setAttribute("controladorOrigen", this);
				UsuarioArea u=(UsuarioArea )listbox_Miembros.getSelectedItem().getValue();
				win.setAttribute("usuario",u);
	}
	
	public void onClick$button_buscar() {
		// actualiz<ar la lista segun el criterio de busqueda

		actualizarLista();
	}

	public void actualizarLista() {
		// obtener datos de la base
		// lista de usuarios
		DBUsuarioArea dbu = new DBUsuarioArea();
		// lista con usuarios encontrados
		List<UsuarioArea > lista = dbu.buscarUsuariosArea(textbox_buscar.getValue());
		// establecer esta lista como modelo de dalos pasra el listbox
		ListModelList<UsuarioArea > listModel = new ListModelList<UsuarioArea >(lista);
		// establecer el modelo de datos
		listbox_Miembros.setModel(listModel);

	}
	
}
