package com.controladores;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.SubirDescargarArchivos.Util;
import com.datos.DBAreas;
import com.datos.DBArticulos;
import com.datos.DBTipoArticulos;
import com.datos.DBUsuario;
import com.entidades.Areas;
import com.entidades.Articulo;
import com.entidades.Persona;
import com.entidades.PersonaArticulo;
import com.entidades.TipoArticulos;
import com.entidades.UsuarioArea;
import com.entidades.Usuarios;

public class articuloController extends GenericForwardComposer<Component> {
	private Combobox cmb_tipo, comboAutor2, comboAutor3, cmb_area;
	public Textbox textbox_Titulo, textbox_Resumen, textbox_PClaves;
	public Button button_Registrar, btnExaminar;
	public Label ruta;
	public Datebox txtfecha;
	private Articulo a = null;
	private PersonaArticulo pa = null;
	public int idTipoArticulo = 0, idTipoArea = 0, idAutor2 = 0, idAutor3 = 0;
	String NombreArchivo, direccion;
	// //----------------------------------------------------------------------------
	// codigo para subir un archivo al servidor
	private Media media;

	@NotifyChange("media")
	@Command
	public void onUpload$btnExaminar(
			@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) {
		// System.out.println("lo hicimos .........!");
		media = event.getMedia();

		if (Util.uploadFile(media)) {
			NombreArchivo = media.getName();
			ruta.setValue(NombreArchivo);
			alert("Archivo Cargado....!");
		}
		// Messagebox.show(Labels.getLabel("app.successfull"));
		else
			Messagebox.show(Labels.getLabel("app.error"));
	}

	public Media getMedia() {
		return media;
	}

	// simply download the file
	@Command
	public void downloadFile() {
		if (media != null)
			Filedownload.save(media);
	}

	// //----------------------------------------------------------------------------
	// codigo de controladores de la clase
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		DBTipoArticulos dbtp = new DBTipoArticulos();
		DBUsuario dbu = new DBUsuario();
		List<TipoArticulos> listaTipos = dbtp.buscarTipos();
		if (listaTipos != null) {
			ListModelList<TipoArticulos> listModel = new ListModelList<TipoArticulos>(
					listaTipos);
			cmb_tipo.setModel(listModel);
		}
		List<Usuarios> listaUsuarios = dbu.buscarUsuarios("");
		if (listaUsuarios != null) {
			ListModelList<Usuarios> listModel = new ListModelList<Usuarios>(
					listaUsuarios);
			comboAutor2.setModel(listModel);
			// comboAutor3.setModel(listModel);
		}
		List<Usuarios> listaUsuarios1 = dbu.buscarUsuarios("");
		if (listaUsuarios1 != null) {
			ListModelList<Usuarios> listModel = new ListModelList<Usuarios>(
					listaUsuarios1);
			// comboAutor2.setModel(listModel);
			comboAutor3.setModel(listModel);
		}
		DBAreas dba = new DBAreas();
		List<Areas> listaAreas = dba.buscarArea();
		if (listaTipos != null) {
			ListModelList<Areas> listModel = new ListModelList<Areas>(
					listaAreas);
			cmb_area.setModel(listModel);
		}
	}

	public void onClick$button_Registrar() throws Exception {
		a = new Articulo();
		obtenerRutaArchivoAdjuntado();
		boolean result = false;
		DBArticulos dbart = new DBArticulos();
		a.setTipo_id(idTipoArticulo);
		a.setId_area(idTipoArea);
		a.setArt_titulo(textbox_Titulo.getValue());
		a.setArt_resumen(textbox_Resumen.getValue());
		a.setArt_palabras_clave(textbox_PClaves.getValue());
		a.setArt_fecha_subida(txtfecha.getText());
		System.out.println("fecha "+txtfecha.getText());
		/*
		 * a.set
		 * 
		 * a.setArt_archivo(direccion);
		 * 
		 * 
		 * a.setArt_estado(1); /* a.setArt_titulo(textbox_Titulo.getValue());
		 * a.setArt_archivo(ruta.getValue());
		 * a.setArt_resumen(textbox_Resumen.getValue());
		 * a.setArt_palabras_clave(textbox_Resumen.getValue());
		 * a.setArt_fecha_subida(txtfecha.getValue()); a.setArt_estado(1);
		 * a.setTipo_id(1); a.setArea_id_padre(0); a.setId_estado(1); result =
		 * dbart.RegistrarArticulo(a); if (result) {
		 * alert("Articulo registrado con exito"); } else {
		 * alert("No se pudo realizar el registro"); } // pa para guardar datos
		 * de otros autores *colaboradores de ese mismo // articulo // aquí iba
		 * a validar si hay datos en los comboAutor2 y comboAutor3, // para
		 * proceder a guardar // porque si no hay mas autores la tabla
		 * tb_persona_articulo no es // obligatoria llenarla pa.setPers_id(1);
		 * pa.setArti_id(1); pa.setPer_art_estado(1); pa.setPer_id_registra(1);
		 * result = dbart.RegistrarPersonaArticulo(pa);
		 * 
		 * 
		 * 
		 * // Usuarios usua=new Usuarios(); // /* Session session =
		 * Sessions.getCurrent(); Usuarios usua = (Usuarios)
		 * session.getAttribute("User"); int idUsuario =usua.getId();
		 * System.out.println(" id user "+usua.getId());
		 */
	}

	public void obtenerRutaArchivoAdjuntado() {
		Util u = new Util();
		direccion = u.ruta + "/" + NombreArchivo;
		System.out.println(direccion);
	}

	public void onSelect$cmb_tipo() {
		TipoArticulos usu = (TipoArticulos) cmb_tipo.getSelectedItem()
				.getValue();
		if (usu != null) {
			idTipoArticulo = (usu.getTipo_id());
			System.out.println("id tipo articulo:  " + idTipoArticulo);
		}
	}

	public void onSelect$comboAutor2() {
		Usuarios usu = (Usuarios) comboAutor2.getSelectedItem().getValue();
		if (usu != null) {
			idAutor2 = (usu.getId());
			System.out.println("autor2: " + idAutor2);
		}
	}

	public void onSelect$comboAutor3() {
		Usuarios usu = (Usuarios) comboAutor3.getSelectedItem().getValue();
		if (usu != null) {
			idAutor3 = (usu.getId());
			System.out.println("autor3: " + idAutor3);
		}
	}

	public void onSelect$cmb_area() {
		Areas usu = (Areas) cmb_area.getSelectedItem().getValue();
		if (usu != null) {
			idTipoArea = (usu.getArea_id());
			System.out.println("idTipo ares: " + idTipoArea);
		}
	}
}
