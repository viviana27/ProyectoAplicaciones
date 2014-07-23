package com.datos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.entidades.Areas;
import com.entidades.ArticuloEvaluado;
import com.entidades.EstadoArticulo;
import com.entidades.ParametrosArticulo;

public class DBArticulosEvaluados {

	//articulos evaluados
	/*public boolean guardarArt_Evalados(ArticuloEvaluado aev) {
		boolean registro = false;
		// añadir el codigo
		int filas_afectadas = 0;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);

			// System.out.println("idPersmiso: "+per.get(0).getId_permiso());
				// String
				// sql="INSERT INTO tb_permiso (id_formulario,id_rol, permiso ) VAlUES (?,?,?)";
				//
			String sql = 	"INSERT INTO tb_estado_articulo(id_articulo,id_estado," +
					"id_persona,fecha) VAlUES (?,?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				
				pstm.setInt(1, aev.getAr_id());
				pstm.setInt(2, 3);
				pstm.setInt(3, aev.);
				java.sql.Date fecha = new java.sql.Date(estadar.getFecha()
						.getTime());

			pstm.setDate(4, fecha);
				
				ResultSet rs = pstm.getGeneratedKeys();
				if(rs.next()){
					int idestado = rs.getInt(1);
					sql = "INSERT INTO tb_articulos_evaluados(eval_fecha,eval_promedio,eval_cantidad,vol_id, estad_id,eval_estado,ar_id,eval_observacion ) VAlUES (?,?,?,?,?,?,?,?)";
					pstm=con.prepareStatement(sql);
					pstm.setDate(1,aev.getEval_fecha());
					pstm.setDouble(2,aev.getEval_promedio());
					pstm.setInt(3,aev.getEval_cantidad());
					pstm.setInt(4,aev.getVol_id());
					pstm.setInt(5,idestado);
					pstm.setInt(6,aev.getEval_estado());
					pstm.setInt(7,aev.getAr_id());
					pstm.setString(8, aev.getEval_observacion());
					
				}
			
				filas_afectadas = pstm.executeUpdate();
					// }
				

			
			con.commit();
			System.out.println("El ingreso de tabla articulos evaluados"+filas_afectadas);
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
	}*/

	
	public boolean RegistrarEstadoArticulo(EstadoArticulo estArt) {
		boolean resultado = false;
		// añadir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql = "INSERT INTO tb_estado_articulo (id_articulo,"
					+ " id_estado,id_ult_estado, id_persona, "
					+ " fecha) VAlUES (?,?,?,?,CURRENT_DATE)";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, estArt.getId_articulo());
			pstm.setInt(2, estArt.getId_estado());
			pstm.setInt(3, estArt.getId_utl_estado());
			pstm.setInt(4, estArt.getId_persona());
			int filas_afectadas = pstm.executeUpdate();
			con.commit();
			System.out.println("filas estado articulo"+filas_afectadas);
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

	public boolean RegistroArt_Evaluados(ArticuloEvaluado artEval) {
		boolean resultado = false;
		// añadir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql = " INSERT INTO tb_articulos_evaluados(eval_promedio,eval_cantidad," +
					" vol_id, estad_id,eval_estado,ar_id,eval_observacion,eval_fecha ) VAlUES (?,?,?,?,?,?,?,CURRENT_DATE)";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm = con.prepareStatement(sql);
			pstm.setDouble(1, artEval.getEval_promedio());
			System.out.println("hola aqui"+artEval.getEval_promedio());
			pstm.setInt(2, artEval.getEval_cantidad());
			System.out.println("cantidad"+artEval.getEval_cantidad());
			pstm.setInt(3, 1);
			System.out.println("volumen"+artEval.getVol_id());
			pstm.setInt(4, 3);
			System.out.println("estadoid"+artEval.getEstad_id());
			pstm.setInt(5, 1);
			System.out.println("estado del art"+artEval.getEval_estado());
			pstm.setInt(6, artEval.getAr_id());
			System.out.println("id articulo"+artEval.getAr_id());
			pstm.setString(7, artEval.getEval_observacion());
			System.out.println("observacion"+artEval.getEval_observacion());
			System.out.println("fecha"+artEval.getEval_fecha());
			int filas_afectadas = pstm.executeUpdate();
			System.out.println("filas"+filas_afectadas);
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
	
	
	
	public List<EstadoArticulo> buscarestados(int criterio) {
		List<EstadoArticulo> lista = new ArrayList<EstadoArticulo>();
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String sql = "select te.id_estado,te.id_ult_estado from tb_estado_articulo as te where  te.id_articulo="+criterio;
		
		try {
			sentencia = con.createStatement();
			resultado = sentencia.executeQuery(sql);
			
			while (resultado.next()) {
				 EstadoArticulo estado = new EstadoArticulo();
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



}
