package com.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.entidades.Areas;
import com.entidades.Articulo;
import com.entidades.PersonaArticulo;
import com.entidades.Roles;

public class DBArticulos {
	public boolean RegistrarArticulo(Articulo art ){
		boolean resultado = false;
		//añadir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql="INSERT INTO tb_articulo" +
					" (art_titulo," +
					" art_archivo," +
					" art_resumen, " +
					" art_palabras_claves, " +
					" art_fecha_subida, " +
					" art_estado, " +
					" tipo_id, " +
					" area_id_padre," +
					" id_estado ) VAlUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstm=con.prepareStatement(sql);
			pstm=con.prepareStatement(sql);
			pstm.setString(1, art.getArt_titulo());
			pstm.setString(2, art.getArt_archivo());
			pstm.setString(3, art.getArt_resumen());
			pstm.setString(4, art.getArt_palabras_clave());
			pstm.setString(5, art.getArt_fecha_subida());
			pstm.setInt(6, art.getArt_estado());
			pstm.setInt(7, art.getTipo_id());
			pstm.setInt(8, art.getArea_id_padre());
			pstm.setInt(9, art.getId_estado());
			int filas_afectadas=pstm.executeUpdate();
			con.commit();
		resultado=true;
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
		return resultado;
	}
	
	public boolean RegistrarPersonaArticulo(PersonaArticulo perArt ){
		boolean resultado = false;
		//añadir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql="INSERT INTO tb_persona_articulo" +
					" (pers_id," +
					" arti_id," +
					" pers_art_estado, " +
					" per_id_registra) VAlUES (?,?,?,?)";
			PreparedStatement pstm=con.prepareStatement(sql);
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, perArt.getPers_id());
			pstm.setInt(2, perArt.getArti_id());
			pstm.setInt(3, perArt.getPer_art_estado());
			pstm.setInt(4, perArt.getPer_id_registra());
			int filas_afectadas=pstm.executeUpdate();
			con.commit();
		resultado=true;
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
		return resultado;
	}
	
	public List<Articulo> buscarArticulos(String criterio){
		List<Articulo> lista=new ArrayList<Articulo>();
		Statement sentencia = null;
		ResultSet resultado=null;
		DBManager dbm = new DBManager();
		Connection con= dbm.getConection();
		String sql="";
		if (criterio.equals("")) {
		sql = "SELECT * FROM tb_articulos as art" +
					" WHERE art.art_estado=1";
			System.out.println("busqueda"+sql);
		} else {
			sql = "SELECT * FROM tb_articulos as art" +
					" WHERE art.art_estado=1 and (r.art_titulo like '%"
					+ criterio+"%')";		
		}		
		try {
			sentencia=con.createStatement();
			resultado=sentencia.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 System.out.println("error al ejecutar la sentencia");
		}
		finally{
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
