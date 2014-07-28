package com.controladores;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.datos.DBArticulos;
import com.datos.DBEstadoArticulo;
import com.datos.DBListarArticulosAutores;
import com.entidades.Articulo;
import com.entidades.Estados;
import com.entidades.Roles;
import com.entidades.Usuarios;

public class ListaArticulosAutores extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	@Wire
	Combobox cmb_estados;
	public int idEstado,idPersona;
	Listbox listaTareas;
	public Textbox textbox_Titulo, textbox_Resumen, textbox_PClaves,
	textbox_autor;
	private Combobox cmb_tipo, comboAutor2, comboAutor3, cmb_area;
	Textbox txtProyecto;
	Textbox txtautor;
	Textbox txttipo;
	Textbox txtarea;
	Label lblCantidadEvaluaciones;
	Articulo u= new Articulo();
	private Window winSubirArticulo;
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		combos();
		//actualizarLista();
		//listaTareas.renderAll();
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
	public void onSelect$cmb_estados() {
		Estados est = (Estados) cmb_estados.getSelectedItem().getValue();
		if (est != null) {
			idEstado = (est.getId_estado());
		}
		Session session = Sessions.getCurrent();
		Usuarios u = (Usuarios) session.getAttribute("User");
		 idPersona= u.getPersona().getPer_id();
		 
		//alert("aca toy: "+idEstado +"id Rol: "+idPersona);
	
		actualizarLista();
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
	public void actualizarLista() {
		DBListarArticulosAutores dbart = new DBListarArticulosAutores();
		
		List<Articulo> lista = dbart.buscarArticuloEvaluador(idPersona);
		
		
		ListModelList<Articulo> listModel = new ListModelList<Articulo>(lista);
		
		listaTareas.setModel(listModel);
		
		listaTareas.renderAll();
		// obtener datos de la base
				// lista de usuarios
			/*	DBArticulos dbart = new DBArticulos();
				// lista con usuarios encontrados
				List<Articulo> lista = dbart.buscarArticulo(idEstado,txtProyecto.getValue(),
						txtautor.getValue(), txttipo.getValue(), txtarea.getValue());
				
				// establecer esta lista como modelo de dalos pasra el listbox
				ListModelList<Articulo> listModel = new ListModelList<Articulo>(lista);
				// establecer el modelo de datos
				listaTareas.setModel(listModel);
				// alert("lista"+ ((Articulo)listaTareas.getItemAtIndex(0)));
				listaTareas.renderAll();*/
			alert("aca estoy listaArticulosAutores");

	}
	public void onSelect$listaTareas() {
		//Articulo a=new Articulo();
		if (listaTareas.getSelectedItem() == null) {
			alert("Seleccion un artículo");
			return;
		}
		int contareva=0;
		DBArticulos dbart = new DBArticulos();
		Articulo art = (Articulo) listaTareas.getSelectedItem().getValue();
		contareva=dbart.CantidadEvaluaciones(art.getArt_id());
		System.out.println("-------------------------"+contareva+" : : "+art.getArt_id() );
		if (contareva==2){
		Window win = (Window) Executions.createComponents(
				"Articulo/Subir Articulo Corregido.zul", null, null);
		win.setClosable(true);
		win.doModal();
		win.setAttribute("opcion", "listaArticuloAutor");
		win.setAttribute("controladorOrigen", this);
		
		win.setAttribute("articulo", art);
		}else{
			alert("Articulo aun no ha sido evaluado por el par completo");
		}
	}
	
	public void onCreate$winSubirArticulo() {
		u = (Articulo) winSubirArticulo.getAttribute("articulo");
		if (u != null) {
			textbox_Titulo.setText(u.getArt_titulo());
			textbox_Resumen.setText(u.getArt_resumen());
			textbox_PClaves.setText(u.getArt_palabras_clave());	
		}
	}
}
