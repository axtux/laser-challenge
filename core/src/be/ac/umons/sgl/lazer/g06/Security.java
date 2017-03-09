package be.ac.umons.sgl.lazer.g06;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
	/*
	 * @return lowercase string representation of md5 hash from data string
	 */
	public static String md5(String data) {
		MessageDigest md5;
		byte[] hash;
		try {
			md5 = MessageDigest.getInstance("md5");
			hash = md5.digest(data.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
		return javax.xml.bind.DatatypeConverter.printHexBinary(hash).toLowerCase();
	}
}