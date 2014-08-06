package com.controladores;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.datos.DBRoles;
import com.datos.DBTipoArticulos;
import com.datos.DBUsuario;
import com.entidades.Areas;
import com.entidades.Persona;
import com.entidades.Roles;
import com.entidades.TipoArticulos;
import com.entidades.Usuarios;

public class TipoArticulosController extends GenericForwardComposer<Component> {
	@Wire
	private Checkbox check_estado;
	private Button button_Registrar;
	private TipoArticulos tipo=null;
	private Window WinRegistrarTipoArticulos;
	private int estado = 0;
	Textbox textbox_tipo, textbox_descripcion;

	public void onClick$button_Registrar() throws Exception {
		boolean result = false;
		DBTipoArticulos dbtipos = new DBTipoArticulos();
		if (tipo != null) {
			tipo.setTipo_nombre(textbox_tipo.getValue());
			tipo.setTipo_descripcion(textbox_descripcion.getValue());
			if (check_estado.isChecked()) {
				estado = 1;
			} else {
				estado = 0;
			}
			tipo.setTipo_estado(estado);
			result=dbtipos.Actualizar_Tipos(tipo);
		} else {
			if (check_estado.isChecked()) {
				estado = 1;
			} else {
				estado = 0;
			}
			TipoArticulos tipo = new TipoArticulos(0, textbox_tipo.getValue(),
					textbox_descripcion.getValue(), estado);
			result = dbtipos.CrearTipoArticulos(tipo);
		}

		if (result) {
			alert("El tipo de artículo ha sido registrado con éxito");
			// evaluar desde donde fue llamada esta ventana
			String opcion = (String) WinRegistrarTipoArticulos
					.getAttribute("opcion");
			if (opcion != null && opcion.equals("listaTipos")) {
				listaTipoArticuloController luc = (listaTipoArticuloController) WinRegistrarTipoArticulos
						.getAttribute("controladorOrigen");
				if (luc != null)
					luc.actualizarLista();
				WinRegistrarTipoArticulos.detach();

			}
		} else {
			alert("No se pudo realizar el registro del tipo de artículo");
		}

	}

	public void onCreate$WinRegistrarTipoArticulos() {
		tipo = (TipoArticulos) WinRegistrarTipoArticulos.getAttribute("tipo");
		if (tipo != null) {
			textbox_tipo.setText(tipo.getTipo_nombre());
			textbox_descripcion.setText(tipo.getTipo_descripcion());
			if (tipo.getTipo_estado() == 1) {
				check_estado.setChecked(true);
			}

		}
	}

}
