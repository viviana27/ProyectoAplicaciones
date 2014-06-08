package com.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.datos.DBRoles;
import com.datos.DBTipoArticulos;
import com.entidades.Roles;
import com.entidades.TipoArticulos;

public class listaTipoArticuloController extends
		GenericForwardComposer<Component> {
	@Wire
	Toolbarbutton toolbarbutton_Eliminar;
	Toolbarbutton toolbarbutton_Nuevo;
	Toolbarbutton toolbarbutton_Editar;
	private Textbox textbox_buscar;
	private Button button_buscar;
	Listbox listbox_TipoArticulos;
	boolean confirmacion=false;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		actualizarLista();
	}

	public void onClick$button_buscar() {
		actualizarLista();
	}

	public void actualizarLista() {
		DBTipoArticulos dbr = new DBTipoArticulos();
		List<TipoArticulos> lista = dbr.buscarTipos(textbox_buscar.getValue());
		// establecer esta lista como modelo de dalos pasra el listbox
		ListModelList<TipoArticulos> listModel = new ListModelList<TipoArticulos>(
				lista);
		// establecer el modelo de datos
		listbox_TipoArticulos.setModel(listModel);

	}

	public void onClick$toolbarbutton_Nuevo() {
		Window win = (Window) Executions.createComponents(
				"Mantenimiento/TipoArticulo/registroTipoArticulo.zul", null,
				null);
		win.setClosable(true);
		win.doModal();
		win.setAttribute("opcion", "listaTipos");
		win.setAttribute("controladOrigen", this);
		
	}

	public void onClick$toolbarbutton_Editar() {
		if (listbox_TipoArticulos.getSelectedItem() == null) {
			alert("Seleccione por favor un tipo de articulo");
			return;
		}
		Window win = (Window) Executions.createComponents(
				"Mantenimiento/TipoArticulo/registroTipoArticulo.zul", null,
				null);
		win.setClosable(true);
		win.doModal();
		win.setAttribute("opcion", "listaTipos");
		win.setAttribute("controladorOrigen", this);
		TipoArticulos tipo = (TipoArticulos ) listbox_TipoArticulos.getSelectedItem().getValue();
		win.setAttribute("tipo", tipo);
	}

	public void onClick$toolbarbutton_Eliminar() {
		boolean result = false;
		if (listbox_TipoArticulos.getSelectedItem() == null) {
			alert("Seleccione el rol que desea eliminar");
			return;
		}
		// mesaagebox
		Messagebox.show("Esta seguro de eliminar el tipo de articilo?", "confirmacion",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event evento) throws Exception {
						// TODO Auto-generated method stub
						if (evento.getName().equals("onOK")) {
							confirmacion = true;
						}
					}
				});
		TipoArticulos tipo = (TipoArticulos) listbox_TipoArticulos.getSelectedItem().getValue();
		DBTipoArticulos tipos= new DBTipoArticulos();
		result = tipos.eliminarTipos(tipo);
		if (result != false) {
			alert("tipo de articulo eliminado correctamente");
		}
		actualizarLista();
	}

}
