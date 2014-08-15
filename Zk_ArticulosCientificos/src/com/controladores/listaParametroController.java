package com.controladores;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.util.Composer;
import com.datos.DBParametrosEvaluacion;
import com.datos.DBRoles;
import com.entidades.ParametrosEvaluacion;

public class listaParametroController extends GenericForwardComposer<Component> {
	@Wire
	Label lbSumatotal;
	Toolbarbutton toolbarbutton_Eliminar;
	Toolbarbutton toolbarbutton_Nuevo;
	Toolbarbutton toolbarbutton_Editar;
	Textbox textbox_buscar;
	Button button_buscar;
	Listbox listbox_Parametros;
	boolean confirmacion = false;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
		actualizarLista();
	}

	public void onSelect$listbox_Categorias() {

	}

	public void onClick$toolbarbutton_Nuevo() {

		Window win = (Window) Executions
				.createComponents(
						"Mantenimiento/ParametrosEvaluacion/registroParametroEvaluacion.zul",
						null, null);
		win.setClosable(true);
		win.doModal();

		// guardar atributos en ventana

		// win.setAttribute("opcion", "registroRol") -- nombre de variable;
		win.setAttribute("opcion", "listaparametros");
		win.setAttribute("controladorOrigen", this);

	}

	public void onClick$toolbarbutton_Editar() {

		if (listbox_Parametros.getSelectedItem() == null) {
			alert("Por favor seleccione un usuario");
			return;
		}

		Window win = (Window) Executions
				.createComponents(
						"Mantenimiento/ParametrosEvaluacion/registroParametroEvaluacion.zul",
						null, null);
		win.setClosable(true);
		win.doModal();
		// guardar atributos en ventana
		win.setAttribute("opcion", "listaparametros");
		win.setAttribute("controladorOrigen", this);
		ParametrosEvaluacion parame = (ParametrosEvaluacion) listbox_Parametros
				.getSelectedItem().getValue();
		win.setAttribute("param", parame);
		
	}

	public void onClick$toolbarbutton_Eliminar() {
		// alert("Click en boton");

		
		if (listbox_Parametros.getSelectedItem() == null) {
			alert("Por favor seleccione el parámetro a eliminar");
			return;
		}
		// mesaagebox
		Messagebox.show("Está seguro de eliminar el rol?", "confirmacion",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event evento) throws Exception {
						boolean result = false;
						// TODO Auto-generated method stub
						if (evento.getName().equals("onOK")) {
							confirmacion = true;
						}
						else{
							confirmacion=false;
						}
						if(confirmacion){
							ParametrosEvaluacion rol = (ParametrosEvaluacion) listbox_Parametros
									.getSelectedItem().getValue();
							DBParametrosEvaluacion pa = new DBParametrosEvaluacion();
							result = pa.eliminarParametrosEvaluacion(rol);
							if (result != false) {
								alert("El parámetro ha sido eliminado correctamente");
							} 
							
							}else {
							alert("Eliminación Cancelada");
						}
					}
		});


}

	public void onClick$button_buscar() {
		actualizarLista();
	}

	
	public void actualizarLista() {
		DBParametrosEvaluacion dbr = new DBParametrosEvaluacion();
		List<ParametrosEvaluacion> lista = dbr
				.buscarParametrosEvaluacion(textbox_buscar.getValue());
		// establecer esta lista como modelo de dalos pasra el listbox
		ListModelList<ParametrosEvaluacion> listModel = new ListModelList<ParametrosEvaluacion>(
				lista);
		// establecer el modelo de datos
		listbox_Parametros.setModel(listModel);
		dbr=new DBParametrosEvaluacion();
		lbSumatotal.setStyle("font-weight: bold; color:red;");
		lbSumatotal.setValue(dbr.TotalParametrosEvaluacion()+"%");
     	}

}
