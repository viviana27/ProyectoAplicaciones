package com.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.entidades.Areas;
import com.entidades.Persona;
import com.entidades.Usuarios;
import com.datos.DBManager;

public class DBAreas {
	public boolean CrearAreas(Areas a ){
		boolean registro = false;
		//añadir el codigo
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql="INSERT INTO tb_area (area_nombre, area_descripcion, area_estado ) VAlUES (?,?,?)";
			PreparedStatement pstm=con.prepareStatement(sql);
			pstm=con.prepareStatement(sql);
			
			//pasar los parametros
			
			pstm.setString(1, a.getArea_nombre());
			pstm.setString(2, a.getArea_descripcion());
			pstm.setInt(3, a.getArea_estado());
			
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

	//actualizar tabla_areas, cuando se editen los datos
	public boolean Actualizr_Areas(Areas area){

		boolean registro=false;
		//crear un objeto para la conexion
		DBManager dbm =new DBManager();
		Connection con= dbm.getConection();
		//vamos a guardar utilizando transacciones 
		try {
			//por defecto es true asi q lo cambiamos
			con.setAutoCommit(false);
			String sql="UPDATE tb_area SET"
					+" area_nombre=?, area_descripcion=?,  area_estado=?"
					+" where area_id=?";
			PreparedStatement pstm=con.prepareStatement(sql);
			Areas a = area;	
			pstm.setString(1,a.getArea_nombre());
			pstm.setString(2,a.getArea_descripcion());
			pstm.setInt(3,a.getArea_estado());
			
			pstm.setInt(4, a.getArea_id());
			
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

	public List<Areas> buscarAreas(String area){
		List<Areas> lista=new ArrayList<Areas>();
		Statement sentencia = null;
		ResultSet registros=null;
		
		//busqueda a la base de datos
		DBManager dbm = new DBManager();
		Connection con= dbm.getConection();
		String sql="";
		if (area.equals("")) {
		sql = "SELECT * FROM tb_area as a" +
					" WHERE a.area_estado=1" +
					" order by a.area_nombre";
			System.out.println("busqueda"+sql);
		} else {
			sql = "SELECT * FROM tb_area as a" +
					" WHERE a.area_estado=1 and (a.area_nombre like '%"
					+ area+"%')";
					
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
		
		Areas a = null;
		try {
			while(registros.next()){
				a=new Areas();
				a.setArea_id(registros.getInt("area_id"));
				a.setArea_nombre(registros.getString("area_nombre"));
				a.setArea_descripcion(registros.getString("area_descripcion"));
				a.setArea_estado(registros.getInt("area_estado"));
				if((registros.getInt("area_estado"))==1){
					a.setEstadostring("Activo");
				}
				else{
					a.setEstadostring("Inactivo");
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
public boolean eliminarAreas(Areas a){
	boolean result=false;
	DBManager dbm =new DBManager();
	Connection con= dbm.getConection();
	try {
		con.setAutoCommit(false);
		String sql="UPDATE tb_area SET area_estado=? WHERE area_id=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		Areas ar=a;
		pstm.setInt(1,0);
		pstm.setInt(2,a.getArea_id());
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
public List<Areas> buscarArea() {
	List<Areas> lista = new ArrayList<Areas>();
	Areas area = null;
	// objeto conexion
	Connection con = null;
	Statement sentencia = null;
	ResultSet resultados = null;
	DBManager dbm = new DBManager();
	con = dbm.getConection();
	try {
		sentencia = con.createStatement();
		String sql = "Select * from tb_area as ar order by ar.area_nombre ASC";
		resultados = sentencia.executeQuery(sql);
		while (resultados.next()) {
			area = new Areas();
			area.setArea_id((resultados.getInt("area_id")));
			area.setArea_nombre(resultados.getString("area_nombre"));
			lista.add(area);
		}

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return lista;
}
	
}
