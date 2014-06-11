package com.datos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.entidades.*;
public class DBRoles {

	
	
	public List<Roles> buscarRoles(){
		//objeto a retornar es una lista
		List<Roles> lista =new ArrayList<Roles>();
		Roles rol=null;
			//objeto conexion
			Connection con=null;
			Statement sentencia=null;
			ResultSet resultados=null;
			DBManager dbm=new DBManager();
			con=dbm.getConection();
			try {
				sentencia = con.createStatement();
				//String sql="Select * from tb_rol where rol_estado=1";
				String sql="Select * from tb_rol";
				resultados=sentencia.executeQuery(sql);		
				while(resultados.next()){
					
					rol=new Roles();
					rol.setRol_id((resultados.getInt("rol_id")));
					rol.setRol_descripcion(resultados.getString("rol_descripcion"));
					//agregar actividades a mi lista
					lista.add(rol);		
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return lista;
		}
	
	public boolean CrearRoles(Roles rol ){
		boolean resultado = false;
		//añadir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql="INSERT INTO tb_rol (rol_descripcion, rol_estado ) VAlUES (?,?)";
			PreparedStatement pstm=con.prepareStatement(sql);
			pstm=con.prepareStatement(sql);
			
			//pasar los parametros
			
			pstm.setString(1, rol.getRol_descripcion());
			pstm.setInt(2, rol.getRol_estado());
			
			//ejecutar el Preparedsatatement
			int num=pstm.executeUpdate();
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

	public boolean Actualizar_Roles(Roles rol){

		boolean resultado=false;
		//crear un objeto para la conexion
		DBManager dbm =new DBManager();
		Connection con= dbm.getConection();
		//vamos a guardar utilizando transacciones 
		try {
			//por defecto es true asi q lo cambiamos
			con.setAutoCommit(false);
			String sql="UPDATE tb_rol SET"
					+" rol_descripcion=?,  rol_estado=?"
					+" where rol_id=?";
			PreparedStatement pstm=con.prepareStatement(sql);
			pstm.setString(1,rol.getRol_descripcion());
			pstm.setInt(2,rol.getRol_estado());
			pstm.setInt(3, rol.getRol_id());
			
			//ejecutar el prepaaredStatement
			//retorna el numero de filas afectadas o retorna 0 si no se pudo realizar
			int num=pstm.executeUpdate();
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

	public List<Roles> buscarRoles(String criterio){
		List<Roles> lista=new ArrayList<Roles>();
		Statement sentencia = null;
		ResultSet resultado=null;
		
		//busqueda a la base de datos
		DBManager dbm = new DBManager();
		Connection con= dbm.getConection();
		String sql="";
		if (criterio.equals("")) {
		sql = "SELECT * FROM tb_rol as r" +
					" WHERE r.rol_estado=1" +
					" order by r.rol_descripcion";
			System.out.println("busqueda"+sql);
		} else {
			sql = "SELECT * FROM tb_rol as r" +
					" WHERE r.rol_estado=1 and (r.rol_descripcion like '%"
					+ criterio+"%')";
					
			System.out.println(""+sql);
			
		}
		
		try {
			sentencia=con.createStatement();
			resultado=sentencia.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 System.out.println("error al ejecutar la sentencia");
		}
		
	
		try {
			Roles rol = null;
			while(resultado.next()){
				rol=new Roles();
				rol.setRol_id(resultado.getInt("rol_id"));
				rol.setRol_descripcion(resultado.getString("rol_descripcion"));
				rol.setRol_estado(resultado.getInt("rol_estado"));
				if((resultado.getInt("rol_estado"))==1){
					rol.setRolestado("Activo");
				}
				else{
					rol.setRolestado("Inactivo");
				}
				lista.add(rol);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public boolean eliminarRoles(Roles rol){
	boolean result=false;
	DBManager dbm =new DBManager();
	Connection con= dbm.getConection();
	try {
		con.setAutoCommit(false);
		String sql="UPDATE tb_rol SET rol_estado=? WHERE rol_id=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setInt(1,0);
		pstm.setInt(2,rol.getRol_id());
		int num=pstm.executeUpdate();
		con.commit();
		result=true;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return result;
}
	
}
