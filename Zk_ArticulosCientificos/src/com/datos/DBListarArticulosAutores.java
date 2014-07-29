package com.datos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.entidades.Areas;
import com.entidades.Articulo;
import com.entidades.EstadoArticulo;
import com.entidades.Persona;
import com.entidades.PersonaArticulo;
import com.entidades.Roles;
import com.entidades.TipoArticulos;
import com.entidades.Usuarios;

public class DBListarArticulosAutores {
	public int idArticuloRegistrado = 0;

	public boolean RegistrarArticulo(Articulo art) {
		boolean resultado = false;
		// añadir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql = "INSERT INTO tb_articulo" + " (art_titulo,"
					+ " art_archivo," + " art_resumen, "
					+ " art_palabras_clave, " + " art_fecha_subida, "
					+ " art_estado, " + " tipo_id, " + " area_id, "
					+ " per_id ) VAlUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm = con.prepareStatement(sql);
			pstm.setString(1, art.getArt_titulo());
			pstm.setString(2, art.getArt_archivo());
			pstm.setString(3, art.getArt_resumen());
			pstm.setString(4, art.getArt_palabras_clave());

			java.sql.Date fecha = new java.sql.Date(art.getArt_fecha_subida()
					.getTime());

			pstm.setDate(5, fecha);
			pstm.setInt(6, art.getArt_estado());
			pstm.setInt(7, art.getTipo_id());
			pstm.setInt(8, art.getId_area());
			pstm.setInt(9, art.getPer_id());
			int filas_afectadas = pstm.executeUpdate();
			con.commit();
			resultado = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultado;
	}

	public boolean RegistrarPersonaArticulo(PersonaArticulo perArt) {
		boolean resultado = false;
		// añadir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql = "INSERT INTO tb_persona_articulo " + " (pers_id,"
					+ " arti_id," + " per_art_estado, "
					+ " per_id_registra) VAlUES (?,?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, perArt.getPers_id());
			pstm.setInt(2, perArt.getArti_id());
			pstm.setInt(3, perArt.getPer_art_estado());
			pstm.setInt(4, perArt.getPer_id_registra());
			int filas_afectadas = pstm.executeUpdate();
			con.commit();
			resultado = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultado;
	}

	public boolean RegistrarEstadoArticulo(EstadoArticulo estArt) {
		boolean resultado = false;
		// añadir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql = "INSERT INTO tb_estado_articulo " + " (id_articulo,"
					+ " id_estado," + " id_persona, "
					+ " fecha) VAlUES (?,?,?,CURRENT_DATE)";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, estArt.getId_articulo());
			pstm.setInt(2, estArt.getId_estado());
			pstm.setInt(3, estArt.getId_persona());
			int filas_afectadas = pstm.executeUpdate();
			con.commit();
			resultado = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultado;
	}

	public List<Articulo> buscarArticulos(String criterio) {
		List<Articulo> lista = new ArrayList<Articulo>();
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String sql = "";
		if (criterio.equals("")) {
			sql = "SELECT * FROM tb_articulos as art"
					+ " WHERE art.art_estado=1";
			System.out.println("busqueda" + sql);
		} else {
			sql = "SELECT * FROM tb_articulos as art"
					+ " WHERE art.art_estado=1 and (r.art_titulo like '%"
					+ criterio + "%')";
		}
		try {
			sentencia = con.createStatement();
			resultado = sentencia.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al ejecutar la sentencia");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lista;
	}

	public int obtenerIdArticuloRegistrado(String direc) {
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();

		Statement sentencia = null;
		ResultSet resultados = null;
		System.out.println("direccion a buscar: " + direc);
		String query = "Select ta.art_id from tb_articulo as ta where ta.art_archivo='"
				+ direc + "'";
		System.out.println("query: " + query);
		try {
			sentencia = con.createStatement();
			resultados = sentencia.executeQuery(query);
			// recorrer el resultset
			while (resultados.next()) {
				Articulo arti = new Articulo();
				arti.setArt_id(resultados.getInt("art_id"));
				idArticuloRegistrado = (resultados.getInt("art_id"));
				System.out.println("idart" + idArticuloRegistrado);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al recorrer resultset");
		} finally {
			//
			try {
				// cerrar la conexion
				con.close();
			} catch (SQLException e) {
				// todo auto generated cath block
				e.printStackTrace();
			}
		}
		return idArticuloRegistrado;
	}

	public List<Articulo> buscarArticulo(int idEstado, String titulo,
			String autor, String tipoa, String area) {
		int idUsuario = 0;
		List<Articulo> lista = new ArrayList<Articulo>();
		// objeto sentencia
		Statement sentencia = null;
		// objeto para resultados
		ResultSet resultados = null;
		// obtener la conexion a la base
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		Session session = Sessions.getCurrent();
		Sessions.getCurrent();
		Usuarios usua = (Usuarios) session.getAttribute("User");
		idUsuario = usua.getId();
		String sql = "";
		if (usua != null) {
			if (usua.getId_rol() == 1) {
				if (titulo.equals("") && autor.equals("") && tipoa.equals("")
						&& area.equals("")) {
					sql = "SELECT a.art_id, a.art_titulo, p2.per_id, p2.per_nombre, p2.per_apellido, ar.area_nombre, "
							+ "ta.tipo_nombre, pa.per_art_id, CONCAT(p.per_nombre,' ', p.per_apellido) as autor, a.art_resumen, a.art_palabras_clave, "
							+ "a.art_fecha_subida, CONCAT(p2.per_institucion_pertenece,' - ',p2.per_direccion_institucion) AS institucion1, CONCAT(p.per_institucion_pertenece,' - ',p.per_direccion_institucion) AS institucion2 "
							+ "FROM tb_persona AS p "
							+ "INNER JOIN tb_persona_articulo AS pa ON p.per_id = pa.pers_id "
							+ "INNER JOIN tb_persona AS p2 ON pa.per_id_registra = p2.per_id "
							+ "INNER JOIN tb_articulo AS a ON pa.arti_id =a.art_id "
							+ "INNER JOIN tb_estado_articulo esa ON esa.id_articulo = a.art_id "
							+ "INNER JOIN tb_estados est ON est.id_estado = esa.id_estado "
							+ "INNER JOIN tb_area AS ar ON a.area_id= ar.area_id  "
							+ "INNER JOIN tb_tipo_articulo ta ON ta.tipo_id = a.tipo_id "
							+ "WHERE esa.id_estado="
							+ idEstado
							+ " and a.art_estado =1 AND pa.per_art_estado =1 "
							+ "order by a.art_titulo";

					System.out.println("ddff" + sql);
				} else {
					sql = "SELECT a.art_id, a.art_titulo, p2.per_id, p2.per_nombre, p2.per_apellido, ar.area_nombre, "
							+ "ta.tipo_nombre, pa.per_art_id, CONCAT(p.per_nombre,' ', p.per_apellido) as autor, a.art_resumen, a.art_palabras_clave, a.art_fecha_subida, "
							+ "CONCAT(p2.per_institucion_pertenece,' - ',p2.per_direccion_institucion) AS institucion1, CONCAT(p.per_institucion_pertenece,' - ',p.per_direccion_institucion) AS institucion2 "
							+ "FROM tb_persona AS p "
							+ "INNER JOIN tb_persona_articulo AS pa ON p.per_id = pa.pers_id "
							+ "INNER JOIN tb_persona AS p2 ON pa.per_id_registra = p2.per_id "
							+ "INNER JOIN tb_articulo AS a ON pa.arti_id =a.art_id "
							+ "INNER JOIN tb_estado_articulo esa ON esa.id_articulo = a.art_id "
							+ "INNER JOIN tb_estados est ON est.id_estado = esa.id_estado "
							+ "INNER JOIN tb_area AS ar ON a.area_id= ar.area_id  "
							+ "INNER JOIN tb_tipo_articulo ta ON ta.tipo_id = a.tipo_id "
							+ "WHERE esa.id_estado="
							+ idEstado
							+ " and a.art_estado =1 AND pa.per_art_estado =1 "
							+ "and (a.art_titulo like '%"
							+ titulo
							+ "%' and (p2.per_nombre like '%"
							+ autor
							+ "%' or p2.per_apellido like '%"
							+ autor
							+ "%' )and ta.tipo_nombre like '%"
							+ tipoa
							+ "%' and ar.area_nombre like '%"
							+ area
							+ "%') order by a.art_titulo";

				}
				try {
					sentencia = con.createStatement();
					resultados = sentencia.executeQuery(sql);
					System.out.println("LLega al codigo =)" + resultados);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("error al ejecutar la sentencia");
				}
				// recorrer el resultset
				try {
					PersonaArticulo personaarticulo = null;
					Articulo articulo = null;
					while (resultados.next()) {
						articulo = new Articulo();
						articulo.setArt_id(resultados.getInt("art_id"));
						articulo.setArt_titulo(resultados
								.getString("art_titulo"));
						articulo.setPer_nombre(resultados
								.getString("per_nombre"));
						articulo.setPer_apellido(resultados
								.getString("per_apellido"));
						articulo.setArea_nombre(resultados
								.getString("area_nombre"));
						articulo.setTipo_nombre(resultados
								.getString("tipo_nombre"));
						articulo.setNom_colaborador(resultados
								.getString("autor"));
						// articulo.setNom_colaborador1(resultados.getString("coautor"));
						articulo.setArt_resumen(resultados
								.getString("art_resumen"));
						articulo.setArt_palabras_clave(resultados
								.getString("art_palabras_clave"));
						articulo.setArt_fecha_subida(resultados
								.getDate("art_fecha_subida"));
						articulo.setPer_institucion1(resultados
								.getString("institucion1"));
						articulo.setPer_institucion2(resultados
								.getString("institucion2"));
						lista.add(articulo);
					}
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("error al recorrer resultset");
				} finally {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			if (usua.getId_rol() == 2 || usua.getId_rol() == 3) {
				if (titulo.equals("") && autor.equals("") && tipoa.equals("")
						&& area.equals("")) {
					sql = "SELECT a.art_id, a.art_titulo, p2.per_id, p2.per_nombre, p2.per_apellido, ar.area_nombre, "
							+ "ta.tipo_nombre, pa.per_art_id, CONCAT(p.per_nombre,' ', p.per_apellido) as autor,a.art_resumen, a.art_palabras_clave, "
							+ "a.art_fecha_subida, CONCAT(p2.per_institucion_pertenece,' - ',p2.per_direccion_institucion) AS institucion1, "
							+ "CONCAT(p.per_institucion_pertenece,' - ',p.per_direccion_institucion) AS institucion2 "
							+ "FROM tb_persona AS p2 "
							+ "INNER JOIN tb_persona_articulo AS pa ON p2.per_id = pa.per_id_registra "
							+ "INNER JOIN tb_articulo AS a ON pa.arti_id =a.art_id "
							+ "INNER JOIN tb_persona AS p ON pa.pers_id= p.per_id "
							+ "INNER JOIN tb_estado_articulo esa ON esa.id_articulo = a.art_id "
							+ "INNER JOIN tb_estados est ON est.id_estado = esa.id_estado "
							+ "INNER JOIN tb_area AS ar ON a.area_id= ar.area_id  "
							+ "INNER JOIN tb_tipo_articulo ta ON ta.tipo_id = a.tipo_id "
							+ "WHERE esa.id_estado="
							+ idEstado
							+ " and (pa.per_id_registra="
							+ idUsuario
							+ " or pa.pers_id="
							+ idUsuario
							+ ") and a.art_estado =1 AND pa.per_art_estado =1 "
							+ "order by a.art_titulo ";

					System.out.println("sql:" + sql);
				} else {
					sql = "SELECT a.art_id, a.art_titulo, p2.per_id, p2.per_nombre, p2.per_apellido, ar.area_nombre, "
							+ "ta.tipo_nombre, pa.per_art_id, CONCAT(p.per_nombre,' ', p.per_apellido) as autor,a.art_resumen, a.art_palabras_clave, "
							+ "a.art_fecha_subida,CONCAT(p2.per_institucion_pertenece,' - ',p2.per_direccion_institucion) AS institucion1, "
							+ "CONCAT(p.per_institucion_pertenece,' - ',p.per_direccion_institucion) AS institucion2 "
							+ "FROM tb_persona AS p2 "
							+ "INNER JOIN tb_persona_articulo AS pa ON p2.per_id = pa.per_id_registra "
							+ "INNER JOIN tb_articulo AS a ON pa.arti_id =a.art_id "
							+ "INNER JOIN tb_persona AS p ON pa.pers_id= p.per_id "
							+ "INNER JOIN tb_estado_articulo esa ON esa.id_articulo = a.art_id "
							+ "INNER JOIN tb_estados est ON est.id_estado = esa.id_estado "
							+ "INNER JOIN tb_area AS ar ON a.area_id= ar.area_id  "
							+ "INNER JOIN tb_tipo_articulo ta ON ta.tipo_id = a.tipo_id "
							+ "WHERE esa.id_estado="
							+ idEstado
							+ " and (pa.per_id_registra="
							+ idUsuario
							+ " or pa.pers_id="
							+ idUsuario
							+ " )and a.art_estado =1 AND pa.per_art_estado =1 "
							+ "and (a.art_titulo like '%"
							+ titulo
							+ "%' and (p2.per_nombre like '%"
							+ autor
							+ "%' or p2.per_apellido like '%"
							+ autor
							+ "%') and ta.tipo_nombre like '%"
							+ tipoa
							+ "%' and ar.area_nombre like '%"
							+ area
							+ "%') order by a.art_titulo";

				}

			}
		}

		try {
			sentencia = con.createStatement();
			resultados = sentencia.executeQuery(sql);
			System.out.println("LLega al codigo =)" + resultados);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al ejecutar la sentencia");
		}
		// recorrer el resultset
		try {

			Articulo articulo = null;
			while (resultados.next()) {
				articulo = new Articulo();
				articulo.setArt_id(resultados.getInt("art_id"));
				articulo.setArt_titulo(resultados.getString("art_titulo"));
				articulo.setPer_nombre(resultados.getString("per_nombre"));
				articulo.setPer_apellido(resultados.getString("per_apellido"));
				articulo.setArea_nombre(resultados.getString("area_nombre"));
				articulo.setTipo_nombre(resultados.getString("tipo_nombre"));
				articulo.setNom_colaborador(resultados.getString("autor"));
				articulo.setArt_resumen(resultados.getString("art_resumen"));
				articulo.setArt_palabras_clave(resultados
						.getString("art_palabras_clave"));
				articulo.setArt_fecha_subida(resultados
						.getDate("art_fecha_subida"));

				articulo.setPer_institucion1(resultados
						.getString("institucion1"));
				articulo.setPer_institucion2(resultados
						.getString("institucion2"));
				lista.add(articulo);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al recorrer resultset");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return lista;
	}

	public List<Articulo> buscarArticuloEvaluador(String titulo, String area, String tipoa) {
		List<Articulo> lista = new ArrayList<Articulo>();
		Statement sentencia = null;
		ResultSet registros = null;
		int idUsuario = 0;
		Session session = Sessions.getCurrent();
		Sessions.getCurrent();
		Usuarios usua = (Usuarios) session.getAttribute("User");
		idUsuario = usua.getId();
		// busqueda a la base de datos
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String sql = "SELECT ta.art_id, ta.art_titulo, tp.per_id, tp.per_nombre, tp.per_apellido, " +
				"tpa.pers_id, tta.tipo_id, tta.tipo_nombre, tar.area_id, tar.area_nombre, ta.art_resumen,ta.padre," +
				"ta.art_palabras_clave, tareva.eval_observacion, tareva.nombre, tareva.direccion " +
				" FROM (((((tb_articulo AS ta INNER JOIN tb_persona AS tp ON ta.per_id = tp.per_id ) " +
				"INNER JOIN tb_tipo_articulo AS tta ON ta.tipo_id = tta.tipo_id ) " +
				"INNER JOIN tb_area AS tar ON ta.area_id = tar.area_id) INNER JOIN tb_persona_articulo AS tpa ON ta.art_id = tpa.arti_id ) " +
				"INNER JOIN tb_estado_articulo esa ON esa.id_articulo = ta.art_id INNER JOIN tb_estados est ON est.id_estado = esa.id_estado )" +
				"INNER JOIN tb_articulos_evaluados AS tareva ON ta.art_id = tareva.ar_id WHERE tp.per_id ="
				+ idUsuario +" AND ta.art_estado =1 AND ( esa.id_estado =3 AND esa.id_ult_estado =1 ) " +
				"and (ta.art_titulo like '%"
				+ titulo
				+ "%' and tar.area_nombre like '%"
				+ area
				+ "%' and tta.tipo_nombre like '%"
				+ tipoa
				+ "%' ) ORDER BY ta.art_titulo";		
			
		
	
		System.out.println("Sql Return Articulo: " + sql);

		try {
			sentencia = con.createStatement();
			registros = sentencia.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al ejecutar la sentencia");
		}

		Articulo a = null;
		try {
			while (registros.next()) {
				a = new Articulo();
				a.setArt_id(registros.getInt("art_id"));
				a.setArt_titulo(registros.getString("art_titulo"));
				a.setPer_id(registros.getInt("per_id"));
				a.setPer_nombre(registros.getString("per_nombre"));
				a.setPer_apellido(registros.getString("per_apellido"));
				a.setIdColaborador(registros.getInt("pers_id"));
				a.setTipo_id(registros.getInt("tipo_id"));
				a.setTipo_nombre(registros.getString("tipo_nombre"));
				a.setId_area(registros.getInt("area_id"));
				a.setArea_nombre(registros.getString("area_nombre"));
				a.setArt_resumen(registros.getString("art_resumen"));
				a.setArt_palabras_clave(registros
						.getString("art_palabras_clave"));
				a.setObservacion(registros.getString("eval_observacion"));
				a.setNombreArticulo(registros.getString("nombre"));
				a.setRuta(registros.getString("direccion"));
				a.setPadre(registros.getInt("padre"));
				System.out.println("padre ultimo"+ a.getPadre());
				// agrego usuario con datos cargados desde la base a mi lista de
				// usuarios
				lista.add(a);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String nombreColaboradores = "";
		String Colaboradores="";
		int id;
		boolean bandera;
		do {
			bandera = false;
			for (int i = 0; i < lista.size() - 1; i++) {
				if (lista.get(i).getArt_id() == lista.get(i + 1).getArt_id()) {
					System.out.println(lista.get(i).getArt_id());
					lista.remove(i + 1);
					bandera = true;
				}
			}
		} while (bandera == true);

		return lista;
	}
	
}
