package com.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.entidades.Roles;
import com.entidades.TipoArticulos;

public class DBTipoArticulos {
	
	public boolean CrearTipoArticulos(TipoArticulos tipo) {
		boolean resultado = false;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql = "INSERT INTO tb_tipo_articulo (tipo_nombre, tipo_descripcion, tipo_estado ) VAlUES (?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm = con.prepareStatement(sql);
			pstm.setString(1, tipo.getTipo_nombre());
			pstm.setString(2, tipo.getTipo_descripcion());
			pstm.setInt(3, tipo.getTipo_estado());
			int num = pstm.executeUpdate();
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

	public List<TipoArticulos> buscarTipos() {
		List<TipoArticulos> lista = new ArrayList<TipoArticulos>();
		TipoArticulos tipo = null;
		// objeto conexion
		Connection con = null;
		Statement sentencia = null;
		ResultSet resultados = null;
		DBManager dbm = new DBManager();
		con = dbm.getConection();
		try {
			sentencia = con.createStatement();
			String sql = "Select * from tb_tipo_articulo as t where t.tipo_estado=1 order by t.tipo_nombre ASC";
			resultados = sentencia.executeQuery(sql);
			while (resultados.next()) {
				tipo = new TipoArticulos();
				tipo.setTipo_id((resultados.getInt("tipo_id")));
				tipo.setTipo_nombre(resultados.getString("tipo_nombre"));
				lista.add(tipo);
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

	public boolean Actualizar_Tipos(TipoArticulos tipo) {

		boolean resultado = false;
		// crear un objeto para la conexion
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		// vamos a guardar utilizando transacciones
		try {
			// por defecto es true asi q lo cambiamos
			con.setAutoCommit(false);
			String sql = "UPDATE tb_tipo_articulo SET"
					+ " tipo_nombre=?, tipo_descripcion=?,  tipo_estado=?"
					+ " where tipo_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, tipo.getTipo_nombre());
			pstm.setString(2, tipo.getTipo_descripcion());
			pstm.setInt(3, tipo.getTipo_estado());
			pstm.setInt(4, tipo.getTipo_id());
			int num = pstm.executeUpdate();
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

	public List<TipoArticulos> buscarTipos(String criterio) {
		List<TipoArticulos> lista = new ArrayList<TipoArticulos>();
		Statement sentencia = null;
		ResultSet resultado = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		String sql = "";
		if (criterio.equals("")) {
			sql = "SELECT * FROM tb_tipo_articulo" + " WHERE tipo_estado=1"
					+ " order by tipo_nombre";
			System.out.println("busqueda" + sql);
		} else {
			sql = "SELECT * FROM tb_tipo_articulo"
					+ " WHERE tipo_estado=1 and (tipo_nombre like '%"
					+ criterio + "%')";

			System.out.println("" + sql);
		}
		try {
			sentencia = con.createStatement();
			resultado = sentencia.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al ejecutar la sentencia");
		}
		TipoArticulos tipo = null;
		try {
			while (resultado.next()) {
				tipo = new TipoArticulos();
				tipo.setTipo_id(resultado.getInt("tipo_id"));
				tipo.setTipo_nombre(resultado.getString("tipo_nombre"));
				tipo.setTipo_descripcion(resultado
						.getString("tipo_descripcion"));
				tipo.setTipo_estado(resultado.getInt("tipo_estado"));
				if ((resultado.getInt("tipo_estado")) == 1) {
					tipo.setTipoestado("Activo");
				} else {
					tipo.setTipoestado("Inactivo");
				}
				lista.add(tipo);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return lista;
	}

	public boolean eliminarTipos(TipoArticulos tipo) {
		boolean result = false;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql = "UPDATE tb_tipo_articulo SET tipo_estado=? WHERE tipo_id=? ";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, 0);
			pstm.setInt(2, tipo.getTipo_id());
			int num = pstm.executeUpdate();
			con.commit();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
