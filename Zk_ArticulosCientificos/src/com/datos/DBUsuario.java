package com.datos;

import com.seguridad.Encriptar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.entidades.Areas;
import com.entidades.Pares;
import com.entidades.Persona;
import com.entidades.Roles;
import com.entidades.UsuarioArea;
import com.entidades.Usuarios;

public class DBUsuario {
	private String clave;
	Encriptar encrypt = new Encriptar();

	// buscar usuario

	public Usuarios buscarUsuario(String user, String password) {
		Usuarios usuario = null;
		// busqueda del usuario en la bd
		// 1. conectarme a la bd
		DBManager dbm = new DBManager();
		
		Connection con = dbm.getConection();
		Statement sentencia;
		ResultSet resultados;
		try {
			clave = encrypt.hash(password);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		String query = "Select * from tb_usuario as u, tb_persona as p, tb_rol as r where "
				+ "u.persona_id=p.per_id and u.rol_id_usuario=r.rol_id and"
				+ " u.usu_nombre='"
				+ user
				+ "'and u.usu_clave='"
				+ clave
				+ "' order by r.rol_descripcion";

		try {
			sentencia = con.createStatement();
			resultados = sentencia.executeQuery(query);
			// recorrer el resultset
			while (resultados.next()) {
				usuario = new Usuarios();
				usuario.setId(resultados.getInt("usu_id"));
				usuario.setUsuario(resultados.getString("usu_nombre"));
				Persona persona = new Persona();
				persona.setPer_nombre(resultados.getString("per_nombre"));
				persona.setPer_apellido(resultados.getString("per_apellido"));
				persona.setPer_cedula(resultados.getString("per_cedula"));
				persona.setPer_email(resultados.getString("per_email"));
				persona.setPer_direccion(resultados.getString("per_direccion"));
				persona.setPer_telefono(resultados.getString("per_telefono"));
				persona.setPer_celular(resultados.getString("per_celular"));
				persona.setPer_institucion_pertenece(resultados
						.getString("per_institucion_pertenece"));
				persona.setPer_direccion_institucion(resultados
						.getString("per_direccion_institucion"));
				persona.setPer_id(resultados.getInt("per_id"));
				usuario.setId_rol(resultados.getInt("rol_id"));
				usuario.setPersona(persona);
			}

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
		// ---------------------------------
		return usuario;
	}

	public boolean crearUsuario(Usuarios usuario) throws Exception {

		boolean resultado = false;
		// crear un objeto para la conexion
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		// vamos a guardar utilizando transacciones
		try {
			// por defecto es true asi q lo cambiamos
			con.setAutoCommit(false);
			String sql = "INSERT INTO tb_persona (per_nombre, per_apellido, per_cedula, per_email, per_direccion, "
					+ "per_telefono, per_celular, per_institucion_pertenece, per_direccion_institucion, per_estado)"
					+ " VAlUES (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			Persona per = usuario.getPersona();
			pstm.setString(1, per.getPer_nombre().toUpperCase());
			pstm.setString(2, per.getPer_apellido().toUpperCase());
			pstm.setString(3, per.getPer_cedula());
			pstm.setString(4, per.getPer_email());
			pstm.setString(5, per.getPer_direccion().toUpperCase());
			pstm.setString(6, per.getPer_telefono());
			pstm.setString(7, per.getPer_celular());
			pstm.setString(8, per.getPer_institucion_pertenece().toUpperCase());
			pstm.setString(9, per.getPer_direccion_institucion().toUpperCase());

			pstm.setInt(10, 1);
			// ejecutar el prepaaredStatement
			// retorna el numero de filas afectadas o retorna 0 si no se pudo
			// realizar
			int num = pstm.executeUpdate();
			// obtener los datos de la fila insertados
			ResultSet rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				// obtener el IdPersona
				int idPersona = rs.getInt(1);
				sql = "INSERT INTO tb_usuario (usu_nombre, usu_clave, usu_estado, persona_id,rol_id_usuario) VALUES (?,?,?,?,?)";
				pstm = con.prepareStatement(sql);
				pstm.setString(1, usuario.getUsuario().toUpperCase());
				clave = encrypt.hash(usuario.getClave());
				pstm.setString(2, clave);
				pstm.setInt(3, 1);
				pstm.setInt(4, idPersona);
				pstm.setInt(5, 2);

				// ejecutar el Preparedsatatement
				int num_filas_afectadas = pstm.executeUpdate();
				// si no hay error
				con.commit();
				resultado = true;
			} else {
				con.rollback();
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

		return resultado;

	}

	public List<Usuarios> buscarUsuarios(String criterio) {
		System.out.println("buscarUsuarios(String criterio)");
		List<Usuarios> lista = new ArrayList<Usuarios>();
		// objeto sentencia
		Statement sentencia = null;
		// objeto para resultados
		ResultSet resultados = null;
		// obtener la conexion a la base
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();

		String sql = "";
		if (criterio.equals("")) {
			sql = "SELECT * FROM tb_usuario as u, tb_persona as p, tb_rol as r"
					+ " WHERE u.usu_estado=1 and p.per_estado=1 and r.rol_estado=1 and u.persona_id=p.per_id"
					+ " and u.rol_id_usuario=r.rol_id group by p.per_id order by p.per_apellido";
			System.out.println("ddff" + sql);
		} else {
			sql = "SELECT * FROM tb_usuario as u, tb_persona as p, tb_rol as r "
					+ "WHERE u.usu_estado=1 and p.per_estado=1 and r.rol_estado=1 and u.persona_id=p.per_id"
					+ " and u.rol_id_usuario=r.rol_id and (u.usu_nombre like '%"
					+ criterio
					+ "%' or p.per_apellido like '%"
					+ criterio
					+ "%') group by p.per_id order by p.per_apellido";
		}
		try {
			sentencia = con.createStatement();
			resultados = sentencia.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al ejecutar la sentencia");
		}
		// recorrer el resultset
		try {
			Usuarios usuario = null;
			while (resultados.next()) {
				usuario = new Usuarios();
				usuario.setId(resultados.getInt("usu_id"));
				usuario.setUsuario(resultados.getString("usu_nombre"));
				Persona persona = new Persona();
				persona.setPer_id(resultados.getInt("per_id"));
				persona.setPer_nombre(resultados.getString("per_nombre"));
				persona.setPer_apellido(resultados.getString("per_apellido"));
				persona.setPer_cedula(resultados.getString("per_cedula"));
				persona.setPer_email(resultados.getString("per_email"));
				persona.setPer_direccion(resultados.getString("per_direccion"));
				persona.setPer_telefono(resultados.getString("per_telefono"));
				persona.setPer_celular(resultados.getString("per_celular"));
				persona.setPer_institucion_pertenece(resultados
						.getString("per_institucion_pertenece"));
				persona.setPer_direccion_institucion(resultados
						.getString("per_direccion_institucion"));
				usuario.setRol_descripcion(resultados
						.getString("rol_descripcion"));
				usuario.setPersona(persona);
				// agrego usuario con datos cargados desde la base a mi lista de
				// usuarios
				lista.add(usuario);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al recorrer resultset");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return lista;
	}

	public List<Usuarios> buscarUsuariosAutores(String criterio) {
		List<Usuarios> lista = new ArrayList<Usuarios>();
		// objeto sentencia
		Statement sentencia = null;
		// objeto para resultados
		ResultSet resultados = null;
		// obtener la conexion a la base
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();

		String sql = "";
		if (criterio.equals("")) {
			sql = "SELECT * FROM tb_usuario as u, tb_persona as p, tb_rol as r"
					+ " WHERE u.usu_estado=1 and p.per_estado=1 and r.rol_estado=1 and u.persona_id=p.per_id"
					+ " and u.rol_id_usuario=r.rol_id and r.rol_id=2 group by p.per_id order by p.per_apellido";
			System.out.println("ddff" + sql);
		} else {
			sql = "SELECT * FROM tb_usuario as u, tb_persona as p, tb_rol as r "
					+ "WHERE u.usu_estado=1 and p.per_estado=1 and r.rol_estado=1 and u.persona_id=p.per_id"
					+ " and u.rol_id_usuario=r.rol_id and r.rol_id=2 and(u.usu_nombre like '%"
					+ criterio
					+ "%' or p.per_apellido like '%"
					+ criterio
					+ "%') group by p.per_id order by p.per_apellido";
		}
		try {
			sentencia = con.createStatement();
			resultados = sentencia.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al ejecutar la sentencia");
		}
		// recorrer el resultset
		try {
			Usuarios usuario = null;
			while (resultados.next()) {
				usuario = new Usuarios();
				usuario.setId(resultados.getInt("usu_id"));
				usuario.setUsuario(resultados.getString("usu_nombre"));
				Persona persona = new Persona();
				persona.setPer_id(resultados.getInt("per_id"));
				persona.setPer_nombre(resultados.getString("per_nombre"));
				persona.setPer_apellido(resultados.getString("per_apellido"));
				persona.setPer_cedula(resultados.getString("per_cedula"));
				persona.setPer_email(resultados.getString("per_email"));
				persona.setPer_direccion(resultados.getString("per_direccion"));
				persona.setPer_telefono(resultados.getString("per_telefono"));
				persona.setPer_celular(resultados.getString("per_celular"));
				persona.setPer_institucion_pertenece(resultados
						.getString("per_institucion_pertenece"));
				persona.setPer_direccion_institucion(resultados
						.getString("per_direccion_institucion"));
				usuario.setRol_descripcion(resultados
						.getString("rol_descripcion"));
				usuario.setPersona(persona);
				// agrego usuario con datos cargados desde la base a mi lista de
				// usuarios
				lista.add(usuario);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al recorrer resultset");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return lista;
	}

	public List<Usuarios> buscarRolesUsuarios(String criterio) {
		List<Usuarios> lista = new ArrayList<Usuarios>();
		// objeto sentencia
		Statement sentencia = null;
		// objeto para resultados
		ResultSet resultados = null;
		// obtener la conexion a la base
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();

		String sql = "";
		if (criterio.equals("")) {
			sql = "SELECT * FROM tb_usuario as u, tb_persona as p, tb_rol as r"
					+ " WHERE u.usu_estado=1 and p.per_estado=1 and r.rol_estado=1 and u.persona_id=p.per_id"
					+ " and u.rol_id_usuario=r.rol_id group by p.per_id order by p.per_apellido";
			System.out.println("ddff" + sql);
		} else {
			sql = "SELECT * FROM tb_usuario as u, tb_persona as p, tb_rol as r "
					+ "WHERE u.usu_estado=1 and p.per_estado=1 and r.rol_estado=1 and u.persona_id=p.per_id"
					+ " and u.rol_id_usuario=r.rol_id and (u.usu_nombre like '%"
					+ criterio
					+ "%' or p.per_apellido like '%"
					+ criterio
					+ "%') group by p.per_id order by p.per_apellido";
		}
		try {
			sentencia = con.createStatement();
			resultados = sentencia.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al ejecutar la sentencia");
		}
		// recorrer el resultset
		try {
			Usuarios usuario = null;
			while (resultados.next()) {
				usuario = new Usuarios();
				usuario.setId(resultados.getInt("usu_id"));
				usuario.setUsuario(resultados.getString("usu_nombre"));
				Persona persona = new Persona();
				persona.setPer_id(resultados.getInt("per_id"));
				persona.setPer_nombre(resultados.getString("per_nombre"));
				persona.setPer_apellido(resultados.getString("per_apellido"));
				usuario.setRol_descripcion(resultados
						.getString("rol_descripcion"));
				usuario.setPersona(persona);
				// agrego usuario con datos cargados desde la base a mi lista de
				// usuarios
				lista.add(usuario);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error al recorrer resultset");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return lista;
	}

	public boolean actualizarUsuario(Usuarios usuario) throws Exception {

		boolean resultado = false;
		// crear un objeto para la conexion
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		// vamos a guardar utilizando transacciones
		try {
			// por defecto es true asi q lo cambiamos
			con.setAutoCommit(false);
			String sql = "UPDATE tb_persona SET"
					+ " per_nombre=?, per_apellido=?, per_cedula=?, per_email=? , "
					+ "per_direccion=?, per_telefono=?, per_celular=?, per_institucion_pertenece=?, per_direccion_institucion=?"
					+ " where per_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			Persona per = usuario.getPersona();
			pstm.setString(1, per.getPer_nombre().toUpperCase());
			pstm.setString(2, per.getPer_apellido().toUpperCase());
			pstm.setString(3, per.getPer_cedula());
			pstm.setString(4, per.getPer_email());
			pstm.setString(5, per.getPer_direccion().toUpperCase());
			pstm.setString(6, per.getPer_telefono());
			pstm.setString(7, per.getPer_celular());
			pstm.setString(8, per.getPer_institucion_pertenece().toUpperCase());
			pstm.setString(9, per.getPer_direccion_institucion().toUpperCase());

			pstm.setInt(10, per.getPer_id());
			// ejecutar el prepaaredStatement
			// retorna el numero de filas afectadas o retorna 0 si no se pudo
			// realizar
			int num = pstm.executeUpdate();
			sql = "UPDATE tb_usuario SET usu_nombre=?, usu_clave=?"
					+ "WHERE usu_id=?";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, usuario.getUsuario().toUpperCase());
			clave = encrypt.hash(usuario.getClave());
			pstm.setString(2, clave);
			// pstm.setInt(3, usuario.getId_rol());
			pstm.setInt(3, usuario.getId());
			// ejecutar el Preparedsatatement
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

	public boolean eliminarUsuario(Usuarios usuario) {
		boolean result = false;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {
			con.setAutoCommit(false);
			String sql = "UPDATE tb_persona SET per_estado=? WHERE per_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			Persona per = usuario.getPersona();
			pstm.setInt(1, 0);
			pstm.setInt(2, per.getPer_id());
			int num = pstm.executeUpdate();
			sql = "UPDATE tb_usuario SET usu_estado=? WHERE usu_id=?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, 0);
			pstm.setInt(2, usuario.getId());
			int num_filas_afectadas = pstm.executeUpdate();
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

	public List<Usuarios> buscarRoles() {
		// objeto a retornar es una lista
		List<Usuarios> lista = new ArrayList<Usuarios>();
		Usuarios rol = null;
		// objeto conexion
		Connection con = null;
		Statement sentencia = null;
		ResultSet resultados = null;
		DBManager dbm = new DBManager();
		con = dbm.getConection();
		try {
			sentencia = con.createStatement();
			// String sql="Select * from tb_rol where rol_estado=1";
			String sql = "Select * from tb_rol";
			resultados = sentencia.executeQuery(sql);
			while (resultados.next()) {

				rol = new Usuarios();
				rol.setId_rol(resultados.getInt("rol_id"));
				rol.setRol_descripcion(resultados.getString("rol_descripcion"));
				// agregar actividades a mi lista
				String id = null;
				id = Integer.toString(rol.getId_rol());
				lista.add(rol);

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

	public boolean modificarClave(String password, Integer id) throws Exception {
		boolean result = false;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		Statement sentencia;
		clave = encrypt.hash(password);
		try {
			sentencia = con.createStatement();
			sentencia.executeUpdate("UPDATE tb_usuario SET usu_clave='" + clave
					+ "' where usu_id='" + id + "'");
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

	public boolean verificarClave(String user, String password) {
		boolean result = false;
		Usuarios usuario = null;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		Statement sentencia;
		ResultSet resultados;
		try {
			clave = encrypt.hash(password);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		String query = "Select * from tb_usuario as u, tb_persona as p, tb_rol as r where "
				+ "u.persona_id=p.per_id and u.rol_id_usuario=r.rol_id and"
				+ " u.usu_nombre='" + user + "'and u.usu_clave='" + clave + "'";
		// 202cb962ac59075b964b07152d234b70
		try {
			sentencia = con.createStatement();
			resultados = sentencia.executeQuery(query);
			while (resultados.next()) {
				usuario = new Usuarios();
				usuario.setId(resultados.getInt("usu_id"));
			}
			// System.out.println(resultados);
			if (usuario != null) {
				result = true;
			} else {
				result = false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//
			try {
				// cerrar la conexion
				con.close();
			} catch (SQLException e) {
				// todo auto generated cath block
				e.printStackTrace();
			}
		}
		return result;

	}

	public boolean cambiarRol(int idUsu, int idRol) {
		boolean result = false;
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		Statement sentencia;
		try {
			sentencia = con.createStatement();
			sentencia.executeUpdate("UPDATE tb_usuario SET rol_id_usuario='"
					+ idRol + "' where usu_id='" + idUsu + "'");
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

	// cargar evaluadores

	public List<Pares> buscarEvaluador(int area) {
		List<Pares> lista = new ArrayList<Pares>();
		// objeto sentencia
		Statement sentencia = null;
		// objeto para resultados
		ResultSet resultados = null;
		Pares pares = null;
		System.out.println("id area" + area);
		// obtener la conexion a la base
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {

			sentencia = con.createStatement();
			// String sql="Select * from tb_rol where rol_estado=1";
			String sql = "Select p.per_id, p.per_nombre, p.per_apellido, p.per_email from tb_persona as p, "
					+ "tb_usuario as u, tb_persona_area as pa where p.per_id=u.persona_id "
					+ "and u.rol_id_usuario=3 and pa.area_id='"
					+ area
					+ "' and pa.per_id=u.persona_id";
			System.out.println("el sql con evaluador" + sql);
			resultados = sentencia.executeQuery(sql);
			while (resultados.next()) {
				pares = new Pares();
				pares.setPersonas_id(resultados.getInt("per_id"));
				System.out.println("id persona" + resultados.getInt("per_id"));
				pares.setPer_nombre(resultados.getString("per_nombre"));
				pares.setPer_apellido(resultados.getString("per_apellido"));
				pares.setPer_email(resultados.getString("per_email"));
				System.out.println("email   :" + pares.getPer_email());
				// agrego usuario con datos cargados desde la base a mi lista de
				// usuarios
				lista.add(pares);
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

	public List<Pares> buscarPar(int area, int idarticulo, int idpersona) {
		List<Pares> listanueva = new ArrayList<Pares>();
		Statement sentencia = null;
		// objeto para resultados
		ResultSet resultados = null;
		Pares pares = null;
		pares = new Pares();
		;

		// obtener la conexion a la base
		DBManager dbm = new DBManager();
		Connection con = dbm.getConection();
		try {

			sentencia = con.createStatement();
			// String sql="Select * from tb_rol where rol_estado=1";
			String sql = "Select p.per_id, p.per_nombre, p.per_apellido, p.per_email from tb_persona as p, "
					+ "tb_usuario as u, tb_persona_area as pa, tb_pares as par where p.per_id=u.persona_id "
					+ "and u.rol_id_usuario=3 and pa.area_id= '"
					+ area
					+ "' and pa.per_id=u.persona_id and "
					+ "(par.personas_id <> p.per_id and par.articulos_id='"
					+ idarticulo + "')";

			System.out.println("el sql con evaluador proximo a buscar " + sql);
			resultados = sentencia.executeQuery(sql);
			if (resultados.next()) {

				pares.setPersonas_id(resultados.getInt("per_id"));
				System.out.println("id persona" + resultados.getInt("per_id"));
				pares.setPer_nombre(resultados.getString("per_nombre"));
				pares.setPer_apellido(resultados.getString("per_apellido"));
				pares.setPer_email(resultados.getString("per_email"));
				System.out.println("email" + pares.getPer_email());
				// agrego usuario con datos cargados desde la base a mi lista de
				// usuarios
				listanueva.add(pares);

			} else {
				listanueva = null;
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
		return listanueva;

	}
	public boolean buscaNombreUser(String name) {
		// objeto a retornar es una lista
		//List<Usuarios> lista = new ArrayList<Usuarios>();
		//Usuarios rol = null;
		// objeto conexion
		boolean bandera=false;
		Connection con = null;
		Statement sentencia = null;
		ResultSet resultados = null;
		DBManager dbm = new DBManager();
		con = dbm.getConection();
		try {
			String nombre=" ";
			sentencia = con.createStatement();
			// String sql="Select * from tb_rol where rol_estado=1";
			String sql = "Select * from tb_usuario where usu_nombre='"+name+"'";
			resultados = sentencia.executeQuery(sql);
			while (resultados.next()) {	
				System.out.println("nombre user: "+resultados.getString("usu_nombre"));
				nombre=resultados.getString("usu_nombre");
				}
			if (nombre.equals(name)){
				bandera=false;
			}
			else{
				bandera=true;
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
		return bandera;
	}
	
}
