package com.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.entidades.Areas;
import com.entidades.Articulo;
import com.entidades.EstadoArticulo;
import com.entidades.Pares;
import com.entidades.Persona;


public class DBPares {
	public boolean InsertarPar(Pares p ){
		boolean registro = false;
		//a�adir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql="INSERT INTO tb_pares (articulos_id, personas_id, id_esta, par_cantidad, par_estado )" +
					" VAlUES (?,?,?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			pstm=con.prepareStatement(sql);
			//pasar los parametros
			pstm.setInt(1, p.getArticulos_id());
			System.out.println("idArticulonuevo:"+p.getArticulos_id());
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
	public int contarRegistros(int id)
	{
		int a=0;
		DBManager dbm = new DBManager();
		Connection con= dbm.getConection();
	//contar
	Statement sentencia = null;
	ResultSet resultado = null;
	String sql1 ="select count(*)as contar from tb_pares " +
			"where tb_pares.articulos_id="+id;
	try {
		sentencia = con.createStatement();
		resultado = sentencia.executeQuery(sql1);
		System.out.println("codigonuevoooooo"+resultado);
		while (resultado.next()) {
			a=resultado.getInt("contar");
		}
		//fin del contar
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	System.out.println(a);
	return a;
	}

}
