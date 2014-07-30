package com.controladores;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.zkforge.timeline.Bandinfo;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;


import com.datos.DBArticulos;
import com.entidades.Articulo;
import com.entidades.EstadoArticulo;

public class HistorialArticulosController extends
		GenericForwardComposer<Component> {
	Listbox listaHistorial;
	Combobox cmb_articulos;
	public int idArticulo;
	Date currentDate;
	public DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Wire
	private Bandinfo bandinfoMonth, bandinfoYear;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		cargarArticulos();
		actualizarLista();
		listaHistorial.renderAll();
		lineaTiempo();
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
		actualizarLista();
	}

	public void actualizarLista() {
		
			DBArticulos dbart = new DBArticulos();
			List<EstadoArticulo> lista = dbart.historialArticulos(idArticulo);
			ListModelList<EstadoArticulo> listModel = new ListModelList<EstadoArticulo>(
					lista);
			listaHistorial.setModel(listModel);
			listaHistorial.renderAll();

			

	}

	public void lineaTiempo() {

		try {
			currentDate = dateFormat.parse("2014-06-29");
			bandinfoMonth.setDate(currentDate);
			bandinfoYear.setDate(currentDate);
			bandinfoMonth.setEventSourceUrl("Articulo/timeline_data.xml");
			bandinfoYear.setEventSourceUrl("Articulo/timeline_data.xml");
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
