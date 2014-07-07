package com.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.entidades.AreaUsuario;
import com.entidades.Persona;
import com.entidades.PersonaArea;
import com.entidades.UsuarioArea;
import com.entidades.Usuarios;

public class DBUsuarioArea {
	
	public List<UsuarioArea> buscarareas(){
		//objeto a retornar es una lista
		List<UsuarioArea> lista =new ArrayList<UsuarioArea>();
		UsuarioArea area=null;
			//objeto conexion
			Connection con=null;
			Statement sentencia=null;
			ResultSet resultados=null;
			DBManager dbm=new DBManager();
			con=dbm.getConection();
			try {
				sentencia = con.createStatement();
				String sql="Select * from tb_area as ar where ar.area_estado=1 order by ar.area_nombre ASC";
				resultados=sentencia.executeQuery(sql);		
				while(resultados.next()){
				
					area=new UsuarioArea();
					area.setArea_id(resultados.getInt("area_id"));
					area.setArea_nombre(resultados.getString("area_nombre"));
					lista.add(area);					
				}				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			return lista;
		}
	
	public boolean actualizarUsuarioArea (UsuarioArea usuarioarea) throws Exception{

		boolean resultado=false;
		//crear un objeto para la conexion
		DBManager dbm =new DBManager();
		Connection con= dbm.getConection();
		//vamos a guardar utilizando transacciones 
		try {
			//por defecto es true asi q lo cambiamos
			con.setAutoCommit(false);
			/*String sql="UPDATE tb_persona SET"
					+" per_nombre=?, per_apellido=? where per_id=?";
			PreparedStatement pstm=con.prepareStatement(sql);
			PersonaArea per=usuarioarea.getPersonaarea();
			pstm.setString(1,per.getPer_nombre());
			pstm.setString(2,per.getPer_apellido());	
			pstm.setInt(3,per.getPer_id());
			//ejecutar el prepaaredStatement
			//retorna el numero de filas afectadas o retorna 0 si no se pudo realizar
			int num=pstm.executeUpdate();*/
			
			String sql ="UPDATE tb_persona_area SET per_area_estado=?,per_id=?, area_id=? "
					+ "WHERE per_area_id=?";
			System.out.println("sql tb_persona "+sql);
			PersonaArea per=usuarioarea.getPersonaarea();
			PreparedStatement pstm=con.prepareStatement(sql);
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, 1);
			pstm.setInt(2, per.getPer_id());
			pstm.setInt(3, usuarioarea.getArea_id());
			pstm.setInt(4, usuarioarea.getPer_area_id());
			System.out.println("ud reposnaad "+per.getPer_id());
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
	
		public List<UsuarioArea> buscarUsuariosArea(String criterio){
		List<UsuarioArea> lista=new ArrayList<UsuarioArea>();
		//objeto sentencia 
		Statement sentencia=null;
		//objeto para resultados
		ResultSet resultados=null;
		//obtener la conexion a la base
		DBManager dbm=new DBManager();
		Connection con=dbm.getConection();
		System.out.println("amigosss");
		String sql="";
		if (criterio.equals("")) {
		/*	sql = "SELECT * FROM tb_usuario as u, tb_persona as p, tb_rol as r, tb_area as a, tb_persona_area as pa" +
					" WHERE u.usu_estado=1 and u.rol_id_usuario=3 and p.per_estado=1 and r.rol_estado=1 " +
					"and a.area_estado=1" +
					" and u.persona_id=p.per_id and u.rol_id_usuario=r.rol_id " +
					" group by p.per_id order by p.per_apellido";*/
			sql ="SELECT pa.per_area_id, p.per_nombre, p.per_apellido, pa.per_id, a.area_nombre, p.per_id as pers_id FROM ((tb_persona p left join tb_persona_area pa on p.per_id=pa.per_id) inner join tb_usuario u on u.persona_id=p.per_id) left join tb_area a on pa.area_id=a.area_id WHERE u.rol_id_usuario=3 group by pa.per_id, p.per_apellido, p.per_nombre, a.area_nombre, pa.per_area_id, pers_id ";
			System.out.println("ddff"+sql);
		} else {
		/*	sql = "SELECT * FROM tb_usuario as u, tb_persona as p, tb_rol as r, tb_area as a,  tb_persona_area as pa " +
					"WHERE u.usu_estado=1 and u.rol_id_usuario=3 and p.per_estado=1 and r.rol_estado=1 " +
					"and a.area_estado=1  " +
					"and u.persona_id=p.per_id  and u.rol_id_usuario=r.rol_id " +
					" and (p.per_nombre like '%"
					+ criterio
					+ "%' or p.per_apellido like '%"
					+ criterio
					+ "%') group by p.per_id order by p.per_apellido";*/
			sql ="SELECT pa.per_area_id, p.per_nombre, p.per_apellido, pa.per_id, a.area_nombre, p.per_id as pers_id FROM ((tb_persona p left join tb_persona_area pa on p.per_id=pa.per_id) inner join tb_usuario u on u.persona_id=p.per_id) left join tb_area a on pa.area_id=a.area_id WHERE u.rol_id_usuario=3"+
					" and (p.per_nombre like '%"
					+ criterio
					+ "%' or p.per_apellido like '%"
					+ criterio
					+ "%')  group by pa.per_id, p.per_apellido, p.per_nombre, a.area_nombre, pa.per_area_id, pers_id ";
		}
		try {
			sentencia=con.createStatement();
			resultados=sentencia.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 System.out.println("error al ejecutar la sentencia");
		}
		//recorrer el resultset
		try {
			UsuarioArea usuarioarea=null;
			while(resultados.next()){
				usuarioarea=new UsuarioArea();
				PersonaArea persona=new PersonaArea();
				usuarioarea.setPer_area_id(resultados.getInt("per_area_id"));
				persona.setPer_id(resultados.getInt("per_id"));
				persona.setPer_nombre(resultados.getString("per_nombre"));
				persona.setPer_apellido(resultados.getString("per_apellido"));
				persona.setPerso_id(resultados.getInt("pers_id"));
				System.out.println("Apellido:"+persona.getPer_nombre());
				//usuarioarea.setArea_id(resultados.getInt("area_id"));
				//System.out.println(resultados.getInt("area_id"));
				usuarioarea.setArea_nombre(resultados.getString("area_nombre"));
				usuarioarea.setPersonaarea(persona);
				//agrego usuario con datos cargados desde la base a mi lista de usuarios
				lista.add(usuarioarea);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al recorrer resultset");
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
	
	public boolean crearUsuarioArea(UsuarioArea usuario) throws Exception{

		boolean resultado=false;
		//crear un objeto para la conexion
		DBManager dbm =new DBManager();
		Connection con= dbm.getConection();
		//vamos a guardar utilizando transacciones 
		try {
			//por defecto es true asi q lo cambiamos
			con.setAutoCommit(false);	
			String sql="INSERT INTO tb_persona_area (area_id, per_id, per_area_estado)" +
						" VAlUES (?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
				PersonaArea per=usuario.getPersonaarea();
				pstm = con.prepareStatement(sql);
				pstm.setInt(1, usuario.getArea_id());
				pstm.setInt(2, usuario.getPersonaarea().getPerso_id());
				System.out.println("hola"+usuario.getPersonaarea().getPerso_id());
				pstm.setInt(3,1);
				//System.out.println("hola"+per.getPer_id());
				int num_filas_afectadas = pstm.executeUpdate();
				// si no hay error
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
