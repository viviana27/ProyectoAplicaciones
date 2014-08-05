package com.controladores;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.SubirDescargarArchivos.Util;
import com.datos.DBArticulos;
import com.datos.DBArticulosEvaluados;
import com.datos.DBParametrosArticulos;
import com.datos.DBParametrosEvaluacion;
import com.datos.DBPermiso;
import com.entidades.Articulo;
import com.entidades.ArticuloEvaluado;
import com.entidades.EstadoArticulo;
import com.entidades.ParametrosArticulo;
import com.entidades.ParametrosEvaluacion;
import com.entidades.Pares;
import com.entidades.Permiso;
import com.entidades.Usuarios;

public class evaluacionController extends GenericForwardComposer<Component> {
	public Label nombreArch, tit, caliFinal;
	Doublebox calific;
	Button button_Registrar, button_Registrar12, button_Obs;
	Window WinEvaluarArticulo;
	Listbox parametros;
	int reg_evaluados, evaluador;
	ListModelList<ParametrosEvaluacion> listModel;
	List<ParametrosArticulo> listaParam = new ArrayList<ParametrosArticulo>();
	Articulo art;
	private Usuarios u = null;
	private Date fecha = new Date(0);
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	java.sql.Date fechaActual = new java.sql.Date(0);
	// java.util.Date fechaActual = new java.util.Date();
	int vmax, vprome, idArticuloSubido = 0;

	int cont;
	int cont1;
	int cont2;
	int cantid2 = 0, prom = 0;
	boolean registro = false;
	public Media media = null;
	ArticuloEvaluado are = null;
	int ida = 0, id = 0;
	public Window winDetalleArticulo, winSubirArticulo;
	int valorMax = 0;
	int existe;
	double acum = 0;
	Double valor = 0.0;
	Textbox txtObservacion;
	String NombreArchi, direccion, nom;
	String email2;

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
		System.out.println("La fecha de hoy es:" + fechaActual);
	}

	public void onCreate$WinEvaluarArticulo() {

		art = (Articulo) WinEvaluarArticulo.getAttribute("articulo");
		if (art != null) {
			tit.setValue(art.getArt_titulo());

			ida = art.getArt_id();
			System.out.println("EL NUEVO ID" + ida);
		} else {
			alert("No se pudo recuperar información del artículo");
		}
		actualizarLista();

	}

	public void actualizarLista() {
		DBParametrosEvaluacion dbp = new DBParametrosEvaluacion();
		List<ParametrosEvaluacion> lista = dbp.buscarParametrosEvaluacion("");

		listModel = new ListModelList<ParametrosEvaluacion>(lista);
		parametros.setModel(listModel);
		parametros.renderAll();
		for (int y = 0; y <= listModel.size() - 1; y++) {
			Listitem item = (Listitem) parametros.getItems().get(y);
			List<Component> lceldas = item.getChildren();
			Listcell lc = (Listcell) lceldas.get(3);
			Doublebox dt = new Doublebox();
			dt.setId("" + y);
			dt.setParent(lc);
			dt.addEventListener("onChange", new Mylistener());
			// alert("" + lc.getLabel());
			// dt.addForward("onClick", dt, "onBlur");
		}
	}

	class Mylistener implements EventListener {
		public void onEvent(Event arg0) throws Exception {
			Doublebox cajax = (Doublebox) arg0.getTarget();
			int i = Integer.parseInt(cajax.getId());
			valor = cajax.getValue();
			
			Listitem item = (Listitem) parametros.getItems().get(i);
			List<Component> listaceldas = item.getChildren();
			Listcell valorM = (Listcell) listaceldas.get(2);
			int valorMax = Integer.parseInt(valorM.getLabel());
			if (valor < 0 || valor > valorMax || valor.equals("")) {
				alert("verifique calificación");
				item.setStyle("background-color: red;");
				cajax.setValue(null);
			} else {
				item.setStyle("");
				acum = acum + valor;
				caliFinal.setValue(acum + " /100");
			}
		
		}
	}

	public boolean recorrerLista() {

		Usuarios u = (Usuarios) session.getAttribute("User");
		ParametrosArticulo pa = null;
		int errores = 0, idParam = 0;
		try {
			System.out.println("tamaño lista recorrida:"
					+ (listModel.size() - 1));

			for (int i = 0; i <= listModel.size() - 1; i++) {
				pa = new ParametrosArticulo();
				Listitem item = (Listitem) parametros.getItems().get(i);
				List<Component> listaceldas = item.getChildren();
				Listcell pId = (Listcell) listaceldas.get(0);
				Listcell pVal = (Listcell) listaceldas.get(3);
				idParam = Integer.parseInt(pId.getLabel());
				Double valor = ((Doublebox) pVal.getFirstChild()).getValue();
				if (item.getStyle() == "background-color: red;") {
					errores++;
				} else {
					pa.setParam_art_valor(valor);
					pa.setParam_id(idParam);
					pa.setPerson_id(u.getId());
					pa.setArticul_id(art.getArt_id());
					email2 = art.getEmail();
					listaParam.add(pa);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			alert("Debe ingresar calificaciones");
		}
		if (errores > 0) {
			return false;
		} else {
			return true;
		}
	}
	


	public void onClick$button_Registrar12() {
		Usuarios u = (Usuarios) session.getAttribute("User");
		EstadoArticulo ea = new EstadoArticulo();
		DBArticulos dba = new DBArticulos();
		boolean bandera = false;
		boolean result = false;
		boolean result1 = false;
		boolean result2 = false;
		boolean result4 = false;
		ArticuloEvaluado arte = new ArticuloEvaluado();
		DBArticulosEvaluados dbaev = new DBArticulosEvaluados();
		DBArticulos dbar = new DBArticulos();
		if (direccion != null) {
		} else {
			if (recorrerLista() == true) {
				evaluador = dbaev.existeEvaluador(ida);
				reg_evaluados = dbaev.contarEvaluados(ida);

				System.out
						.println("el reg contar primero  es " + reg_evaluados);

				if (reg_evaluados < 2 && u.getId() != evaluador) {

					DBParametrosArticulos dbp = new DBParametrosArticulos();
					bandera = dbp.guardarEvaluacion(listaParam);
					if (media != null) {
						if (Util.uploadFileObservacion(media)) {
							alert("se subio");
							obtenerRutaArchivoAdjuntado();
							arte.setNombre(nom);
							arte.setDireccion(direccion);
						}
					} else {
						// alert("Q pasa");

						arte.setNombre("");
						arte.setDireccion("");
					}

					// tabla estado articulo

					ea.setId_articulo(ida);
					System.out.println("id articulo evaluacion: " + ida);
					ea.setId_estado(3);
					System.out
							.println("id estado evaluado" + ea.getId_estado());
					ea.setId_utl_estado(1);
					ea.setId_persona(u.getId());
					result = dbaev.RegistrarEstadoArticulo(ea);

					DBArticulosEvaluados dbart = new DBArticulosEvaluados();
					cantid2 = dbart.contarEvaluados(ida);
					System.out.println("la cantidad en evaluacion controller "
							+ cantid2);

					// tabla articulo evaluado

					arte.setEval_promedio(acum);
					arte.setEval_cantidad(1);
					arte.setEstad_id(3);
					arte.setEval_estado(1);
					arte.setAr_id(ida);
					arte.setEval_observacion(txtObservacion.getValue().trim());

					if (acum < 70
							&& (txtObservacion.getValue().trim().length() < 1 && media == null)) {
						alert("articulo con calificacion menor a 70, por favor adjuntar o escribir observaciones");
					} else {
						// para articulo con estado observado

						result1 = dbaev.RegistroArt_Evaluados(arte);
						ea.setId_articulo(ida);
						System.out.println("id articulo evaluacion: " + ida);
						ea.setId_estado(6);
						System.out.println("id estado evaluado"
								+ ea.getId_estado());
						ea.setId_utl_estado(1);
						ea.setId_persona(u.getId());
						result = dbaev.RegistrarEstadoArticulo(ea);

					}

					System.out.println("el reg contar del if es "
							+ reg_evaluados);
					if (bandera && result && result1) {

						alert("Evaluación registrada con exito");

						String opcion = (String) WinEvaluarArticulo
								.getAttribute("opcion");
						if (opcion != null && opcion.equals("Evaluacion")) {
							ListaArticulosAsignados lac = (ListaArticulosAsignados) WinEvaluarArticulo
									.getAttribute("controladorOrigen");
							if (lac != null)
								lac.actualizarLista();
							WinEvaluarArticulo.detach();
						} else {
							// alert("Fallamos...!");
						}
						/*
						 * if(result && result1){
						 * alert("Guardado Exitosamente");
						 * 
						 * } else{ alert("Fallamos...!"); }
						 */

					} else {
						String opcion = (String) WinEvaluarArticulo
								.getAttribute("opcion");
						alert("No se puede evaluar, el articulo ya fue evaluado");
						if (opcion != null && opcion.equals("Evaluacion")) {
							ListaArticulosAsignados lac = (ListaArticulosAsignados) WinEvaluarArticulo
									.getAttribute("controladorOrigen");
							if (lac != null)
								lac.actualizarLista();
							WinEvaluarArticulo.detach();
						}
					}
				}// FIN DEL REG EVALUADO
				System.out
						.println("El reg_evaluado antes del if para promedio es: "
								+ reg_evaluados);
				if (reg_evaluados == 1) {
					prom = dbar.buscarPromedioArticulo(ida);

					if (prom > 70) {
						System.out.println("El contador de si existe es 70: "
								+ cont);
						DBArticulos dbarti = new DBArticulos();
						existe = dbarti.IDpadre(ida);
						cont = dbarti.Cantidadcorreciones(existe);
						if (existe != 0) {
							System.out.println("El contador de si existe es: "
									+ cont);
							if (cont < 3) {
								System.out
										.println("Cuando el id articulo existe Kata");
								result2 = dbarti.ActualizarEstadoAceptado(ida);
								ea.setId_articulo(ida);
								System.out
										.println("id articulo evaluacion promedio: "
												+ ida);
								ea.setId_estado(4);
								System.out.println("id estado evaluado"
										+ ea.getId_estado());
								ea.setId_utl_estado(1);
								ea.setId_persona(u.getId());
								result4 = dbaev.RegistrarEstadoArticulo(ea);

							}
							System.out
									.println("El contador de si existe es antes de 2: "
											+ cont);
							if (cont == 2) {
								result2 = dbarti.ActualizarEstadoAceptado(ida);
								ea.setId_articulo(ida);
								System.out
										.println("id articulo evaluacion promedio: "
												+ ida);
								ea.setId_estado(4);
								System.out.println("id estado evaluado"
										+ ea.getId_estado());
								ea.setId_utl_estado(1);
								ea.setId_persona(u.getId());
								result4 = dbaev.RegistrarEstadoArticulo(ea);

							}
						} else {
							System.out
									.println("El contador de si existe es else: "
											+ cont);
							// actualizacion de estado
							DBArticulos dbart = new DBArticulos();
							result2 = dbart.ActualizarEstadoAceptado(ida);
							ea.setId_articulo(ida);
							System.out
									.println("id articulo evaluacion promedio: "
											+ ida);
							ea.setId_estado(4);
							System.out.println("id estado evaluado"
									+ ea.getId_estado());
							ea.setId_utl_estado(1);
							ea.setId_persona(u.getId());
							result4 = dbaev.RegistrarEstadoArticulo(ea);
						}
					}

					else {
						DBArticulos dbarti = new DBArticulos();
						existe = dbarti.IDpadre(ida);
						cont1 = dbarti.Cantidadcorreciones(existe);
						System.out
								.println("el cont 1 de padre es antes existe: "
										+ cont1);

						if (existe != 0) {
							if (cont1 < 3) {
								System.out.println("el cont 1 de padre es: "
										+ cont1);
								cont2 = dbarti.Cantidadcorreciones(cont1);

								result2 = dbarti.ActualizarEstadoAceptado(ida);

								ea.setId_articulo(ida);
								System.out
										.println("id articulo evaluacion promedio de nuevo estado 6 :): "
												+ ida);
								ea.setId_estado(6);
								System.out.println("id estado evaluado"
										+ ea.getId_estado());
								ea.setId_utl_estado(1);
								ea.setId_persona(u.getId());
								result4 = dbaev.RegistrarEstadoArticulo(ea);
								System.out
										.println("El contador de si existe es antes del cont1: "
												+ cont1);
							}
							if (cont1 == 2) {
								result2 = dbarti.act_rechazados(ida);
								ea.setId_articulo(ida);
								System.out
										.println("id articulo evaluacion promedio :) de nuevo estado 5: "
												+ ida);
								ea.setId_estado(5);
								System.out.println("id estado evaluado"
										+ ea.getId_estado());
								ea.setId_utl_estado(1);
								ea.setId_persona(u.getId());
								result4 = dbaev.RegistrarEstadoArticulo(ea);

							}
						}

					}
				}
				List<EstadoArticulo> listaestado = dba.buscarestados(ida);
				id = listaestado.get(1).getId();
				result2 = dba.Actualizr_estadoar(id, ida);
			} else {
				alert("corrija calificaciones altas");
			}
		}
	
	}

	public void ObtenerIdArticuloRegistrado(String direc) {
		DBArticulos dba = new DBArticulos();
		idArticuloSubido = dba.obtenerIdArticuloRegistrado(direc);
		// System.out.println("idArticulo registrado: " + idArticuloSubido);
	}

	public void obtenerRutaArchivoAdjuntado() {
		Util u = new Util();
		direccion = u.ruta + "/" + NombreArchi;
		nom = NombreArchi;
		System.out.println("Direccion EvaluacionController: " + direccion);
		System.out.println("Nombre file EvaluacionController " + nom);
	}

}
