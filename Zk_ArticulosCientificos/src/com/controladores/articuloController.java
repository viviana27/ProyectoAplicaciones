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
import org.zkoss.zul.Div;
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
import com.datos.DBArticulosEvaluados;
import com.datos.DBTipoArticulos;
import com.datos.DBUsuario;
import com.entidades.Areas;
import com.entidades.Articulo;
import com.entidades.EstadoArticulo;
import com.entidades.ObservacionesEvaluadores;
import com.entidades.Persona;
import com.entidades.PersonaArticulo;
import com.entidades.TipoArticulos;
import com.entidades.UsuarioArea;
import com.entidades.Usuarios;

public class articuloController extends GenericForwardComposer<Component> {
	private Combobox cmb_tipo, comboAutor2, comboAutor3, cmb_area;
	// public Textbox textbox_Titulo, textbox_Resumen, textbox_PClaves;
	// public Button button_Registrar, btnExaminar;
	public Label nombreArchivo, nombreArticuloCorregido,
			nombreArticuloCorregido2, lblDescargar, lblidArticulo,
			lblCalificacion, lblCantidadEvaluaciones, evaluadores;
	public Textbox textbox_Titulo, textbox_Resumen, textbox_PClaves,
			textbox_autor, txtObservaciones, txtObservaciones2;
	public Button button_Registrar, btnExaminar, button_Nuevo, button_descarga,
			button_descarga2;
	public Label nombreArticulo, titulo, nombreAutorP, nombreAutoresS, resumen,
			palabrasClaves, fechaRecibido, institucion, direccionInstitucion,
			idtipo, idarea, lblDescargar2;
	public Datebox txtfecha;
	public Date fecha;
	private Articulo a = new Articulo();;
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
	String direccionArticuloCorregido, direccionArticuloCorregido2;
	int ida = 0, id = 0;
	int idart = 0, idpadre1 = 0, regcontar = 0, reconta = 0;
	Div divEvaluadores,detalle;

	// //----------------------------------------------------------------------------
	// codigo para subir un archivo al servidor
	public Media media;
	public Window winDetalleArticulo, winSubirArticulo,	winObservacionesArticulo;

	File f, f2;

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
			alert("Debe subir un archivo de Word, este tiene una extensión :"
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
		List<Usuarios> listaUsuarios = dbu.buscarUsuariosAutores("");
		if (listaUsuarios != null) {
			ListModelList<Usuarios> listModel = new ListModelList<Usuarios>(
					listaUsuarios);
			comboAutor2.setModel(listModel);
		}
		List<Usuarios> listaUsuarios1 = dbu.buscarUsuariosAutores("");
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
		DBArticulos dbart = new DBArticulos();

		Sessions.getCurrent();
		Usuarios usua = (Usuarios) session.getAttribute("User");
		idUsuario = usua.getId();

		pa = new PersonaArticulo();
		ea = new EstadoArticulo();
		try {
			if (textbox_Titulo.equals("") || textbox_Resumen.equals("")
					|| textbox_PClaves.equals("") || cmb_tipo.equals(null)
					|| cmb_area.equals(null) || comboAutor2.equals(null)
					|| comboAutor3.equals(null)) {
				alert("Ingrese todos los campos");
			} else {

				if (nombreArticulo.getValue().trim().length() > 0) {
					if (art == null) {
						if (Util.uploadFile(media)) {
							obtenerRutaArchivoAdjuntado();
							a.setTipo_id(idTipoArticulo);
							a.setId_area(idTipoArea);
							a.setArt_titulo(textbox_Titulo.getValue());
							a.setArt_resumen(textbox_Resumen.getValue());
							a.setArt_palabras_clave(textbox_PClaves.getValue());
							fecha = txtfecha.getValue();
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
						// alert("existe");
						DBArticulos contac = new DBArticulos();
						reconta = contac.Cantidadcorreciones(idpadre1);
						if (reconta == 0) {
							if (Util.uploadFile(media)) {
								obtenerRutaArchivoAdjuntado();
								onSelect$cmb_tipo();
								onSelect$comboAutor2();
								onSelect$comboAutor3();
								onSelect$cmb_area();
								a = new Articulo();
								a.setArt_titulo(textbox_Titulo.getValue()
										.trim());
								a.setArt_archivo(direccion);
								a.setArt_resumen(textbox_Resumen.getValue()
										.trim());
								a.setArt_palabras_clave(textbox_PClaves
										.getValue().trim());
								a.setArt_estado(1);
								a.setTipo_id(idTipoArticulo);
								a.setId_area(idTipoArea);
								a.setPer_id(idUsuario);
								a.setIdPadre(Integer.parseInt(lblidArticulo
										.getValue().trim()));
								a.setPadre(Integer.parseInt(lblidArticulo
										.getValue().trim()));
								System.out.println("idpadreprincipal" + idart);
								System.out.println("reconta" + reconta);
								result = dbart.RegistrarArticuloModificado(a,
										idAutor2, idAutor3);
								if (result) {
									alert("Articulo registrado con exito");
								} else {
									alert("No se pudo realizar el registro");
								}
								Colaboradores2();

							}
						} else {
							if (reconta > 0 && reconta <= 1) {
								if (Util.uploadFile(media)) {
									obtenerRutaArchivoAdjuntado();
									onSelect$cmb_tipo();
									onSelect$comboAutor2();
									onSelect$comboAutor3();
									onSelect$cmb_area();
									a = new Articulo();
									a.setArt_titulo(textbox_Titulo.getValue()
											.trim());
									a.setArt_archivo(direccion);
									a.setArt_resumen(textbox_Resumen.getValue()
											.trim());
									a.setArt_palabras_clave(textbox_PClaves
											.getValue().trim());
									a.setArt_estado(1);
									a.setTipo_id(idTipoArticulo);
									a.setId_area(idTipoArea);
									a.setPer_id(idUsuario);
									a.setIdPadre(Integer.parseInt(lblidArticulo
											.getValue().trim()));
									a.setPadre(idpadre1);
									System.out.println("reconta" + reconta);
									System.out.println("idpadreprincipal"
											+ idpadre1);
									result = dbart.RegistrarArticuloModificado(
											a, idAutor2, idAutor3);
									if (result) {
										alert("Articulo registrado con exito");
									} else {
										alert("No se pudo realizar el registro");
									}
									Colaboradores2();

								}
							} else {
								if (reconta == 2) {
									alert("ya no puede subir una nueva correccion del articulo");

								}
							}
						}
					}

				} else {
					alert("Suba articulo+");
				}
			}

		} catch (Exception e) { // TODO: handle exception
			alert("Ingrese Todos los Campos y Suba el Articulo");
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
		ea.setId_utl_estado(1);
		ea.setId_persona(idUsuario);
		result3 = dbart.RegistrarEstadoArticulo(ea);
	}

	public void Colaboradores2() {
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
		ea.setId_estado(2);
		ea.setId_utl_estado(1);
		ea.setId_persona(idUsuario);
		result3 = dbart.RegistrarEstadoArticulo(ea);
	}

	public void Colaboradores3() {
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
		ea.setId_estado(5);
		ea.setId_utl_estado(1);
		ea.setId_persona(idUsuario);
		DBArticulos dba=new DBArticulos();
		result3 = dbart.RegistrarEstadoArticulo(ea);
		List<EstadoArticulo> listaestado = dba.buscarestados(idArticuloSubido);
		id = listaestado.get(0).getId();
		result2 = dba.Actualizr_estadoar(id, idArticuloSubido);
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
		// txtfecha.setValue(null);
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
	   // int idE = (Integer) winDetalleArticulo.getAttribute("idEstado");
		//&& idE == 1
		if (art != null ) {
			titulo.setValue(art.getArt_titulo());
			nombreAutorP.setValue(art.getNom_colaborador() + " " + ". \n"
					+ art.getPer_institucion1());
			nombreAutoresS.setValue(art.getNom_colaborador1() + ". \n"
					+ art.getPer_institucion2());
			resumen.setValue(art.getArt_resumen());
			palabrasClaves.setValue(art.getArt_palabras_clave());
			fechaRecibido.setValue(art.getArt_fecha_subida().toString());

		} else {
			detalle.setVisible(false);
			divEvaluadores.setVisible(true);
			String inf = (String) winDetalleArticulo.getAttribute("evaluadores");
			evaluadores.setValue(inf);
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
			idart = art.getArt_id();
			idpadre1 = art.getPadre();
			System.out.println("id ultimo paaaaa" + idpadre1);
			System.out.println("en Modificar Articulo el idArticulo: "
					+ art.getArt_id());
			lblidArticulo.setValue(Integer.toString(art.getArt_id()));
			buscarPromedioArticulo(art.getArt_id());
			txtObservaciones.setText(art.getObservacion());
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

			if (art.getNombreArticulo().trim().length() > 0) {
				// /alert("tiene notificaciones observaciones ");
				System.out.println("nombre articulo max cadena: "
						+ art.getNombreArticulo() + " "
						+ art.getNombreArticulo().trim().length());
				nombreArticuloCorregido.setValue(art.getNombreArticulo());
				direccionArticuloCorregido = art.getRuta();

			} else {
				System.out.println("no tiene observaciones adjuntas :");
				button_descarga.setVisible(false);
				nombreArticuloCorregido.setVisible(false);
				lblDescargar.setVisible(false);
			}
			if (art.getObservacion().trim().length() > 0) {
				System.out.println("tiene observaciones escritas");
			} else {
				System.out.println("no tiene observaciones escritas");
			}
			
			DBArticulos dbt=new DBArticulos();
			List<ObservacionesEvaluadores> lista=dbt.ObservacionesEvaluaciones(art.getArt_id());
			if (lista.size()>0){
				if(lista.get(1).getDireccion().trim().length()>0){
				//txtObservaciones2.setValue(lista.get(1).getEval_observacion());
				nombreArticuloCorregido2.setValue(lista.get(1).getNombre());	
				direccionArticuloCorregido2 = lista.get(1).getDireccion();}
				else{
					button_descarga2.setVisible(false);
					lblDescargar2.setVisible(false);
					nombreArticuloCorregido2.setVisible(false);
				}
				if (lista.get(1).getEval_observacion().trim().length() > 0) {
					txtObservaciones2.setValue(lista.get(1)
							.getEval_observacion());
				}
			}

		}
	}

	public void buscarPromedioArticulo(int idArticulo) {
		dbart = new DBArticulos();
		lblCalificacion.setValue(Integer.toString(dbart
				.buscarPromedioArticulo(idArticulo)));
		lblCantidadEvaluaciones.setValue(Integer.toString(dbart
				.CantidadEvaluaciones(idArticulo)));

	}

	@Command
	public void onClick$button_descarga() {

		// nombreArticuloCorregido.setValue(art.getArt_archivo());
		f = new File(direccionArticuloCorregido);
		try {
			Filedownload.save(f, null);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		alert("descarga exitosa");
	}

	public void onClick$button_descarga2() {

		f2 = new File(direccionArticuloCorregido2);
		try {
			Filedownload.save(f2, null);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		alert("descarga exitosa");
	}

	public void onCreate$winObservacionesArticulo() {
		art = (Articulo) winObservacionesArticulo.getAttribute("articulo");
		if (art != null) {
			txtObservaciones.setText(art.getObservacion());
			if (art.getNombreArticulo().trim().length() > 0) {
				// /alert("tiene notificaciones observaciones ");
				System.out.println("nombre articulo max cadena: "
						+ art.getNombreArticulo() + " "
						+ art.getNombreArticulo().trim().length());
				nombreArticuloCorregido.setValue(art.getNombreArticulo());
				direccionArticuloCorregido = art.getRuta();
	
			} else {
				System.out.println("no tiene observaciones adjuntas :");
				button_descarga.setVisible(false);
				nombreArticuloCorregido.setVisible(false);
				lblDescargar.setVisible(false);
			}
			if (art.getObservacion().trim().length() > 0) {
				System.out.println("tiene observaciones escritas");
			} else {
				System.out.println("no tiene observaciones escritas");
			}
			
			DBArticulos dbt=new DBArticulos();
			List<ObservacionesEvaluadores> lista=dbt.ObservacionesEvaluaciones(art.getArt_id());
			if (lista.size()>0){
				if(lista.get(1).getDireccion().trim().length()>0){
				//txtObservaciones2.setValue(lista.get(1).getEval_observacion());
				nombreArticuloCorregido2.setValue(lista.get(1).getNombre());
				direccionArticuloCorregido2 = lista.get(1).getDireccion();}
				else{
					button_descarga2.setVisible(false);
					lblDescargar2.setVisible(false);
					nombreArticuloCorregido2.setVisible(false);
				}
				if(lista.get(1).getEval_observacion().trim().length()>0){
					txtObservaciones2.setValue(lista.get(1).getEval_observacion());
				}
			}
			
			
		}
	}

}
