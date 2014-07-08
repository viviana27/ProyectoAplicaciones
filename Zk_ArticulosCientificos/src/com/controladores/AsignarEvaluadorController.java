package com.controladores;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.datos.DBPares;
import com.datos.DBUsuario;
import com.entidades.Areas;
import com.entidades.Articulo;
import com.entidades.EstadoArticulo;
import com.entidades.Pares;
import com.entidades.Persona;
import com.entidades.Usuarios;


public class AsignarEvaluadorController extends GenericForwardComposer<Component> {
	//se ejecuta al crear la ventana
	// registrar un nuevo evaluador
	private static final long serialVersionUID = 1L;
	@Wire
	
	private Window winNuevoEvaluador;
	private Textbox textbox_Titulo,textbox_area;
	private Button button_Registrar;
	int idarea;
	private Label lblidpersona;
	private Pares par = null;
	private Articulo art=null;
	private Combobox cmb_evaluador;
	int idarticulo=0;
	int idpersona=0;
	int num_pares=0;
	
	public void onClick$button_Registrar() throws Exception {
		boolean result = false;
		boolean result1 = false;
	
		EstadoArticulo ea=new EstadoArticulo();
		DBPares dbusuarios = new DBPares();	
		System.out.println("idArticulonuevo:"+idarticulo);
		System.out.println("idpersonaaa:"+idpersona);
		if ( par!=null) {
			// existe estoy editando
			par.setArticulos_id(idarticulo);
			par.setPersonas_id(idpersona);
			par.setEstado_id(2);
			par.setCant_par(2);
			par.setPar_estado(1);
			// llamo al metodo para actualizar los datos
			
			result = dbusuarios.actualizarEvaluador(par);

			} else {
				Pares par= new Pares(0,idarticulo,idpersona,2,2,1 );
				result = dbusuarios.InsertarPar(par);
				ea.setId_articulo(idarticulo);
				ea.setId_estado(2);
				ea.setId_persona(idpersona);
				result1 = dbusuarios.RegistrarEstadoArticulo(ea);

}
if (result && result1) {
	alert("Articulo asignado a los evaluadores correctamente ");
	num_pares++;
	// evaluar desde donde fue llamada esta ventana
	String opcion = (String) winNuevoEvaluador.getAttribute("opcion");
	if (opcion != null && opcion.equals("Revision")) {
		// entonces la ventana ufe llamada desde lista usuarios
		// cerrar ventana

		// actualizar la lista de usuarios
		ListaArticulosEvaluador luc = (ListaArticulosEvaluador) winNuevoEvaluador
				.getAttribute("controladorOrigen");
		if (luc != null)
			luc.actualizarLista();
		// luc.actualizarLista();
		winNuevoEvaluador.detach();

	}
	if(num_pares==2){
		alert("Numero de pares correcto");
		button_Registrar.setVisible(false);
		
	}
} else {
	alert("No se pudo realizar el registro");
}

}
	public void onSelect$cmb_evaluador() {
		Pares pares = (Pares) cmb_evaluador.getSelectedItem().getValue();
		if (pares != null) {
			
			idpersona= pares.getPersonas_id();
	
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
					idarticulo=art.getArt_id();
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
