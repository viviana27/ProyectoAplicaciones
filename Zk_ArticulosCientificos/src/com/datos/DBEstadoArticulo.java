package com.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.entidades.Estados;


public class DBEstadoArticulo {
	
	public List<Estados> listarEstados() {
		List<Estados> lista = new ArrayList<Estados>();
		Estados estado = null;
		// objeto conexion
		Connection con = null;
		Statement sentencia = null;
		ResultSet resultados = null;
		DBManager dbm = new DBManager();
		con = dbm.getConection();
		try {
			sentencia = con.createStatement();
			String sql = "Select * from tb_estados";
			resultados = sentencia.executeQuery(sql);
			while (resultados.next()) {
				estado = new Estados();
				estado.setId_estado(resultados.getInt("id_estado"));
				estado.setEstado_descripcion(resultados.getString("estado_descripcion"));
				lista.add(estado);
			}

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
		return lista;
	}

}