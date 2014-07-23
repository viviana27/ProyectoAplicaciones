package com.controladores;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.SubirDescargarArchivos.Util;
import com.datos.DBArticulos;
import com.entidades.Articulo;
import com.entidades.Usuarios;

public class ListaArticulosEvaluador  extends GenericForwardComposer<Component>{
	Textbox txtProyecto;
	Button idfiltroTitulo;
	Listbox listaArticulosporpar;
	Toolbarbutton toolbarbutton_Editar;
	Textbox txtarea;
	Button idarea;
	
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);	
		
				actualizarLista();
				listaArticulosporpar.renderAll();
				Session sesion=Sessions.getCurrent();
				Usuarios u=(Usuarios)sesion.getAttribute("User");
				if(u!=null){
					if(u.getId_rol()==3){
						toolbarbutton_Editar.setVisible(false)	;
					}
				}
	}
	
	public void onClick$idfiltroTitulo() {
		actualizarLista();
	}
	
	public void onClick$idarea() {
		actualizarLista();
		System.out.println("holadfdf");
	}
	
	public void actualizarLista() {
		DBArticulos dbart = new DBArticulos();
		List<Articulo> lista = dbart.buscarArticuloEvaluador( txtProyecto.getValue(), txtarea.getValue());
		ListModelList<Articulo> listModel = new ListModelList<Articulo>(lista);
		listaArticulosporpar.setModel(listModel);
		listaArticulosporpar.renderAll();
	}
	
	public void onClick$toolbarbutton_Editar() {
		// verificar q usuario haya seleccionado un elemento de la lista
		if (listaArticulosporpar.getSelectedItem() == null) {
			alert("Seleccione por favor un articulo");
			return;
		}

		Window win = (Window) Executions.createComponents(
				"Articulo/RegistroPorEvaluador.zul", null, null);
		win.setClosable(true);
		win.doModal();
		// guardar atributos en ventana
		win.setAttribute("opcion", "Revision");
		win.setAttribute("controladorOrigen", this);
		// dos opciones
		// pasar todo el objeto usuario
		// pasar un identificador id de Usuario
		Articulo u = (Articulo) listaArticulosporpar.getSelectedItem().getValue();
		
		win.setAttribute("articulo", u);
	}
	

	
	
	
}
