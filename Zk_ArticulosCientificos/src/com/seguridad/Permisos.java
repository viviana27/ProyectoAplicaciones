package com.seguridad;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.Elements;
import javax.swing.plaf.ListUI;

import org.zkoss.idom.Element;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.datos.DBPermiso;
import com.datos.DBRoles;

import com.entidades.Permiso;
import com.entidades.Roles;
import com.entidades.TipoArticulos;

public class Permisos extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	@Wire
	Button button_Registrar, button_Cancelar;
	Window WinPermisos;
	Combobox cbTipoRol;
	Label lbIdrol;
	Listbox listbox_Permisos;
	ListModelList<Permiso> listModel;
	List<Permiso> listaPermisos = new ArrayList<Permiso>();
	boolean bandera = false;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		Combos();
	}

	public void Combos() {
		DBRoles dbtp = new DBRoles();
		List<Roles> listaTipos = dbtp.buscarRoles();
		if (listaTipos != null) {
			ListModelList<Roles> listModel = new ListModelList<Roles>(
					listaTipos);
			cbTipoRol.setModel(listModel);

		}
	}

	public void onSelect$cbTipoRol() {
		Roles usu = (Roles) cbTipoRol.getSelectedItem().getValue();
		if (usu != null) {
			lbIdrol.setValue(Integer.toString(usu.getRol_id()));
			actualizarLista();
			// presentarLista();
		}
	}

	public void actualizarLista() {
		DBPermiso dbp = new DBPermiso();
		List<Permiso> lista = dbp.buscarPermisos(Integer.parseInt(lbIdrol
				.getValue()));
		listModel = new ListModelList<Permiso>(lista);
		listbox_Permisos.setModel(listModel);
	}

	public void onCellClicked(ForwardEvent event) {
		System.out.println(event);
		MouseEvent mouseEvent = (MouseEvent) event.getOrigin();
		Listcell target = (Listcell) mouseEvent.getTarget();
		if (target.getLabel().equals("Activo")) {
			target.setStyle("font-weight: bold; color:blue;");
			target.setLabel("Inactivo");
		} else {
			target.setStyle("font-weight: bold; color:green;");
			target.setLabel("Activo");
		}

		// Clients.alert("Clicked: " + target.getLabel());
	}

	public void onClick$button_Registrar() {
		// alert("tamos");
		recorrerLista();
		DBPermiso dbp = new DBPermiso();
		bandera = dbp.guardarPermisosFormularios(listaPermisos);
		if (bandera) {
			alert("Permisos Registrados...!");
			WinPermisos.detach();
		} else {
			alert("Fallamos...!");
		}
		listaPermisos.clear();
	}

	public void presentarLista() {
		for (int i = 0; i <= listModel.size() - 2; i++) {

			Listitem item = (Listitem) listbox_Permisos.getItems().get(i);
			List<Component> listaceldas = item.getChildren();
			// Listcell liId=(Listcell) listaceldas.get(0);
			// Listcell liIdFormulario=(Listcell) listaceldas.get(1);
			Listcell liEstado = (Listcell) listaceldas.get(5);
			String estado = liEstado.getLabel();
			// int idPermsio=Integer.parseInt(liId.getLabel());
			// int idFormulario=Integer.parseInt(liIdFormulario.getLabel());
			// int idRol=Integer.parseInt(lbIdrol.getValue());
			// int permiso=0;
			if (estado.equals("Activo")) {
				liEstado.setStyle("font-weight: bold; color:green;");
			} else {
				liEstado.setStyle("font-weight: bold; color:blue;");
			}
		}
	}

	public void recorrerLista() {
		// alert(listModel.size()+"");
		Permiso per = null;
		for (int i = 0; i <= listModel.size() - 2; i++) {
			per = new Permiso();
			Listitem item = (Listitem) listbox_Permisos.getItems().get(i);
			List<Component> listaceldas = item.getChildren();
			Listcell liId = (Listcell) listaceldas.get(0);
			Listcell liIdFormulario = (Listcell) listaceldas.get(1);
			Listcell liEstado = (Listcell) listaceldas.get(5);
			String estado = liEstado.getLabel();
			int idPermsio = Integer.parseInt(liId.getLabel());
			int idFormulario = Integer.parseInt(liIdFormulario.getLabel());
			int idRol = Integer.parseInt(lbIdrol.getValue());
			int permiso = 0;
			if (estado.equals("Activo")) {
				permiso = 1;
			} else {
				permiso = 0;
			}

			// System.out.println(listaceldas);
			// System.out.println(liId.getLabel());
			// System.out.println(liIdFormulario.getLabel());
			// System.out.println(liEstado.getLabel());
			per.setId_permiso(idPermsio);
			per.setId_formulario(idFormulario);
			per.setId_rol(idRol);
			per.setPermiso(permiso);

			listaPermisos.add(per);
		}
		// System.out.println(listaPermisos.size()+"");
	}

	public void onClick$button_Cancelar() {
		WinPermisos.detach();
	}
}
