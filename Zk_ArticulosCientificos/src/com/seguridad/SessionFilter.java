package com.seguridad;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.entidades.Usuarios;

@WebFilter("/SessionFilter")
public class SessionFilter implements Filter {
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
				// utilizar request para poder leer la sescion
		HttpServletRequest httpr=(HttpServletRequest)request;
		HttpSession session=httpr.getSession();
       //obtener el objeto almacenado en la session	
		Session session1 = Sessions.getCurrent();
		Usuarios usuario=(Usuarios)session.getAttribute("User");
		if(usuario!=null)
		{
		int idrol =usuario.getId_rol();
		System.out.println("idrol"+idrol);
		String path=httpr.getServletPath();
		if(idrol==2 || usuario!=null || path.equals("/Usuarios/nuevoUsuario.zul")||path.equals("/Usuarios/verUsuario.zul") 
				||path.equals("/Usuarios/modificarClave.zul")||path.equals("/Articulo/Listar Articulo.zul")||path.equals("/Articulo/Subir Articulo.zul")
				||path.equals("/Notificaciones/enviarNotificaciones.zul")||path.equals("/Notificaciones/listaNotificaciones.zul")){
			//pass the request along the filter chain la ultima llamada a chain.dofilter se encarga
			//de llamar al metodo service del servlet
			chain.doFilter(request, response);
                  	}else{if(idrol==3 || usuario!=null || path.equals("/Usuarios/nuevoUsuario.zul")||path.equals("/Usuarios/verUsuario.zul") 
            				||path.equals("/Usuarios/modificarClave.zul")||path.equals("/Articulo/Listar Articulo.zul")
            				||path.equals("/Articulo/Subir Articulo.zul") ||path.equals("/Articulo/Evaluacion.zul")
            				||path.equals("/Notificaciones/enviarNotificaciones.zul")||path.equals("/Notificaciones/listaNotificaciones.zul")){
                  		chain.doFilter(request, response);
                  	}else{
			                 //usuario no existe en sesion
		                      System.out.println("usuario no auntenticada");
		                      httpr.getServletContext().getRequestDispatcher("/index-login.zul").forward(httpr,(HttpServletResponse)response);	
		                    }
                  	}
		}
		String path1=httpr.getServletPath();
		//comprobar si usuario existe en la session
		//path.equals("/Usuarios/listaUsuarios.zul")||
		if(path1.equals("/Usuarios/nuevoUsuario.zul")|| usuario!=null){
			//pass the request along the filter chain la ultima llamada a chain.dofilter se encarga
			//de llamar al metodo service del servlet
			chain.doFilter(request, response);
                  	}else{
			                 //usuario no existe en sesion
		                      System.out.println("usuario no auntenticada");
		                      httpr.getServletContext().getRequestDispatcher("/index-login.zul").forward(httpr,(HttpServletResponse)response);	
		                    }
	      
		
	}
		
		
		// pass the request along the filter chain
		//la ultima llamada a chain dofilter se encarga de llamar al metodo service del servlet
		
		
	
		
		// pass the request along the filter chain
		//la ultima llamada a chain dofilter se encarga de llamar al metodo service del servlet
	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
