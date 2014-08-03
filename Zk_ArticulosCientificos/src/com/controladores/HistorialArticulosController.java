package com.controladores;

import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import org.zkforge.timeline.Bandinfo;
import org.zkforge.timeline.Timeline;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.datos.DBArticulos;
import com.entidades.Articulo;
import com.entidades.EstadoArticulo;
import com.entidades.Usuarios;

public class HistorialArticulosController extends
		GenericForwardComposer<Component> {
	Listbox listaHistorial, listaHistorialEvaluador;
	Combobox cmb_articulos;
	public int idArticulo;
	Date currentDate;
	Timeline timeline;
	public DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Session session = Sessions.getCurrent();
	Usuarios usua = (Usuarios) session.getAttribute("User");
	Label etiqueta;
	@Wire
	private Bandinfo bandinfoMonth, bandinfoYear;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if (usua.getId_rol() == 3) {
			listaHistorial.setVisible(false);
			listaHistorialEvaluador.setVisible(true);
			cmb_articulos.setVisible(false);
			timeline.setVisible(false);
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
			lineaTiempo();
		}
	}

	public void actualizarLista() {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		DBArticulos dbart = new DBArticulos();
		List<EstadoArticulo> lista = dbart.historialArticulos(idArticulo);
		ListModelList<EstadoArticulo> listModel = new ListModelList<EstadoArticulo>(
				lista);
		listaHistorial.setModel(listModel);
		listaHistorial.renderAll();

		// ----------------XML------------------
		Element root = new Element("data");
		for (int i = 0; i <= listModel.size() - 1; i++) {
			Listitem item = (Listitem) listaHistorial.getItems().get(i);
			List<Component> listaceldas = item.getChildren();
			Listcell fecha = (Listcell) listaceldas.get(0);
			Listcell estado = (Listcell) listaceldas.get(1);
			Listcell persona = (Listcell) listaceldas.get(2);
			Listcell informacion = (Listcell) listaceldas.get(3);
			String fecha1 = fecha.getLabel();
			String estado1 = estado.getLabel();
			String persona1 = persona.getLabel();
			String info = informacion.getLabel();
			Element item1 = new Element("event");

			item1.setAttribute("start", "" + fecha1);
			item1.setAttribute("title", "El artículo fue " + estado1 + " por "
					+ persona1);
			item1.setText(info);
			root.addContent(item1);
		}
		try {
			System.out.println("escribe xml");
			outputter
					.output(new Document(root),
							new FileOutputStream(
									"C:/Users/Viviana/git/ProyectoAplicaciones/Zk_ArticulosCientificos/WebContent/Articulo/Datos.xml"));
		} catch (Exception e) {
			e.getMessage();
		}

	}

	public void actualizarListaEvaluador() {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		DBArticulos dbart = new DBArticulos();
		List<EstadoArticulo> lista = dbart.historialArticulosE(usua.getId());
		ListModelList<EstadoArticulo> listModel = new ListModelList<EstadoArticulo>(
				lista);
		listaHistorialEvaluador.setModel(listModel);
		listaHistorialEvaluador.renderAll();
	}

	public void lineaTiempo() {

		try {
			System.out.println("lee xml");
			currentDate = dateFormat.parse("2014-07-29");
			bandinfoMonth.setDate(currentDate);
			// bandinfoYear.setDate(currentDate);
			bandinfoMonth.setEventSourceUrl("Articulo/Datos.xml");
			// bandinfoYear.setEventSourceUrl("Articulo/timeline_data.xml");
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
