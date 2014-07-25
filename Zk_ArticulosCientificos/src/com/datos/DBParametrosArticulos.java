package com.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zul.Textbox;

import com.entidades.ParametrosArticulo;
import com.entidades.ParametrosEvaluacion;
import com.entidades.Permiso;

public class DBParametrosArticulos {

	public boolean guardarEvaluacion(List<ParametrosArticulo> pa) {
		boolean registro = false;
		// añadir el codigo
		int filas_afectadas = 0;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			if(pa.size()>0){
				
			
			Iterator iter = pa.iterator();
			// System.out.println("idPersmiso: "+per.get(0).getId_permiso());
			if (pa.get(0).getParam_art_id() == 0) {
				// String
				// sql="INSERT INTO tb_permiso (id_formulario,id_rol, permiso ) VAlUES (?,?,?)";
				String sql = "INSERT INTO tb_parametros_articulo(param_art_valor, param_id, person_id, articul_id ) VAlUES (?,?,?,?)";

				while (iter.hasNext()) {
					ParametrosArticulo parA = (ParametrosArticulo) iter.next();
					PreparedStatement pstm = con.prepareStatement(sql);
					pstm = con.prepareStatement(sql);
					pstm.setDouble(1, parA.getParam_art_valor());
					System.out.println("El valor es: "+parA.getArticul_id());
					pstm.setInt(2, parA.getParam_id());
					pstm.setInt(3, parA.getPerson_id());
					pstm.setInt(4, parA.getArticul_id());
					filas_afectadas = pstm.executeUpdate();
					// }
				}

			}
			con.commit();
			System.out.println("El ingreso de tb_parametros_articulos"+filas_afectadas);
			registro = true;
			
			}
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
		return registro;
	}
	
	
}
