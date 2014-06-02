package com.seguridad;

import java.security.MessageDigest;

public class Encriptar {
	public static String hash (String clear) throws Exception { 

		MessageDigest md = MessageDigest.getInstance("MD5"); //$NON-NLS-1$ 
		byte[] b = md.digest(clear.getBytes()); 

		int size = b.length; 
		StringBuffer h = new StringBuffer(size); 
		for (int i = 0; i < size; i++) { 

		int u = b[i] & 255; // unsigned conversion 

		if (u < 16) { 

		h.append("0" + Integer.toHexString(u)); //$NON-NLS-1$ 
		} else { 

		h.append(Integer.toHexString(u)); 
		} 
		} 
		return h.toString(); 
		}
}

