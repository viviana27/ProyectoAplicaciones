package com.controladores;
import java.util.ArrayList;

import java.util.List;

import javax.persistence.EntityManager;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.datos.DBAreas;
import com.datos.DBUsuario;
import com.entidades.Areas;
import com.entidades.Usuarios;


public class listaAreaController extends GenericForwardComposer<Component> {
	@Wire
	Toolbarbutton toolbarbutton_Eliminar;
	Toolbarbutton toolbarbutton_Nuevo;
	Toolbarbutton toolbarbutton_Editar;
	private Textbox textbox_buscar;
	private Button button_buscar;
	Listbox listbox_areas;

	boolean confirmacion=false;
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		Session session = Sessions.getCurrent();
		Usuarios u = (Usuarios) session.getAttribute("User");
		if(u!=null){
			if(u.getId_rol()==1){
				actualizarAreasLista();
			}
			else
				if(u.getId_rol()==2 || u.getId_rol()==3){
					Executions.sendRedirect("http://localhost:8080/Zk_ArticulosCientificos/index.zul");
				}
		}
		
	}

	public void onClick$toolbarbutton_Nuevo() {
		Window win = (Window) Executions.createComponents(
				"Mantenimiento/Areas/registroArea.zul", null, null);
		win.setClosable(true);
		
		//ventana show dialog
		win.doModal();
		
		// guardar atributos en ventana
		
		//win.setAttribute("opcion", "registroArea") -- nombre de variable;
				win.setAttribute("opcion", "listaAreas");
				win.setAttribute("controladorOrigen", this);
	}

	public void onClick$toolbarbutton_Editar() {
		//verificar que este seleccionado un elemento de la lista
		if(listbox_areas.getSelectedItem()==null){
			alert("Por favor seleccione un �rea");
			return;
		}
		
		//crear ventana de editar area
		Window win = (Window) Executions.createComponents(
				"Mantenimiento/Areas/registroArea.zul", null, null);
		win.setClosable(true);
		win.doModal();
		// guardar atributos en ventana
		win.setAttribute("opcion","listaAreas");
		win.setAttribute("controladorOrigen", this);
		Areas a=(Areas)listbox_areas.getSelectedItem().getValue();
		win.setAttribute("areas",a);
		actualizarAreasLista();
	}
	
	public void onClick$toolbarbutton_Eliminar() {
	
		
		if(listbox_areas.getSelectedItem()==null){
			alert("Por favor seleccione un �rea");
			return;
		}
		Messagebox.show("Est� seguro de eliminar el �rea?",
				"confirmaci�n",Messagebox.OK | Messagebox.CANCEL,Messagebox.QUESTION,new EventListener<Event>(){
			@Override
			public void onEvent(Event evento) throws Exception {
				boolean registros = false;
				// TODO Auto-generated method stub
				if(evento.getName().equals("onOK")){
					confirmacion=true;
				}
				else{
					confirmacion=false;
				}
				if(confirmacion){
					Areas a = listbox_areas.getSelectedItem().getValue();
					DBAreas dba = new DBAreas ();
					registros=dba.eliminarAreas(a);
					if (registros) {
						alert("El �rea ha sido eliminada correctamente");
					}
				}
				else {
					alert("Eliminaci�n Cancelada");
				}
				actualizarAreasLista();
			}
		});


}
	
	public void onClick$button_buscar(){
	actualizarAreasLista();
	}
	
	public void actualizarAreasLista() {
		// obtener datos de la base
		// lista de areas
		
		DBAreas dbar = new DBAreas();
		// lista con areas encontradas
		List<Areas> lista = dbar.buscarAreas(textbox_buscar.getValue());
		// establecer esta lista como modelo de dalos pasra el listbox
		ListModelList<Areas> listModel = new ListModelList<Areas>(lista);
		// establecer el modelo de datos
		listbox_areas.setModel(listModel);

	}
	
}
