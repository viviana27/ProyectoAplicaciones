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

public class DBArticulos {
	public int idArticuloRegistrado = 0;

	public boolean RegistrarArticulo(Articulo art) {
		boolean resultado = false;
		// a�adir el codigo
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
System.out.println("la fecha es : "+fecha);
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
		// a�adir el codigo
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
		// a�adir el codigo
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

		Statement sentencia=null;
		ResultSet resultados=null;
		System.out.println("direccion a buscar: " + direc);
		String query = "Select ta.art_id from tb_articulo as ta where ta.art_archivo='"
				+ direc + "'";
		System.out.println("query: " + query);
		try {
			sentencia = con.createStatement();
			resultados = sentencia.executeQuery(query);
			// recorrer el resultset
			while (resultados.next()) {
				Articulo arti =new Articulo();
				arti.setArt_id(resultados.getInt("art_id"));
				idArticuloRegistrado = (resultados.getInt("art_id"));
				System.out.println("idart"+idArticuloRegistrado );
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

	public List<Articulo> buscarArticulo(int idEstado, String titulo, String autor,
			String tipoa, String area) {
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
				 * 
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
								articulo.setArt_titulo(rs
										.getString("artTitulo"));
								articulo.setNom_colaborador(rs
										.getString("autor"));
								
								articulo.setArea_nombre(rs
										.getString("area_nombre"));
								articulo.setTipo_nombre(rs
										.getString("tipo_nombre"));
								//articulo.setNom_colaborador1(resultados.getString("coautor"));
								articulo.setArt_resumen(rs
										.getString("art_resumen"));
								articulo.setNom_colaborador1(rs.getString("coautor"));
								articulo.setArt_palabras_clave(rs
										.getString("art_palab_clave"));
								articulo.setPer_institucion1(rs
										.getString("per_institucion1"));
								articulo.setPer_institucion2(rs
										.getString("per_institucion2"));
								articulo.setArt_fecha_subida(rs
										.getDate("art_fecha_subida"));
								
								lista.add(articulo);
							  	System.out.println("Name : " + rs.getString(5));
						  }

						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("ddff" + sql);
				}
			
		 /*else {
						try {
						CallableStatement proc;
						proc = con.prepareCall("{call spautor(?)}");
						proc.setInt(1, idEstadoult);
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
								//articulo.setNom_colaborador1(resultados.getString("coautor"));
								articulo.setArt_resumen(rs
										.getString("art_resumen"));
								articulo.setNom_colaborador1(rs.getString("coautor"));
								articulo.setArt_palabras_clave(rs
										.getString("art_palab_clave"));
								articulo.setPer_institucion1(rs
										.getString("per_institucion1"));
								articulo.setPer_institucion2(rs
										.getString("per_institucion2"));
								lista.add(articulo);
							  	System.out.println("Name : " + rs.getString(5));
						  }

						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}*/

			}
			else{
				if (usua.getId_rol() == 2 || usua.getId_rol() == 3) {
					if (titulo.equals("") && autor.equals("") && tipoa.equals("")
							&& area.equals("")) {
						try {
							CallableStatement proc;
							proc = con.prepareCall("{call sp_segun_autor(?,?)}");
							proc.setInt(1, idEstado);
							proc.setInt(2, idUsuario);
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
									//articulo.setNom_colaborador1(resultados.getString("coautor"));
									articulo.setArt_resumen(rs
											.getString("art_resumen"));
									articulo.setNom_colaborador1(rs.getString("coautor"));
									articulo.setArt_palabras_clave(rs
											.getString("art_palab_clave"));
									articulo.setPer_institucion1(rs
											.getString("per_institucion1"));
									articulo.setPer_institucion2(rs
											.getString("per_institucion2"));
									articulo.setArt_fecha_subida(rs
											.getDate("art_fecha_subida"));
									lista.add(articulo);
								  	System.out.println("Name : " + rs.getString(3));
							  }

							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						System.out.println("ddff" + sql);
					

					}
				
			}
		}

			/*
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
		}*/
		}
		return lista;

		
	}

	
	
	public List<Articulo> buscarArticuloEvaluador(String titulo, String area) {
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
				if (titulo.equals("") && area.equals("") ) {
					sql = "SELECT pa.articulos_id, ar.art_id, ar.art_titulo, ar.art_archivo, " +
							" a.area_nombre, " +
							"a.area_id, p.per_nombre, p.per_apellido " +
							"FROM tb_persona AS p LEFT JOIN tb_pares AS pa ON pa.personas_id = p.per_id " +
							"RIGHT JOIN tb_articulo AS ar ON ar.art_id = pa.articulos_id " +
							"LEFT JOIN tb_area AS a ON a.area_id = ar.area_id " +
							"WHERE ar.art_estado =1 " +
							"ORDER BY  p.per_nombre ";

					System.out.println("sentencia por rol administrador" + sql);
				} else {
					sql =  "SELECT pa.articulos_id, ar.art_id, ar.art_titulo, ar.art_archivo," +
							" a.area_nombre, " +
							"a.area_id, p.per_nombre, p.per_apellido " +
							"FROM tb_persona AS p LEFT JOIN tb_pares AS pa ON pa.personas_id = p.per_id " +
							"RIGHT JOIN tb_articulo AS ar ON ar.art_id = pa.articulos_id " +
							"LEFT JOIN tb_area AS a ON a.area_id = ar.area_id " +
							"WHERE ar.art_estado =1 " +
							"and (ar.art_titulo like '%"
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
						articulo.setArt_titulo(resultados.getString("art_titulo"));
						System.out.println(articulo.getArt_titulo()+"  titulo");
						articulo.setArea_nombre(resultados.getString("area_nombre"));
						articulo.setId_area(resultados.getInt("area_id"));
						articulo.setPer_nombre(resultados.getString("per_nombre"));
						articulo.setPer_apellido(resultados.getString("per_apellido"));
						articulo.setArt_archivo(resultados.getString("art_archivo"));
						System.out.println(articulo.getArt_archivo()+"  archivo");
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
					if (titulo.equals("")  && area.equals("")) {
						sql = "SELECT pa.articulos_id, ar.art_id, ar.art_titulo,ar.art_archivo, " +
								" a.area_nombre, " +
								"a.area_id, p.per_id,p.per_nombre, p.per_apellido " +
								"FROM tb_persona AS p LEFT JOIN tb_pares AS pa ON pa.personas_id = p.per_id " +
								"RIGHT JOIN tb_articulo AS ar ON ar.art_id = pa.articulos_id " +
								"LEFT JOIN tb_area AS a ON a.area_id = ar.area_id " +
								"LEFT JOIN tb_estado_articulo esa ON esa.id_articulo = ar.art_id " +
								"WHERE ar.art_estado =1 and p.per_id="+idUsuario+" and esa.id_estado=2 "+
								"ORDER BY  p.per_nombre";

						System.out.println("ddff" + sql);
							} else {
								sql =  "SELECT pa.articulos_id, ar.art_id, ar.art_titulo,ar.art_archivo," +
										" a.area_nombre, " +
										"a.area_id, p.per_nombre, p.per_apellido " +
										"FROM tb_persona AS p LEFT JOIN tb_pares AS pa ON pa.personas_id = p.per_id " +
										"RIGHT JOIN tb_articulo AS ar ON ar.art_id = pa.articulos_id " +
										"LEFT JOIN tb_area AS a ON a.area_id = ar.area_id " +
										"LEFT JOIN tb_estado_articulo esa ON esa.id_articulo = ar.art_id " +
										"WHERE ar.art_estado =1 and p.per_id="+idUsuario+" and esa.id_estado=2 " +
										"and (ar.art_titulo like '%"
										+ titulo
										+ "%' and a.area_nombre like '%"
										+ area
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
							articulo.setArt_archivo(resultados.getString("art_archivo"));
							System.out.println(articulo.getArt_archivo()+"  ruta");
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
	
	
	public List<EstadoArticulo> buscarestados(int criterio) {
		List<EstadoArticulo> lista = new ArrayList<EstadoArticulo>();
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String sql = "select te.id, te.id_estado,te.id_ult_estado from tb_estado_articulo as te where  te.id_articulo="+criterio;
		
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
	
	public boolean Actualizr_estadoar(int estad, int ida){

		boolean registro=false;
		//crear un objeto para la conexion
		DBManager dbm =new DBManager();
		Connection con= dbm.getConection();
		//vamos a guardar utilizando transacciones 
		try {
			//por defecto es true asi q lo cambiamos
			con.setAutoCommit(false);
			String sql="UPDATE tb_estado_articulo SET"
					+" id_ult_estado=?"
					+" where id=? and id_articulo=?";
			PreparedStatement pstm=con.prepareStatement(sql);	
			pstm.setInt(1,0);
			pstm.setInt(2,estad);
			pstm.setInt(3,ida);
			//ejecutar el prepaaredStatement
			//retorna el numero de filas afectadas o retorna 0 si no se pudo realizar
			int num=pstm.executeUpdate();
				//si no hay error 
				con.commit();
				registro=true;
				
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
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
