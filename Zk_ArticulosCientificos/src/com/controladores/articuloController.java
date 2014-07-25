package com.controladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.SubirDescargarArchivos.Util;
import com.datos.DBAreas;
import com.datos.DBArticulos;
import com.datos.DBTipoArticulos;
import com.datos.DBUsuario;
import com.entidades.Areas;
import com.entidades.Articulo;
import com.entidades.EstadoArticulo;
import com.entidades.Persona;
import com.entidades.PersonaArticulo;
import com.entidades.TipoArticulos;
import com.entidades.UsuarioArea;
import com.entidades.Usuarios;

public class articuloController extends GenericForwardComposer<Component> {
	private Combobox cmb_tipo, comboAutor2, comboAutor3, cmb_area;
	// public Textbox textbox_Titulo, textbox_Resumen, textbox_PClaves;
	// public Button button_Registrar, btnExaminar;
	public Label nombreArchivo, nombreArticuloCorregido,lblDescargar;
	public Textbox textbox_Titulo, textbox_Resumen, textbox_PClaves,
			textbox_autor;
	public Button button_Registrar, btnExaminar, button_Nuevo,button_descarga;
	public Label nombreArticulo, titulo, nombreAutorP, nombreAutoresS, resumen,
			palabrasClaves, fechaRecibido, institucion, direccionInstitucion,
			idtipo, idarea;
	public Datebox txtfecha;
	public Date fecha;
	private Articulo a = null;
	private PersonaArticulo pa = null;

	private EstadoArticulo ea = null;
	public int idTipoArticulo = 0, idTipoArea = 0, idAutor2 = 0, idAutor3 = 0,
			idUsuario = 0, idArticuloSubido = 0;
	private Articulo art = null;
	String NombreArchi, direccion, nom;
	boolean result = false;
	boolean result2 = false;
	boolean result3 = false;
	DBArticulos dbart = new DBArticulos();
String direccionArticuloCorregido;
	// //----------------------------------------------------------------------------
	// codigo para subir un archivo al servidor
	public Media media;
	public Window winDetalleArticulo, winSubirArticulo;

	File f;

	@NotifyChange("media")
	@Command
	public void onUpload$btnExaminar(
			@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) {
		media = event.getMedia();

		String extension = media.getFormat();
		if (extension.equals("docx")) {
			NombreArchi = media.getName();
			nombreArticulo.setValue(NombreArchi);
		} else {
			alert("Debe subir un archivo de Word, este tiene una extensi�n :"
					+ extension);
			NombreArchi = "";
			nombreArticulo.setValue("");
		}
	}

	public Media getMedia() {
		return media;
	}

	// simply download the file
	@Command
	public void downloadFile(Media media) {
		if (media != null)
			Filedownload.save(media);
		alert("descarga exitosa");
	}

	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

	}

	public void Combos() {
		DBTipoArticulos dbtp = new DBTipoArticulos();
		TipoArticulos tipo = new TipoArticulos();
		DBUsuario dbu = new DBUsuario();
		List<TipoArticulos> listaTipos = dbtp.buscarTipos();
		if (listaTipos != null) {
			ListModelList<TipoArticulos> listModel = new ListModelList<TipoArticulos>(
					listaTipos);
			cmb_tipo.setModel(listModel);
			idtipo.setValue(Integer.toString(tipo.getTipo_id()));
		}
		List<Usuarios> listaUsuarios = dbu.buscarUsuarios("");
		if (listaUsuarios != null) {
			ListModelList<Usuarios> listModel = new ListModelList<Usuarios>(
					listaUsuarios);
			comboAutor2.setModel(listModel);
		}
		List<Usuarios> listaUsuarios1 = dbu.buscarUsuarios("");
		if (listaUsuarios1 != null) {
			ListModelList<Usuarios> listModel = new ListModelList<Usuarios>(
					listaUsuarios1);
			// comboAutor2.setModel(listModel);
			comboAutor3.setModel(listModel);
		}
		DBAreas dba = new DBAreas();
		Areas ar = new Areas();
		List<Areas> listaAreas = dba.buscarArea();
		if (listaTipos != null) {
			ListModelList<Areas> listModel = new ListModelList<Areas>(
					listaAreas);
			cmb_area.setModel(listModel);
			idarea.setValue(Integer.toString(ar.getArea_id()));
		}
	}

	public void onClick$button_Registrar() throws Exception {

		fecha = txtfecha.getValue();
		Sessions.getCurrent();
		Usuarios usua = (Usuarios) session.getAttribute("User");
		idUsuario = usua.getId();
		a = new Articulo();
		pa = new PersonaArticulo();
		ea = new EstadoArticulo();
		try {

			if (textbox_Titulo.equals("") || textbox_Resumen.equals("")
					|| textbox_PClaves.equals("") || txtfecha.equals(null)
					|| cmb_tipo.equals(null) || cmb_area.equals(null)
					|| comboAutor2.equals(null) || comboAutor3.equals(null)) {
				alert("Ingrese todos los campos");
			} else {

				if (nombreArticulo.getValue() != "Archivo.doc") {
					if (art == null) {
						if (Util.uploadFile(media)) {
							obtenerRutaArchivoAdjuntado();
							a.setTipo_id(idTipoArticulo);
							a.setId_area(idTipoArea);
							a.setArt_titulo(textbox_Titulo.getValue());
							a.setArt_resumen(textbox_Resumen.getValue());
							a.setArt_palabras_clave(textbox_PClaves.getValue());
							a.setArt_fecha_subida(fecha);
							a.setArt_archivo(direccion);
							a.setArt_estado(1);
							// a.setId_estado(1);
							a.setPer_id(idUsuario);
							result = dbart.RegistrarArticulo(a);
							if (result) {
								alert("Articulo registrado con exito");
							} else {
								alert("No se pudo realizar el registro");
							}
						} else {
							Messagebox.show(Labels.getLabel("app.error"));
						}
						Colaboradores();
					} else {
						alert("existe");
						if (Util.uploadFile(media)) {
							obtenerRutaArchivoAdjuntado();
							a.setArt_id(a.getArt_id());
							alert(a.getArt_id() + "");
							/*
							 * a.setTipo_id(idTipoArticulo);
							 * a.setId_area(idTipoArea);
							 * a.setArt_titulo(textbox_Titulo.getValue());
							 * a.setArt_resumen(textbox_Resumen.getValue());
							 * a.setArt_palabras_clave
							 * (textbox_PClaves.getValue());
							 * a.setArt_fecha_subida(fecha);
							 * a.setArt_archivo(direccion); a.setArt_estado(1);
							 * // a.setId_estado(1); a.setPer_id(idUsuario);
							 * result = dbart.RegistrarArticulo(a); if (result)
							 * { alert("Articulo registrado con exito"); } else
							 * { alert("No se pudo realizar el registro"); } }
							 * else {
							 * Messagebox.show(Labels.getLabel("app.error")); }
							 * Colaboradores();
							 */
						}
					}

				} else {
					alert("Suba articulo");
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			alert("Suba articulo");
		}

		media = null;
		limpiar();

	}

	public void Colaboradores() {
		if (comboAutor2.getValue() != null) {
			ObtenerIdArticuloRegistrado(direccion);

			System.out.println(" id autor2 en guardar: " + idAutor2);
			pa.setPers_id(idAutor2);
			pa.setArti_id(idArticuloSubido);
			pa.setPer_art_estado(1);
			pa.setPer_id_registra(idUsuario);
			result2 = dbart.RegistrarPersonaArticulo(pa);
		}
		if (comboAutor3.getValue() != null) {
			ObtenerIdArticuloRegistrado(direccion);
			pa.setPers_id(idAutor3);
			pa.setArti_id(idArticuloSubido);
			pa.setPer_art_estado(1);
			pa.setPer_id_registra(idUsuario);
			result2 = dbart.RegistrarPersonaArticulo(pa);
		}
		ObtenerIdArticuloRegistrado(direccion);
		ea.setId_articulo(idArticuloSubido);
		ea.setId_estado(1);
		ea.setId_persona(idUsuario);
		result3 = dbart.RegistrarEstadoArticulo(ea);
	}

	public void limpiar() {
		textbox_Titulo.setValue("");
		textbox_Resumen.setValue("");
		textbox_PClaves.setValue("");
		cmb_tipo.setValue("");
		comboAutor2.setValue("");
		comboAutor3.setValue("");
		cmb_area.setValue("");
		nombreArticulo.setValue("");
		txtfecha.setValue(null);
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

	public void onSelect$cmb_tipo() {
		TipoArticulos usu = (TipoArticulos) cmb_tipo.getSelectedItem()
				.getValue();
		if (usu != null) {
			idTipoArticulo = (usu.getTipo_id());
			System.out.println("id tipo articulo:  " + idTipoArticulo);
		}
	}

	public void onSelect$comboAutor2() {
		Usuarios usua = (Usuarios) comboAutor2.getSelectedItem().getValue();
		if (usua != null) {
			idAutor2 = (usua.getId());
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

	public void ListarTodosArticulos() {
	}

	public void onCreate$winDetalleArticulo() {

		art = (Articulo) winDetalleArticulo.getAttribute("articulo");
		if (art != null) {
			titulo.setValue(art.getArt_titulo());
			nombreAutorP.setValue(art.getPer_nombre() + " "
					+ art.getPer_apellido() + ". \n"
					+ art.getPer_institucion1());
			nombreAutoresS.setValue(art.getNom_colaborador() + ". \n"
					+ art.getPer_institucion2());
			resumen.setValue(art.getArt_resumen());
			palabrasClaves.setValue(art.getArt_palabras_clave());
			fechaRecibido.setValue(art.getArt_fecha_subida().toString());
		}
	}

	public void onCreate$winSubirArticulo() {
		Combos();
		TipoArticulos dbtp = new TipoArticulos();
		art = (Articulo) winSubirArticulo.getAttribute("articulo");
		Session session = Sessions.getCurrent();
		Usuarios u = (Usuarios) session.getAttribute("User");
		textbox_autor.setText(u.getPersona().getPer_nombre() + " "
				+ u.getPersona().getPer_apellido());
		if (art != null) {
			// System.out.println("arti titulo: " + art.getArt_titulo());
			textbox_Titulo.setText(art.getArt_titulo());
			textbox_Resumen.setText(art.getArt_resumen());
			textbox_PClaves.setText(art.getArt_palabras_clave());
			// comboAutor2.setValue(art.getNom_colaborador());
			cmb_tipo.setValue(art.getTipo_nombre());
			cmb_area.setValue(art.getArea_nombre());

			DBArticulos da = new DBArticulos();
			List<Persona> li = da.buscarColaboradores(art.getArt_id());
			System.out.println("lista: " + li.size());
			comboAutor2.setValue(li.get(0).getPer_nombre() + " "
					+ li.get(0).getPer_apellido());
			if (li.size() > 1) {
				comboAutor3.setValue(li.get(1).getPer_nombre() + " "
						+ li.get(1).getPer_apellido());
			}
			if (art.getNombreArticulo().trim().length() > 0
					|| art.getObservacion().trim().length() > 0) {
				// /alert("tiene notificaciones observaciones ");
				System.out.println("nombre articulo max cadena: "
						+ art.getNombreArticulo() + " "
						+ art.getNombreArticulo().trim().length());
				nombreArticuloCorregido.setValue(art.getNombreArticulo());
				direccionArticuloCorregido=art.getRuta();
				
			} else {
				System.out.println("no tiene notificaciones :");
				button_descarga.setVisible(false);
				nombreArticuloCorregido.setVisible(false);
				lblDescargar.setVisible(false);
			}

		}
	}

	@Command
	public void onClick$button_descarga() {
		// Articulo art = (Articulo) listaArticulosporpar.getSelectedItem()
		// .getValue();
		// nombreArticulo.setVisible(true);
		nombreArticuloCorregido.setValue(art.getArt_archivo());
		f = new File(direccionArticuloCorregido);
		// System.out.println("nombre completo" + nombreArticulo.getValue());
		try {
			Filedownload.save(f, null);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		alert("descarga exitosa");
	}
}
