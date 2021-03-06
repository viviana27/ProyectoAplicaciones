package com.controladores;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.datos.DBAreas;
import com.entidades.Areas;
import com.entidades.Usuarios;

public class RegistroAreaController extends GenericForwardComposer<Component> {
	@Wire
	private Textbox textbox_area, textbox_descArea;
	private Button button_Registrar;
	private Window WinRegistrarAreas;
	private Areas are = null;
	private int i = 0;

	public void onClick$button_Registrar() {

		boolean registro = false;
		DBAreas dbar = new DBAreas();

		if (are != null) {

			// existe estoy editando
			are.setArea_nombre(textbox_area.getValue());
			are.setArea_descripcion(textbox_descArea.getValue());

			are.setArea_estado(1);
			// llamo al metodo para actualizar los datos

			registro = dbar.Actualizr_Areas(are);

		} else {
			// no existe, es nuevo
			Areas are = new Areas(0, textbox_area.getValue(),
					textbox_descArea.getValue(), 1);
			registro = dbar.CrearAreas(are);

		}

		if (registro) {
			alert("El �rea ha sido registrada con �xito");
			// evaluar desde donde fue llamada esta venta
			String opcion = (String) WinRegistrarAreas.getAttribute("opcion");
			System.out.println(opcion + "");
			if (opcion != null && opcion.equals("listaAreas")) {
				listaAreaController lac = (listaAreaController) WinRegistrarAreas
						.getAttribute("controladorOrigen");
				if (lac != null)
					lac.actualizarAreasLista();
				WinRegistrarAreas.detach();

			}

		} else {
			alert("No se pudo realizar el registro del �rea");
		}
	}

	// se ejecuta el crear ventana
	public void onCreate$WinRegistrarAreas() {
		Session session = Sessions.getCurrent();
		Usuarios u = (Usuarios) session.getAttribute("User");
		if (u != null) {
			if (u.getId_rol() == 1) {
				are = (Areas) WinRegistrarAreas.getAttribute("areas");
				if (are != null) {
					textbox_area.setText(are.getArea_nombre());
					textbox_descArea.setText(are.getArea_descripcion());

				}

			} else {
				if (u.getId_rol() == 2 || u.getId_rol() == 3) {
					Executions
							.sendRedirect("http://localhost:8080/Zk_ArticulosCientificos/index.zul");
				}
			}
		}

	}
}
