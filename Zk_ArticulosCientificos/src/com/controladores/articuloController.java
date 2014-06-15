package com.controladores;

import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import com.datos.DBArticulos;
import com.datos.DBTipoArticulos;
import com.datos.DBUsuario;
import com.entidades.Articulo;
import com.entidades.PersonaArticulo;
import com.entidades.TipoArticulos;
import com.entidades.Usuarios;

public class articuloController extends GenericForwardComposer<Component> {
	private Combobox cmb_tipo, comboAutor2, comboAutor3;
	public Textbox textbox_Titulo, textbox_Resumen, textbox_PClaves, txtfecha;
	public Button button_Registrar;
	public Label ruta;
	private Articulo a = null;
	private PersonaArticulo pa = null;

	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		DBTipoArticulos dbtp = new DBTipoArticulos();
		DBUsuario dbu = new DBUsuario();
		List<TipoArticulos> listaTipos = dbtp.buscarTipos();
		if (listaTipos != null) {
			ListModelList<TipoArticulos> listModel = new ListModelList<TipoArticulos>(
					listaTipos);
			cmb_tipo.setModel(listModel);
		}
		List<Usuarios> listaUsuarios = dbu.buscarUsuarios("");
		if (listaUsuarios != null) {
			ListModelList<Usuarios> listModel = new ListModelList<Usuarios>(
					listaUsuarios);
			comboAutor2.setModel(listModel);
			comboAutor3.setModel(listModel);
		}
	}

	public void onClick$button_Registrar() throws Exception {
		boolean result = false;
		DBArticulos dbart = new DBArticulos();
		a.setArt_titulo(textbox_Titulo.getValue());
		a.setArt_archivo(ruta.getValue());
		a.setArt_resumen(textbox_Resumen.getValue());
		a.setArt_palabras_clave(textbox_Resumen.getValue());
		a.setArt_fecha_subida(txtfecha.getValue());
		a.setArt_estado(1);
		a.setTipo_id(1);
		a.setArea_id_padre(0);
		a.setId_estado(1);
		result = dbart.RegistrarArticulo(a);
		if (result) {
			alert("Articulo registrado con exito");
		} else {
			alert("No se pudo realizar el registro");
		}
		//pa para guardar datos de otros autores *colaboradores de ese mismo articulo
		//aquí iba a validar si hay datos en los comboAutor2 y comboAutor3, para proceder a guardar
		//porque si no hay mas autores la tabla tb_persona_articulo no es obligatoria llenarla
		pa.setPers_id(1);
		pa.setArti_id(1);
		pa.setPer_art_estado(1);
		pa.setPer_id_registra(1);
		result=dbart.RegistrarPersonaArticulo(pa);
		
	}
}
