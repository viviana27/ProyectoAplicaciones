package com.datos;

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
import com.entidades.Persona;
import com.entidades.PersonaArticulo;
import com.entidades.Roles;
import com.entidades.TipoArticulos;
import com.entidades.Usuarios;

public class DBArticulos {
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
					+ " per_id," + " id_estado ) VAlUES (?,?,?,?,?,?,?,?,?,?)";
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
			pstm.setInt(10, art.getId_estado());
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
			String sql = "INSERT INTO tb_persona_articulo" + " (pers_id,"
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

		Statement sentencia;
		ResultSet resultados;
System.out.println("direccion a buscar: "+direc);
		String query = "Select ta.art_id from tb_articulo as ta where ta.art_archivo='"
				+ direc+"'";
		System.out.println("query: "+query);
		try {
			sentencia = con.createStatement();
			resultados = sentencia.executeQuery(query);
			// recorrer el resultset
			while (resultados.next()) {
				idArticuloRegistrado = (resultados.getInt("art_id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public List<Articulo> buscarArticulo(String titulo, String autor, String tipoa, String area) {
		int idUsuario=0;
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
		
//||
		String sql = "";
		if (usua != null) {
			if (usua.getId_rol() == 1) {
				if (titulo.equals("") && autor.equals("") && tipoa.equals("") && area.equals("") ) {
					sql = "SELECT a.art_id, a.art_titulo, p2.per_id, p2.per_nombre, p2.per_apellido, ar.area_nombre, " +
							"ta.tipo_nombre, pa.per_art_id, p.per_nombre as autor " +
							"FROM tb_persona AS p " +
							"INNER JOIN tb_persona_articulo AS pa ON p.per_id = pa.pers_id " +
							"INNER JOIN tb_persona AS p2 ON pa.per_id_registra = p2.per_id " +
							"INNER JOIN tb_articulo AS a ON pa.arti_id =a.art_id " +
							"INNER JOIN tb_area AS ar ON a.area_id= ar.area_id  " +
							"INNER JOIN tb_tipo_articulo ta ON ta.tipo_id = a.tipo_id " +
							"WHERE  a.id_estado =1 AND pa.per_art_estado =1 " +
							"order by a.art_titulo";

					System.out.println("ddff" + sql);
				} else {
					sql = "SELECT a.art_id, a.art_titulo, p2.per_id, p2.per_nombre, p2.per_apellido, ar.area_nombre, " +
							"ta.tipo_nombre, pa.per_art_id, p.per_nombre as autor " +
							"FROM tb_persona AS p " +
							"INNER JOIN tb_persona_articulo AS pa ON p.per_id = pa.pers_id " +
							"INNER JOIN tb_persona AS p2 ON pa.per_id_registra = p2.per_id " +
							"INNER JOIN tb_articulo AS a ON pa.arti_id =a.art_id " +
							"INNER JOIN tb_area AS ar ON a.area_id= ar.area_id  " +
							"INNER JOIN tb_tipo_articulo ta ON ta.tipo_id = a.tipo_id " +
							"WHERE a.id_estado =1 AND pa.per_art_estado =1 " +
							"and (a.art_titulo like '%"
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
					System.out.println("LLega al codigo =)"+resultados);
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
						articulo.setArt_titulo(resultados.getString("art_titulo"));
						articulo.setPer_nombre(resultados.getString("per_nombre"));
						articulo.setPer_apellido(resultados.getString("per_apellido"));
						articulo.setArea_nombre(resultados.getString("area_nombre"));
						articulo.setTipo_nombre(resultados.getString("tipo_nombre"));
						articulo.setNom_colaborador(resultados.getString("autor"));
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
			
			} 
		if (usua.getId_rol() == 2 || usua.getId_rol() == 3) {
				if (titulo.equals("") && autor.equals("") && tipoa.equals("") && area.equals("") ) {
					sql = "SELECT a.art_id, a.art_titulo, p2.per_id, p2.per_nombre, p2.per_apellido, ar.area_nombre, " +
							"ta.tipo_nombre, pa.per_art_id, p.per_nombre as autor " +
							"FROM tb_persona AS p " +
							"INNER JOIN tb_persona_articulo AS pa ON p.per_id = pa.pers_id " +
							"INNER JOIN tb_persona AS p2 ON pa.per_id_registra = p2.per_id " +
							"INNER JOIN tb_articulo AS a ON pa.arti_id =a.art_id " +
							"INNER JOIN tb_area AS ar ON a.area_id= ar.area_id  " +
							"INNER JOIN tb_tipo_articulo ta ON ta.tipo_id = a.tipo_id " +
							"WHERE p2.per_id="+idUsuario+" and a.id_estado =1 AND pa.per_art_estado =1 " +
							"order by a.art_titulo";

					System.out.println("ddff" + sql);
				}
				else
				{
					sql = "SELECT a.art_id, a.art_titulo, p2.per_id, p2.per_nombre, p2.per_apellido, ar.area_nombre, " +
							"ta.tipo_nombre, pa.per_art_id, p.per_nombre as autor " +
							"FROM tb_persona AS p " +
							"INNER JOIN tb_persona_articulo AS pa ON p.per_id = pa.pers_id " +
							"INNER JOIN tb_persona AS p2 ON pa.per_id_registra = p2.per_id " +
							"INNER JOIN tb_articulo AS a ON pa.arti_id =a.art_id " +
							"INNER JOIN tb_area AS ar ON a.area_id= ar.area_id  " +
							"INNER JOIN tb_tipo_articulo ta ON ta.tipo_id = a.tipo_id " +
							"WHERE p2.per_id="+idUsuario+" and a.id_estado =1 AND pa.per_art_estado =1 " +
							"and (a.art_titulo like '%"
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
		try {
			sentencia = con.createStatement();
			resultados = sentencia.executeQuery(sql);
			System.out.println("LLega al codigo =)"+resultados);
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
				articulo.setArt_titulo(resultados.getString("art_titulo"));
				articulo.setPer_nombre(resultados.getString("per_nombre"));
				articulo.setPer_apellido(resultados.getString("per_apellido"));
				articulo.setArea_nombre(resultados.getString("area_nombre"));
				articulo.setTipo_nombre(resultados.getString("tipo_nombre"));
				articulo.setNom_colaborador(resultados.getString("autor"));
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
	
	public List<Articulo> buscarArticuloEvaluador(String titulo) {
		int idUsuario=0;
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
		
//||
		String sql = "";
		if (usua != null) {
			if (usua.getId_rol() == 1) {
				if (titulo.equals("") ) {
					sql = "SELECT pa.articulos_id, ar.art_id, ar.art_titulo, a.area_nombre, " +
							"a.area_id, p.per_nombre, p.per_apellido " +
							"FROM tb_persona AS p LEFT JOIN tb_pares AS pa ON pa.personas_id = p.per_id " +
							"RIGHT JOIN tb_articulo AS ar ON ar.art_id = pa.articulos_id " +
							"LEFT JOIN tb_area AS a ON a.area_id = ar.area_id " +
							"WHERE ar.art_estado =1 " +
							"ORDER BY  p.per_nombre";

					System.out.println("ddff" + sql);
				} else {
					sql =  "SELECT pa.articulos_id, ar.art_id, ar.art_titulo, a.area_nombre, " +
							"a.area_id, p.per_nombre, p.per_apellido " +
							"FROM tb_persona AS p LEFT JOIN tb_pares AS pa ON pa.personas_id = p.per_id " +
							"RIGHT JOIN tb_articulo AS ar ON ar.art_id = pa.articulos_id " +
							"LEFT JOIN tb_area AS a ON a.area_id = ar.area_id " +
							"WHERE ar.art_estado =1 " +
							"and (ar.art_titulo like '%"
							+ titulo
							+ "%' ) order by  p.per_nombre";
					
				}
				try {
					sentencia = con.createStatement();
					resultados = sentencia.executeQuery(sql);
					System.out.println("LLega al codigo =)"+resultados);
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
						articulo.setArt_titulo(resultados.getString("art_titulo"));
						articulo.setArea_nombre(resultados.getString("area_nombre"));
						articulo.setId_area(resultados.getInt("area_id"));
						articulo.setPer_nombre(resultados.getString("per_nombre"));
						articulo.setPer_apellido(resultados.getString("per_apellido"));
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
			
			} 
		

	
		return lista;
	}
}


		
		
