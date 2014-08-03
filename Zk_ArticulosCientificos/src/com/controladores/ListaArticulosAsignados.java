package com.controladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.ContentTypes;
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
import com.datos.DBArticulosEvaluados;
import com.datos.DBParametrosEvaluacion;
import com.entidades.Articulo;
import com.entidades.Usuarios;
import com.lowagie.text.Document;

public class ListaArticulosAsignados extends GenericForwardComposer<Component> {
	Textbox txtProyecto;
	Button idfiltroTitulo;
	Textbox txtarea;
	Button idarea;
	Listbox listaArticulosporpar;
	Toolbarbutton toolbarbutton_Editar;
	Button button_descarga, evaluar;
	String NombreArchi, direccion;
	Label nombreArticulo;
	public Media media;
	String emailper;
	int prom=0;
	int registro = 0, cantid2=0,idart=0,evaluador=0;
	File f;

	@NotifyChange("media")
	@Command
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		actualizarLista();
		listaArticulosporpar.renderAll();
		Session sesion = Sessions.getCurrent();
		Usuarios u = (Usuarios) sesion.getAttribute("User");
		if (u != null) {
			if (u.getId_rol() == 3) {
				toolbarbutton_Editar.setVisible(false);
			}
		}
	}

	public void onClick$idfiltroTitulo() {
		actualizarLista();
	}

	public void onClick$idarea() {
		actualizarLista();
	}

	public void actualizarLista() {
		// obtener datos de la base
		// lista de usuarios
		DBArticulos dbart = new DBArticulos();
		// lista con usuarios encontrados
		
		//articulo a evaluar debe actualizarse en  lista
		List<Articulo> lista = dbart.buscarArticuloAEvaluar(
				txtProyecto.getValue(), txtarea.getValue());
		// establecer esta lista como modelo de dalos pasra el listbox
		ListModelList<Articulo> listModel = new ListModelList<Articulo>(lista);
		// establecer el modelo de datos
		listaArticulosporpar.setModel(listModel);
		// alert("lista"+ ((Articulo)listaTareas.getItemAtIndex(0)));
		listaArticulosporpar.renderAll();
	}

	public void onClick$toolbarbutton_Editar() {
		// verificar q usuario haya seleccionado un elemento de la lista
		if (listaArticulosporpar.getSelectedItem() == null) {
			alert("Seleccione por favor un articulo :)");
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
		Articulo u = (Articulo) listaArticulosporpar.getSelectedItem()
				.getValue();

		win.setAttribute("articulo", u);
	}

	// simply download the file
	@Command
	public void onClick$button_descarga() {
		Articulo art = (Articulo) listaArticulosporpar.getSelectedItem()
				.getValue();
		// nombreArticulo.setVisible(true);
		nombreArticulo.setValue(art.getArt_archivo());
		f = new File(nombreArticulo.getValue());
		//System.out.println("nombre completo" + nombreArticulo.getValue());
		try {
			Filedownload.save(f, null);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		alert("descarga exitosa");
	}

	public void onSelect$listaArticulosporpar() {
		Articulo art = (Articulo) listaArticulosporpar.getSelectedItem()
				.getValue();
		idart=art.getArt_id();
		// nombreArticulo.setVisible(true);
		button_descarga.setVisible(true);
		String ruta;
		nombreArticulo.setValue(art.getArt_archivo());

	}

	public void onClick$evaluar() {
		if (listaArticulosporpar.getSelectedItem() == null) {
			alert("Seleccione por favor un articulo");
			return;
		}
		DBParametrosEvaluacion dp= new DBParametrosEvaluacion();
		DBArticulosEvaluados dbart=new DBArticulosEvaluados();
		Usuarios u = (Usuarios) session.getAttribute("User");
		Articulo art=new Articulo();
		emailper=art.getEmail();
		registro= dp.TotalParametros();
		cantid2=dbart.contarEvaluados(idart);
		System.out.println("cantidad evalauciones"+cantid2);
		evaluador= dbart.existeEvaluador(idart);
		System.out.println("id articulo"+evaluador);
		if(registro==100 ){
			if(cantid2<2 && u.getId() != evaluador){
				
		Window win = (Window) Executions.createComponents(
				"Articulo/Evaluacion.zul", null, null);
		win.setClosable(true);
		win.doModal();
		Articulo arti = (Articulo) listaArticulosporpar.getSelectedItem()
				.getValue();
		win.setAttribute("opcion", "Evaluacion");
		win.setAttribute("articulo", arti);
	}
	else{
				alert("No se puede evaluar, el articulo ya fue evaluado");
			}
		}
	else{
		alert("No está disponible los parámetros a considerar");
	}
		
}
}
