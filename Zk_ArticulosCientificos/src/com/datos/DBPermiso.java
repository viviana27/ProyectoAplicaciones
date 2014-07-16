package com.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.entidades.ParametrosEvaluacion;
import com.entidades.Permiso;
import com.entidades.Roles;

public class DBPermiso {
	
	
	public List<Permiso> buscarPermisos(int idRol){
		//objeto a retornar es una lista
		List<Permiso> lista =new ArrayList<Permiso>();
		Permiso per=null;
			//objeto conexion
			Connection con=null;
			Statement sentencia=null;
			ResultSet resultados=null;
			DBManager dbm=new DBManager();
			con=dbm.getConection();
			try {
				sentencia = con.createStatement();
				//String sql="Select * from tb_rol where rol_estado=1";
				String sql="SELECT * FROM tb_formulario AS tf left JOIN  tb_permiso AS tp ON tp.id_formulario = tf.id_formulario and tp.id_rol="+idRol;
				resultados=sentencia.executeQuery(sql);	
		
				while(resultados.next()){
					
					per=new Permiso();
					per.setId_formulario((resultados.getInt("id_formulario")));
					per.setNombreFormulario(resultados.getString("nombre"));
					per.setId_permiso(resultados.getInt("id_permiso"));
					per.setId_rol(resultados.getInt("id_rol"));
					per.setPermiso(resultados.getInt("permiso"));
					if(per.getPermiso()==1){
						per.setPermisoEstado("Activo");
					}
					else{
						per.setPermisoEstado("Inactivo");
					}
					//agregar actividades a mi lista
					lista.add(per);		
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return lista;
		}
	public boolean guardarPermisosFormularios(List<Permiso> per){
		boolean registro = false;
		//añadir el codigo
		int filas_afectadas=0;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			Iterator iter = per.iterator();
			System.out.println("idPersmiso: "+per.get(0).getId_permiso());
			if (per.get(0).getId_permiso()==0){
				String sql="INSERT INTO tb_permiso (id_formulario,id_rol, permiso ) VAlUES (?,?,?)";
				
				
				while (iter.hasNext()){
						Permiso permiso = (Permiso)iter.next();
				//	if(permiso.getId_formulario()==0){
						PreparedStatement pstm=con.prepareStatement(sql);
						pstm=con.prepareStatement(sql);
						pstm.setInt(1, permiso.getId_formulario());
					  	System.out.println("Inseert: "+permiso.getId_formulario()+"");
						pstm.setInt(2, permiso.getId_rol());
						pstm.setInt(3, permiso.getPermiso());
						filas_afectadas	=pstm.executeUpdate();	
					//}
				}
				
			
			}else{
				String sql1="UPDATE tb_permiso SET id_formulario=?,id_rol=?, permiso=? where id_permiso=?";
				while (iter.hasNext()){
					Permiso permiso = (Permiso)iter.next();
					PreparedStatement pstm=con.prepareStatement(sql1);
					pstm=con.prepareStatement(sql1);
					pstm.setInt(1, permiso.getId_formulario());
				  	System.out.println("update: "+permiso.getId_formulario()+"");
					pstm.setInt(2, permiso.getId_rol());
					pstm.setInt(3, permiso.getPermiso());
					pstm.setInt(4, permiso.getId_permiso());
		  			filas_afectadas	=pstm.executeUpdate();
				}	
			
			}
			//pasar los parametros
				//ejecutar el Preparedsatatement
			
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
	
	public boolean ConsultarPermisos(int idRol,int idFormulario){
		//objeto a retornar es una lista
		//List<Permiso> lista =new ArrayList<Permiso>();
		//Permiso per=null;
			//objeto conexion
		boolean bandera=false;
			Connection con=null;
			Statement sentencia=null;
			ResultSet resultados=null;
			DBManager dbm=new DBManager();
			con=dbm.getConection();
			try {
				sentencia = con.createStatement();
				//String sql="Select * from tb_rol where rol_estado=1";
				String sql="Select permiso from tb_permiso where id_rol="+idRol+" and id_formulario="+idFormulario;
				resultados=sentencia.executeQuery(sql);	
		
				while(resultados.next()){
					if(resultados.getInt("permiso")==1){
						bandera=true;
					}else
					{
						bandera=false;
					}
							
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return bandera;
		}
}
