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
import com.entidades.Estados;
import com.entidades.ObservacionesEvaluadores;
import com.entidades.Persona;
import com.entidades.PersonaArticulo;
import com.entidades.Roles;
import com.entidades.TipoArticulos;
import com.entidades.Usuarios;

public class DBArticulos {
	public int idArticuloRegistrado = 0;
	int idUsuario = 0;
	boolean existe;
	Session session = Sessions.getCurrent();

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
					+ " per_id) VAlUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm = con.prepareStatement(sql);
			pstm.setString(1, art.getArt_titulo());
			pstm.setString(2, art.getArt_archivo());
			pstm.setString(3, art.getArt_resumen());
			pstm.setString(4, art.getArt_palabras_clave());

			java.sql.Date fecha = new java.sql.Date(art.getArt_fecha_subida()
					.getTime());
			System.out.println("la fecha es : " + fecha);
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

	public boolean RegistrarArticuloModificado(Articulo art, int idAutor2,
			int idAutor3) {
		boolean resultado = false;
		// añadir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		Statement sentencia = null;
		ResultSet resultadoSet = null;
		int filas_afectadas = 0;
		try {
			con.setAutoCommit(false);
			// DBManager dbm = new DBManager();
			// Connection cone = dbm.getConection();

			// por defecto es true asi q lo cambiamos

			String sqlUpdate = "UPDATE tb_articulo SET art_estado=? where art_id=?";
			PreparedStatement pstm = con.prepareStatement(sqlUpdate);
			pstm.setInt(1, 0);
			pstm.setInt(2, art.getIdPadre());
			int num = pstm.executeUpdate();

			// pstm.setInt(3, rol.getRol_id());

			// ejecutar el prepaaredStatement
			// retorna el numero de filas afectadas o retorna 0 si no se pudo
			// realizar
			// int num=pstm.executeUpdate();
			// Connection con1 = dbm.getConection();
			// con.setAutoCommit(false);
			String sql = "INSERT INTO tb_articulo"
					+ " (art_titulo, art_archivo, art_resumen, "
					+ " art_palabras_clave, "
					+ " art_estado, "
					+ " tipo_id, "
					+ " area_id, "
					+ " per_id, id_padre, padre , art_fecha_subida ) VAlUES (?,?,?,?,?,?,?,?,?,?,CURRENT_DATE)";
			// pstm=new PreparedStatement();
			PreparedStatement pstm1 = con.prepareStatement(sql);
			pstm1 = con.prepareStatement(sql);
			pstm1.setString(1, art.getArt_titulo());
			pstm1.setString(2, art.getArt_archivo());
			pstm1.setString(3, art.getArt_resumen());
			pstm1.setString(4, art.getArt_palabras_clave());

			/*
			 * java.sql.Date fecha = new java.sql.Date(art.getArt_fecha_subida()
			 * .getTime()); System.out.println("la fecha es : "+fecha);
			 */

			pstm1.setInt(5, art.getArt_estado());
			pstm1.setInt(6, art.getTipo_id());
			pstm1.setInt(7, art.getId_area());
			pstm1.setInt(8, art.getPer_id());
			pstm1.setInt(9, art.getIdPadre());
			pstm1.setInt(10, art.getPadre());
			num = pstm1.executeUpdate();

			String sqlTbPerArticulo = "INSERT into tb_persona_articulo (pers_id,per_art_estado,per_id_registra,arti_id) values (?,?,?,(SELECT MAX(art_id) AS id FROM tb_articulo))";
			PreparedStatement pstm2 = con.prepareStatement(sqlTbPerArticulo);
			pstm2 = con.prepareStatement(sqlTbPerArticulo);
			pstm2.setInt(1, idAutor2);
			pstm2.setInt(2, 1);
			pstm2.setInt(3, art.getPer_id());
			// num = pstm2.executeUpdate();

			String sqlTbPerArticuloAutor2 = "INSERT into tb_persona_articulo (pers_id,arti_id,per_art_estado,per_id_registra) values (?,(SELECT MAX(art_id) AS id FROM tb_articulo),?,?)";
			PreparedStatement pstm3 = con
					.prepareStatement(sqlTbPerArticuloAutor2);
			pstm3 = con.prepareStatement(sqlTbPerArticuloAutor2);
			pstm3.setInt(1, idAutor3);
			pstm3.setInt(2, 1);
			pstm3.setInt(3, art.getPer_id());
			// num = pstm3.executeUpdate();

			String sqlBuscaEvaluadores = "select * from tb_pares where articulos_id="
					+ art.getIdPadre();
			// PreparedStatement pstm3 = con.prepareStatement(sqlTbPerArticulo);
			sentencia = con.createStatement();
			resultadoSet = sentencia.executeQuery(sqlBuscaEvaluadores);
			String sqlInsertEvaluadores = "INSERT into tb_pares (personas_id,id_esta,par_cantidad,par_estado,articulos_id) values (?,?,?,?,(SELECT MAX(art_id) AS id FROM tb_articulo))";
			while (resultadoSet.next()) {
				System.out.println("idEvaluador: "
						+ resultadoSet.getInt("personas_id"));
				PreparedStatement pstm4 = con
						.prepareStatement(sqlInsertEvaluadores);
				pstm4 = con.prepareStatement(sqlInsertEvaluadores);
				pstm4.setInt(1, resultadoSet.getInt("personas_id"));
				pstm4.setInt(2, 2);
				pstm4.setInt(3, 2);
				pstm4.setInt(4, 1);
				filas_afectadas = pstm4.executeUpdate();
			}

			String sqlUpdateEstado = "Update tb_estado_articulo set id_ult_estado=0 where id_articulo=?";
			PreparedStatement pstm5 = con.prepareStatement(sqlUpdateEstado);
			pstm5 = con.prepareStatement(sqlUpdateEstado);
			pstm5.setInt(1, art.getIdPadre());
			num = pstm5.executeUpdate();

			//

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
			String sql = "INSERT INTO tb_estado_articulo (id_articulo,"
					+ " id_estado, id_ult_estado, id_persona, "
					+ " fecha) VAlUES (?,?,?,?,CURRENT_DATE)";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, estArt.getId_articulo());
			pstm.setInt(2, estArt.getId_estado());
			pstm.setInt(3, estArt.getId_utl_estado());
			pstm.setInt(4, estArt.getId_persona());
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
					/*
					 * Aqui se cambia el sql por el nuevo procedimiento
					 */

					try {
						CallableStatement proc;
						proc = con.prepareCall("{call spautor(?)}");
						proc.setInt(1, idEstado);
						proc.execute();
						Articulo articulo = null;
						ResultSet rs = proc.getResultSet();
						while (rs.next()) {
							articulo = new Articulo();
							articulo.setArt_id(rs.getInt("artid"));
							articulo.setArt_titulo(rs.getString("artTitulo"));
							articulo.setNom_colaborador(rs.getString("autor"));

							articulo.setArea_nombre(rs.getString("area_nombre"));
							articulo.setTipo_nombre(rs.getString("tipo_nombre"));
							// articulo.setNom_colaborador1(resultados.getString("coautor"));
							articulo.setArt_resumen(rs.getString("art_resumen"));
							articulo.setNom_colaborador1(rs
									.getString("coautor"));
							articulo.setArt_palabras_clave(rs
									.getString("art_palab_clave"));
							articulo.setPer_institucion1(rs
									.getString("per_institucion1"));
							articulo.setPer_institucion2(rs
									.getString("per_institucion2"));
							articulo.setArt_fecha_subida(rs
									.getDate("art_fecha_subida"));
							articulo.setEmail(rs.getString("per_email"));
							articulo.setObservacion(rs.getString("veval_observacion"));
							System.out.println("observacion : " + articulo.getObservacion());
							articulo.setNombreArticulo(rs.getString("vnombre"));
							articulo.setRuta(rs.getString("vdireccion"));
							lista.add(articulo);
							System.out.println("Name : " + rs.getString(5));
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("ddff" + sql);
				}

				else {
					try {
						CallableStatement proc;
						proc = con
								.prepareCall("{call spautorparametros(?,?,?,?,?)}");
						proc.setInt(1, idEstado);
						proc.setString(2, '%' + titulo + '%');
						proc.setString(3, '%' + autor + '%');
						proc.setString(4, '%' + tipoa + '%');
						proc.setString(5, '%' + area + '%');
						proc.execute();
						Articulo articulo = null;
						ResultSet rs = proc.getResultSet();
						while (rs.next()) {
							articulo = new Articulo();
							articulo.setArt_id(rs.getInt("artid"));
							articulo.setArt_titulo(rs.getString("artTitulo"));
							articulo.setNom_colaborador(rs.getString("autor"));

							articulo.setArea_nombre(rs.getString("area_nombre"));
							articulo.setTipo_nombre(rs.getString("tipo_nombre"));
							// articulo.setNom_colaborador1(resultados.getString("coautor"));
							articulo.setArt_resumen(rs.getString("art_resumen"));
							articulo.setNom_colaborador1(rs
									.getString("coautor"));
							articulo.setArt_palabras_clave(rs
									.getString("art_palab_clave"));
							articulo.setPer_institucion1(rs
									.getString("per_institucion1"));
							articulo.setPer_institucion2(rs
									.getString("per_institucion2"));

							articulo.setArt_fecha_subida(rs
									.getDate("art_fecha_subida"));
							articulo.setEmail(rs.getString("per_email"));
							articulo.setObservacion(rs.getString("veval_observacion"));
							articulo.setNombreArticulo(rs.getString("vnombre"));
							articulo.setRuta(rs.getString("vdireccion"));
							lista.add(articulo);
							
							System.out.println("Name : " + rs.getString(5));
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			} else {
				if (usua.getId_rol() == 2 || usua.getId_rol() == 3) {
					if (titulo.equals("") && autor.equals("")
							&& tipoa.equals("") && area.equals("")) {
						try {
							CallableStatement proc;
							proc = con
									.prepareCall("{call sp_segun_autor(?,?)}");
							proc.setInt(1, idEstado);
							proc.setInt(2, idUsuario);
							proc.execute();
							Articulo articulo = null;
							ObservacionesEvaluadores observ=null;
							ResultSet rs = proc.getResultSet();
							while (rs.next()) {
								articulo = new Articulo();
								observ=new ObservacionesEvaluadores();
								articulo.setArt_id(rs.getInt("artid"));
								articulo.setArt_titulo(rs
										.getString("artTitulo"));
								articulo.setNom_colaborador(rs
										.getString("autor"));

								articulo.setArea_nombre(rs
										.getString("area_nombre"));
								articulo.setTipo_nombre(rs
										.getString("tipo_nombre"));
								// articulo.setNom_colaborador1(resultados.getString("coautor"));
								articulo.setArt_resumen(rs
										.getString("art_resumen"));
								articulo.setNom_colaborador1(rs
										.getString("coautor"));
								articulo.setArt_palabras_clave(rs
										.getString("art_palab_clave"));
								articulo.setPer_institucion1(rs
										.getString("per_institucion1"));
								articulo.setPer_institucion2(rs
										.getString("per_institucion2"));
								articulo.setArt_fecha_subida(rs
										.getDate("art_fecha_subida"));
								articulo.setEmail(rs.getString("per_email"));
								articulo.setObservacion(rs.getString("veval_observacion"));
								articulo.setNombreArticulo(rs.getString("vnombre"));
								articulo.setRuta(rs.getString("vdireccion"));
								lista.add(articulo);
								System.out.println("Name : " + rs.getString(3));
							}

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						System.out.println("ddff" + sql);

					} else {
						try {
							CallableStatement proc;
							proc = con
									.prepareCall("{call sp_segun_autorparame(?,?,?,?,?,?)}");
							proc.setInt(1, idEstado);
							proc.setInt(2, idUsuario);
							proc.setString(3, '%' + titulo + '%');
							proc.setString(4, '%' + autor + '%');
							proc.setString(5, '%' + tipoa + '%');
							proc.setString(6, '%' + area + '%');
							proc.execute();
							Articulo articulo = null;
							ResultSet rs = proc.getResultSet();
							while (rs.next()) {
								articulo = new Articulo();
								articulo.setArt_id(rs.getInt("artid"));
								articulo.setArt_titulo(rs
										.getString("artTitulo"));
								articulo.setNom_colaborador(rs
										.getString("autor"));

								articulo.setArea_nombre(rs
										.getString("area_nombre"));
								articulo.setTipo_nombre(rs
										.getString("tipo_nombre"));
								// articulo.setNom_colaborador1(resultados.getString("coautor"));
								articulo.setArt_resumen(rs
										.getString("art_resumen"));
								articulo.setNom_colaborador1(rs
										.getString("coautor"));
								articulo.setArt_palabras_clave(rs
										.getString("art_palab_clave"));
								articulo.setPer_institucion1(rs
										.getString("per_institucion1"));
								articulo.setPer_institucion2(rs
										.getString("per_institucion2"));
								articulo.setArt_fecha_subida(rs
										.getDate("art_fecha_subida"));
								articulo.setEmail(rs.getString("per_email"));
								articulo.setObservacion(rs.getString("veval_observacion"));
								articulo.setNombreArticulo(rs.getString("vnombre"));
								articulo.setRuta(rs.getString("vdireccion"));
								lista.add(articulo);
								System.out.println("Name : " + rs.getString(5));
							}

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}

		}
		return lista;

	}

	public List<Articulo> buscarArticuloEvaluador(String titulo, String area) {
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

		// ||
		String sql = "";
		if (usua != null) {
			if (usua.getId_rol() == 1) {
				if (titulo.equals("") && area.equals("")) {
					sql = "SELECT ar.art_id, ar.art_titulo, ar.art_archivo, "
							+ "a.area_nombre, a.area_id, p.per_nombre, p.per_apellido "
							+ "FROM tb_persona AS p right join tb_pares as pa "
							+ "on pa.personas_id=p.per_id right join tb_articulo as ar"
							+ " on ar.per_id=p.per_id inner join tb_area as a "
							+ "on a.area_id=ar.area_id right join tb_estado_articulo as esa "
							+ "on esa.id_articulo=ar.art_id where(esa.id_estado=1 and esa.id_ult_estado=1)";

					System.out.println("sentencia por rol administrador" + sql);
				} else {
					sql = "SELECT ar.art_id, ar.art_titulo, ar.art_archivo, "
							+ "a.area_nombre, a.area_id, p.per_nombre, p.per_apellido "
							+ "FROM tb_persona AS p right join tb_pares as pa "
							+ "on pa.personas_id=p.per_id right join tb_articulo as ar"
							+ " on ar.per_id=p.per_id inner join tb_area as a "
							+ "on a.area_id=ar.area_id right join tb_estado_articulo as esa "
							+ "on esa.id_articulo=ar.art_id "
							+ "WHERE ar.art_estado =1 "
							+ "and (ar.art_titulo like '%" + titulo
							+ "%' and a.area_nombre like '%" + area
							+ "%' ) order by  p.per_nombre";

				}
				try {
					sentencia = con.createStatement();
					resultados = sentencia.executeQuery(sql);
					System.out.println("LLega al codigo =)");
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
						System.out.println(articulo.getArt_titulo()
								+ "  titulo");
						articulo.setArea_nombre(resultados
								.getString("area_nombre"));
						articulo.setId_area(resultados.getInt("area_id"));
						articulo.setPer_nombre(resultados
								.getString("per_nombre"));
						articulo.setPer_apellido(resultados
								.getString("per_apellido"));
						articulo.setArt_archivo(resultados
								.getString("art_archivo"));
						System.out.println(articulo.getArt_archivo()
								+ "  archivo");
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
			if (usua.getId_rol() == 3) {
				if (titulo.equals("") && area.equals("")) {
					sql = "SELECT pa.articulos_id, ar.art_id, ar.art_titulo,ar.art_archivo, "
							+ " a.area_nombre, "
							+ "a.area_id, p.per_id,p.per_nombre, p.per_apellido "
							+ "FROM tb_persona AS p LEFT JOIN tb_pares AS pa ON pa.personas_id = p.per_id "
							+ "right JOIN tb_articulo AS ar ON ar.art_id = pa.articulos_id "
							+ "LEFT JOIN tb_area AS a ON a.area_id = ar.area_id "
							+ "LEFT JOIN tb_estado_articulo esa ON esa.id_articulo = ar.art_id "
							+ "WHERE ar.art_estado =1 and p.per_id="
							+ idUsuario
							+ " and esa.id_estado=2 "
							+ "ORDER BY  p.per_nombre";

					System.out.println("ddff" + sql);
				} else {
					sql = "SELECT pa.articulos_id, ar.art_id, ar.art_titulo,ar.art_archivo,"
							+ " a.area_nombre, "
							+ "a.area_id, p.per_nombre, p.per_apellido "
							+ "FROM tb_persona AS p LEFT JOIN tb_pares AS pa ON pa.personas_id = p.per_id "
							+ "right JOIN tb_articulo AS ar ON ar.art_id = pa.articulos_id "
							+ "LEFT JOIN tb_area AS a ON a.area_id = ar.area_id "
							+ "LEFT JOIN tb_estado_articulo esa ON esa.id_articulo = ar.art_id "
							+ "WHERE ar.art_estado =1 and p.per_id="
							+ idUsuario
							+ " and esa.id_estado=2 "
							+ "and (ar.art_titulo like '%"
							+ titulo
							+ "%' and a.area_nombre like '%"
							+ area
							+ "%' ) order by  p.per_nombre";

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
						articulo.setArea_nombre(resultados
								.getString("area_nombre"));
						articulo.setId_area(resultados.getInt("area_id"));
						articulo.setPer_nombre(resultados
								.getString("per_nombre"));
						articulo.setPer_apellido(resultados
								.getString("per_apellido"));
						articulo.setArt_archivo(resultados
								.getString("art_archivo"));
						System.out
								.println(articulo.getArt_archivo() + "  ruta");
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

	// articulos para evaluar
	public List<Articulo> buscarArticuloAEvaluar(String titulo, String area) {
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

		// ||
		String sql = "";
		if (usua != null) {
			if (usua.getId_rol() == 1) {
				if (titulo.equals("") && area.equals("")) {
					sql = "SELECT pa.articulos_id, ar.art_id, ar.art_titulo, ar.art_archivo, "
							+ "a.area_nombre, a.area_id, p.per_nombre, p.per_apellido, p.per_email "
							+ "FROM tb_persona AS p "
							+ "left JOIN tb_pares AS pa ON pa.personas_id = p.per_id "
							+ "left JOIN tb_articulo AS ar ON ar.art_id = pa.articulos_id "
							+ "LEFT JOIN tb_area AS a ON a.area_id = ar.area_id"
							+ " WHERE ar.art_estado =1 ORDER BY p.per_nombre";

					System.out.println("sentencia por rol administrador" + sql);
				} else {
					sql = "SELECT pa.articulos_id, ar.art_id, ar.art_titulo, ar.art_archivo,"
							+ " a.area_nombre, "
							+ "a.area_id, p.per_nombre, p.per_apellido, p.per_email "
							+ "FROM tb_persona AS p LEFT JOIN tb_pares AS pa ON pa.personas_id = p.per_id "
							+ "left JOIN tb_articulo AS ar ON ar.art_id = pa.articulos_id "
							+ "LEFT JOIN tb_area AS a ON a.area_id = ar.area_id "
							+ "WHERE ar.art_estado =1 "
							+ "and (ar.art_titulo like '%"
							+ titulo
							+ "%' and a.area_nombre like '%"
							+ area
							+ "%' ) order by  p.per_nombre";

				}
				try {
					sentencia = con.createStatement();
					resultados = sentencia.executeQuery(sql);
					System.out.println("LLega al codigo =)");
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
						System.out.println(articulo.getArt_titulo()
								+ "  titulo");
						articulo.setArea_nombre(resultados
								.getString("area_nombre"));
						articulo.setId_area(resultados.getInt("area_id"));
						articulo.setPer_nombre(resultados
								.getString("per_nombre"));
						articulo.setPer_apellido(resultados
								.getString("per_apellido"));
						articulo.setArt_archivo(resultados
								.getString("art_archivo"));
						articulo.setEmail(resultados.getString("per_email"));
						System.out.println(articulo.getArt_archivo()
								+ "  archivo");
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
			if (usua.getId_rol() == 3) {
				if (titulo.equals("") && area.equals("")) {
					sql = "SELECT pa.articulos_id, ar.art_id, ar.art_titulo,ar.art_archivo, "
							+ " a.area_nombre, "
							+ "a.area_id, p.per_id,p.per_nombre, p.per_apellido, p.per_email "
							+ "FROM tb_persona AS p LEFT JOIN tb_pares AS pa ON pa.personas_id = p.per_id "
							+ "left JOIN tb_articulo AS ar ON ar.art_id = pa.articulos_id "
							+ "LEFT JOIN tb_area AS a ON a.area_id = ar.area_id "
							+ "LEFT JOIN tb_estado_articulo esa ON esa.id_articulo = ar.art_id "
							+ "WHERE ar.art_estado =1 and p.per_id="
							+ idUsuario
							+ " and esa.id_estado=2 "
							+ "ORDER BY  p.per_nombre";

					/*
					 * sql=
					 * "SELECT pa.articulos_id, ar.art_id, ar.art_titulo,ar.art_archivo, "
					 * +
					 * "a.area_nombre, a.area_id, p.per_id,p.per_nombre, p.per_apellido"
					 * +
					 * " FROM tb_persona AS p LEFT JOIN tb_pares AS pa ON pa.personas_id = "
					 * + "p.per_id left JOIN tb_articulo AS ar ON ar.art_id = "
					 * +
					 * "pa.articulos_id LEFT JOIN tb_area AS a ON a.area_id = "
					 * +
					 * "ar.area_id LEFT JOIN tb_estado_articulo esa ON esa.id_articulo ="
					 * + " ar.art_id WHERE ar.art_estado =1 and p.per_id=12 and"
					 * +
					 * " (esa.id_estado=2 and esa.id_ult_estado=1) ORDER BY p.per_nombre"
					 * ;
					 */
					System.out.println("ddff" + sql);
				} else {
					sql = "SELECT pa.articulos_id, ar.art_id, ar.art_titulo,ar.art_archivo,"
							+ " a.area_nombre, "
							+ "a.area_id, p.per_nombre, p.per_apellido, p.per_email "
							+ "FROM tb_persona AS p LEFT JOIN tb_pares AS pa ON pa.personas_id = p.per_id "
							+ "left JOIN tb_articulo AS ar ON ar.art_id = pa.articulos_id "
							+ "LEFT JOIN tb_area AS a ON a.area_id = ar.area_id "
							+ "LEFT JOIN tb_estado_articulo esa ON esa.id_articulo = ar.art_id "
							+ "WHERE ar.art_estado =1 and p.per_id="
							+ idUsuario
							+ " and esa.id_estado=2 "
							+ "and (ar.art_titulo like '%"
							+ titulo
							+ "%' and a.area_nombre like '%"
							+ area
							+ "%' ) order by  p.per_nombre";

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
						articulo.setArea_nombre(resultados
								.getString("area_nombre"));
						articulo.setId_area(resultados.getInt("area_id"));
						articulo.setPer_nombre(resultados
								.getString("per_nombre"));
						articulo.setPer_apellido(resultados
								.getString("per_apellido"));
						articulo.setEmail(resultados.getString("per_email"));
						articulo.setArt_archivo(resultados
								.getString("art_archivo"));
						System.out
								.println(articulo.getArt_archivo() + "  ruta");
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

	public List<Persona> buscarColaboradores(int criterio) {
		List<Persona> lista = new ArrayList<Persona>();
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String sql = "SELECT tp.per_nombre, tp.per_apellido FROM tb_persona_articulo AS tpa INNER JOIN tb_persona tp ON tpa.pers_id = tp.per_id WHERE tpa.arti_id ="
				+ criterio;

		try {
			sentencia = con.createStatement();
			resultado = sentencia.executeQuery(sql);

			while (resultado.next()) {
				Persona persona = new Persona();
				persona.setPer_nombre(resultado.getString("per_nombre"));
				persona.setPer_apellido(resultado.getString("per_apellido"));
				lista.add(persona);
			}
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

	public List<EstadoArticulo> buscarestados(int criterio) {
		List<EstadoArticulo> lista = new ArrayList<EstadoArticulo>();
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String sql = "select te.id, te.id_estado,te.id_ult_estado from tb_estado_articulo as te where  te.id_articulo="
				+ criterio;

		try {
			sentencia = con.createStatement();
			resultado = sentencia.executeQuery(sql);

			while (resultado.next()) {
				EstadoArticulo estado = new EstadoArticulo();
				estado.setId(resultado.getInt("id"));
				estado.setId_utl_estado(resultado.getInt("id_ult_estado"));
				lista.add(estado);
			}
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

	public boolean Actualizr_estadoar(int estad, int ida) {

		boolean registro = false;
		// crear un objeto para la conexion
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		// vamos a guardar utilizando transacciones
		try {
			// por defecto es true asi q lo cambiamos
			con.setAutoCommit(false);
			String sql = "UPDATE tb_estado_articulo SET" + " id_ult_estado=?"
					+ " where id=? and id_articulo=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, 0);
			pstm.setInt(2, estad);
			pstm.setInt(3, ida);
			// ejecutar el prepaaredStatement
			// retorna el numero de filas afectadas o retorna 0 si no se pudo
			// realizar
			int num = pstm.executeUpdate();
			// si no hay error
			con.commit();
			registro = true;

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

		return registro;

	}

	
	/*
	 * public boolean Actualizr_estadoarmodificado(int estad1, int ida){
	 * 
	 * boolean registro=false; //crear un objeto para la conexion DBManager dbm
	 * =new DBManager(); Connection con= dbm.getConection(); //vamos a guardar
	 * utilizando transacciones try { //por defecto es true asi q lo cambiamos
	 * con.setAutoCommit(false); String sql="UPDATE tb_estado_articulo SET"
	 * +" id_ult_estado=?" +" where id=? and id_articulo=?"; PreparedStatement
	 * pstm=con.prepareStatement(sql); pstm.setInt(1,0); pstm.setInt(2,estad1);
	 * pstm.setInt(4,ida); //ejecutar el prepaaredStatement //retorna el numero
	 * de filas afectadas o retorna 0 si no se pudo realizar int
	 * num=pstm.executeUpdate(); //si no hay error con.commit(); registro=true;
	 * 
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); try { con.rollback(); } catch (SQLException e1) { //
	 * TODO Auto-generated catch block e1.printStackTrace(); } }finally{ try {
	 * con.close(); } catch (SQLException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } }
	 * 
	 * return registro;
	 * 
	 * }
	 */
	public int buscarPromedioArticulo(int idArticulo) {
		int promedio = 0;
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String sql = "select sum(eval_promedio)/2 as promedio from tb_articulos_evaluados where ar_id="
				+ idArticulo;
		try {
			sentencia = con.createStatement();
			resultado = sentencia.executeQuery(sql);

			while (resultado.next()) {
				promedio = (resultado.getInt("promedio"));
			}
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
		return promedio;
	}

	public int CantidadEvaluaciones(int idArticulo) {
		int promedio = 0;
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String sql = "select count(*) as cantidad from tb_articulos_evaluados where ar_id="
				+ idArticulo;
		try {
			sentencia = con.createStatement();
			resultado = sentencia.executeQuery(sql);

			while (resultado.next()) {
				promedio = (resultado.getInt("cantidad"));
			}
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
		return promedio;
	}

	public int Cantidadcorreciones(int idpadre) {
		int cantico = 0;
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String sql = "select count(*) as cantidad from tb_articulo where padre="
				+ idpadre;
		try {
			sentencia = con.createStatement();
			resultado = sentencia.executeQuery(sql);

			while (resultado.next()) {
				cantico = (resultado.getInt("cantidad"));
			}
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
		return cantico;
	}

	public List<ObservacionesEvaluadores> ObservacionesEvaluaciones(
			int idArticulo) {
		// int promedio=0;
		List<ObservacionesEvaluadores> lista = new ArrayList<ObservacionesEvaluadores>();
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String sql = "select eval_observacion,nombre,direccion from tb_articulos_evaluados where ar_id="
				+ idArticulo;
		try {
			sentencia = con.createStatement();
			resultado = sentencia.executeQuery(sql);

			while (resultado.next()) {
				ObservacionesEvaluadores obs = new ObservacionesEvaluadores();
				obs.setEval_observacion(resultado.getString("eval_observacion"));
				obs.setNombre(resultado.getString("nombre"));
				obs.setDireccion(resultado.getString("direccion"));
				lista.add(obs);
			}
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

	public List<EstadoArticulo> historialArticulos(int idArt) {
		List<EstadoArticulo> lista = new ArrayList<EstadoArticulo>();
		Statement sentencia = null;
		ResultSet resultado = null;
		EstadoArticulo art = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		Usuarios usua = (Usuarios) session.getAttribute("User");
		String sql = "";
		try {
			sentencia = con.createStatement();
			sql = "SELECT DATE_FORMAT(ea.fecha, '%M %d %Y') AS fecha, e.id_estado, e.estado_descripcion, p.per_id, CONCAT( p.per_nombre,' ', p.per_apellido) as persona, u.usu_id, r.rol_id, @idp:=p.per_id,"
					+ "case"
					+ " when e.id_estado = 1"
					+ " then 'El articulo fue subido por el autor principal y receptado por el administrador del sistema' "
					+ " when e.id_estado = 2 "
					+ " then (SELECT CONCAT('LOS EVALUADORES PARA ESTE ARTICULO SON: ',GROUP_CONCAT(' ',CONCAT( p.per_nombre,' ', p.per_apellido))) as evaluadores from tb_pares as pa, tb_persona as p where p.per_id=pa.personas_id and pa.articulos_id="
					+ idArt
					+ ") "
					+ " when e.id_estado = 3"
					+ " then (select CONCAT('LA CALIFICACION DE ESTA EVALUACION FUE DE : ',sum(tpa.param_art_valor), '  PUNTOS') as calificacion from (tb_parametros_articulo as tpa inner join tb_persona as tp on tpa.person_id=tp.per_id) where tpa.articul_id="
					+ idArt
					+ " and tpa.person_id=@idp) "
					+ " when e.id_estado = 4 "
					+ " then (select CONCAT('EL ARTICULO FUE ACEPTADO CON UNA CALIFICACION DE : ', sum(eval_promedio)/2 , ' PUNTOS') as promedio from tb_articulos_evaluados where ar_id="
					+ idArt
					+ ")"
					+ " when e.id_estado = 5 "
					+ " then (select CONCAT('EL ARTICULO FUE RECHAZADO POR OBTENER ', sum(eval_promedio)/2, ' PUNTOS') as promedio  from tb_articulos_evaluados where ar_id="
					+ idArt
					+ ") "
					+ " when e.id_estado = 6 "
					+ " then (select CONCAT('LAS OBSERVACIONES DE ESTE EVALUADOR FUERON: ', eval_observacion) as observaciones from tb_articulos_evaluados where ar_id="
					+ idArt
					+ " and eval_persona=@idp) "
					+ " when e.id_estado = 7 "
					+ " then '' "
					+ " else 'NO HAY DETALLES' "
					+ " END AS Info_adicional "
					+ " FROM tb_estado_articulo as ea,tb_estados as e,tb_persona as p,tb_articulo as a,tb_usuario as u,tb_rol as r "
					+ " WHERE u.persona_id=p.per_id and r.rol_id=u.rol_id_usuario and a.art_id = ea.id_Articulo and  "
					+ " ea.id_persona=p.per_id and e.id_estado=ea.id_estado and ea.id_articulo="
					+ idArt + ";";
			resultado = sentencia.executeQuery(sql);
			while (resultado.next()) {

				art = new EstadoArticulo();
				art.setFecha(resultado.getString("fecha"));

				art.setInfo_adicional(resultado.getString("Info_adicional"));
				Estados e = new Estados();
				e.setEstado_descripcion(resultado
						.getString("estado_descripcion"));
				art.setEstados(e);
				Persona p = new Persona();
				if (usua.getId_rol() == 2 || usua.getId_rol() == 3) {
					if (Integer.parseInt(resultado.getString("id_estado")) == 2) {
						art.setInfo_adicional("El Administrador asignó un par de evaluadores");
					}
					if (Integer.parseInt(resultado.getString("rol_id")) == 1) {
						p.setPer_nombre("administrador");
					} else {
						if (Integer.parseInt(resultado.getString("rol_id")) == 3) {
							p.setPer_nombre("evaluador");
						} else {
							p.setPer_nombre(resultado.getString("persona"));
						}
					}
				} else {
					p.setPer_nombre(resultado.getString("persona"));
					art.setInfo_adicional(resultado.getString("Info_adicional"));
				}

				art.setPersona(p);
				lista.add(art);
			}
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

	public List<Articulo> buscarArticuloH() {

		List<Articulo> lista = new ArrayList<Articulo>();
		Statement sentencia = null;
		ResultSet resultados = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();

		Sessions.getCurrent();
		Usuarios usua = (Usuarios) session.getAttribute("User");
		idUsuario = usua.getId();
		String sql = "";
		if (usua != null) {
			if (usua.getId_rol() == 1) {
				sql = "SELECT a.art_id," + "case " + "when a.padre is NULL "
						+ "then a.art_titulo " + "else "
						+ "Concat(a.art_titulo, ' [ version modificada ]') "
						+ "END AS artitulo " + "FROM tb_articulo as a "
						+ "group by a.art_id order by a.art_titulo ";
				try {
					sentencia = con.createStatement();
					resultados = sentencia.executeQuery(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					Articulo articulo = null;
					while (resultados.next()) {
						articulo = new Articulo();
						articulo.setArt_id(resultados.getInt("art_id"));
						articulo.setArt_titulo(resultados
								.getString("artitulo"));
						lista.add(articulo);
					}
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			if (usua.getId_rol() == 2 || usua.getId_rol() == 3) {
				sql = "SELECT a.art_id, "
						+ "case "
						+ "when a.padre is NULL "
						+ "then a.art_titulo "
						+ "else "
						+ "Concat('             ',a.art_titulo, ' [ version modificada ]') "
						+ "END AS artitulo "
						+ "FROM tb_persona AS p2 "
						+ "INNER JOIN tb_persona_articulo AS pa ON p2.per_id = pa.per_id_registra "
						+ "INNER JOIN tb_articulo AS a ON pa.arti_id =a.art_id "
						+ "INNER JOIN tb_persona AS p ON pa.pers_id= p.per_id "
						+ "INNER JOIN tb_pares AS pares ON  a.art_id=pares.articulos_id "
						+ "WHERE  pa.per_id_registra=" + idUsuario
						+ " or pa.pers_id=" + idUsuario
						+ " or pares.personas_id=" + idUsuario
						+ " AND pa.per_art_estado =1 " + "group by a.art_id ";
			}
		}

		try {
			sentencia = con.createStatement();
			resultados = sentencia.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {

			Articulo articulo = null;
			while (resultados.next()) {
				articulo = new Articulo();
				articulo.setArt_id(resultados.getInt("art_id"));
				articulo.setArt_titulo(resultados.getString("artitulo"));
				lista.add(articulo);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return lista;
	}


	public List<EstadoArticulo> historialArticulosE(int idPersona) {
		List<EstadoArticulo> lista = new ArrayList<EstadoArticulo>();
		Statement sentencia = null;
		ResultSet resultado = null;
		EstadoArticulo art = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();

		String sql = "";
		try {
			sentencia = con.createStatement();
			sql = "SELECT ae.eval_fecha, a.art_titulo, ae.eval_promedio, ae.eval_observacion, ae.nombre, ae.direccion FROM tb_articulos_evaluados as ae, tb_articulo as a WHERE a.art_id=ae.ar_id and ae.eval_persona ="
					+ idPersona + ";";
			resultado = sentencia.executeQuery(sql);
			System.out.println("sql: " + sql);
			while (resultado.next()) {

				art = new EstadoArticulo();
				art.setFecha(resultado.getString("eval_fecha"));
				Articulo artic = new Articulo();
				artic.setArt_titulo(resultado.getString("art_titulo"));
				art.setCalif(resultado.getDouble("eval_promedio"));
				art.setInfo_adicional(resultado.getString("eval_observacion"));
				System.out.println("observacion : " + art.getInfo_adicional());
			    artic.setArt_archivo(resultado.getString("nombre"));
			    System.out.println("nombre archivo : " + artic.getArt_archivo());
				artic.setRuta(resultado.getString("direccion"));
				System.out.println("direccion archivo : " + artic.getRuta());
				art.setArticulo(artic);
				lista.add(art);
			}
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

	public String mostrarEvaluadores(int idArt) {
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String info = null;
		String sql = "";
		try {
			sentencia = con.createStatement();
			
			sql = "SELECT GROUP_CONCAT('  ',CONCAT( p.per_nombre,' ', p.per_apellido)) as evaluadores from tb_pares as pa, tb_persona as p where p.per_id=pa.personas_id and pa.articulos_id="
					+ idArt + ";";
			resultado = sentencia.executeQuery(sql);
			while (resultado.next()) {
				info = resultado.getString("evaluadores");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al ejecutar la sentencia");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return info;
	}
	//actualizar estado aceptado 
	
	public boolean ActualizarEstadoAceptado(int id_articulo){
		boolean registro = false;
		// crear un objeto para la conexion
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		// vamos a guardar utilizando transacciones
		try {
			
			CallableStatement proc;
			proc = con.prepareCall("{call actualizar_estado(?)}");
			proc.setInt(1, id_articulo);
			proc.execute();
			registro = true;
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

		return registro;

		
	}// fin de actualiza estado para articulo aceptado
	
	//obtener padre del articulo 
	public int IDpadre (int idart){
		
		int idpadre=0;
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String sql = "select a.padre as idpadre from tb_articulo as a where a.art_id="
				+ idart;
		try {
			sentencia = con.createStatement();
			resultado = sentencia.executeQuery(sql);

			if (resultado.next()) {
				existe = true;
				idpadre = (resultado.getInt("idpadre"));
			}
			else{
				existe=false;
			}
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

		return idpadre;
	}
	
	public boolean act_rechazados(int id){
		boolean registro=true;
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			// por defecto es true asi q lo cambiamos
						con.setAutoCommit(false);
						String sql = "update tb_estado_articulo set id_ult_estado=? where" +
								" id_articulo=? and (id_estado=3 or id_estado=6 or id_estado=2)";
						PreparedStatement pstm = con.prepareStatement(sql);
						pstm.setInt(1, 0);
						pstm.setInt(2, id);
						
						// ejecutar el prepaaredStatement
						// retorna el numero de filas afectadas o retorna 0 si no se pudo
						// realizar
						int num = pstm.executeUpdate();
						// si no hay error
						con.commit();
						registro = true;
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

		return registro;
	}
}
