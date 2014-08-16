package com.controladores;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


import com.datos.DBParametrosEvaluacion;

import com.entidades.ParametrosEvaluacion;

public class ParametrosEvaluacionController extends GenericForwardComposer<Component>{
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox textbox_area,textbox_valor;
	private Window WinRegistrarParametros;
	private ParametrosEvaluacion param=null;
	Label lbSumatotal;
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
					param.setParam_estado(1);		
				//llamo al metodo para actualizar los datos
				
				registro=dbar.Actualizr_ParametrosEvaluacion(param);
			}else{
				//no existe, es nuevo
				ParametrosEvaluacion parametros=new ParametrosEvaluacion(0, textbox_area.getValue(), Integer.parseInt(textbox_valor.getValue()), 1);
				registro=dbar.CrearParametrosEvaluacion(parametros);
				
			}
			
			if(registro)
			{
			
				alert("El parámetro ha sido registrado con éxito");
				//evaluar desde donde fue llamada esta venta				
				String opcion=(String)WinRegistrarParametros.getAttribute("opcion");
			
				if(opcion!=null && opcion.equals("listaparametros")){
					System.out.println(opcion+"");
			listaParametroController lac = (listaParametroController) WinRegistrarParametros.getAttribute("controladorOrigen");
			if(lac!=null)
				lac.actualizarLista();
				WinRegistrarParametros.detach();
				mensaje();
			}
				}
			else {
				alert("No se pudo realizar el registro del parámetro");
				}
	}
	
	public void mensaje(){
		if(Integer.parseInt(lbSumatotal.getValue())<100)
				{
			alert("Por favor modificar el valor del parámetro " +
				"o agregar un nuevo parámetro hasta completar el 100% ");
				}
	}
	
	public void onCreate$WinRegistrarParametros() {
		param = (ParametrosEvaluacion) WinRegistrarParametros.getAttribute("param");
		if (param != null) {
			textbox_area.setText(param.getParam_descripcion());
			textbox_valor.setText(Integer.toString( param.getParam_valor()));
		}
		
		DBParametrosEvaluacion dbar = new DBParametrosEvaluacion();
		lbSumatotal.setStyle("font-weight: bold; color:green;");
		int resto=0;
		lbSumatotal.setValue(dbar.TotalParametrosEvaluacion()+"");
		resto=Integer.parseInt( lbSumatotal.getValue())-Integer.parseInt( textbox_valor.getValue());
		lbSumatotal.setValue(resto+"");
	
	}
	
	public void onBlur$textbox_valor(){
		int resto=0;
		resto=100-Integer.parseInt(lbSumatotal.getValue());
		if (resto<Integer.parseInt(textbox_valor.getValue())){
			alert("La suma total de los valores de los parámetros no debe exceder al 100% del total ");
			alert("La suma total está completada sobre 100%");
			textbox_valor.setValue("");
				
			}
			
		
	}
}
