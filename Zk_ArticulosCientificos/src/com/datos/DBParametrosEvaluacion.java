package com.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.entidades.ParametrosEvaluacion;

public class DBParametrosEvaluacion {
	public int sumaParametros=0;
	public boolean CrearParametrosEvaluacion(ParametrosEvaluacion param ){
		boolean registro = false;
		//añadir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql="INSERT INTO tb_parametros_evaluacion (param_descripcion, param_valor, param_estado ) VAlUES (?,?,?)";
			PreparedStatement pstm=con.prepareStatement(sql);
			pstm=con.prepareStatement(sql);
			
			//pasar los parametros
			
			pstm.setString(1, param.getParam_descripcion());
			pstm.setInt(2, param.getParam_valor());
			pstm.setInt(3, 1);
			
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
	
	public boolean Actualizr_ParametrosEvaluacion(ParametrosEvaluacion param){

		boolean registro=false;
		//crear un objeto para la conexion
		DBManager dbm =new DBManager();
		Connection con= dbm.getConection();
		//vamos a guardar utilizando transacciones 
		try {
			//por defecto es true asi q lo cambiamos
			con.setAutoCommit(false);
			String sql="UPDATE tb_parametros_evaluacion SET"
					+" param_descripcion=?, param_valor=?,  param_estado=?"
					+" where param_id=?";
			PreparedStatement pstm=con.prepareStatement(sql);
			ParametrosEvaluacion a = param;	
			pstm.setString(1,a.getParam_descripcion());
			pstm.setInt(2,a.getParam_valor());
			pstm.setInt(3,a.getParam_estado());
			pstm.setInt(4,a.getParam_id());
							
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

	public List<ParametrosEvaluacion> buscarParametrosEvaluacion(String parametro){
		List<ParametrosEvaluacion> lista=new ArrayList<ParametrosEvaluacion>();
		Statement sentencia = null;
		ResultSet registros=null;
		
		//busqueda a la base de datos
		DBManager dbm = new DBManager();
		Connection con= dbm.getConection();
		String sql="";
		if (parametro.equals("")) {
		sql = "SELECT * FROM tb_parametros_evaluacion as a" +
					" WHERE a.param_estado=1" +
					" order by a.param_descripcion";
			System.out.println("busqueda"+sql);
		} else {
			sql = "SELECT * FROM tb_parametros_evaluacion as a" +
					" WHERE a.param_estado=1 and (a.param_descripcion like '%"
					+ parametro+"%')";
					
			System.out.println(""+sql);
			
		}
		
		try {
			sentencia=con.createStatement();
			registros=sentencia.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 System.out.println("error al ejecutar la sentencia");
		}
		
		ParametrosEvaluacion a = null;
		try {
			while(registros.next()){
				a=new ParametrosEvaluacion();
				a.setParam_id(registros.getInt("param_id"));
				a.setParam_descripcion(registros.getString("param_descripcion"));
				a.setParam_valor(registros.getInt("param_valor"));
				a.setParam_estado(registros.getInt("param_estado"));
				if((registros.getInt("param_estado"))==1){
					a.setParam_estadoString("Activo");
				}
				else{
					a.setParam_estadoString("Inactivo");
				}
				//agrego usuario con datos cargados desde la base a mi lista de usuarios
				lista.add(a);
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
	//eliminar
	public boolean eliminarParametrosEvaluacion(ParametrosEvaluacion a){
		boolean result=false;
		DBManager dbm =new DBManager();
		Connection con= dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql="UPDATE tb_parametros_evaluacion SET param_estado=? WHERE param_id=?";
			PreparedStatement pstm=con.prepareStatement(sql);
			ParametrosEvaluacion ar=a;
			pstm.setInt(1,0);
			pstm.setInt(2,a.getParam_id());
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
	public int TotalParametrosEvaluacion(){
		//List<ParametrosEvaluacion> lista=new ArrayList<ParametrosEvaluacion>();
		Statement sentencia = null;
		ResultSet registros=null;
		
		//busqueda a la base de datos
		DBManager dbm = new DBManager();
		Connection con= dbm.getConection();
		String sql="";
			sql = "SELECT sum(param_valor)as suma from tb_parametros_evaluacion where param_estado=1";
					
			System.out.println(""+sql);
			
		
		
		try {
			sentencia=con.createStatement();
			registros=sentencia.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 System.out.println("error al ejecutar la sentencia");
		}
		
		ParametrosEvaluacion a = null;
		try {
			while(registros.next()){
				sumaParametros=registros.getInt("suma");
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

		return sumaParametros;	
	}
}
