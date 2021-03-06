package com.SubirDescargarArchivos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ejb.NoMoreTimeoutsException;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.entidades.Usuarios;

/**
 * Util class to upload files
 * 
 * @author <a href="mailto:jmcp18@gmail.com">Jose Manuel Campechano P.</a>
 * @version 1.0
 */

public class Util {

	public static final String separator = System.getProperty("file.separator");// Get
																				// de
	public static String ruta; // system
	public String nombreArticulo; // separator
	public static String complemento_nombre;

	public static boolean uploadFile(Media media) {

		return saveFile(media, getPath());
	}

	// Gets the path of the current web application
	public static String getPath() {
		// return
		// Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator)+"uploads"+separator;
		
		Session session = Sessions.getCurrent();
		Usuarios usua = (Usuarios) session.getAttribute("User");
		int idUsuario = usua.getId();
		String nombre=usua.getPersona().getPer_apellido();
		complemento_nombre=Integer.toString(idUsuario);
		
		ruta = "/Uploads/Articulo"+complemento_nombre+nombre;
		 System.out.println(" ruta: "+ruta);
		File directorio = new File(ruta+separator);
		directorio.mkdir();
		return Executions.getCurrent().getDesktop().getCurrentDirectory()
				+ ruta + separator;
	}
	
	
	// save file
	public static boolean saveFile(Media media, String path) {
		boolean uploaded = false;

		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			InputStream ins = media.getStreamData();

			in = new BufferedInputStream(ins);

			String fileName = media.getName();

			File arc = new File(path + fileName);

			OutputStream aout = new FileOutputStream(arc);
			out = new BufferedOutputStream(aout);
			System.out.println("Nombre: " + media.getName() + " :StreamData: ");
			// nombreArticulo=media.getName();
			byte buffer[] = new byte[1024];
			int ch = in.read(buffer);
			while (ch != -1) {
				out.write(buffer, 0, ch);
				ch = in.read(buffer);
			}
			uploaded = true;
		} catch (IOException ie) {
			throw new RuntimeException(ie);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return uploaded;
	}
	
	public static boolean uploadFileObservacion(Media media) {

		return saveFileObservacion(media, getPathObservacion());
	}
	public static String getPathObservacion() {
		// return
		// Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator)+"uploads"+separator;
		
		Session session = Sessions.getCurrent();
		Usuarios usua = (Usuarios) session.getAttribute("User");
		int idUsuario = usua.getId();
		String nombre=usua.getPersona().getPer_apellido();
		complemento_nombre=Integer.toString(idUsuario);
		
		ruta = "/Uploads/Articulo"+complemento_nombre+"Obsercacion"+nombre;
		 System.out.println(" ruta: "+ruta);
		File directorio = new File(ruta+separator);
		directorio.mkdir();
		return Executions.getCurrent().getDesktop().getCurrentDirectory()
				+ ruta + separator;
	}
	public static boolean saveFileObservacion(Media media, String path) {
		boolean uploaded = false;

		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			InputStream ins = media.getStreamData();

			in = new BufferedInputStream(ins);

			String fileName = media.getName();

			File arc = new File(path + fileName);

			OutputStream aout = new FileOutputStream(arc);
			out = new BufferedOutputStream(aout);
			System.out.println("Nombre: " + media.getName() + " :StreamData: ");
			// nombreArticulo=media.getName();
			byte buffer[] = new byte[1024];
			int ch = in.read(buffer);
			while (ch != -1) {
				out.write(buffer, 0, ch);
				ch = in.read(buffer);
			}
			uploaded = true;
		} catch (IOException ie) {
			throw new RuntimeException(ie);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return uploaded;
	}
	
}
