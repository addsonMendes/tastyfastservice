package br.com.tastyfast.tastyfastservice.config;

import java.security.MessageDigest;

public class MD5 {

	public String criptografa(String senha){
		try{
			MessageDigest algoritmo = MessageDigest.getInstance("MD5");
			byte valorMD5[] = algoritmo.digest(senha.getBytes("UTF-8"));
			
			StringBuffer sb = new StringBuffer();
            for (byte b : valorMD5){
                   sb.append(Integer.toHexString((b & 0xFF) |
                   0x100).substring(1,3));
            }
            return sb.toString();
            
		} catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Problemas para criptografar...\n" + ex.getMessage());
			return null;
		}

	}
	
	/*public static void main(String[] args) {
		String senha = "1";
		String novaSenha = new MD5().criptografa(senha);
		System.out.println(novaSenha);
	}*/
}
