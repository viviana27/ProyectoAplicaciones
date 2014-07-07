package com.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.entidades.Areas;
import com.entidades.Articulo;
import com.entidades.Pares;
import com.entidades.Persona;
import com.entidades.PersonaArea;
import com.entidades.UsuarioArea;

public class DBPares {
	public boolean InsertarPar(Pares p ){
		boolean registro = false;
		//añadir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql="INSERT INTO tb_pares (articulos_id, personas_id, est_id, par_cantidad, par_estado )" +
					" VAlUES (?,?,?,?,?)";
			PreparedStatement pstm=con.prepareStatement(sql);
			pstm=con.prepareStatement(sql);
			//pasar los parametros
			pstm.setInt(1, p.getArticulos_id());
			pstm.setInt(2, p.getPersonas_id());
			pstm.setInt(3, 2);
			pstm.setInt(4, 2);
			pstm.setInt(5, 1);
			
			//ejecutar el Preparedsatatement
			int filas_afectadas=pstm.executeUpdate();
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
// fin de codigo insertar nueva area

	
	public boolean actualizarEvaluador (Pares par) throws Exception{

		boolean resultado=false;
		//crear un objeto para la conexion
		DBManager dbm =new DBManager();
		Connection con= dbm.getConection();
		//vamos a guardar utilizando transacciones 
		try {
			//por defecto es true asi q lo cambiamos
			con.setAutoCommit(false);
			String sql ="UPDATE tb_pares SET par_estado=?,articulos_id=?, personas_id=? "
					+ "WHERE par_id=?";
			PreparedStatement pstm=con.prepareStatement(sql);
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, 1);
			pstm.setInt(2, par.getArticulos_id());
			pstm.setInt(3, par.getPersonas_id());
			pstm.setInt(4, par.getPar_id());
				//ejecutar el Preparedsatatement
				int num_filas_afectadas=pstm.executeUpdate();
				
				//si no hay error 
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
}
