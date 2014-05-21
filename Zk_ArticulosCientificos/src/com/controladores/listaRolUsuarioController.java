package com.controladores;


import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;


public class listaRolUsuarioController extends GenericForwardComposer<Component> {
	@Wire
	Toolbarbutton toolbarbutton_Editar;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);	
	}


	public void onClick$toolbarbutton_Editar() {
		Window win = (Window) Executions.createComponents(
				"Mantenimiento/RolesUsuario/ModificarRolUsuario.zul", null, null);
		win.setClosable(true);
		win.doModal();
	}
	
}
