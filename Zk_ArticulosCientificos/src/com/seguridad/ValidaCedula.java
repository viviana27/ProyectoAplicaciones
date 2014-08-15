package com.seguridad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.org.apache.bcel.internal.generic.RETURN;

public class ValidaCedula {
	private static final int num_provincias = 24;
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static Boolean validacionCedula(String cedula) {
		// verifica que los dos primeros d�gitos correspondan a un valor entre 1
		// y NUMERO_DE_PROVINCIAS
		if (cedula.trim().length() < 10) {
			return false;
		}

		int prov = Integer.parseInt(cedula.substring(0, 2));

		if (!((prov > 0) && (prov <= num_provincias))) {
			// addError("La c�dula ingresada no es v�lida");
			System.out.println("Error: cedula ingresada mal");
			return false;
		}

		// verifica que el �ltimo d�gito de la c�dula sea v�lido
		int[] d = new int[10];
		// Asignamos el string a un array
		for (int i = 0; i < d.length; i++) {
			d[i] = Integer.parseInt(cedula.charAt(i) + "");
		}

		int imp = 0;
		int par = 0;

		// sumamos los duplos de posici�n impar
		for (int i = 0; i < d.length; i += 2) {
			d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
			imp += d[i];
		}

		// sumamos los digitos de posici�n par
		for (int i = 1; i < (d.length - 1); i += 2) {
			par += d[i];
		}

		// Sumamos los dos resultados
		int suma = imp + par;

		// Restamos de la decena superior
		int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1)
				+ "0")
				- suma;

		// Si es diez el d�cimo d�gito es cero
		d10 = (d10 == 10) ? 0 : d10;

		// si el d�cimo d�gito calculado es igual al digitado la c�dula es
		// correcta
		if (d10 == d[9]) {
			return true;
		} else {
			// addError("La c�dula ingresada no es v�lida");
			return false;
		}
	}

	public static boolean validateEmail(String email) {
		// Compiles the given regular expression into a pattern.
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);

		// Match the given input against this pattern
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

	public static boolean ValidarNumeros(String numeros) {
		try {
			Integer.parseInt(numeros);
			return true;
		} catch (Exception e) {
			// TODO: handle exception3
			return false;
		}
	}

	public static boolean ValidarContrase�a(String contrase�a) {
		boolean bandera = false;
		int mayuscula = 0;int 	especiales=0;int numeros=0;
		try {
			if (contrase�a.length() > 7) {
				bandera = true;
				StringBuffer nuevaCadena = new StringBuffer();
				for (int i = 0; i < contrase�a.length(); i++) {
					if (Character.isUpperCase(contrase�a.charAt(i))) {
						mayuscula = mayuscula + 1;
					}
					if ((contrase�a.codePointAt(i)>=1 && contrase�a.codePointAt(i)<=47)|| (contrase�a.codePointAt(i)>=91 && contrase�a.codePointAt(i)<=96) || (contrase�a.codePointAt(i)>=123 && contrase�a.codePointAt(i)<=126))  {
						especiales=especiales+1;
					}
					if ((contrase�a.codePointAt(i)>=48 && contrase�a.codePointAt(i)<=57)){
						numeros=numeros+1;
					}
					
				}
				if (mayuscula != 0) {
					bandera = true;
				} else {
					bandera = false;
					return bandera;
				}
				if (especiales != 0) {
					bandera = true;
				} else {
					bandera = false;
					return bandera;
				}
				if (numeros != 0) {
					bandera = true;
				} else {
					bandera = false;
					return bandera;
				}
			} else {
				bandera = false;
				return bandera;
			}

		} catch (Exception e) {
			// TODO: handle exception
			bandera = false;
		} finally {
			return bandera;
		}

	}
}
