package com.controladores;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.datos.DBUsuario;
import com.entidades.Usuarios;

public class LoginController {
	
	@Wire
	private Textbox textbox_User;
	private Textbox textbox_Password;
	private Label label_Mensaje;
	private Label label_Mensajee;
	
	public void onClick$button_Ingresar(){
		//comprobar que el usuario existe.
		//1. Comprobar que usuario ingreso datos
		if(textbox_User.getValue().isEmpty() || textbox_Password.getValue().isEmpty()){
			label_Mensaje.setValue("usuario y clave con requeridos");
			//evaluar si datos son correctos
		}
		else{
			DBUsuario dbusuario = new DBUsuario();
			Usuarios usuario= dbusuario.buscarUsuario(textbox_User.getValue(), textbox_Password.getValue());
			if(usuario != null){
		
				//almacenar datos del usuario en la sesion
				Session session;
				session= Sessions.getCurrent();
				//guardar objeto usuario en la  session 
				session.setAttribute("User", usuario);
				//Redirreccionar a la pagina principal
				
			//	Executions.sendRedirect("index.zul");
						
		
			}else
		
			{
			//datos no son correctos
			label_Mensaje.setValue("Usuario y/o clave incorrectos");
		
			}
			}
		}


}
