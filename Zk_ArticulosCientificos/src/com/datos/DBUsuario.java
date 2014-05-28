package com.datos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.entidades.Usuarios;

public class DBUsuario {

	//buscar usuario
	public Usuarios buscarUsuario(String user, String password){
		Usuarios usuario=null;
		//busqueda del usuario en la bd
		//1. conectarme a la bd
		DBManager dbm=new DBManager();
		Connection con=dbm.getConection();
		
		Statement sentencia;
		ResultSet resultados;
		String query="SELECT * FROM tb_usuario as u WHERE"
		+ " u.usu_nombre='"+user+"' and "
		+ "u.usu_clave= '"+password + "'";
	
		System.out.println(query);
		try {
		sentencia=con.createStatement();
		resultados=sentencia.executeQuery(query);
		
		//RECORRER EL RESULTSET
		while (resultados.next()){
			usuario=new Usuarios();
			usuario.setId(resultados.getInt("usu_id"));
			usuario.setUsuario(resultados.getString("usu_nombre"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				//cerrar la conexion
				con.close();
			}catch(SQLException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		return usuario;
		
		}
	}
