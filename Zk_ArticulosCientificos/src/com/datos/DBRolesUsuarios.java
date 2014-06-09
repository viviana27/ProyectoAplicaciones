package com.datos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.entidades.*;
public class DBRolesUsuarios {

	
	
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
	
	
}