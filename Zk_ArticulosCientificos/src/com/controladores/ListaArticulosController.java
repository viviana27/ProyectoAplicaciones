package com.controladores;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
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
		Articulo art = (Articulo) listaTareas.getSelectedItem().getValue();
		Window win = (Window) Executions.createComponents(
				"Articulo/VerDetalleArticulo.zul", null, null);
		win.setClosable(true);
		win.doModal();
		win.setAttribute("articulo", art);
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
		alert("aca toy");
		actualizarLista();
	}
	
}
