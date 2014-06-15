package com.controladores;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.datos.DBUsuario;
import com.datos.DBUsuarioArea;
import com.entidades.AreaUsuario;
import com.entidades.Persona;
import com.entidades.PersonaArea;
import com.entidades.UsuarioArea;
import com.entidades.Usuarios;


public class UsuarioAreaController extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox textbox_Pnombre;
	private Textbox textbox_Apellidos;
	private Combobox combo_area;
	private Label lblidarea;
	private Button button_Guardar;
	private Window winNuevoUsuarioArea;
	private UsuarioArea u = null;
	int idper=0;
	int idpersona=0;
	
	
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		DBUsuarioArea dbr = new DBUsuarioArea();
		List<UsuarioArea> lista = dbr.buscarareas();
		if (lista != null) {
			ListModelList<UsuarioArea> listModel = new ListModelList<UsuarioArea>(
					lista);
			combo_area.setModel(listModel);
			//System.out.println("estamos aca darwin");
		}
	}
	
	
	public void onClick$button_Guardar() throws Exception {
		boolean result = false;
		DBUsuarioArea dbusuarios = new DBUsuarioArea();
		// verificar si es nuevo usuario o usuario a editar
		idper=u.getPer_area_id();
		idpersona=u.getPersonaarea().getPer_id();
		if ( idper > 0) {
			// existe estoy editando
			u.getPersonaarea().setPer_nombre(textbox_Pnombre.getValue());
			u.getPersonaarea().setPer_apellido(textbox_Apellidos.getValue());
			u.setArea_id(Integer.parseInt(lblidarea.getValue()));
			// llamo al metodo para actualizar los datos
			System.out.println();
			System.out.println("idArea: "+ lblidarea.getValue()+"nombre: "+textbox_Pnombre.getValue()+" idPesona"+u.getPersonaarea().getPer_id());
			result = dbusuarios.actualizarUsuarioArea(u);
		} else {
						u.getPersonaarea().setPer_nombre(textbox_Pnombre.getValue());
						u.getPersonaarea().setPer_apellido(textbox_Apellidos.getValue());
						u.setArea_id(Integer.parseInt(lblidarea.getValue()));
						result = dbusuarios.crearUsuarioArea(u);
		}
		if (result) {
			alert("Usuario con area registrado con exito");
			// evaluar desde donde fue llamada esta ventana
			String opcion = (String) winNuevoUsuarioArea.getAttribute("opcion");
			if (opcion != null && opcion.equals("listaAreasUsuarios")) {
				// entonces la ventana ufe llamada desde lista usuarios
				// cerrar ventana

				// actualizar la lista de usuarios
				listaAreaUsuarioController luc = (listaAreaUsuarioController) winNuevoUsuarioArea
						.getAttribute("controladorOrigen");
				if (luc != null)
					luc.actualizarLista();
				// luc.actualizarLista();
				winNuevoUsuarioArea.detach();

			}
		} else {
			alert("No se pudo realizar el registro");
		}

	}
	
	public void onSelect$combo_area() {
		UsuarioArea usu = (UsuarioArea) combo_area.getSelectedItem().getValue();
		//System.out.println("hola" + combo_area.getSelectedItem().getValue());
		if (usu != null) {
			String id = null;
			id = Integer.toString(usu.getArea_id());
	//		System.out.println();
		//	System.out.println("id area" + id);
			lblidarea.setValue(id);
	
		}
	}
	
public void onCreate$winNuevoUsuarioArea() {
		
		u = (UsuarioArea) winNuevoUsuarioArea.getAttribute("usuario");
		if (u != null) {
			textbox_Pnombre.setText(u.getPersonaarea().getPer_nombre());
			System.out.println("aqui"+u.getPersonaarea().getPer_nombre());
			textbox_Apellidos.setText(u.getPersonaarea().getPer_apellido());
			combo_area.setValue(u.getArea_nombre());
			String id = null;
			id = Integer.toString(u.getArea_id());
			lblidarea.setValue(id);
		}
	}
	
}
