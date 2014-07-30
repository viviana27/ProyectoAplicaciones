package com.controladores;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.datos.DBArticulos;
import com.datos.DBEstadoArticulo;
import com.datos.DBUsuario;
import com.entidades.Articulo;
import com.entidades.Estados;
import com.entidades.Usuarios;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class ListaArticulosController extends GenericForwardComposer<Component> {
	Textbox txtProyecto;
	Textbox txtautor;
	Textbox txttipo;
	Textbox txtarea;
	Button idfiltroTitulo;
	Button idfiltroautor;
	Button idtipo;
	Button idarea;
	Listbox listaTareas;
	Combobox cmb_estados;
	public int idEstado;
	String email2;

	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		combos();
		actualizarLista();
		listaTareas.renderAll();

	}

	public void combos() {
		DBEstadoArticulo dbe = new DBEstadoArticulo();
		List<Estados> listaEstados = dbe.listarEstados();
		if (listaEstados != null) {
			ListModelList<Estados> listModel = new ListModelList<Estados>(
					listaEstados);
			cmb_estados.setModel(listModel);
		
		}
	}

	public void onSelect$listaTareas() {
		org.zkoss.zk.ui.Session sesion = Sessions.getCurrent();
		Usuarios u = (Usuarios) sesion.getAttribute("User");
		if(idEstado==3 && u.getId_rol()==1){
			Articulo art = (Articulo) listaTareas.getSelectedItem().getValue();
			email2=art.getEmail();
			System.out.println("email autor"+email2);
			enviarEmail();
		}else{
		Articulo art = (Articulo) listaTareas.getSelectedItem().getValue();
		Window win = (Window) Executions.createComponents(
				"Articulo/VerDetalleArticulo.zul", null, null);
		win.setClosable(true);
		win.doModal();
		win.setAttribute("articulo", art);
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

	/*
	 * public void onChange$txtarea(){
	 * 
	 * /*DBArticulos dbart = new DBArticulos(); dbart.buscarArticulo("", "", "",
	 * txtautor.getValue().trim());
	 */
	/*
	 * alert("llegamos al evento change area"); actualizarLista();
	 * 
	 * }
	 */
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
						message.setText("Tiene observaciones en su Artículo Científico");
						Transport t = session.getTransport("smtp");
						//t.connect("emiliorr2013@gmail.com","darwinemilio");
						t.connect(de,clave);
						t.sendMessage(message,message.getAllRecipients());
						t.close();
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			}
	
	public void actualizarLista() {
		// obtener datos de la base
		// lista de usuarios
		DBArticulos dbart = new DBArticulos();
		// lista con usuarios encontrados
		List<Articulo> lista = dbart.buscarArticulo(idEstado,txtProyecto.getValue(),
				txtautor.getValue(), txttipo.getValue(), txtarea.getValue());
		
		// establecer esta lista como modelo de dalos pasra el listbox
		ListModelList<Articulo> listModel = new ListModelList<Articulo>(lista);
		// establecer el modelo de datos
		listaTareas.setModel(listModel);
		// alert("lista"+ ((Articulo)listaTareas.getItemAtIndex(0)));
		listaTareas.renderAll();

	}

	public void onSelect$cmb_estados() {
		Estados est = (Estados) cmb_estados.getSelectedItem().getValue();
		if (est != null) {
			idEstado = (est.getId_estado());
		}
		actualizarLista();
	}
	
}
