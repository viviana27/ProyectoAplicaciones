package com.controladores;

import java.util.List;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

//import com.controller.Exception;
//import com.controller.String;
import com.datos.DBArticulos;
import com.datos.DBPares;
import com.datos.DBUsuario;
import com.entidades.Areas;
import com.entidades.Articulo;
import com.entidades.EstadoArticulo;
import com.entidades.Pares;
import com.entidades.Persona;
import com.entidades.Usuarios;
//import com.hibernate.deportistas.Tbmiembros;

public class AsignarEvaluadorController extends
		GenericForwardComposer<Component> {
	// se ejecuta al crear la ventana
	// registrar un nuevo evaluador
	private static final long serialVersionUID = 1L;
	int regcontar;
	int id=0;
	@Wire
	private Window winNuevoEvaluador;
	private Textbox textbox_Titulo, textbox_area;
	private Button button_Registrar;
	int idarea;
	Label lblidpersona, idestado;
	private Pares par = null;
	private Articulo art = null;
	private Combobox cmb_evaluador;
	int idarticulo = 0;
	int idpersona = 0;
	int idpersonaart = 0;
	int num_pares = 0;
	int idare = 0;
	int regcontar2 = 0;
	String email,email2;
	String persorecibe;
	Textbox remite,mensaje, recibe; 
	public void onClick$button_Registrar() throws Exception {
		boolean result = false;
		boolean result1 = false;
		boolean result2 = false;
		DBArticulos dba = new DBArticulos();
		EstadoArticulo ea = new EstadoArticulo();
		DBPares dbusuarios = new DBPares();
		System.out.println("idArticulonuevo:" + idarticulo);
		System.out.println("idpersonaaa:" + idpersona);
		if (par != null) {
			// existe estoy editando
			par.setArticulos_id(idarticulo);
			par.setPersonas_id(idpersona);
			par.setEstado_id(2);
			par.setCant_par(2);
			par.setPar_estado(1);
			// llamo al metodo para actualizar los datos

			result = dbusuarios.actualizarEvaluador(par);

		} else {
			Pares par = new Pares(0, idarticulo, idpersona, 2, 2, 1);
			result = dbusuarios.InsertarPar(par);
			regcontar = dbusuarios.contarRegistros(idarticulo);
			if (regcontar == 2) {
				ea.setId_articulo(idarticulo);
				ea.setId_estado(2);
				ea.setId_utl_estado(1);
				ea.setId_persona(idpersonaart);
				result1 = dbusuarios.RegistrarEstadoArticulo(ea);
			}
			
			//notificaciones

			enviarEmail();
			
			
		}
		if (result && result1) {
			alert("Articulo asignado a los evaluadores correctamente ");
			// evaluar desde donde fue llamada esta ventana
			String opcion = (String) winNuevoEvaluador.getAttribute("opcion");
			if (opcion != null && opcion.equals("Revision")) {
				// entonces la ventana ufe llamada desde lista usuarios
				// cerrar ventana

				// actualizar la lista de usuarios
				ListaArticulosEvaluador luc = (ListaArticulosEvaluador) winNuevoEvaluador
						.getAttribute("controladorOrigen");
				if (luc != null)
					luc.actualizarLista();
				// luc.actualizarLista();
				winNuevoEvaluador.detach();

			}
		} else {
			alert("Asignacion correcta... Asigne el siguiente evaluador");
			String opcion = (String) winNuevoEvaluador.getAttribute("opcion");
			if (opcion != null && opcion.equals("Revision")) {
				// entonces la ventana ufe llamada desde lista usuarios
				// cerrar ventana

				// actualizar la lista de usuarios
				ListaArticulosEvaluador luc = (ListaArticulosEvaluador) winNuevoEvaluador
						.getAttribute("controladorOrigen");
				if (luc != null)
					luc.actualizarLista();
				// luc.actualizarLista();
				winNuevoEvaluador.detach();
			}
		}
		//para actualizar estado
		List<EstadoArticulo> listaestado = dba.buscarestados(idarticulo);
		id = listaestado.get(0).getId();
		result2 = dba.Actualizr_estadoar(id, idarticulo);
	}

	public void onSelect$cmb_evaluador() {
		DBPares dbusuarios = new DBPares();
		DBUsuario dbu = new DBUsuario();
		Pares pares = (Pares) cmb_evaluador.getSelectedItem().getValue();
		email2=pares.getPer_email();
		int regcontar2 = dbusuarios.contarRegistros(idarticulo);

		if (regcontar2 == 2) {
			alert("No se permite el ingreso de un nuevo evaluador");
		} else {
			if (pares != null) {
				idpersona = pares.getPersonas_id();
			}

		}

	}
	
	//notificaciones a enviar al evaluador
	private void enviarEmail() {
	/*	try {
			// Propiedades de la conexión
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.auth", "true");

			// Preparamos la sesion	
			Session session = Session.getDefaultInstance(props);
			org.zkoss.zk.ui.Session sesion = Sessions.getCurrent();
			Usuarios u = (Usuarios) sesion.getAttribute("User");
			// Recoger los datos
			// String str_De = jtfRemitente.getText();
			String str_De = u.getPersona().getPer_email();
			String str_PwRemitente = "ponce1992";
			System.out.println("email remitente------------: "+u.getPersona().getPer_email());
			String str_Para = email2;
			System.out.println("email destino------------: "+str_Para);
			//String destinos[] = str_Para.split(",");
			String str_Asunto = "Artículo asignado a evaluar";
			String str_Mensaje = "Se le ha asignado un articulo a Evaluar por favor revisar en el sistema";
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(str_De));
			
			Address[] receptores = new Address[] { new InternetAddress(
					str_De) };
			receptores[0] = new InternetAddress(str_Para);
			// receptores.
			message.addRecipients(Message.RecipientType.TO, receptores);
			message.setSubject(str_Asunto);
			message.setText(str_Mensaje);
			// Lo enviamos.
			Transport t = ( session).getTransport("smtp");
			t.connect(str_De, str_PwRemitente);
			t.sendMessage(message,
					message.getRecipients(Message.RecipientType.TO));

			// Cierre de la conexion.
			t.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
		//try {
			// Propiedades de la conexión
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.auth", "true");

			// Preparamos la sesion
			Session session = Session.getDefaultInstance(props);
			session.setDebug(true);

			org.zkoss.zk.ui.Session sesion = Sessions.getCurrent();
			Usuarios u = (Usuarios) sesion.getAttribute("User");
			MimeMessage message = new MimeMessage(session);
			try {
				String de=u.getPersona().getPer_email();
				String clave="darwinemilio";
				message.setFrom(new InternetAddress("haydeponcep@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email2));
				message.setSubject("Notificacion Sistema Articulos Cientificos UPSE");
				message.setText("Se le ha asignado un artìculo a evaluar");
				Transport t = session.getTransport("smtp");
				//t.connect("emiliorr2013@gmail.com","darwinemilio");
				t.connect(de,clave);
				t.sendMessage(message,message.getAllRecipients());
				t.close();
				alert("Se ha enviado correctamente la notificación al evaluador " +
						"con el mensaje de que tiene un artículo asignado a evaluar");
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	

	public void onCreate$winNuevoEvaluador() {
		DBPares dbusuarios = new DBPares();
		DBUsuario dbr = new DBUsuario();
		DBArticulos dba = new DBArticulos();
		org.zkoss.zk.ui.Session sesion = Sessions.getCurrent();
		Usuarios u = (Usuarios) sesion.getAttribute("User");
		if (u != null) {
			if (u.getId_rol() == 1) {
				art = (Articulo) winNuevoEvaluador.getAttribute("articulo");
				if (art != null) {
					textbox_Titulo.setText(art.getArt_titulo());
					textbox_area.setText(art.getArea_nombre());
					idarea = art.getId_area();
					idarticulo = art.getArt_id();
					email= art.getEmail();
					idpersonaart = u.getId();
					System.out.println("id persona que registra:"
							+ idpersonaart);
					List<Pares> lista = dbr.buscarEvaluador(idarea);
					List<Pares> listanueva = dbr.buscarPar(idarea, idarticulo,
							idpersona);
					int regcontar1 = dbusuarios.contarRegistros(idarticulo);
					if (regcontar1 == 2) {
						button_Registrar.setVisible(false);
						// alert("No se permite el ingreso de un nuevo evaluador");
					}
					if (listanueva == null) {
						System.out.println("el de lista nueva es null");
					} else {
						System.out
								.println("el de lista nueva no es nullllllll");
					}
					if (lista != null) {
						if (listanueva == null) {
							System.out.println("es nullo en condicion");
							ListModelList<Pares> listModel = new ListModelList<Pares>(
									lista);
							cmb_evaluador.setModel(listModel);
						} else {
							ListModelList<Pares> listModel = new ListModelList<Pares>(
									listanueva);
							cmb_evaluador.setModel(listModel);
						}

					}

				}
			}

		}
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

	}

}
