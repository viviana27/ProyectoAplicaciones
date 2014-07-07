package com.controladores;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.datos.DBPares;
import com.datos.DBUsuario;
import com.datos.DBUsuarioArea;
import com.entidades.Articulo;
import com.entidades.Pares;
import com.entidades.Persona;
import com.entidades.Usuarios;


public class AsignarEvaluadorController extends GenericForwardComposer<Component> {
	//se ejecuta al crear la ventana
	// registrar un nuevo evaluador
	
	private Window winNuevoEvaluador;
	private Textbox textbox_Titulo,textbox_area;
	private Usuarios u=null;
	private Button button_Registrar;

	int idarea;
	private Pares par = null;
	private Articulo art=null;
	private Combobox cmb_evaluador;
	int idarticulo=0;
	int idpersona=0;
	public void onClick$button_Registrar() throws Exception {
		boolean result = false;
		DBPares dbusuarios = new DBPares();
		idarticulo=par.getArticulos_id();
		idpersona=par.getPersonas_id();
		if ( idpersona > 0) {
			// existe estoy editando
			
			result = dbusuarios.actualizarEvaluador(par);
			if (result) {
				alert("Evaluador asignado con exito");
				// evaluar desde donde fue llamada esta ventana
				String opcion = (String) winNuevoEvaluador.getAttribute("opcion");
	//			if (opcion != null && opcion.equals("Revision")) {
					// entonces la ventana ufe llamada desde lista usuarios
					// cerrar ventana

					// actualizar la lista de usuarios
					/*listaAreaUsuarioController luc = (listaAreaUsuarioController) winNuevoEvaluador
							.getAttribute("controladorOrigen");
					if (luc != null)
						luc.actualizarLista();
					// luc.actualizarLista();
					winNuevoEvaluador.detach();
*/
		//		}
			} else {
				alert("No se pudo realizar el registro");
			}

			
		}
	}
	public void onCreate$winNuevoEvaluador(){
		DBUsuario dbr = new DBUsuario();
		Session sesion=Sessions.getCurrent();
		Usuarios u=(Usuarios)sesion.getAttribute("User");
		if(u!=null){
			if(u.getId_rol()==1){
				art=(Articulo)winNuevoEvaluador.getAttribute("articulo");
				if(art!=null){
					textbox_Titulo.setText(art.getArt_titulo());
					textbox_area.setText(art.getArea_nombre());
					idarea=art.getId_area();
					List<Pares> lista = dbr.buscarEvaluador(idarea);
					if (lista != null) {
						ListModelList<Pares> listModel = new ListModelList<Pares>(
								lista);
						cmb_evaluador.setModel(listModel);
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
