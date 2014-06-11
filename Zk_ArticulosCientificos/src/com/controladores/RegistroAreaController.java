package com.controladores;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.datos.DBArticulo;
import com.entidades.Areas;

public class RegistroAreaController extends GenericForwardComposer<Component>
{
	@Wire
	private Textbox textbox_area,textbox_descArea;
	private Checkbox check_estado;
	private Button button_Registrar;
	private Window WinRegistrarAreas;
	private Areas are=null;
	private int i=0;
	 	public void onClick$button_Registrar(){
		
		boolean registro=false;
		 DBArticulo dbar = new DBArticulo();
		
			if(are!=null){

				//existe estoy editando
				are.setArea_nombre(textbox_area.getValue());
				are.setArea_descripcion(textbox_descArea.getValue());
					if(check_estado.isChecked()){
						i=1;
					}else
					{
						i=0;
					}
				
				are.setArea_estado(i);		
				//llamo al metodo para actualizar los datos
				
				registro=dbar.Actualizr_Areas(are);
				
			}else{
				//no existe, es nuevo
					
				if(check_estado.isChecked()){
					i=1;
				}else
				{
					i=0;
				}
				
				Areas are= new Areas(0,textbox_area.getValue(),textbox_descArea.getValue(),i );
				registro=dbar.CrearAreas(are);
				
			}
			
			if(registro)
			{
				alert("Areas registrada con exito");
				//evaluar desde donde fue llamada esta venta				
				String opcion=(String)WinRegistrarAreas.getAttribute("opcion");
				System.out.println(opcion+"");
				if(opcion!=null && opcion.equals("listaAreas")){
			listaAreaController lac = (listaAreaController) WinRegistrarAreas.getAttribute("controladOrigen");
			if(lac!=null) lac.actualizarAreasLista();
				WinRegistrarAreas.detach();
					//actualizar la lista de usuarios
			
				

			}
		
		}
			else {
				alert("No se pudo realizar el registro");
				}
	}
	

	//se ejecuta el crear ventana
		public void onCreate$WinRegistrarAreas(){
			are=(Areas) WinRegistrarAreas.getAttribute("areas");
			if(are!=null){		
				textbox_area.setText(are.getArea_nombre());
				textbox_descArea.setText(are.getArea_descripcion());
				if (are.getArea_estado()==1){
					//chkvista.isChecked();
					check_estado.setChecked(true);
				}
				
			}					
		}
}
