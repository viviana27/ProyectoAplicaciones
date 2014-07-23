package com.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.SubirDescargarArchivos.Util;
import com.datos.DBArticulos;
import com.datos.DBParametrosArticulos;
import com.datos.DBParametrosEvaluacion;
import com.datos.DBPermiso;
import com.entidades.Articulo;
import com.entidades.ParametrosArticulo;
import com.entidades.ParametrosEvaluacion;
import com.entidades.Permiso;
import com.entidades.Usuarios;

public class evaluacionController extends GenericForwardComposer<Component> {
	public Label nombreArch, tit, caliFinal;
	Doublebox calific;
	Button button_Registrar, button_Obs;
	Window WinEvaluarArticulo;
	Listbox parametros;
	ListModelList<ParametrosEvaluacion> listModel;
	List<ParametrosArticulo> listaParam = new ArrayList<ParametrosArticulo>();
	Articulo art;
	int vmax, idArticuloSubido = 0;
	boolean bandera = false;
	public Media media;
	public Window winDetalleArticulo, winSubirArticulo;

	String NombreArchi, direccion, nom;

	@NotifyChange("media")
	@Command
	public void onUpload$button_Obs(
			@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) {
		media = event.getMedia();

		String extension = media.getFormat();
		if (extension.equals("docx")) {
			NombreArchi = media.getName();
			nombreArch.setValue(NombreArchi);
		} else {
			alert("Debe subir un archivo de Word, este tiene una extensión :"
					+ extension);
			NombreArchi = "";
			nombreArch.setValue("");
		}
	}

	public Media getMedia() {
		return media;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		// actualizarLista();
	}

	public void onCreate$WinEvaluarArticulo() {
		art = (Articulo) WinEvaluarArticulo.getAttribute("articulo");
		if (art != null) {
			tit.setValue(art.getArt_titulo());
		} else {
			alert("No se pudo recuperar información del artículo");
		}
		actualizarLista();
	}

	public void actualizarLista() {
		DBParametrosEvaluacion dbp = new DBParametrosEvaluacion();
		List<ParametrosEvaluacion> lista = dbp.buscarParametrosEvaluacion("");

		listModel = new ListModelList<ParametrosEvaluacion>(
				lista);
		parametros.setModel(listModel);
		parametros.renderAll();
	}

	public void recorrerLista() {
		Usuarios u = (Usuarios) session.getAttribute("User");
		ParametrosArticulo pa = null;
		for (int i = 0; i <= listModel.size() - 2; i++) {
			pa = new ParametrosArticulo();

			Listitem item = (Listitem) parametros.getItems().get(i);

			List<Component> listaceldas = item.getChildren();

			Listcell pId = (Listcell) listaceldas.get(0);
			Listcell pVal = (Listcell) listaceldas.get(3);

			int idParam = Integer.parseInt(pId.getLabel());
			int val = pVal.getValue();
			
			pa.setParam_art_valor(val);
			pa.setParam_id(idParam);
			pa.setPerson_id(u.getId());
			pa.setArticul_id(art.getArt_id());
			
			listaParam.add(pa);
		}
	}

	public void onClick$button_Registrar() {
		if (direccion != null) {

		} else {
			recorrerLista();
			DBParametrosArticulos dbp = new DBParametrosArticulos();
			bandera = dbp.guardarEvaluacion(listaParam);
			if (bandera) {
				alert("Evaluación registrada con exito");

			} else {
				alert("Fallamos...!");
			}
		}

	}

	public void ObtenerIdArticuloRegistrado(String direc) {
		DBArticulos dba = new DBArticulos();
		idArticuloSubido = dba.obtenerIdArticuloRegistrado(direc);
		System.out.println("idArticulo registrado: " + idArticuloSubido);
	}

	public void obtenerRutaArchivoAdjuntado() {
		Util u = new Util();
		direccion = u.ruta + "/" + NombreArchi;
		nom = NombreArchi;
		System.out.println(direccion);
		System.out.println(nom);
	}

	/*
	 * public void onBlur$textbox_valor() { if (vmax <
	 * Integer.parseInt(caliFinal.getValue())) {
	 * alert("la suma total de parametros no debe exeder al 100% del total ");
	 * caliFinal.setValue(""); } }
	 */
}
