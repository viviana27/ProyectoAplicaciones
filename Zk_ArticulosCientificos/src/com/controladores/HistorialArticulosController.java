package com.controladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;


import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.datos.DBArticulos;
import com.entidades.Articulo;
import com.entidades.EstadoArticulo;
import com.entidades.Usuarios;

public class HistorialArticulosController extends
		GenericForwardComposer<Component> {
	Listbox listaHistorial, listaHistorialEvaluador;
	Combobox cmb_articulos;
	public int idArticulo;
	Session session = Sessions.getCurrent();
	Usuarios usua = (Usuarios) session.getAttribute("User");
	Label etiqueta,nombreArticulo;
	Div observaciones;
	Button button_descarga, evaluar;
	File f;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if (usua.getId_rol() == 3) {
			listaHistorial.setVisible(false);
			listaHistorialEvaluador.setVisible(true);
			cmb_articulos.setVisible(false);
			etiqueta.setVisible(false);
			actualizarListaEvaluador();
		} else {
			cargarArticulos();
		}
	}

	public void cargarArticulos() throws SQLException {
		DBArticulos dbe = new DBArticulos();
		List<Articulo> listaArticulos = dbe.buscarArticuloH();
		if (listaArticulos != null) {
			ListModelList<Articulo> listModel = new ListModelList<Articulo>(
					listaArticulos);
			cmb_articulos.setModel(listModel);

		}
	}

	public void onSelect$cmb_articulos() {
		Articulo a = (Articulo) cmb_articulos.getSelectedItem().getValue();
		if (a != null) {
			idArticulo = (a.getArt_id());
		}
		if (usua.getId_rol() == 3) {
			actualizarListaEvaluador();
		} else {
			actualizarLista();
		}
	}

	public void actualizarLista() {
		DBArticulos dbart = new DBArticulos();
		List<EstadoArticulo> lista = dbart.historialArticulos(idArticulo);
		ListModelList<EstadoArticulo> listModel = new ListModelList<EstadoArticulo>(
				lista);
		listaHistorial.setModel(listModel);
		listaHistorial.renderAll();
	}

	public void actualizarListaEvaluador() {
		DBArticulos dbart = new DBArticulos();
		List<EstadoArticulo> lista = dbart.historialArticulosE(usua.getId());
		ListModelList<EstadoArticulo> listModel = new ListModelList<EstadoArticulo>(
				lista);
		listaHistorialEvaluador.setModel(listModel);
		listaHistorialEvaluador.renderAll();
	}

	public void onClick$button_descarga() {
		EstadoArticulo art = (EstadoArticulo) listaHistorialEvaluador.getSelectedItem().getValue();
		// nombreArticulo.setVisible(true);
		nombreArticulo.setValue(art.getArticulo().getRuta());
		// nombreArticulo.setVisible(true);
				nombreArticulo.setValue(art.getArticulo().getRuta());
				if( art.getArticulo().getRuta().isEmpty()){
					alert("No hay archivo adjunto");	
				} else{
					f = new File(nombreArticulo.getValue());
					//System.out.println("nombre completo" + nombreArticulo.getValue());
					try {
						Filedownload.save(f, null);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					alert("Descarga Exitosa");
				}
				
	}
	
	public void onSelect$listaHistorialEvaluador() {
		EstadoArticulo art = (EstadoArticulo) listaHistorialEvaluador.getSelectedItem().getValue();
		observaciones.setVisible(true);
		button_descarga.setVisible(true);
		
	}

}
