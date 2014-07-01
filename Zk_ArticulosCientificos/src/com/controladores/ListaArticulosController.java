package com.controladores;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.datos.DBArticulos;
import com.datos.DBUsuario;
import com.entidades.Articulo;
import com.entidades.Usuarios;

public class ListaArticulosController extends GenericForwardComposer<Component> {
	Textbox txtProyecto;
	Textbox  txtautor;
	Textbox txttipo;
	Textbox txtarea;
	Button idfiltroTitulo;
	Button idfiltroautor;
	Button idtipo;
	Button idarea; 
	Listbox listaTareas;
	
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);	
		Session session = Sessions.getCurrent();
		Usuarios u = (Usuarios) session.getAttribute("User");
		if(u!=null){
			if(u.getId_rol()==1){
				actualizarLista();
				listaTareas.renderAll();
			}
			else
				
					Executions.sendRedirect("http://localhost:8080/Zk_ArticulosCientificos/index.zul");
				
		}
		
		
	}

	public void onClick$idfiltroTitulo() {
		actualizarLista();
	}
	
	public void onClick$idfiltroautor() {
		actualizarLista();
	}
	
	public void onClick$idtipo() {
		actualizarLista();
	}
	public void onClick$idarea() {
		actualizarLista();
	}
	
	
   
    
   /* public void onChange$txtarea(){
		
		/*DBArticulos dbart = new DBArticulos();
	  dbart.buscarArticulo("", "", "", txtautor.getValue().trim());*/
  /*  	alert("llegamos al evento change area");
    	actualizarLista();
		
	}*/

	public void actualizarLista() {
		// obtener datos de la base
		// lista de usuarios
		DBArticulos dbart = new DBArticulos();
		// lista con usuarios encontrados
		List<Articulo> lista = dbart.buscarArticulo( txtProyecto.getValue(), txtautor.getValue(), txttipo.getValue(), txtarea.getValue());
		// establecer esta lista como modelo de dalos pasra el listbox
		ListModelList<Articulo> listModel = new ListModelList<Articulo>(lista);
		// establecer el modelo de datos
		listaTareas.setModel(listModel);
		listaTareas.renderAll();
		

	}
	
}
