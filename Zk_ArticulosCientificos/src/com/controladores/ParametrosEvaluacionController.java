package com.controladores;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


import com.datos.DBParametrosEvaluacion;

import com.entidades.ParametrosEvaluacion;

public class ParametrosEvaluacionController extends GenericForwardComposer<Component>{
	
	private Textbox textbox_area,textbox_valor;
	private Checkbox check_estado;
	private Window WinRegistrarParametros;
	private ParametrosEvaluacion param=null;
	
	private int i=0,valor=0;
	
	public void onClick$button_Registrar(){
		//System.out.println("estamos aca");
		 boolean registro=false;
		 DBParametrosEvaluacion dbar = new DBParametrosEvaluacion();
		 
		 if(param!=null){

				//existe estoy editando
				param.setParam_descripcion(textbox_area.getValue());
				valor=Integer.parseInt(textbox_valor.getValue());
				System.out.println("estes "+valor);
				param.setParam_valor(valor);
					if(check_estado.isChecked()){
						i=1;
					}else
					{
						i=0;
					}
				
				param.setParam_estado(i);		
				//llamo al metodo para actualizar los datos
				
				registro=dbar.Actualizr_ParametrosEvaluacion(param);
			}else{
				//no existe, es nuevo
					
				if(check_estado.isChecked()){
					i=1;
				}else
				{
					i=0;
				}
				
				ParametrosEvaluacion parametros=new ParametrosEvaluacion(0, textbox_area.getValue(), Integer.parseInt(textbox_valor.getValue()), i);
				registro=dbar.CrearParametrosEvaluacion(parametros);
				
			}
			
			if(registro)
			{
			
				alert("Parametros registrada con exito");
				//evaluar desde donde fue llamada esta venta				
				String opcion=(String)WinRegistrarParametros.getAttribute("opcion");
			
				if(opcion!=null && opcion.equals("listaparametros")){
					System.out.println(opcion+"");
			listaParametroController lac = (listaParametroController) WinRegistrarParametros.getAttribute("controladOrigen");
			if(lac!=null) lac.actualizarLista();
				WinRegistrarParametros.detach();
					
			}
				}
			else {
				alert("No se pudo realizar el registro");
				}
	}
	
	public void onCreate$WinRegistrarParametros() {
		param = (ParametrosEvaluacion) WinRegistrarParametros.getAttribute("param");
		if (param != null) {
			textbox_area.setText(param.getParam_descripcion());
			textbox_valor.setText(Integer.toString( param.getParam_valor()));
			if (param.getParam_estado() == 1) {
				// chkvista.isChecked();
				check_estado.setChecked(true);
			}

		}
	}
}